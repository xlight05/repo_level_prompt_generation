package framework.struts.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import framework.struts.util.MailSendUtil;

/**
 * @struts:form name="fileForm"
 *
 */
public class FileForm extends ActionForm {
	/**
	 * 
	 */
	//	logging
	final Log log = LogFactory.getLog(this.getClass());
	private static final long serialVersionUID = 8134582362472003420L;
	// you can upload 4 files.
	private FormFile[] fileList = new FormFile[4];
	private MailForm mailForm=new MailForm();
	private int totalSize;
	private boolean[] fileIndex = new boolean[4];
	
	
	public FormFile[] getFileList() {
		return fileList;
	}
	public void setFileList(FormFile[] fileList) {
		this.fileList = fileList;
	}
	public MailForm getMailForm() {
		return mailForm;
	}
	public void setMailForm(MailForm mailForm) {
		this.mailForm = mailForm;
	}
	
	
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public boolean[] getFileIndex() {
		return fileIndex;
	}
	public void setFileIndex(boolean[] fileIndex) {
		this.fileIndex = fileIndex;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return "fileList.size="+fileList.length+
		 ", fileIndex.size="+fileIndex.length;
	}
			
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
	ActionErrors errors = new ActionErrors();
	int sizeOfFiles=0;
	
	// get file size
	for (int i = 0; i < fileList.length; i++) {
		if (fileList[i]!=null) {
			sizeOfFiles += fileList[i].getFileSize();
		}
	}//end for
	this.totalSize= sizeOfFiles;
	// check whether upload-file size is over than a indicated size
	if (sizeOfFiles > MailSendUtil.maxUploadLength) {
		if (log.isDebugEnabled()) {
			log.debug("upload max length exceeded.");
		}
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.file.maxLengthExceeded"));
	}
	return errors;
	}
}
