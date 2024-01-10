package br.com.dyad.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataClass extends DyadBaseModel implements IsSerializable {
	private static transient final long serialVersionUID = 1L;

	public String getClassName() {
		return get("className");
	}

	public void setClassName(String className) {
		set("className", className);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public ArrayList<DataClass> getChildren() {
		if (get("children") == null){
			set("children", new ArrayList<DataClass>());
		}
		return get("children");
	}

	public void setChildren(ArrayList<DataClass> children) {
		set("children", children);
	}
	
	@Override
	public boolean equals(Object obj) {
		DataClass clazz = (DataClass)obj;	
		if ( clazz.getClassId() == null ){
			return false;
		}
		return clazz.getClassId().equals(this.getClassId());
	}

	public Long getClassId() {
		return get("classId");
	}

	public void setClassId(Long classId) {
		set("classId", classId);
	}

	public Long getLicenseId() {
		return get("licenseId");
	}

	public void setLicenseId(Long licenseId) {
		set("licenseId", licenseId);
	}	
	
	public Integer getInheritLevel() {
		return get("inheritLevel");
	}

	public void setInheritLevel(Integer inheritLevel) {
		set("inheritLevel", inheritLevel);
	}
}