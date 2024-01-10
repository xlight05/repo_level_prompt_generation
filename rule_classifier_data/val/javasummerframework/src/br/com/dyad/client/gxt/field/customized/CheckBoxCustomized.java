package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.form.CheckBox;

public class CheckBoxCustomized extends CheckBox{

	private CheckBoxGroupCustomized checkBoxGroup;
	private String oldRawValue;

	public void setValueAndSincronizer(Boolean value) {
		ClientField clientField = this.checkBoxGroup.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		//super.setValue(value);
		setValue(value);
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
	}	

	protected void onClick(ComponentEvent arg0) {
		Boolean expression1 = getOldRawValue() == null && ! getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
				this.setOldRawValue(this.getRawValue());				
				this.setValueAndSincronizer( getRawValue().equalsIgnoreCase("true") ? true : false );	 						
			} catch (Exception e) {
				this.setValueAndSincronizer(null);
			}
		}
		super.onBlur(arg0);
	}

	public CheckBoxGroupCustomized getCheckBoxGroup() {
		return checkBoxGroup;
	}

	public void setCheckBoxGroup(CheckBoxGroupCustomized checkBoxGroup) {
		this.checkBoxGroup = checkBoxGroup;
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
