package framework.struts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import framework.struts.model.FileInfo;

public class FileUploadUtil {
	final static Log log = LogFactory.getLog(framework.struts.util.FileUploadUtil.class);
	public static FileInfo doFileUpload(FormFile file) throws IOException,FileNotFoundException {
		
		InputStream is = file.getInputStream();
				
		//upload path
		String path = MailSendUtil.fileUploadDir;
		
		String fileName = String.valueOf(System.currentTimeMillis());
		
		OutputStream fos = new FileOutputStream(path+File.separator+fileName);
		int bytesRead=0;
		
		byte[] buffer = new byte[8192];
				
		while ((bytesRead = is.read(buffer, 0, 8192))!=-1) {
			fos.write(buffer, 0, bytesRead);
					
		}
				
		fos.close();
		is.close();
		
		//save FileInfo
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(FileUtils.getBaseFileName(file.getFileName()));
		fileInfo.setTempFileName(fileName);
		fileInfo.setFileSize(file.getFileSize());
		fileInfo.setContentType(file.getContentType());
		fileInfo.setPathName(file.getFileName());
		
		return fileInfo;
	}
		
}
