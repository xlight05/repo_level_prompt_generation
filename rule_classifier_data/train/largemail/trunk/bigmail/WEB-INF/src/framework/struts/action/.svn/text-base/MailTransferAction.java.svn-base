package framework.struts.action;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import framework.struts.form.MailForm;
import framework.struts.model.FileInfo;
import framework.struts.util.MailSendUtil;
import framework.struts.util.BoardUtils;
/**
 * XDoclet configuration for struts-config.xml 
 * @struts.action 
 * 			name="mailForm"
 * 			path="/mailTransfer"
 * 			input="/upload_view.jsp"
 * 			scope="request"
 * 			validate="true"
 * @struts.action-forward
 * 			name="Success"
 * 			path="/result.jsp"
 * 			redirect="false"
 * @struts.action-exception
 * 			key="error.send.mail"
 * 			type="javax.mail.SendFailedException"
 * 			path="/upload_view.jsp"
 * @struts.action-exception
 * 			key="error.messaging.exception"
 * 			type="javax.mail.MessagingException"
 * 			path="/upload_view.jsp"			
 */			
public class MailTransferAction extends Action {
	//	logging
	final Log log = LogFactory.getLog(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionMessages messages = new ActionMessages();
		StringBuffer message = new StringBuffer();
		
		
		//get upload information
		HttpSession httpSession = request.getSession(false);
		MailForm bigmail = (MailForm)httpSession.getAttribute("attached.file.message");
		
		//read a configuration infomation from properties.xml
		Properties props=MailSendUtil.loadConfigXML(request, "config.properties");
		
		String mailSmtpHost = props.getProperty("mailSmtpHost");
		String fileUploadHost = props.getProperty("fileUploadHost");
		String cookieLifeSpan = props.getProperty("cookieLifeSpan");
		
		Properties propsMail = new Properties();
		propsMail.put("mail.smtp.host",mailSmtpHost);
		
		//get a mail session
		Session session = Session.getDefaultInstance(propsMail);
		String mailFrom = ((MailForm)form).getFromAddress();
		String mailTo = ((MailForm)form).getToAddress();
		String title = ((MailForm)form).getTitle();
		
		//write a messageۼ
		message.append(BoardUtils.convertHtmlBr(((MailForm)form).getContent()));
		message.append("<br/>"); 
		message.append("------------------------------------------------------");
		message.append("<br/>"); 
				
		//add attached files' link to a mail message.ͼ
		List fileList=null;
		
		if (bigmail !=null){
			fileList = bigmail.getFileList();		
		}
		if (fileList !=null && fileList.size()!=0) {
						
			for (int i=0;i<fileList.size();i++) {
				FileInfo file = (FileInfo) fileList.get(i);
				
				String fileName = file.getFileName();
				if(log.isDebugEnabled()){
					log.debug("encoded ISO-8859-1 file name="+file.getFileName());
				}
				String downParm="tempFileName="+file.getTempFileName()+"&fileName="+fileName+"&content-type="+file.getContentType();
				message.append("File "+(i+1)+" : ");
				// browser encodes this downParm with UTF-8 
				message.append("<a href='"+fileUploadHost+"download.do?"+downParm+"'>"+fileName);
				message.append("</a>");
				message.append("<br>");
				
			}//end for
		}else{
			message.append("No attached files.");
		}
		if(log.isInfoEnabled()){
			log.info("MailTransferAction: fileListSize="+((MailForm)form).getFileList().size());
		}
		//post a mail
		try {
			MailSendUtil.doMailTransfer(session, mailFrom, mailTo, title, message.toString());
		} catch (SendFailedException sfe) {
			// TODO: logging 
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.send.mail"));
		}catch( MessagingException me){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.messaging.exception"));
		}
		
		//write logging messages
		if (log.isInfoEnabled()) {
			log.info(
				"SMTP HOST: "+mailSmtpHost+ " sent message to '"+mailTo+
				"'\r\n Message: "+message.toString());
						
		}
		
		
		//save a cookie
						
		Cookie cookie = new Cookie("mailFrom",mailFrom);
		cookie.setMaxAge(Integer.parseInt(cookieLifeSpan)); 
		response.addCookie(cookie);
		
		return mapping.findForward("Success");
	}
	
}
