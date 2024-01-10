package se.openprocesslogger.proxy.dojo;

import java.io.Serializable;


public class TreeHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1038972875793920605L;
	
	public DojoTree data;

	public TreeHolder(DojoTree data) {
		this.data = data;
	}

	public TreeHolder() {
	}

	public DojoTree getData() {
		return data;
	}

	public void setData(DojoTree data) {
		this.data = data;
	}
	
	

}
