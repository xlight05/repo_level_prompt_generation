package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class TextAreaFieldCustomized extends TextArea{

	private ClientField clientField;
	private String oldRawValue;
	
	public ColumnConfig columnConfig = null;  

	public TextAreaFieldCustomized(){
		super();
	}

	public void setValueAndSincronizer(String value) {
		ClientField clientField = this.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		//super.setValue(value);
		setValue(value);
	}			

 	@Override
 	protected void onBlur(ComponentEvent arg0) {
		Boolean expression1 = getOldRawValue() == null && ! getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
				this.setOldRawValue(this.getRawValue());
				this.setValueAndSincronizer( getRawValue() );	 						
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
		//System.out.println("KeyPressTextArea=");
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
		if ( this.columnConfig == null ){
			
			TextAreaFieldCustomized template = (TextAreaFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  

			this.columnConfig.setEditor( new CellEditor( template ) );
		}	
		return this.columnConfig;
	}
}