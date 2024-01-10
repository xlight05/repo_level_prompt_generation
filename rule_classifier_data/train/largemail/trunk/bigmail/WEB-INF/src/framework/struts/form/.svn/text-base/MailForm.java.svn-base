package framework.struts.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import framework.struts.model.FileInfo;

/**
 * @struts:form name="mailForm"
 * 
 */
public class MailForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String fromAddress = "";

	private String title = "";

	private String toAddress = "";

	private String content = "";

	private String[] attachedFiles;

	private String attachedFile;

	private List fileList = null;

	private int totalSize = 0;

	private int fileNo = 0;

	private boolean resetFlg = false;

	public MailForm() {
		// TODO Auto-generated constructor stub
		fileList = new ArrayList();

	}

	// キャンセルをクリックした場合セッション処理
	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}

	public String getContent() {
		return content;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getTitle() {
		return title;
	}

	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @struts.validator type="required"
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @struts.validator type="email"
	 * 
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @struts.validator type="required"
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @struts.validator type="email"
	 * 
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return "From=" + fromAddress + ", to=" + toAddress + ", title=" + title
				+ ", content=" + content + ", attachedFile" + fileList;
	}

	public void addFileInfo(FileInfo fileInfo) {
		fileInfo.setMailForm(this);
		fileList.add(fileInfo);
	}

	public List getFileList() {
		return fileList;
	}

	public String[] getAttachedFiles() {
		return attachedFiles;
	}

	public void setAttachedFiles(String attachedFiles[]) {
		this.attachedFiles = attachedFiles;
	}

	public String getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public boolean isResetFlg() {
		return resetFlg;
	}

	public void setResetFlg(boolean resetFlg) {
		this.resetFlg = resetFlg;
	}

}
