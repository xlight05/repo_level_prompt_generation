package se.openprocesslogger.proxy.dojo;

import java.io.Serializable;

public class DojoTreeItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1387023705260859407L;
	
	private String name;
	private String id;

	
	public DojoTreeItem(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public DojoTreeItem() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
