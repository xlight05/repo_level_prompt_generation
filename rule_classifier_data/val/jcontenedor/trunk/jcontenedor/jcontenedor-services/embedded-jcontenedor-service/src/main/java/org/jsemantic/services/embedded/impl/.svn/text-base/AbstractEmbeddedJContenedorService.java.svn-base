package org.jsemantic.services.embedded.impl;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.exception.CycleException;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractService;
import org.jsemantic.services.embedded.EmbeddedJContenedorService;
import org.jsemantic.services.embedded.annotation.EmbeddedHttpServiceAnnotationProcessor;
import org.jsemantic.services.httpservice.HttpService;

public abstract class AbstractEmbeddedJContenedorService extends
		AbstractService implements EmbeddedJContenedorService {

	private JContenedor contenedor = null;
	
	private static final String JCONTENEDOR_EMBEDDED_CONFIGURATION = "META-INF/embedded-jcontenedor-service/embedded-jcontenedor-service.xml";
	
	private static final String CONFIGURE_METHOD = "configure";
	
	private Service embeddedHttpService = null;
	
	private String containerConfigurationFile = null;
	
	public String getContainerConfigurationFile() {
		return containerConfigurationFile;
	}

	public void setContainerConfigurationFile(String containerConfigurationFile) {
		this.containerConfigurationFile = containerConfigurationFile;
	}

	public AbstractEmbeddedJContenedorService() {
		super.init();
	}
	
	protected void postConstruct() throws CycleException {
		if (containerConfigurationFile == null) {
			this.containerConfigurationFile = JCONTENEDOR_EMBEDDED_CONFIGURATION;
		}
		
	}
	
	private void processHtppServiceAnnotations() {

		Class<?> serviceClazz = null;
		try {
			serviceClazz = getClass().getMethod(CONFIGURE_METHOD , new Class[] {})
					.getDeclaringClass();
			embeddedHttpService = contenedor.getService(HttpService.HTTP_SERVICE_ID);
			EmbeddedHttpServiceAnnotationProcessor
					.processHtppServiceAnnotations(embeddedHttpService,
							serviceClazz);
		} catch (Throwable e) {
			throw new ServiceException(e);
		}

	}

	protected void startService() throws ServiceException {
		contenedor = JContenedorFactory
				.getInstance(containerConfigurationFile);
		contenedor.start();
		processHtppServiceAnnotations();

		embeddedHttpService.start();
	}

	protected void stopService() throws ServiceException {
		embeddedHttpService.stop();
		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}

	public Service getService(String id) throws ServiceException {
		return this.contenedor.getService(id);
	}

	public Component getComponent(String id) throws ComponentException {
		return this.contenedor.getComponent(id);
	}
	
	public Object getBean(String id) {
		return this.contenedor.getBean(id);
	}
	
	public Context getContext() {
		return this.contenedor.getContext();
	}
	
	public Layer getRoot() {
		return this.contenedor.getRoot();
	}
	
	public abstract void configure();
}
