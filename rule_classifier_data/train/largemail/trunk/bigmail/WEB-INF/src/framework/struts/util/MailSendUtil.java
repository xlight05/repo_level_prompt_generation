package framework.struts.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class MailSendUtil {
	
	//private static boolean sendResult;
	private static final String TYPE="text/html; charset=UTF-8";
	
	public static String fileUploadDir;
	public static String tempFileDir;
	public static String fileLifeSpan;
	public static int maxUploadLength;
	public static long maxUploadSize;
		
	public static void doMailTransfer(Session session, String mailFrom, String mailTo, String title, String message) 
		throws MessagingException, SendFailedException
	{
				//get MimeMessage
				MimeMessage  msg = new MimeMessage(session);
				
				//set mail provider
				msg.setFrom(new InternetAddress(mailFrom));
				
				//set mail recipient
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
				
				//set mail title
				msg.setSubject(title);
				
				//set the content of mail
				msg.setContent(message, TYPE);
				
				//post mail
				Transport.send(msg);
				
	}
	// read a configuration file from Servlet or Action class
	public static Properties loadConfigXML(HttpServletRequest request, String xmlSrc) 
		throws FileNotFoundException,IllegalArgumentException, IOException 
	{
		Properties props = new Properties();
		
		String path = request.getRealPath("WEB-INF/classes");
		File file = new File(path, xmlSrc);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		
		props.load(is);
		//props.loadFromXML(is);
		return props;
	}
	
	// read a configuration file from JSP
	public static Properties loadConfigXML(ServletContext application, String xmlSrc) throws FileNotFoundException,IllegalArgumentException, IOException {
		Properties props = new Properties();
		
		String path = application.getRealPath("WEB-INF/classes");
		File file = new File(path, xmlSrc);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		
		props.load(is);
		//props.loadFromXML(is);
		return props;
	}
			
}
