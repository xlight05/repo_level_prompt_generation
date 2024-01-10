package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.i18n.client.NumberFormat;

public class NumberFieldCustomized extends NumberField{

	private ClientField clientField;
	protected String format = "##0.00";	
	private String oldRawValue;

	public ColumnConfig columnConfig = null;
	
	public NumberFieldCustomized() {
		super();
		this.setAutoWidth(false);
		this.setFormat(NumberFormat.getFormat(this.format));
		this.setBaseChars("-0123456789");
		this.setAllowDecimals(true);
		this.addStyleName("x-form-content-align-right");
		this.setAllowBlank(true);
		this.setSelectOnFocus(true);
	}

	@Override
 	protected void onKeyPress(FieldEvent fe) {
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
 		ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);
 		ClientField.goToNextFieldOnKeyPress(fe, this, 13);
 		super.onKeyPress(fe);
	
	}

	public void setValueAndSincronizer(Double value) {
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
 		this.formatNumberFieldValue();
		Boolean expression1 = getOldRawValue() == null && ! getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
				this.setOldRawValue(this.getRawValue());
				this.setValueAndSincronizer( getRawValue().equals("") ? null : new Double( getRawValue() ) );	 						
			} catch (Exception e) {
				this.setValueAndSincronizer(null);
			}
		}		
 		super.onBlur(arg0);
 	}

 	private void formatNumberFieldValue(){
		if (getRawValue() != null && !getRawValue().equals("")) {
 			try {
 				Double rawvalue = NumberFormat.getFormat(format).parse(getRawValue());
 				setRawValue(NumberFormat.getFormat(format).format(rawvalue));
 			} catch (NumberFormatException e) {
 			}
 		}
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
			
			NumberFieldCustomized template = (NumberFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setAlignment(HorizontalAlignment.RIGHT);  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  

			this.columnConfig.setEditor( new CellEditor( template ) );		
		}

		return this.columnConfig;
	}
}
