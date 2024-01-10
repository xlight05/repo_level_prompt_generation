package org.jsemantic.jcontenedor;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;

public interface JContenedor extends Service {
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Service getService(String id) throws ServiceException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ComponentException
	 */
	public Component getComponent(String id) throws ComponentException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Object getBean(String id);
	/**
	 * 
	 * @return
	 */
	public Context getContext();
	/**
	 * 
	 * @return
	 */
	public Layer getRoot();
	
}
