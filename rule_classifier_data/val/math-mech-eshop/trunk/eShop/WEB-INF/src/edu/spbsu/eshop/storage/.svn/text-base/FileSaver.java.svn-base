package edu.spbsu.eshop.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import edu.spbsu.eshop.storage.exceptions.FileRecivingException;

public class FileSaver {

    public static void saveFile(UploadedFile upFile, String name, String subdir)
	    throws IOException, FileRecivingException {
	File file = createFile(name, Storage.IMAGES_SUBDIR);
	writeFileToFile(upFile, file);
    }

    public static boolean removeFile(String filename, String subdir) {
	File file = new File(Storage.UPLOAD_DIR + subdir + File.pathSeparator
		+ filename);
	return file.delete();
    }

    public static String savePhoto(UploadedFile photo) throws IOException,
	    FileRecivingException {

	String filename = reciveNewImageName();

	String extension = photo.getContentType().split("/")[1];
	String fullname = filename + "." + extension;

	saveFile(photo, fullname, Storage.IMAGES_SUBDIR);

	return fullname;

    }

    private static void writeFileToFile(UploadedFile upFile, File file)
	    throws IOException {
	byte[] arr = IOUtils.toByteArray(upFile.getInputStream());
	FileUtils.writeByteArrayToFile(file, arr);
    }

    private static File createFile(String fullname, String subdir)
	    throws IOException, FileRecivingException {
	String filepath = Storage.UPLOAD_DIR + subdir + File.pathSeparator
		+ fullname;
	File file = new File(filepath);
	if (file.exists()) {
	    throw new FileRecivingException("duplicated file:" + fullname);
	}
	file.createNewFile();
	return file;
    }

    private static String reciveNewImageName() throws IOException,
	    FileRecivingException {
	Long num;

	File file = new File(Storage.UPLOAD_DIR + Storage.IMAGES_SUBDIR
		+ File.pathSeparator + "counter");
	try {
	    String fileString = FileUtils.readFileToString(file);
	    num = Long.parseLong(fileString);

	} catch (FileNotFoundException e) {
	    if (!file.createNewFile())
		throw new FileRecivingException("can't create counter");
	    num = 0l;
	}

	// increase count
	FileUtils.writeStringToFile(file, Long.toString(num + 1));

	return Storage.PRODUCTS_PHOTO_PREFIX + num;
    }
}
