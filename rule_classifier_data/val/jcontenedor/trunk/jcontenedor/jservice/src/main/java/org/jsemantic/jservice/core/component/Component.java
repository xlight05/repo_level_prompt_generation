package org.jsemantic.jservice.core.component;

import org.jsemantic.jservice.core.Cycle;
import org.jsemantic.jservice.core.entity.Entity;

/**
 * 
 * @author adolfo
 *
 */ 
public interface Component extends Entity, Cycle {

	/**
	 * 
	 * @return
	 */
	public boolean isInitialized();
	/**
	 * 
	 * @return
	 */
	public boolean isInitializing();
	/**
	 * 
	 * @return
	 */
	public boolean isDisposing();
	
}
