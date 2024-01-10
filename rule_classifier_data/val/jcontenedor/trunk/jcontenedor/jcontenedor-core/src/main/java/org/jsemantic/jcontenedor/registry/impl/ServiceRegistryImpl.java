package org.jsemantic.jcontenedor.registry.impl;

import java.util.Map;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.registry.ServiceRegistry;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.service.Service;

public class ServiceRegistryImpl extends AbstractComponent implements
		ServiceRegistry {

	private Layer registry = null;

	public ServiceRegistryImpl(Layer registry) {
		this.registry = registry;
		init();
	}

	public void setRegistry(Layer registry) {
		this.registry = registry;
	}

	public Service getService(String serviceName) {
		return (Service) registry.getComponent(serviceName);
	}

	public Service getService(String serviceName, Class<?> serviceClazz) {
		return (Service) registry.getComponent(serviceName, serviceClazz);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Service> getServices() {
		return (Map<String, Service>) registry
				.getComponentsOfType(Service.class);
	}
	
	@Override
	protected void release() throws ComponentException {
		// TODO Auto-generated method stub

	}
	@Override
	protected void postConstruct() throws ComponentException {
		// TODO Auto-generated method stub

	}

	public void setContainerContext(Context contenedorContext) {
		// TODO Auto-generated method stub
		Map<String, Service> services = getServices();

		for (String serviceId : services.keySet()) {
			Service service = services.get(serviceId);
			service.setContext(contenedorContext);
		}
	}

}
