package br.com.dyad.client.widget.field;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.field.customized.ComboBoxCustomized;
import br.com.dyad.client.gxt.field.customized.HtmlEditorCustomized;
import br.com.dyad.client.gxt.field.customized.TextFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ClientHtmlEditorField extends ClientField {

	private Integer height;
	
	public ClientHtmlEditorField() {
		this.setFormViewField(new HtmlEditorCustomized());
		this.setTableViewField(new TextFieldCustomized());
		
		((TextFieldCustomized) this.getTableViewField()).setClientField(this);
		
		this.setWidth(250);
		this.setHeight(100);
	}
	
	public ClientHtmlEditorField(String name) {
		this();
		setName(name);
	}
	
	public void setHeight(Integer height) {
		String strHeight = ""+(height != null && height > 50 ? height : 50);
		this.height = height;

		this.getFormViewField().setHeight(strHeight);
		this.getTableViewField().setHeight(strHeight);
	}

	public Integer getHeight() {
		return height;
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

	public ColumnConfig getColumnConfig(){
		return ((TextFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
	
}
