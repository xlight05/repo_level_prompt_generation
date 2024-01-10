package br.com.dyad.client.widget.field;

import java.util.Date;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.field.customized.DateFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientSimpleDateField extends ClientField {		
	
	public <X extends Object> X set(String name, X value) {		
		X result = super.set(name, value);
		
		if (name.equals("value" )) {
			setOldRawValue((Date)value);
			clearInvalid();
		}
		
		return result;		
	};
	
	public ClientSimpleDateField(){		
		this.setFormViewField(new DateFieldCustomized());	
		this.setTableViewField(new DateFieldCustomized());

		((DateFieldCustomized) this.getFormViewField()).setClientField(this);
		((DateFieldCustomized) this.getTableViewField()).setClientField(this);
	
		this.setWidth(100);
	}
	
	public ClientSimpleDateField(String name){		
		this();
		this.setName(name);
	}

	public void setRequired(Boolean required){
		super.setRequired(required);
		if ( required != null ){
			if ( required ){	
				((DateFieldCustomized) this.getFormViewField()).setAllowBlank(false);
				((DateFieldCustomized) this.getTableViewField()).setAllowBlank(false);
			} else {
				((DateFieldCustomized) this.getFormViewField()).setAllowBlank(true);
				((DateFieldCustomized) this.getTableViewField()).setAllowBlank(true);
			}
		}					
	}

	public void setValue(Date value) {
		((DateFieldCustomized) this.getFormViewField()).setValue(value);
		((DateFieldCustomized) this.getTableViewField()).setValue(value);
		this.setTableViewValue(value);
	}	
	
	public void setOldRawValue(String value) {
		((DateFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((DateFieldCustomized) this.getTableViewField()).setOldRawValue( value );
	}		
	
	@SuppressWarnings("deprecation")
	public void setOldRawValue(Date value) {
		String formatedValue = "";
		
		if ( value != null ){
			if ( value.getDate() < 9 ){
				formatedValue += "0" + value.getDate();
			} else {
				formatedValue += value.getDate();
			}
			formatedValue += "/";

			int realMonth = value.getMonth() + 1;
			if ( realMonth < 9 ){
				formatedValue += "0" + realMonth;
			} else {
				formatedValue += realMonth;
			}
			formatedValue += "/";		

			formatedValue += ( value.getYear() + 1900 ); 
		}	

		this.setOldRawValue( formatedValue  );
	}
	
	public ColumnConfig getColumnConfig(){
		return ((DateFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
}