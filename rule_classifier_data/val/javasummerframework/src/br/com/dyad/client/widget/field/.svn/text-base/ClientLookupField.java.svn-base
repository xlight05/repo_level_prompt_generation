package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.LookupFieldCutomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientLookupField extends ClientField{
	
	public <X extends Object> X set(String name, X value) {
		if (name.equals("value")) {
			setIdValue(value == null ? null : (Long)value);
			
			return value;
		} else {
			return super.set(name, value);
		}
		
	}
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		/*if (name.equals("value")) {
			//setValue(value == null ? null : value.toString());
			setIdValue(value == null ? null : (Long)value);
		} else */if (name.equals("oldRawValue")) {
			setOldRawValue(value == null ? null : value.toString());
		} else if (name.equals("rawValue")) {
			setRawValue(value == null ? null : value.toString());
		} else if (name.equals("idValue")) {
			setIdValue((Long)value);
		} else if (name.equals("lastValidValue")) {
			setLastValidValue((String)value);
		} else if (name.equals("validValue")) {
			setValidValue((Boolean)value);
		} else if (name.equals("required")) {
			Boolean required = (Boolean)value;
			
			if ( required != null ){
				if ( required ){	
					((LookupFieldCutomized) this.getFormViewField()).setAllowBlank(false);
					((LookupFieldCutomized) this.getTableViewField()).setAllowBlank(false);
				} else {
					((LookupFieldCutomized) this.getFormViewField()).setAllowBlank(true);
					((LookupFieldCutomized) this.getTableViewField()).setAllowBlank(true);
				}
			}
		} else if (name.equals("lookupDisplayValue")) {
			String lookupDisplayValue = (String)value;
			
			setValue( lookupDisplayValue );
			setOldRawValue( lookupDisplayValue );
			clearInvalid();
			
			if ( value != null ){
				setLastValidValue(lookupDisplayValue);
				setValidValue(true);
			}
		} else if (name.equals("beanName" )) {
			((LookupFieldCutomized) this.getFormViewField()).setBeanName((String)value);
			((LookupFieldCutomized) this.getTableViewField()).setBeanName((String)value);
		} else if (name.equals("findExpress")) {
			((LookupFieldCutomized) this.getFormViewField()).setFindExpress((String)value);
			((LookupFieldCutomized) this.getTableViewField()).setFindExpress((String)value);
		} else if (name.equals("isClassLookup")){
			((LookupFieldCutomized) this.getFormViewField()).setIsClassLookup((Boolean)value);
			((LookupFieldCutomized) this.getTableViewField()).setIsClassLookup((Boolean)value);
		}
	}
			
	public ClientLookupField() {
		this.setFormViewField(new LookupFieldCutomized(getBeanName()));
		this.setTableViewField(new LookupFieldCutomized(getBeanName()));

		((LookupFieldCutomized) this.getFormViewField()).setClientField(this);
		((LookupFieldCutomized) this.getTableViewField()).setClientField(this);

		this.setWidth(100);
	}
	
	public ClientLookupField(String name, String beanName) {
		this();
		this.setName(name);
		this.setBeanName(beanName);
	}	
	
	public String getBeanName() {
		return get("beanName");
	}

	public void setBeanName(String beanName) {
		set("beanName", beanName);				
	}

	public String getFindExpress() {
		return get("findExpress");
	}

	public void setFindExpress(String findExpress) {
		set("findExpress", findExpress);		
	}
	
	public Boolean isClassLookup() {
		return get("isClassLookup");
	}

	public void setIsClassLookup(Boolean isClassLookup) {
		set("isClassLookup", isClassLookup);		
	}

	public void setValue(String value) {
		clearInvalid();
		((LookupFieldCutomized) this.getFormViewField()).setValue(value);
		((LookupFieldCutomized) this.getTableViewField()).setValue(value);
		this.setTableViewValue(value);
	}

	public void setOldRawValue(String value) {
		((LookupFieldCutomized) this.getFormViewField()).setOldRawValue( value );
		((LookupFieldCutomized) this.getTableViewField()).setOldRawValue( value );
	}		

	public void setIdValue(Long id) {
		((LookupFieldCutomized) this.getFormViewField()).setIdValue(id);
		((LookupFieldCutomized) this.getTableViewField()).setIdValue(id);
	}

	public void setLastValidValue( String lookupDisplayValue ){
		((LookupFieldCutomized) this.getFormViewField()).setLastValidValue(lookupDisplayValue);
		((LookupFieldCutomized) this.getTableViewField()).setLastValidValue(lookupDisplayValue);		
	}

	public void setValidValue(Boolean b) {
		((LookupFieldCutomized) this.getFormViewField()).setValidValue(b);
		((LookupFieldCutomized) this.getTableViewField()).setValidValue(b);
	}

	public ColumnConfig getColumnConfig(){
		return ((LookupFieldCutomized) this.getTableViewField()).getColumnConfig();
	}
}