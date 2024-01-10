/**
 * 
 */
package org.jsemantic.services.logging.parser;

import org.jsemantic.services.logging.impl.LoggingServiceImpl;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


public class LoggingServiceParser extends AbstractSingleBeanDefinitionParser {
	
	private static final String LOG_TYPE = "logType";
	
	@SuppressWarnings("unchecked")

	protected Class getBeanClass(Element element) {
	      return LoggingServiceImpl.class; 
	}
	
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String logType = element.getAttribute(LOG_TYPE);
		if (StringUtils.hasText(logType)) {
			bean.addPropertyValue(LOG_TYPE, logType);
		}
		else {
			bean.addPropertyValue(LOG_TYPE, LoggingServiceImpl.DEFAULT_LOG_TYPE);
		}
		bean.addPropertyValue("autoStarted", true);
	}
}
