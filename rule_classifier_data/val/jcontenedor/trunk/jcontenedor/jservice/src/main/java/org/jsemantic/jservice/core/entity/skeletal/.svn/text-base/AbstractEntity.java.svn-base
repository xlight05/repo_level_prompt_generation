package org.jsemantic.jservice.core.entity.skeletal;

import org.jsemantic.jservice.core.AbstractCycle;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.entity.Entity;
import org.jsemantic.jservice.core.exception.CycleException;

public abstract class AbstractEntity extends AbstractCycle implements Entity {

	private String id = null;
		
	private Context context = null;
	
	public void setContext(Context context) {
		this.context = context;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public void init() throws CycleException {
		super.setState(STATE.INITIALIZED);
		postConstruct();
	}

	public void dispose() throws CycleException {
		super.setState(STATE.DISPOSED);
		release();
	}
	
	/**
	 * 
	 * @throws ComponentException
	 */
	protected abstract void postConstruct() throws CycleException;
	/**
	 * 
	 * @throws ComponentException
	 */
	protected abstract void release() throws CycleException;
	
}
