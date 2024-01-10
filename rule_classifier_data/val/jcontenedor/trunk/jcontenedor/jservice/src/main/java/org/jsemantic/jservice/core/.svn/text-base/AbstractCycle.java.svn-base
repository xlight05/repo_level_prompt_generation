package org.jsemantic.jservice.core;

import org.jsemantic.jservice.core.exception.CycleException;

public abstract class AbstractCycle implements Cycle {
	
	protected enum STATE {
		NOT_INITIALIZED, INITIALIZING, INITIALIZED,  STARTED, STOPPED, DISPOSING, DISPOSED
	};
	
	protected STATE state = STATE.NOT_INITIALIZED;
	
	public STATE getState() {
		return state;
	}
	
	protected void setState(STATE state) {
		this.state = state;
	}
	
	public boolean isInitialized() {
		return this.state == STATE.INITIALIZED;
	}
	
	public boolean isInitializing() {
		return this.state == STATE.INITIALIZING;
	}
	
	public boolean isDisposing() {
		return this.state == STATE.DISPOSING;
	}
	
	public boolean isStarted() {
		return state == STATE.STARTED;
	}

	public boolean isStopped() {
		return state == STATE.STOPPED;
	}
	
	
	protected void initializing() throws CycleException {
		setState(STATE.INITIALIZING);
	}
	
	protected void disposing() throws CycleException {
		setState(STATE.DISPOSING);
	}
	
	/**
	 * 
	 */
	public abstract void init() throws CycleException;
	/**
	 * 
	 */
	public abstract void dispose() throws CycleException;
	
	/**
	 * 
	 */
	public abstract void start() throws CycleException;
	/**
	 * 
	 */
	public abstract void stop() throws CycleException;

}
