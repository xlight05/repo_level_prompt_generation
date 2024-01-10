package org.jsemantic.jcontenedor.configuration.web;

import java.util.Map;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.CollectionFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

@SuppressWarnings("unchecked")
public class JContenedorLoader extends
		org.springframework.web.context.ContextLoader {

	private JContenedor contenedor = null;

	private static final String JCONTENEDOR_CONFIGURATION_LOCATION = "jContenedorConfigurationLocation";

	public static final String ROOT_CONTAINER_CONTEXT_ATTRIBUTE = JContenedor.class
			.getName()
			+ ".ROOT";
	private static Map<ClassLoader, JContenedor> cCurrentContainerPerThread =  CollectionFactory
			.createConcurrentMapIfPossible(1);

	public void closeWebApplicationContext(ServletContext pServletContext) {
		super.closeWebApplicationContext(pServletContext);
		try {
			this.contenedor.stop();
			this.contenedor = null;
		} finally {
			cCurrentContainerPerThread.remove(Thread.currentThread()
					.getContextClassLoader());
			pServletContext.removeAttribute(ROOT_CONTAINER_CONTEXT_ATTRIBUTE);
		}
	}

	public static JContenedor getCurrentContainer() {
		return cCurrentContainerPerThread.get(Thread.currentThread()
				.getContextClassLoader());
	}

	protected WebApplicationContext createWebApplicationContext(
			ServletContext servletContext, ApplicationContext parent)
			throws BeansException {

		if (StringUtils.hasText(servletContext
				.getInitParameter(JCONTENEDOR_CONFIGURATION_LOCATION))) {
			this.contenedor = JContenedorFactory
					.getContenedorWeb(
							servletContext
									.getInitParameter(JCONTENEDOR_CONFIGURATION_LOCATION),
							servletContext);
		} else {
			this.contenedor = JContenedorFactory
					.getDefaultInstance(servletContext);
		}
		this.contenedor.start();
		
		servletContext.setAttribute(ROOT_CONTAINER_CONTEXT_ATTRIBUTE,
				this.contenedor);
		
		cCurrentContainerPerThread.put(Thread.currentThread()
				.getContextClassLoader(), this.contenedor);

		XmlWebApplicationContext wac = (XmlWebApplicationContext) this.contenedor
				.getRoot().getParent().getApplicationContext();

		if (parent != null) {
			wac.setParent(parent);
		}
		customizeContext(servletContext, wac);
		return wac;
	}

}