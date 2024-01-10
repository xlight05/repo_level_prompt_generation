package org.jsemantic.services.httpservice;

import javax.servlet.ServletContext;

import org.jsemantic.jservice.core.service.Service;
import org.mortbay.jetty.webapp.WebAppContext;

public interface HttpService extends Service {
	
	public static final String HTTP_SERVICE_ID = "httpService";
	/**
	 * 
	 * @param host
	 */
	public void setHost(String host);
	/**
	 * 
	 * @param rootContext
	 */
	public void setRootContext(String rootContext);
	/**
	 * 
	 * @param webApplication
	 */
	public void setWebApplication(String webApplication);
	/**
	 * 
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * 
	 * @return
	 */
	public ServletContext getServerContext();
	/**
	 * 
	 * @return
	 */
	public WebAppContext getApplicationContext();
		
	
}
