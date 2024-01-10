package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.widget.field.ClientField;

import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class CheckBoxGroupCustomized extends  CheckBoxGroup{

	private CheckBoxCustomized checked;  

	public ColumnConfig columnConfig = null;  
	
	private ClientField clientField;	
	
	public CheckBoxGroupCustomized(){
		this.checked = new CheckBoxCustomized();
		this.checked.setBoxLabel("");
		this.checked.setCheckBoxGroup(this);
		this.add(checked);
	}	
			
	@Override
	protected void onKeyPress(FieldEvent fe) {
		super.onKeyPress(fe);
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
		ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);
		ClientField.goToNextFieldOnKeyPress(fe, this, 13);
	}

	public ClientField getClientField() {
		return clientField;
	}

	public void setClientField(ClientField clientField) {
		this.clientField = clientField;
	}

	public CheckBoxCustomized getChecked() {
		return checked;
	}
	
	public ColumnConfig getColumnConfig(){				
		if ( this.columnConfig == null ){
			CheckBoxGroupCustomized template = (CheckBoxGroupCustomized) this.getClientField().getTableViewField();

			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  
			this.columnConfig.setEditor( new CellEditor( template.getChecked() ) );
		}	
		return this.columnConfig;
	}
}
