package org.jsemantic.services.amqservice.factory;

import java.lang.annotation.Annotation;

import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.services.amqservice.ActiveMQService;
import org.jsemantic.services.amqservice.annotation.ActiveMQServiceConfiguration;
import org.springframework.util.StringUtils;

public class ActiveMQServiceFactory {

	public ActiveMQServiceFactory() {
		//
	}

	public static Service getInstance(Service activeMQService, Annotation ann) {

		if (ann instanceof ActiveMQServiceConfiguration) {
			String connector = ((ActiveMQServiceConfiguration) ann).connector();
			String jmx = ((ActiveMQServiceConfiguration) ann).jmx();

			boolean jmValue = Boolean.parseBoolean(jmx);

			if (StringUtils.hasText(connector)) {
				((ActiveMQService) activeMQService).setConnector(connector);
			}

			if (StringUtils.hasText(jmx)) {
				((ActiveMQService) activeMQService).setJmxUsed(jmValue);
			}
		}
		return activeMQService;
	}

}
