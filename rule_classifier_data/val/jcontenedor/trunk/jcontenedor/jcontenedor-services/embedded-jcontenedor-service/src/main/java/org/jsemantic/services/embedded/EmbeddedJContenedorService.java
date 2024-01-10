package org.jsemantic.services.embedded;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;
/**
 * 
 * @author adolfo
 *
 */
public interface EmbeddedJContenedorService extends JContenedor {
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
}
