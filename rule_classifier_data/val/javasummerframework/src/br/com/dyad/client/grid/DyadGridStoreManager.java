package br.com.dyad.client.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;

import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;

public class DyadGridStoreManager extends DyadAsyncCallback {
	
	private StoreEvent se;
	private String className;

	public DyadGridStoreManager(StoreEvent se, String className) {
		this.se = se;		
		this.className = className;
	}
	
	public void saveBean() {
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("className", className);
		
		List<DyadBaseModel> beans = new ArrayList<DyadBaseModel>();
		beans.add((DyadBaseModel)se.getModel());		
		params.put("beans", beans);
		
		proxy.getServiceValue("br.com.dyad.infrastructure.service.PersistenceService", params, this);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		se.getRecord().reject(true);
		super.onFailure(arg0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(Object ret) {
		try{			
			HashMap value = (HashMap)ret;
			List<DyadBaseModel> beans = (List<DyadBaseModel>)value.get("beans");
			se.getRecord().commit(true);
			DyadBaseModel baseModel = (DyadBaseModel)se.getModel();
			baseModel.setProperties(beans.get(0));
		}catch(Throwable e){			
			se.getRecord().cancelEdit();
			se.getStore().rejectChanges();
			MessageBox.alert("ERROR", e.getMessage(), null);
		}
		
		super.onSuccess(ret);
	}

}