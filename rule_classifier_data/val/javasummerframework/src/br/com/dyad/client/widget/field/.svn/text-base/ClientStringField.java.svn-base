package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.TextFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientStringField extends ClientField{
	private String caseType;
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("allowDecimals")) {
			Boolean boolValue = (Boolean)value;
			((TextFieldCustomized) this.getFormViewField()).setAllowDecimals(boolValue);
			((TextFieldCustomized) this.getTableViewField()).setAllowDecimals(boolValue);
		}
	}
	
	public Boolean getOnlyNumbers() {
		return get("onlyNumbers");
	}
	
	public void setOnlyNumbers(Boolean onlyNumbers) {
		set("onlyNumbers", onlyNumbers);
	}
	
	public ClientStringField(){	
		this.setFormViewField(new TextFieldCustomized());
		this.setTableViewField(new TextFieldCustomized());
		
		((TextFieldCustomized) this.getFormViewField()).setClientField(this);
		((TextFieldCustomized) this.getTableViewField()).setClientField(this);
	
		this.setWidth(250);
	}
	
	public ClientStringField(String name){	
		this();
		this.setName(name);
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public void setRequired(Boolean required){
		super.setRequired(required);
		if ( required != null ){
			if ( required ){	
				((TextFieldCustomized) this.getFormViewField()).setAllowBlank(false);
				((TextFieldCustomized) this.getTableViewField()).setAllowBlank(false);
			} else {
				((TextFieldCustomized) this.getFormViewField()).setAllowBlank(true);
				((TextFieldCustomized) this.getTableViewField()).setAllowBlank(true);
			}
		}					
	}

	@SuppressWarnings("unchecked")
	public void setValue(String value) {
		this.getFormViewField().setValue(value);
		this.getTableViewField().setValue(value);
		this.setTableViewValue( value );
	}	
	
	public void setOldRawValue(String value) {
		((TextFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((TextFieldCustomized) this.getTableViewField()).setOldRawValue(value);
	}

	public void setPassword(Boolean password) {
		((TextFieldCustomized) this.getFormViewField()).setPassword(password);		
		((TextFieldCustomized) this.getTableViewField()).setPassword(password);		
	}		

	public ColumnConfig getColumnConfig(){
		return ((TextFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
}