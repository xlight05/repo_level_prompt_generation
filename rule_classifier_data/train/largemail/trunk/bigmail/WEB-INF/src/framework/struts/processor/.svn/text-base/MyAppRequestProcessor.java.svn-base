package framework.struts.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.RequestProcessor;

import framework.struts.util.MailSendUtil;

// japanese encoding
public class MyAppRequestProcessor extends RequestProcessor {
	//	logging
	final Log log = LogFactory.getLog(this.getClass());
	
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			
		try {
			
			if ( request.getServletPath().equals("/processUpload.do") )
			{
				//read properties from config.properties During UPLOAD!
				MailSendUtil.fileUploadDir = MailSendUtil.loadConfigXML(request, "config.properties").getProperty("fileUploadDir");
				MailSendUtil.tempFileDir = MailSendUtil.loadConfigXML(request, "config.properties").getProperty("tempFileDir");
				MailSendUtil.fileLifeSpan = MailSendUtil.loadConfigXML(request, "config.properties").getProperty("fileLifeSpan");
				MailSendUtil.maxUploadLength = Integer.parseInt(MailSendUtil.loadConfigXML(request, "config.properties").getProperty("maxFileSize"));
				MailSendUtil.maxUploadSize = Long.parseLong(MailSendUtil.loadConfigXML(request, "config.properties").getProperty("maxUploadSize"));
			}
			request.setCharacterEncoding("UTF-8");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			if(log.isErrorEnabled()){
				log.error("cannot read properties from a configuration file.", e);
			}
			return false;
		}
	}

}
