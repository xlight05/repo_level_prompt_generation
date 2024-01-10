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

public class IntegerFieldCustomized extends NumberField{

	protected String format = "##0";
	private ClientField clientField;

	private String oldRawValue;

	public ColumnConfig columnConfig = null;  
	
	private Long longValue;

	
	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public void setValueAndSincronizer(Long value) {
		ClientField clientField = this.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();		
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		setValue(value);			
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
	}			

	protected void onBlur(ComponentEvent arg0) {
		Boolean expression1 = getOldRawValue() == null && ! getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
				this.setOldRawValue(this.getRawValue());
				this.setValueAndSincronizer( getRawValue().equals("") ? null : new Long( getRawValue() ) );	 						
			} catch (Exception e) {
				e.printStackTrace();
				this.setValueAndSincronizer(null);				
			}
		}
		super.onBlur(arg0);
	}
	
	@Override
 	protected void onKeyPress(FieldEvent fe) {
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
 		ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);	 	
 		ClientField.goToNextFieldOnKeyPress(fe, this, 13);
 		super.onKeyPress(fe); 		
	}
	
	public IntegerFieldCustomized(){
		super();
		this.setAutoWidth(false);
		this.setFormat(NumberFormat.getFormat(this.format));
		this.setBaseChars("-0123456789");
		this.setAllowDecimals(false);
		this.addStyleName("x-form-content-align-right");
		this.setAllowBlank(true);
		this.setSelectOnFocus(true);
	}

	public String getOldRawValue() {
		return oldRawValue;
	}

	public void setOldRawValue(String oldRawValue) {
		this.oldRawValue = oldRawValue;
	}

	public ClientField getClientField() {
		return clientField;
	}

	public void setClientField(ClientField clientField) {
		this.clientField = clientField;
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
			IntegerFieldCustomized template = (IntegerFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setAlignment(HorizontalAlignment.RIGHT);  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );
			this.columnConfig.setEditor( new CellEditor( template ) );
		}

		return this.columnConfig;
	}

	@Override
	public Long getValue() {
		if ( super.getValue() instanceof Long ){
			setLongValue((Long)super.getValue());
			return (Long) super.getValue();
		} else {
			//if ( getLongValue() == null ){
				Double doubleValue = (Double)super.getValue();
				Long value = doubleValue != null ? new Long( doubleValue.longValue() ) : null ;
				setLongValue(value);
			//}
		}
		return longValue;
	}
}
