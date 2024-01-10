package br.com.dyad.client;

import java.util.HashMap;

import com.extjs.gxt.ui.client.data.BaseModel;

public class DyadBaseModel extends BaseModel {

	@SuppressWarnings("unchecked")
	public HashMap getBeanProperties(boolean includeNull) {
		HashMap value = new HashMap();

		for (String name : getPropertyNames()) {
			if (this.get(name) != null || includeNull) {
				value.put(name, this.get(name));
			}
		}

		return value;
	}

	public void setProperties(DyadBaseModel model) {
		map.clear();
		map.putAll(model.map.getTransientMap());
	}
	
	public <X extends Object> X set(String name, X value) {
		X result = super.set(name, value);		
		
		callSetDependentProperties(name, value);
		
		return result;
	};
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DyadBaseModel && ((DyadBaseModel)obj).get("id") != null){			
			return ((DyadBaseModel)obj).get("id").equals(this.get("id"));
		} else {
			return false;
		}		
	}

	@Override
	public String toString() {
		if ( this.get("id") != null){			
			return "DyadBaseModel: Id = " + this.get("id");
		} else {	
			return super.toString();
		}
	}
	
	public void setDefaultProperties(HashMap props) {
		for (Object obj : props.keySet()) {
			if (obj instanceof String) {
				set((String)obj, props.get(obj));				
			}
		}
	}

}
