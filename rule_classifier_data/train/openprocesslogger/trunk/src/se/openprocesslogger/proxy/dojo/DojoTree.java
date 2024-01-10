package se.openprocesslogger.proxy.dojo;

import java.io.Serializable;

public class DojoTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -536339224558319785L;
	
	private String label;
	private String identifier;
	private DojoTreeItem[] items;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public DojoTreeItem[] getItems() {
		return items;
	}
	public void setItems(DojoTreeItem[] items) {
		this.items = items;
	}
	
}
