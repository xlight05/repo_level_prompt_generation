package br.com.dyad.client.gxt.field.customized;

import java.util.Date;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class TimeFieldCustomized extends TextField<String> {
	
	private String oldRawValue;
	private ClientField clientField;
	public ColumnConfig columnConfig = null;
	
	//public static com.google.gwt.i18n.client.DateTimeFormat DEFAULT_TIME_FORMAT = com.google.gwt.i18n.client.DateTimeFormat.getFormat("HH:mm");

	public TimeFieldCustomized(){
		//this.getPropertyEditor().setFormat( TimeFieldCustomized.DEFAULT_TIME_FORMAT );
		//this.setFormat(DEFAULT_TIME_FORMAT);
		this.setSelectOnFocus(true);
	}	
	
	public static final Integer ONE_DAY = 1000    * 24  * 60    * 60;
	//--                                  1000 ms * 24h * 60min * 60s.  
		
	public void setValueAndSincronizer(Date value) {
		ClientGrid grid = this.clientField.getGrid();
		ClientProcess process = grid.getProcess();

		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), this.clientField.getServerObjectId(), value );
		this.setValue(getTimeString(value)); 		
		
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
		if (getRawValue() == null || getRawValue().trim().equals("")){
			return null;
		}
		
		Date now = new Date();
		
		if (getRawValue().equalsIgnoreCase("H") ) {	 					
			return now;
		} else if ( getRawValue().charAt(0) == '+' ){
			String value = this.getRawValue().trim().replace("+", "");
			Integer hours = new Integer(value);			
			now.setHours(now.getHours() + hours);					
		} else if ( getRawValue().charAt(0) == '-' ){
			String value = this.getRawValue().trim().replace("-", "");
			Integer hours = new Integer(value);
			now.setHours(now.getHours() - hours);
		} else {	 					
			String value = this.getRawValue().trim().replace(":", "");
			
			if ( value.length() < 4  ){
				while (value.length() < 4){
					value += '0';
				}
			} else if(value.length() > 4 ){
				value = value.substring(0, 5);
			}
			
			Integer hours = Integer.parseInt(value.substring(0, 2));
			Integer minutes = Integer.parseInt(value.substring(2, 4));
			now.setHours(hours);
			now.setMinutes(minutes);
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
			
			TimeFieldCustomized template = (TimeFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig( this.getClientField().getName(), this.getClientField().getLabel(), this.getClientField().getTableViewWidth() );  
			//this.columnConfig.setDateTimeFormat( TimeFieldCustomized.DEFAULT_TIME_FORMAT );
			this.columnConfig.setEditor(new CellEditor( template ) );
		}
		return this.columnConfig;
	}
	
	public static String getTimeString(Date date) {
		if (date != null){
			String hours = "" + date.getHours();
			while (hours.length() < 2){
				hours = "0" + hours;
			}
			
			String minutes = "" + date.getMinutes();
			while (minutes.length() < 2){
				minutes = "0" + minutes;
			}
			
			return hours + ":" + minutes;
		}
		
		return "";
	}

}
