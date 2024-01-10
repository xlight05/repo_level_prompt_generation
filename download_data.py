import os
import requests
from zipfile import ZipFile

def download_and_extract(url, destination_folder):
    # Download the zip file
    response = requests.get(url)
    
    if response.status_code == 200:
        # Create the destination folder if it doesn't exist
        os.makedirs(destination_folder, exist_ok=True)
        
        # Save the zip file
        zip_file_path = os.path.join(destination_folder, 'temp.zip')
        with open(zip_file_path, 'wb') as zip_file:
            zip_file.write(response.content)
        
        # Extract the contents of the zip file
        with ZipFile(zip_file_path, 'r') as zip_ref:
            zip_ref.extractall(destination_folder)
        
        # Remove the temporary zip file
        os.remove(zip_file_path)
        
        print(f"Successfully downloaded and extracted {url} to {destination_folder}")
    else:
        print(f"Failed to download {url}")

def main():
    # Read the projects.txt file
    with open('projects.txt', 'r') as file:
        lines = file.readlines()

    # Download and extract each zip file
    for line in lines:
        project_url = line.strip()
        download_and_extract(project_url, 'gcode-data')

if __name__ == "__main__":
    main()
