package se.openprocesslogger.svg.data;

import java.io.Serializable;



public class ProcDataHolder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8753039866897492694L;
	
	private boolean isAnalog;
	private String title;
	
	public DataPoint[] pData;
	public ProcDataHolder(DataPoint[] data, boolean isAnalog2, String title) {
		pData = data;
		this.isAnalog = isAnalog2;
		this.title = title;
	}
	
	public boolean isAnalog() {
		return isAnalog;
	}

	public String getTitle() {
		return title;
	}	
	
}
