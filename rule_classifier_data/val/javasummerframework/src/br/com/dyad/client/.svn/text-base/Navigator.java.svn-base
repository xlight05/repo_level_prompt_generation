package br.com.dyad.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Navigator extends DyadBaseModel implements IsSerializable {
	
	private static transient final long serialVersionUID = 1L;

	public String getClassName() {
		return get("className");
	}

	public void setClassName(String className) {
		set("className", className);
	}

	public String getProcess() {
		return get("process");
	}

	public void setProcess(String process) {
		set("process", process);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public List<Navigator> getSubmenu() {
		if (get("submenu") == null){
			set("submenu", new ArrayList<Navigator>());
		}
		return get("submenu");
	}

	public void setSubmenu(List<Navigator> submenu) {
		set("submenu", submenu);
	}
	
	@Override
	public boolean equals(Object obj) {
		Navigator nav = (Navigator)obj;
		
		return nav.getClassName().equals(this.getClassName()) &&
		       nav.getName().equals(this.getName());
	}

	public String getCssName() {
		return get("cssName");
	}

	public void setCssName(String cssName) {
		set("cssName", cssName);
	}

	public Long getId() {
		return get("id");
	}

	public void setId(Long id) {
		set("id", id);
	}

	public Long getLicenseId() {
		return get("licenseId");
	}

	public void setLicenseId(Long licenseId) {
		set("licenseId", licenseId);
	}

	
}
