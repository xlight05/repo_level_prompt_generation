package org.jsemantic.jcontenedor.configuration.web;

import javax.servlet.ServletContextListener;

/**
 * 
 * @author adolfo
 *
 */
public class JContenedorListener extends
		org.springframework.web.context.ContextLoaderListener implements ServletContextListener {
	/**
	 * 
	 */
	protected org.springframework.web.context.ContextLoader createContextLoader() {
		return new JContenedorLoader();
	}	
}