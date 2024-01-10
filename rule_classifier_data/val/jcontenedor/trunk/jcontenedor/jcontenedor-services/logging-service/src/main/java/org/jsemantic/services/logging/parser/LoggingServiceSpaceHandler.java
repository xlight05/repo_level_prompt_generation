/**
 * 
 */
package org.jsemantic.services.logging.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * 
 * Clase para registrar en el space handler el tag de contenedor <nextply:container/>
 * 
 * @author aestevez
 */
public class LoggingServiceSpaceHandler extends NamespaceHandlerSupport {

	/** 
	 * Para mayor informaci�n:
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
		registerBeanDefinitionParser("loggingService", new LoggingServiceParser());
	}
}
