package org.jsemantic.jcontenedor.registry;

import java.util.Map;

import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.service.Service;

public interface ServiceRegistry extends Component {
	/**
	 * 
	 * @param serviceName
	 * @return
	 */
	public Service getService(String serviceName);
	/**
	 * 
	 * @return
	 */
	public Map<String, Service> getServices();
	/**
	 * 
	 * @param serviceName
	 * @param serviceClazz
	 * @return
	 */
	public Service getService (String serviceName, Class<?> serviceClazz);
	/**
	 * 
	 * @param containerContext
	 */
	public void setContainerContext(Context containerContext);
	
	
}
