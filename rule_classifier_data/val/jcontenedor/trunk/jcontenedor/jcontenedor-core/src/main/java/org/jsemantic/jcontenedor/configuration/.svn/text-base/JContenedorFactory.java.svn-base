package org.jsemantic.jcontenedor.configuration;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.JContenedor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class JContenedorFactory {

	private final static String DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/jcontenedor/contenedor-configuration.xml";

	private final static String WEB_DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/jcontenedor/contenedor-configuration.xml";

	private final static String JCONTENEDOR_ID = "contenedor";

	/**
	 * 
	 */
	private JContenedorFactory() {
		//
	}

	public static JContenedor getInstance(String configurationFile) {
		if (!StringUtils.hasText(configurationFile)) {
			return getDefaultInstance();
		}
		return getContenedor(configurationFile);
	}

	/**
	 * 
	 * @return
	 */
	public static JContenedor getDefaultInstance() {
		return getContenedor(DEFAULT_CONFIGURATION_FILE);
	}

	private static JContenedor getContenedor(String configurationFile) {
		ClassPathXmlApplicationContext contenedorCtx = new ClassPathXmlApplicationContext();
		contenedorCtx.setConfigLocation(configurationFile);
		contenedorCtx.refresh();
		contenedorCtx.registerShutdownHook();

		JContenedor contenedor = (JContenedor) contenedorCtx
				.getBean(JCONTENEDOR_ID);

		return contenedor;
	}

	public static JContenedor getDefaultInstance(ServletContext servletContext) {
		return getContenedorWeb(WEB_DEFAULT_CONFIGURATION_FILE, servletContext);
	}

	public static JContenedor getContenedorWeb(String configurationFile,
			ServletContext servletContext) {
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
				.instantiateClass(XmlWebApplicationContext.class);
		wac.setConfigLocation(configurationFile);
		wac.setServletContext(servletContext);
		wac.refresh();
		JContenedor contenedor = (JContenedor) wac.getBean(JCONTENEDOR_ID);
		return contenedor;
	}

}
