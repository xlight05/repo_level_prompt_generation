package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;

public class HtmlEditorCustomized extends HtmlEditor {
	
	private ClientField clientField;
	private String oldRawValue;

	public void setValueAndSincronizer(String value) {
		ClientField clientField = this.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		super.setValue(value);
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
	}
	
	@Override
	protected void onKeyUp(FieldEvent fe) {
		super.onKeyUp(fe);
		onBlur(null);
	}
	
 	@Override
 	protected void onBlur(ComponentEvent arg0) {
		Boolean expression1 = getOldRawValue() == null && ! this.getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( this.getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
	 			this.setOldRawValue(this.getRawValue());
				this.setValueAndSincronizer( this.getRawValue() );	 						
			} catch (Exception e) {
				this.setValueAndSincronizer(null);
			}
		}		
 		super.onBlur(arg0);
 	}
 	
 	@Override
 	protected void onKeyDown(FieldEvent fe) {
 		super.onKeyDown(fe);
 		onBlur(null);
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
		//ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
 		//ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);
 		//ClientField.goToNextFieldOnKeyPress(fe, this, 13);
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

}
