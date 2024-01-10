package org.jsemantic.jcontenedor.utils;

import javax.servlet.ServletContext;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class JContenedorUtils {
	
	private JContenedorUtils() {
		//	
	}
	
	public static ConfigurableApplicationContext  getApplicationContext(ServletContext servletContext) {
		return (ConfigurableApplicationContext)WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
	
	public static ConfigurableApplicationContext getServletDispatcherContext(ServletContext servletContext) {
		return (ConfigurableApplicationContext)servletContext.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
	}
}
