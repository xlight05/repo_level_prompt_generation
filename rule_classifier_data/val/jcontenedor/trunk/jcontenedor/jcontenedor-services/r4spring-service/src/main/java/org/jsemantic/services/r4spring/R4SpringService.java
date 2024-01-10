package org.jsemantic.services.r4spring;

import java.util.Collection;

import org.jsemantic.jservice.core.service.Service;
/**
 * 
 * @author Owner
 *
 */
public interface R4SpringService extends Service {
	/**
	 * 
	 * @param facts
	 * @return
	 */
	public Collection<?> execute(Object facts);
	/**
	 * 
	 * @param facts
	 * @return
	 */
	public Collection<?> execute(Collection<?> facts);
	
}
