package br.com.dyad.client.widget.field;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.field.customized.CheckBoxGroupCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ClientBooleanField extends ClientField{
	
	public ClientBooleanField(){
		this.setFormViewField(new CheckBoxGroupCustomized());
		this.setTableViewField(new CheckBoxGroupCustomized());

		((CheckBoxGroupCustomized) this.getFormViewField()).setClientField(this);
		((CheckBoxGroupCustomized) this.getFormViewField()).setClientField(this);
	
		this.setWidth(15);
	}

	public <X extends Object> X set(String name, X value) {
		X result = super.set(name, value);
		
		if (name.equals("value" )) {
			setOldRawValue( value != null && value.equals(true) ? "true" : "false" );
			clearInvalid();
		}
		
		return result;		
	};
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value")) {
			setValue((Boolean)value);
		} else if (name.equals("rawValue")) {
			setRawValue(value == null ? null : value.toString());
		} else if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		}
	}
	
	public ClientBooleanField(String name){
		this();
		this.setName(name);
	}

	public void setValue(Boolean value) {
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setValue(value);
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setValue(value);
	}

	public void setRawValue(String value) {
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setRawValue( value );
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setRawValue( value );
	}	
	
	public void setOldRawValue(String value) {
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setOldRawValue( value );
		((CheckBoxGroupCustomized) this.getFormViewField()).getChecked().setOldRawValue( value );
	}		

	public ColumnConfig getColumnConfig(){
		return ((CheckBoxGroupCustomized) this.getFormViewField()).getColumnConfig();
	}
}
