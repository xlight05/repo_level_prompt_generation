package br.com.dyad.client.widget.field;

import java.util.Date;
import br.com.dyad.client.gxt.field.customized.TimeFieldCustomized;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ClientTimeField extends ClientField {
	
	public ClientTimeField(){		
		this.setFormViewField(new TimeFieldCustomized());	
		this.setTableViewField(new TimeFieldCustomized());

		((TimeFieldCustomized) this.getFormViewField()).setClientField(this);
		((TimeFieldCustomized) this.getTableViewField()).setClientField(this);
	
		this.setWidth(100);
	}
	
	public ClientTimeField(String name){		
		this();
		this.setName(name);
	}
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value")) {
			setValue((Date)value);
		} else if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		} else if (name.equals("required")) {
			Boolean required = (Boolean)value;
			
			if ( required != null ){
				if ( required ){	
					((TimeFieldCustomized) this.getFormViewField()).setAllowBlank(false);
					((TimeFieldCustomized) this.getTableViewField()).setAllowBlank(false);
				} else {
					((TimeFieldCustomized) this.getFormViewField()).setAllowBlank(true);
					((TimeFieldCustomized) this.getTableViewField()).setAllowBlank(true);
				}
			}
		}
	}

	public void setRequired(Boolean required){
		super.setRequired(required);						
	}

	public void setValue(Date value) {
		((TimeFieldCustomized) this.getFormViewField()).setValue( TimeFieldCustomized.getTimeString(value) );
		((TimeFieldCustomized) this.getTableViewField()).setValue( TimeFieldCustomized.getTimeString(value) );
		this.setTableViewValue(value);
	}	
	
	public void setOldRawValue(String value) {
		((TimeFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((TimeFieldCustomized) this.getTableViewField()).setOldRawValue( value );
	}
	
	public ColumnConfig getColumnConfig(){
		return ((TimeFieldCustomized) this.getTableViewField()).getColumnConfig();
	}

}
