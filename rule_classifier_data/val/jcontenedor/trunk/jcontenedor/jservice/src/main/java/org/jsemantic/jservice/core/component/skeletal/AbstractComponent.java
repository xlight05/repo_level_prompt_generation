package org.jsemantic.jservice.core.component.skeletal;

import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.entity.skeletal.AbstractEntity;

/**
 * 
 * @author adolfo
 * 
 */
public abstract class AbstractComponent extends AbstractEntity implements
		Component {

	public AbstractComponent() {

	}

	public void start() {
		throw new ComponentException(
				"Unavailable Operation. A component can not be started. A Service can.");
	}

	public void stop() {
		throw new ComponentException(
				"Unavailable Operation. A component can not be stopped. A Service can.");
	}
	
	public void restart() {
		throw new ComponentException(
				"Unavailable Operation. A component can not be restarted. A Service can.");
	}
	

}
