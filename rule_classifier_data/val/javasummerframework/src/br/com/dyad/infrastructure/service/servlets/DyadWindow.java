package br.com.dyad.infrastructure.service.servlets;

import java.util.ArrayList;
import java.util.Iterator;

public class DyadWindow {
	private String className;
	private String process;
	private String name;	
	private Long id;
	private Long licenseId;
	private ArrayList<DyadWindow> children;
	
	public DyadWindow(){
		
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public ArrayList<DyadWindow> getChildren() {
		if ( this.children == null ){
			this.children = new ArrayList<DyadWindow>();
		}
		return children;
	}

	public void setChildren(ArrayList<DyadWindow> children) {		
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
		DyadWindow nav = (DyadWindow)obj;
		
		return nav.getClassName().equals(this.getClassName()) &&
		       nav.getName().equals(this.getName());
	}
	
	public String toXmlString(){
		String content = "<window id=\""+ id +"\">";		
		content += "<className><![CDATA[" + (className != null ? className : "") + "]]></className>";
		content += "<process><![CDATA[" + (process != null ? process : "") + "]]></process>";
		content += "<name><![CDATA[" + (name != null ? name : "") + "]]></name>";
		content += "<id>" + (id != null ? id : "") + "</id>";
		content += "<licenseId><![CDATA[" + (licenseId != null ? licenseId : "") + "]]></licenseId>";
		if ( children != null ){
			content += "<children>";
			for (Iterator iterator = children.iterator(); iterator.hasNext();) {
				DyadWindow window = (DyadWindow) iterator.next();
				content += (window != null ? window.toXmlString() : "");
			}
			content += "</children>";
		}
		content += "</window>";
		return content;
	}

}
