package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class ComboBoxCustomized extends ComboBox<DyadBaseModel>{

	private ClientField clientField;
	private String oldRawValue;
	
	public ColumnConfig columnConfig = null;  
	
	public ComboBoxCustomized() {
		super();
				
		this.setEmptyText("");  
		this.setValueField("id");
		this.setDisplayField("description");
		this.setWidth(150);  
		this.setTypeAhead(true);  
		this.setTriggerAction(TriggerAction.ALL);  	
		this.setSelectOnFocus(true);
	}


	public void setValueAndSincronizer(DyadBaseModel value) {
		ClientField clientField = this.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		//super.setValue(value);
		setValue(value);
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
	}			

 	@Override
 	protected void onBlur(ComponentEvent arg0) {
		Boolean expression1 = getOldRawValue() == null && ! this.getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( this.getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
	 			this.setOldRawValue(this.getRawValue());
	 			if ( this.getRawValue() != null  ){
	 				DyadBaseModel model = this.getStore().findModel( "description", this.getRawValue() );
	 				this.setValueAndSincronizer( model );	
	 			} else {
	 				this.setValueAndSincronizer( null );	
	 			}
			} catch (Exception e) {
				this.setValueAndSincronizer(null);
			}
		}		
 		super.onBlur(arg0);
 	}
 	
 	@Override
 	public void setRawValue(String value) {
 		if ( this.rendered ){
 			value = value != null ? value.trim() : null;
 			if ( value != null && ( value.length() == 0 || value.equals("") ) ){
 				value = null;
 			}
 			super.setRawValue(value);
 		}
 	}
 	
	@Override
 	protected void onKeyPress(FieldEvent fe) {
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
 		ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);
 		ClientField.goToNextFieldOnKeyPress(fe, this, 13);
 		super.onKeyPress(fe);
	}

	public ClientField getClientField() {
		return clientField;
	}

	public void setClientField(ClientField clientField) {
		this.clientField = clientField;
	}

	public String getOldRawValue() {
		return oldRawValue;
	}

	public void setOldRawValue(String oldRawValue) {
		this.oldRawValue = oldRawValue;
	}

	@Override
	public boolean validate() {
		if ( this.isEnabled() ){
			return super.validate();
		} else {
			return true;
		}
	}		

	public ColumnConfig getColumnConfig(){
		if ( columnConfig == null ){
			
			ComboBoxCustomized template = (ComboBoxCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  

			this.columnConfig.setEditor(new CellEditor( template ) );
		}
			
		return this.columnConfig;
	}
}
