package br.com.dyad.client;

import java.util.HashMap;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

public class DyadBaseTreeModel extends BaseTreeModel{
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

	public void setProperties(DyadBaseTreeModel model) {
		map.clear();
		map.putAll(model.map.getTransientMap());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DyadBaseTreeModel && this.get("id") != null && ((DyadBaseTreeModel)obj).get("id") != null){
			return ((DyadBaseTreeModel)obj).get("id").equals(this.get("id"));
		} else {
			return false;
		}		
	}
}
