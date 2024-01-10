package framework.struts.model;

import java.io.Serializable;

import framework.struts.form.MailForm;


public class FileInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -972318116201018335L;
	
	private MailForm mailForm=null;
	// original file name
	private String fileName=null;
	// save file name
	private String tempFileName=null;
	private String contentType=null;
	private int fileSize=0;
	// full path name
	private String pathName=null;
	
	
	public FileInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public MailForm getMailForm() {
		return mailForm;
	}

	public void setMailForm(MailForm mailForm) {
		this.mailForm = mailForm;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getTempFileName() {
		return tempFileName;
	}
	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

		
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return "fileSize="+fileSize+
		", fileName="+fileName+
		", tempFileName="+tempFileName+
		", fileSize="+fileSize+
		", pathName="+pathName;
	}
}
