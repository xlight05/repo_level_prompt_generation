package br.com.dyad.client.widget.field;

import java.util.ArrayList;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.field.customized.ComboBoxCustomized;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ClientComboField extends ClientField{  
	
	public <X extends Object> X set(String name, X value) {
		X result = super.set(name, value);
		
		if (name.equals("value" )) {
			
			if ( value != null ){
				DyadBaseModel model = getOptionsStore().findModel("id", value);
				if ( model == null ){
					throw new RuntimeException("Cannot find value " + value + " in options of combo field." );
				}					
				result = (X)super.set(name, model);
				setOldRawValue( (String)model.get("description") );
			} else {
				result = super.set(name, null);
				setOldRawValue( null );
			}
			
			clearInvalid();
		} else if (name.equals("options")) {
			updateStore((ArrayList)value);
		}
		
		callSetDependentProperties(name, value);
		
		return result;		
	}
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value")) {
			DyadBaseModel model = null;
			if (value instanceof Integer) {
				model = getDyadBaseModelFromStore((Integer)value);
			} else {
				model = (DyadBaseModel)value;
			}
			setValue(model);
		} else if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		} else if (name.equals("password")) {
			setPassword((Boolean)value);
		} else if (name.equals("required")) {
			Boolean required = (Boolean)value;
			
			if ( required != null ){
				if ( required ){	
					((ComboBoxCustomized) this.getFormViewField()).setAllowBlank(false);
					((ComboBoxCustomized) this.getTableViewField()).setAllowBlank(false);
				} else {
					((ComboBoxCustomized) this.getFormViewField()).setAllowBlank(true);
					((ComboBoxCustomized) this.getTableViewField()).setAllowBlank(true);
				}
			}
		}
	}
	
	public ListStore<DyadBaseModel> getOptionsStore() {
		ListStore<DyadBaseModel> optionsStore = get("optionsStore"); 
		
		if (optionsStore == null) {
			optionsStore = new ListStore<DyadBaseModel>();
			setOptionsStore(optionsStore);
		}
		
		return optionsStore;
	}

	public void setOptionsStore(ListStore<DyadBaseModel> optionsStore) {
		set("optionsStore", optionsStore);
	}

	public ClientComboField(){	
		this.setFormViewField(new ComboBoxCustomized());
		this.setTableViewField(new ComboBoxCustomized());
		
		((ComboBoxCustomized) this.getFormViewField()).setClientField(this);
		((ComboBoxCustomized) this.getTableViewField()).setClientField(this);
	
		((ComboBoxCustomized) this.getFormViewField()).setStore(getOptionsStore());
		((ComboBoxCustomized) this.getTableViewField()).setStore(getOptionsStore());
	}
	
	public ClientComboField(String name){	
		this();		
		this.setName(name);
	}
	
	@SuppressWarnings("unchecked")
	public void updateStore( ArrayList options ){
		this.getOptionsStore().removeAll();
		for (int i = 0; i < options.size(); i++) {
			ArrayList option = (ArrayList)options.get(i);

			DyadBaseModel model = new DyadBaseModel();
			model.set("id", option.get(0) );
			model.set("description", option.get(1) );
			
			this.getOptionsStore().add(model);
		}
	}
	
	public DyadBaseModel getDyadBaseModelFromStore(Integer id) {
		for (DyadBaseModel model : getOptionsStore().getModels()) {
			if (id.equals(model.get("id"))) {
				return model;
			}
		}
		
		return null;
	}
	
	public void setRequired(Boolean required){
		super.setRequired(required);						
	}

	@SuppressWarnings("unchecked")
	public void setValue(DyadBaseModel value) {
		this.getFormViewField().setValue(value);
		this.getTableViewField().setValue(value);
		this.setTableViewValue( value );
	}	
	
	public void setOldRawValue(String value) {
		((ComboBoxCustomized) this.getFormViewField()).setOldRawValue( value );
		((ComboBoxCustomized) this.getTableViewField()).setOldRawValue( value );
	}

	public void setPassword(Boolean password) {
		((ComboBoxCustomized) this.getFormViewField()).setPassword(password);		
		((ComboBoxCustomized) this.getTableViewField()).setPassword(password);		
	}		

	public ColumnConfig getColumnConfig(){
		return ((ComboBoxCustomized) this.getTableViewField()).getColumnConfig();
	}
}
