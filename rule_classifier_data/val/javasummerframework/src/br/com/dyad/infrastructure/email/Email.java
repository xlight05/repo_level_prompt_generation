package br.com.dyad.infrastructure.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	private String protocol;
	private String smtpHost;
	private Boolean authenticate;
	private String user, password;
	private String subject;
	private String emailFrom;
	private String[] recipients;
	private HashMap attachments;	
	private Properties props;
	private Object emailContent;
	private String contentType;
	
	public static String CONTENT_TYPE_TEXT = "text/plain";
	public static String CONTENT_TYPE_HTML = "text/html";
	
	/**
	    ****** Sample ******** 
	 	Email email = new Email();
		email.setAuthenticate(true);
		email.setEmailContent("Email Test");
		email.setEmailFrom("danilo.sampaio@gmail.com");
		email.setPassword(password);
		email.setRecipients(new String[]{"danilo.sampaio@gmail.com"});
		email.setSmtpHost("smtp.gmail.com");
		email.setSubject("Email Test");
		email.setUser(user);
		email.send();
	 */
	public Email(){
		setContentType(CONTENT_TYPE_TEXT);		
	}	
	
	public Email(String smtpHost, Boolean authenticate) {
		super();
		this.smtpHost = smtpHost;
		this.authenticate = authenticate;
	}

	public void send() throws Exception{		
		initProps();
		
		//-- Init session
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		Transport transport = session.getTransport();
		
		//-- Create message
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());  
		message.setSubject(subject);
		message.setContent(emailContent, contentType);		
		
		//-- From and To
        message.setFrom(new InternetAddress(emailFrom));       
        for (int i = 0; i < recipients.length; i++) {
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients[i]));
		}          
        
        //-- Add attachment: TODO
        if ( attachments != null && attachments.size() > 0 ){
	        /*BodyPart messageBodyPart = new MimeBodyPart();
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			DataSource source = new FileDataSource("C:\\backup.zzz");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("C:\\backup.zzz");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);*/
        }	
        
        //-- Send message
        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}	
	
	private void initProps() throws Exception{
		props = new Properties();  
		props.put("mail.transport.protocol", ( protocol != null ? protocol : "smtp" ) );
		if ( smtpHost == null ){
			throw new Exception("smtp server must be informed.");
		}
		props.put("mail.smtp.host", smtpHost);		
		props.put("mail.smtp.auth", authenticate ? "true" : "false" );
		if ( authenticate ){
			props.put("mail.smtp.starttls.enable", "true");
		}
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(user, password);
        }
    }	
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public Boolean getAuthenticate() {
		return authenticate;
	}
	public void setAuthenticate(Boolean authenticate) {
		this.authenticate = authenticate;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String[] getRecipients() {
		return recipients;
	}
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	public HashMap getAttachments() {
		return attachments;
	}
	public void setAttachments(HashMap attachments) {
		this.attachments = attachments;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public Object getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(Object emailContent) {
		this.emailContent = emailContent;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}