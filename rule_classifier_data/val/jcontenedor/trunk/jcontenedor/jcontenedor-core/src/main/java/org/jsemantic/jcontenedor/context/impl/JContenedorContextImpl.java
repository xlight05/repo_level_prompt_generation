package org.jsemantic.jcontenedor.context.impl;

import javax.servlet.ServletContext;

import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.jsemantic.jservice.core.context.Context;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class JContenedorContextImpl extends AbstractComponent implements
		ServletContextAware, ApplicationContextAware, Context {

	private ServletContext servletContext = null;
	/**
	 * 
	 */
	private ApplicationContext applicationContext = null;

	public boolean isWebContext() {
		return servletContext != null;
	}

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;

	}

	@Override
	protected void release() throws ComponentException {
		applicationContext = null;
	}

	@Override
	protected void postConstruct() throws ComponentException {
		//
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}

	public Object getExternal() {
		if (isWebContext()) {
			return this.servletContext;
		}
		return this.applicationContext;
	}

}
