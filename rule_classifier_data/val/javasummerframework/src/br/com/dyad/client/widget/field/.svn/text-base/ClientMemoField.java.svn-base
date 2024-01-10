package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.IntegerFieldCustomized;
import br.com.dyad.client.gxt.field.customized.TextAreaFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientMemoField extends ClientField{
	/*private Integer height;*/
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value")) {
			setValue((String)value);
		} else if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		} else if (name.equals("height")) {
			Integer height = (Integer)value;
			String strHeight = ""+(height != null && height > 50 ? height : 50);

			this.getFormViewField().setHeight(strHeight);
			this.getTableViewField().setHeight(strHeight);
		}
	}
	
	public <X extends Object> X set(String name, X value) {
		X result = super.set(name, value);
		
		if (name.equals("required")) {
			Boolean required = (Boolean)value;
			
			if ( required != null ){
				if ( required ){	
					((TextAreaFieldCustomized) this.getFormViewField()).setAllowBlank(false);
					((TextAreaFieldCustomized) this.getTableViewField()).setAllowBlank(false);
				} else {
					((TextAreaFieldCustomized) this.getFormViewField()).setAllowBlank(true);
					((TextAreaFieldCustomized) this.getTableViewField()).setAllowBlank(true);
				}
			}	
		}
		
		callSetDependentProperties(name, value);
		
		return result;		
	}
	
	public ClientMemoField(){
		this.setFormViewField(new TextAreaFieldCustomized());
		this.setTableViewField(new TextAreaFieldCustomized());

		((TextAreaFieldCustomized) this.getFormViewField()).setPreventScrollbars(true);  
		((TextAreaFieldCustomized) this.getTableViewField()).setClientField(this);
		
		((TextAreaFieldCustomized) this.getFormViewField()).setPreventScrollbars(true);  
		((TextAreaFieldCustomized) this.getTableViewField()).setClientField(this);

		this.setWidth(250);
		this.setHeight(100);
	}
	
	public ClientMemoField(String name){
		this();
		this.setName(name);
	}

	public Integer getHeight() {
		return get("height");
	}

	public void setHeight(Integer height) {
		set("height", height);
	}

	public void setValue(String value) {
		((TextAreaFieldCustomized) this.getFormViewField()).setValue(value);
		((TextAreaFieldCustomized) this.getTableViewField()).setValue(value);
		this.setTableViewValue( value );
	}
	
	public void setOldRawValue(String value) {
		((TextAreaFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((TextAreaFieldCustomized) this.getTableViewField()).setOldRawValue( value );
	}		

	public ColumnConfig getColumnConfig(){
		return ((TextAreaFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
}