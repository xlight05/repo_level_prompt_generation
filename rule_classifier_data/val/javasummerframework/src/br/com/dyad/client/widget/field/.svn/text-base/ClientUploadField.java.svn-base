package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.UploadFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ClientUploadField extends ClientField {

	public ClientUploadField() {
		this.setFormViewField(new UploadFieldCustomized());
		this.setTableViewField(new UploadFieldCustomized());

		((UploadFieldCustomized)this.getFormViewField()).setClientField(this);
		((UploadFieldCustomized)this.getTableViewField()).setClientField(this);
		
		this.setWidth(100);
	}
	
	public ClientUploadField(String name) {
		this();
		this.setName(name);
	}
	
	public void setPictureField(Boolean picture) {
		((UploadFieldCustomized)this.getFormViewField()).setPictureField(picture);
		((UploadFieldCustomized)this.getTableViewField()).setPictureField(picture);
	}
	
	public void setRequired(Boolean required){
		super.setRequired(required);
		if ( required != null ){
			if ( required ){	
				((UploadFieldCustomized) this.getFormViewField()).setAllowBlank(false);
				((UploadFieldCustomized) this.getTableViewField()).setAllowBlank(false);
			} else {
				((UploadFieldCustomized) this.getFormViewField()).setAllowBlank(true);
				((UploadFieldCustomized) this.getTableViewField()).setAllowBlank(true);
			}
		}					
	}

	public void setValue(String value) {
		((UploadFieldCustomized) this.getFormViewField()).setValue(value);
		((UploadFieldCustomized) this.getTableViewField()).setValue(value);
		this.setTableViewValue(value);
	}		
	
	public void setOldRawValue(Long value) {
		((UploadFieldCustomized) this.getFormViewField()).setOldRawValue(value != null ? value.toString() : null);
		((UploadFieldCustomized) this.getTableViewField()).setOldRawValue(value != null ? value.toString() : null);
	}
	
	public ColumnConfig getColumnConfig(){
		return ((UploadFieldCustomized) this.getTableViewField()).getColumnConfig();
	}

	@Override
	public void setOldRawValue(String value) {
		((UploadFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((UploadFieldCustomized) this.getTableViewField()).setOldRawValue(value);		
	}
	
}
