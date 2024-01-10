package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.IntegerFieldCustomized;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientIntegerField extends ClientField{
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value")) {
			setValue((Long)value);
		} else if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		} else if (name.equals("min" )) {
			Double min = (Double)value;
			
			((IntegerFieldCustomized) this.getFormViewField()).setMinValue(min);
			((IntegerFieldCustomized) this.getTableViewField()).setMinValue(min);
		} else if (name.equals("max" )) {
			Double max = (Double)value;
			
			((IntegerFieldCustomized) this.getFormViewField()).setMaxValue(max);
			((IntegerFieldCustomized) this.getTableViewField()).setMaxValue(max);
		} else if (name.equals("required")) {
			Boolean required = (Boolean)value;
			
			if ( required != null ){
				if ( required ){	
					((IntegerFieldCustomized) this.getFormViewField()).setAllowBlank(false);
					((IntegerFieldCustomized) this.getTableViewField()).setAllowBlank(false);
				} else {
					((IntegerFieldCustomized) this.getFormViewField()).setAllowBlank(true);
					((IntegerFieldCustomized) this.getTableViewField()).setAllowBlank(true);
				}
			}	
		}
	}
	
	public ClientIntegerField(){	
		this.setFormViewField(new IntegerFieldCustomized());
		this.setTableViewField(new IntegerFieldCustomized());
		
		((IntegerFieldCustomized) this.getFormViewField()).setClientField(this);
		((IntegerFieldCustomized) this.getTableViewField()).setClientField(this);

		this.setWidth(100);
	}
	
	public ClientIntegerField(String name){	
		this();
		this.setName(name);
	}

	public Double getMin() {
		return get("min");
	}

	public void setMin(Double min) {
		set("min", min);		
	}

	public Double getMax() {
		return get("max");
	}

	public void setMax(Double max) {
		set("max", max);		
	}

	public Boolean getNegativeInRed() {
		return get("negativeInRed");
	}

	public void setNegativeInRed(Boolean negativeInRed) {
		set("negativeInRed", negativeInRed);
	}

	public void setRequired(Boolean required){
		super.setRequired(required);						
	}
	
	public void setValue(Long value) {
		((IntegerFieldCustomized)this.getFormViewField()).setValue(value);
		((IntegerFieldCustomized)this.getTableViewField()).setValue(value);
		this.setTableViewValue(value);
	}		
	
	public void setOldRawValue(String value) {
		((IntegerFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((IntegerFieldCustomized) this.getTableViewField()).setOldRawValue( value );
	}		

	public ColumnConfig getColumnConfig(){
		return ((IntegerFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
}
