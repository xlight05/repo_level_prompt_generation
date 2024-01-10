package br.com.dyad.client.gxt.field.customized;

import java.util.Date;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class DateFieldCustomized extends DateField {
	
	private String oldRawValue;
	private ClientField clientField;
	public ColumnConfig columnConfig = null;
	
	public static com.google.gwt.i18n.client.DateTimeFormat DEFAULT_DATE_FORMAT = com.google.gwt.i18n.client.DateTimeFormat.getFormat("dd/MM/yyyy");

	public DateFieldCustomized(){
		this.getPropertyEditor().setFormat( DateFieldCustomized.DEFAULT_DATE_FORMAT );
		this.setSelectOnFocus(true);
	}	
	
	public static final Integer ONE_DAY = 1000    * 24  * 60    * 60;
	//--                                  1000 ms * 24h * 60min * 60s.  
		
	public void setValueAndSincronizer(Date value) {
		ClientGrid grid = this.clientField.getGrid();
		ClientProcess process = grid.getProcess();

		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), this.clientField.getServerObjectId(), value );
		//super.setValue(value);
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
 				Date now = this.formatAutoComplete();
 				this.setOldRawValue(this.getRawValue());
 				this.setValueAndSincronizer(now);	 						
 			} catch (Exception e) {
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

	@SuppressWarnings( "deprecation" )
	private Date formatAutoComplete() throws Exception{
		Date now = new Date();
		if (getRawValue().equalsIgnoreCase("H") ) {	 					
			return now;
		} else 
 		if (getRawValue().equalsIgnoreCase("O") ) {	 					
			now.setTime( now.getTime() - DateFieldCustomized.ONE_DAY );
		} else
		if (getRawValue().equalsIgnoreCase("A") ) {	 					
			now.setTime( now.getTime() + DateFieldCustomized.ONE_DAY );
		} else 
		if ( getRawValue().charAt(0) == '+' ){
			String value = this.getRawValue().trim().replace("+", "");
			Integer daysToAdd = new Integer(value);
			now.setTime( now.getTime() + daysToAdd * DateFieldCustomized.ONE_DAY );
		} else 
		if ( getRawValue().charAt(0) == '-' ){
			String value = this.getRawValue().trim().replace("-", "");
			Integer daysToAdd = new Integer(value);
			now.setTime( now.getTime() - daysToAdd * DateFieldCustomized.ONE_DAY );
		} else {	 					
			String value = this.getRawValue().trim().replace("/", "");
			System.out.println("value=" + value);
			if ( value.length() == 1 || value.length() == 2 ){
				Integer day = new Integer(value);
				if ( day < 1 || day > 31 ){
					throw new Exception();
				}
				now.setDate(day);
			} else 
			if ( value.length() == 3 || value.length() == 4 ){
				Integer day = new Integer(value.substring(0, 2));
				if ( day < 1 || day > 31 ){
					throw new Exception();
				}
				now.setDate(day);
				//
				Integer month = new Integer(value.substring(2,value.length()));
				if ( month < 1 || month > 12 ){
					throw new Exception();
				}
				now.setMonth(month-1);
			} else 
			if(value.length() > 4 ){
				Integer day = new Integer(value.substring(0, 2));
				if ( day < 1 || day > 31 ){
					throw new Exception();
				}
				now.setDate(day);
				//
				Integer month = new Integer(value.substring(2,4));
				if ( month < 1 || month > 12 ){
					throw new Exception();
				}
				now.setMonth(month-1);
				//
				Integer year = null;
				String yearString = value.substring(4, value.length() );
				System.out.println("yearString=" + yearString );
				if ( yearString.length() == 1 || yearString.length() == 2 || yearString.length() == 3 ){
					year = new Integer(yearString);
					year = year + 2000 - 1900;
				} else {
					year = new Integer(yearString);
					year = year - 1900;
				}
				now.setYear(year);
			}
		}
		return now;
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
			
			DateFieldCustomized template = (DateFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig( this.getClientField().getName(), this.getClientField().getLabel(), this.getClientField().getTableViewWidth() );  
			this.columnConfig.setDateTimeFormat( DateFieldCustomized.DEFAULT_DATE_FORMAT );
			this.columnConfig.setEditor(new CellEditor( template ) );
		}
		return this.columnConfig;
	}
};
