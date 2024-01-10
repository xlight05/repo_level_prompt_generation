package framework.struts.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;


import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.upload.FormFile;

import framework.struts.form.FileForm;
import framework.struts.form.MailForm;
import framework.struts.model.FileInfo;
import framework.struts.util.FileUploadUtil;
import framework.struts.util.FileUtils;
import framework.struts.util.MailSendUtil;
import framework.struts.util.DateUtil;

/**
 * @struts.action
 * 			name="fileForm"
 * 			path="/processUpload"
 * 			input="/progressBar.jsp"
 * 			parameter="action"
 * 			scope="request"
 * 			validate="true"
 * @struts.action-forward
 * 			name="Success"
 * 			path="/progressBar.jsp"
 * 			redirect="false"
 * 
 */
public class CheckoutUploadAction extends LookupDispatchAction {

	/* (non-Javadoc)
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 */
//	logging
	final Log log = LogFactory.getLog(this.getClass());
	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("button.send", "upload");
		map.put("button.delete.file", "delete");
		
		return map;
	}
	
	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		
		// check upload directory whether the span of old files is expired or not. 
		int uploadFiles=0;
		File oldFile = new File(MailSendUtil.fileUploadDir);
		uploadFiles = checkUploadDir(oldFile);
		if(log.isDebugEnabled()){
			log.debug("Checking "+uploadFiles+" old files.");
		}
        
		// check upload directory whether the size of directory overflows or not.
		long fileSize = FileUtils.getSizeOfDir(oldFile);
		if(fileSize>MailSendUtil.maxUploadSize){
			// post a mail to Administrator
			//read a configuration infomation from properties.xml
			Properties props=MailSendUtil.loadConfigXML(request, "properties.xml");
			
			String mailSmtpHost = props.getProperty("mailSmtpHost");
			Properties propsMail = new Properties();
			propsMail.put("mail.smtp.host",mailSmtpHost);
			
			//get a mail session
			Session mailSession = Session.getDefaultInstance(propsMail);
			String mailFrom = request.getRemoteAddr();
			String mailTo = props.getProperty("adminMailAddress");
			String title = "[Bigmail] Alert Message!";
			String message ="Disk space is not enough. Please backup data or remove old files.";
			//post a mail
			try {
				MailSendUtil.doMailTransfer(mailSession, mailFrom, mailTo, title, message);
			} catch (SendFailedException sfe) {
				// TODO: logging 
				if(log.isErrorEnabled())
					log.error("Failed to send a mail",sfe);
			}catch( MessagingException me){
				if(log.isErrorEnabled())
					log.error("invalid message",me);
			}
			//	write logging messages
			if (log.isInfoEnabled()) {
				log.info(
					"SMTP HOST: "+mailSmtpHost+ " accepts a message from '"+mailFrom+
					"'\r\n Alert Message: "+message);
							
			}
		}//end if
				
		//File upload
		MailForm bigmail = processFileUpload(form, session);
		
		//clear temp files
		int tempFiles=0;
		File file = new File(MailSendUtil.tempFileDir);
		tempFiles = FileUtils.deleteFiles(file);
		if(log.isDebugEnabled()){
			log.debug("Deleting "+tempFiles+" temporary files.");
		}							
		/*
		 * 1. write attached files
		 * 2. save attached files in session scope
		 */
		updateMessage(bigmail);
		
		session.setAttribute("attached.file.message", bigmail);				
		
		return mapping.findForward("Success");
	}
	/*	
	 *	1. get selected file indexes and those file info
	 *	2. delete selected files from temporary directory
	 *	3. delete selected files form upload dir
	 *	4. refresh a session attribute of attached.file.message
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		FileForm fileForm = (FileForm)form;
				
		HttpSession session = request.getSession(false);
		MailForm mailForm = (MailForm)session.getAttribute("attached.file.message");
		
		if(mailForm !=null){
			List fileList = mailForm.getFileList();
			int totalSize = mailForm.getTotalSize();
			if(fileList !=null && fileList.size()!=0){
				//添付ファイルの数を覚える。
				String[] str = mailForm.getAttachedFiles();

				int fileNo = str.length;
				boolean[] fileIndex=null;
				boolean isDeleted=false;
				//添付ファイルの位置を表す変数����Ʈũ�Ⱑ � ����
				int j=0;
				
				for (int i = 0; i < fileNo; i++) {
					fileIndex = fileForm.getFileIndex();
					
					if(fileIndex[i]){
						
						FileInfo fileInfo = (FileInfo)fileList.get(j);
						isDeleted = FileUtils.delete(MailSendUtil.fileUploadDir+File.separator+fileInfo.getTempFileName());
						fileList.remove(j);
						//update total size of attached files.
						totalSize -= fileInfo.getFileSize();
						mailForm.setTotalSize(totalSize);
						
						//update String[] attachedFiles
						
						for(int k=fileNo-1; k>=0; k--){
							if(k==(fileNo-1)){
								str[k]="";
							}else{
								str[k]= str[k+1];
							}
						}
						
						if(log.isDebugEnabled()){
							log.debug(fileInfo.getFileName()+" is deleted : result="+isDeleted);
						}
					}else{
						j++;
					}
				}//end for
				
			}//end if
			
			//update a session attribute of attached.file.message
			updateMessage(mailForm);
		}
		
		session.setAttribute("attached.file.message", mailForm);
		
		
		return mapping.findForward("Success");
	}

	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward("Success");

	}

	/**
	 * @author Administrator
	 *	1. file uploading
	 *	2. add FileInfo to List.
	 *  @param FileForm
	 *  @return MailForm
	 */ 
	private MailForm processFileUpload(ActionForm form, HttpSession session)
		throws FileNotFoundException, IOException{
		FileForm fileForm = (FileForm)form;
		FormFile[] fileList = fileForm.getFileList();	
		int totalSize=0;
		// get MailForm from session
		MailForm mailForm = (MailForm)session.getAttribute("attached.file.message");
		if(mailForm==null){
			mailForm = new MailForm();
			totalSize = fileForm.getTotalSize();
		}else{
			totalSize = mailForm.getTotalSize()+fileForm.getTotalSize();
		}
		
		if(log.isDebugEnabled()){
			log.debug("size of upload files="+totalSize);
		}
		mailForm.setTotalSize(totalSize);
		for (int i = 0; i < fileList.length; i++) {
			FormFile file = fileList[i];
			
			if(log.isDebugEnabled()){
				log.debug("Inside copying temp file to upload dir");
			}
			if (file.getFileSize()>0) {
				
				FileInfo fileInfo = FileUploadUtil.doFileUpload(file);
				
				// add FileInfo to List and connect MailForm with FileInfo
				mailForm.addFileInfo(fileInfo);
				if(log.isDebugEnabled()){
					log.debug("Leaving a task of copying :"+fileInfo.getFileName());
				}
			}
			
		}
							
		return mailForm;
		
	}
	
	private void updateMessage(MailForm mailForm) {
		List fileList = mailForm.getFileList();
		String[] str = mailForm.getAttachedFiles();
		int length = 0;
		// the length of String array 'attachedFiles'
		if(str==null){
			str = new String[]{"No Attached Files"};
		}
		
		if(log.isDebugEnabled()){
			log.debug("String of attached files="+length+", upload file="+fileList.size()+", str="+str);
		}
		
		if(fileList.size()!=0){
			length = fileList.size();
			str = new String[length];
			
			int i=0;
			for (Iterator iterator = fileList.iterator(); iterator.hasNext();) {
				FileInfo fileInfo = (FileInfo) iterator.next();
				str[i] = fileInfo.getFileName();
				i++;
				if(log.isDebugEnabled()){
					log.debug("add file to str[]="+fileInfo.getPathName());
				}
			}//end for
			// save fileNo
			mailForm.setFileNo(length);
		}//end if~else
		mailForm.setAttachedFiles(str);
	}
	
	/*
	 * 	clear expired files in upload directory
	 *  @param path: upload file directory
	 *  @return result : number of upload files deleted
	 */
	private int checkUploadDir(File infile) throws IOException{
		int deleteFiles=0;
		long current = System.currentTimeMillis();
		int preserveDay=0;
		
		try {
			preserveDay = Integer.parseInt(MailSendUtil.fileLifeSpan);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		OutputStreamWriter osw =null;
		if(infile.isDirectory()){
			String files[] = infile.list();
			
			for (int i = 0; i < files.length; i++) {
				File file = new File(infile, files[i]);
				//check spent time since uploading
				int spent = (int)((current-file.lastModified())/(1000*3600*24));
				
				if(spent>preserveDay){
					//ファイル削除の記録を残すファイルをアツプロードディレクトリーに作る。
					String today = DateUtil.getNow("yyyyMMdd");
					File logFile = new File(MailSendUtil.fileUploadDir+File.separator+today+".txt");
					osw = FileUtils.createClearLogFile(logFile);
					
					if(log.isDebugEnabled()){
						log.debug(FileUtils.readFile(logFile));
					}
					//record the file's information cleared
					StringBuffer sf = new StringBuffer();
					sf.append("["+DateUtil.getNow("yyyy-MM-dd HH:mm:ss")+"]");
					sf.append("\t expired file \t");
					sf.append(file.getName());
					sf.append("\t"+file.length());
					String timeFormat="yyyy-MM-dd a h:mm";		
					Date date = new Date(file.lastModified()); 
					sf.append("\t"+DateUtil.getDate(date, timeFormat));
					sf.append(System.getProperty("line.separator"));
								
					osw.write(sf.toString());
					osw.flush();
					//delete expired file after logging
					if(log.isDebugEnabled()){
						log.debug(FileUtils.readFile(logFile));
					}
					deleteFiles = FileUtils.deleteFiles(file);
					
				}
			}
			
		}else{
			int spent = (int)((current-infile.lastModified())/(1000*3600));
			
			if(spent>preserveDay)
				deleteFiles = FileUtils.deleteFiles(infile);
		}
		if(osw!=null)
			osw.close();
		//initialize a field variable after this task
		FileUtils.tempFiles = 0 ;
		return deleteFiles;
	}
}
