package br.com.dyad.client.gxt.field.customized;

import java.util.ArrayList;
import java.util.List;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.field.ClientStringField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Element;

@SuppressWarnings("unchecked")
public class TextFieldCustomized extends TextField{

	private ClientField clientField;
	private String oldRawValue;
	
	public ColumnConfig columnConfig = null;
	protected int lastKeyCode;
	protected List<Character> allowed;
	protected String baseChars = "0123456789";	
	protected boolean allowDecimals = true;
	protected boolean allowNegative = true;
	protected String decimalSeparator = ".";	
		
	public int getLastKeyCode() {
		return lastKeyCode;
	}

	public void setLastKeyCode(int lastKeyCode) {
		this.lastKeyCode = lastKeyCode;
	}

	public List<Character> getAllowed() {
		return allowed;
	}

	public void setAllowed(List<Character> allowed) {
		this.allowed = allowed;
	}

	public String getBaseChars() {
		return baseChars;
	}

	public void setBaseChars(String baseChars) {
		this.baseChars = baseChars;
	}

	public boolean isAllowDecimals() {
		return allowDecimals;
	}

	public void setAllowDecimals(boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}

	public boolean isAllowNegative() {
		return allowNegative;
	}

	public void setAllowNegative(boolean allowNegative) {
		this.allowNegative = allowNegative;
	}

	public String getDecimalSeparator() {
		return decimalSeparator;
	}

	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}

	public TextFieldCustomized(){
		super();
		this.setSelectOnFocus(true);
	}
	
	@Override
	protected void onRender(Element target, int index) {
		super.onRender(target, index);
		
		allowed = new ArrayList<Character>();
	    for (int i = 0; i < baseChars.length(); i++) {
	      allowed.add(baseChars.charAt(i));
	    }

	    if (allowNegative) {
	      allowed.add('-');
	    }
	    if (allowDecimals) {
	      for (int i = 0; i < decimalSeparator.length(); i++) {
	        allowed.add(decimalSeparator.charAt(i));
	      }
	    }
	}

	public void setValueAndSynchronizer(String value) {
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
		Boolean expression1 = getOldRawValue() == null && ! this.getRawValue().equals("");
		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( this.getRawValue() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
	 			this.setOldRawValue(this.getRawValue());
				this.setValueAndSynchronizer( this.getRawValue() );	 						
			} catch (Exception e) {
				this.setValueAndSynchronizer(null);
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
 	protected void onKeyDown(FieldEvent fe) {
 		// TODO Auto-generated method stub
 		super.onKeyDown(fe);
 		this.lastKeyCode = fe.getKeyCode();
 	}
 	
	@Override
 	protected void onKeyPress(FieldEvent fe) {
		Boolean onlyNumbers = false;
		
		if (getClientField() != null) {
			onlyNumbers =  ((ClientStringField)getClientField()).getOnlyNumbers();
		}
		
		if (onlyNumbers != null && onlyNumbers) {
			char key = (char) fe.getKeyCode();

		    if (fe.isSpecialKey(lastKeyCode) || lastKeyCode == KeyCodes.KEY_BACKSPACE
		        || lastKeyCode == KeyCodes.KEY_DELETE || fe.isControlKey()) {
		      return;
		    }

		    if (!allowed.contains(key)) {
		      fe.stopEvent();
		    }
		}
		
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
			
			TextFieldCustomized template = (TextFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  

			this.columnConfig.setEditor(new CellEditor( template ) );
		}
			
		return this.columnConfig;
	}
}