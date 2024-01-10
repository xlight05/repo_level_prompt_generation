package org.jsemantic.jservice.core.entity;

import org.jsemantic.jservice.core.context.Context;

public interface Entity {
	/**
	 * 
	 * @return
	 */
	public String getId();
	
	/**
	 * 
	 * @param applicationContext
	 */
	public void setContext(Context context);
	/**
	 * 
	 * @return
	 */
	public Context getContext();
	
}
