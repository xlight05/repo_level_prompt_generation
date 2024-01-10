
package pl.edu.agh.iosr.ftpserverremote.data;

/**
 *
 * @author Tomasz Jadczyk
 */
public class FileData extends ServerData{

    public enum FileType {UPLOADED, DOWNLOADED, REMOVED, DIRCREATED, DIRREMOVED}
    
    private String fileName;
    private String userName;
    private String date;
    private FileType fileType;
    
    public FileData(String fileName, String userName, String date, FileType fileType) {
        this.fileName = fileName;
        this.userName = userName;
        this.date = date;
        this.fileType = fileType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
    
    
    
}
