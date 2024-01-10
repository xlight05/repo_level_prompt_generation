package br.com.dyad.client.gxt.field.customized;

import br.com.dyad.client.upload.UploadActionWindow;
import br.com.dyad.client.upload.UploadWindow;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.TriggerField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class UploadFieldCustomized extends TriggerField<String>  {

	protected Listener<BaseEvent> listener;
	protected UploadWindow uploadWindow; 
	protected String oldRawValue;
	protected ClientField clientField;
	public ColumnConfig columnConfig = null;
	protected Boolean pictureField = false;
	
	public UploadFieldCustomized() {
		this.listener = new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) {
				if ( ! UploadFieldCustomized.this.disabled  ){
					defineLookupWindow();
				}	
			}			
		};
		
		
		this.addListener(Events.TriggerClick, listener);		
	}
	
	public Boolean getPictureField() {
		return pictureField;
	}

	public void setPictureField(Boolean pictureField) {
		this.pictureField = pictureField;
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

	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
	}

	public Listener<BaseEvent> getListener() {
		return listener;
	}

	public void setListener(Listener<BaseEvent> listener) {
		this.listener = listener;
	}

	public UploadWindow getUploadWindow() {
		return uploadWindow;
	}

	public void setUploadWindow(UploadWindow uploadWindow) {
		this.uploadWindow = uploadWindow;
	}

	protected void defineLookupWindow(){		
		if (this.getPictureField() != null && this.getPictureField()){			
			Long pictureId = null;
			if (getPictureField() != null && getPictureField() && getRawValue() != null && !getRawValue().equals("")){
				pictureId = Long.parseLong(getRawValue());
			}
			
			if (pictureId != null){				
				UploadActionWindow uploadActionWindow = new UploadActionWindow(this, this.getAbsoluteLeft(), this.getAbsoluteTop() + 20);				
				uploadActionWindow.setPictureId(pictureId);
				uploadActionWindow.show();
				return;
			}
		}			
		
		if (uploadWindow == null){			
			uploadWindow = new UploadWindow(this);
		}
		
		uploadWindow.setResizable(false);
		uploadWindow.setScrollMode(Scroll.NONE);
		uploadWindow.setHeading("File upload");
		uploadWindow.setPosition(this.getAbsoluteLeft(), this.getAbsoluteTop() + 20);
		uploadWindow.setVisible(true);		
	}
	
	protected void onBlur(ComponentEvent arg0) {
 		Boolean expression1 = getOldRawValue() == null && ! getRawValue().equals("");
 		Boolean expression2 = getOldRawValue() != null && ! getOldRawValue().equals( getRawValue() );
 		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
 			try {	 				
 				this.setOldRawValue(getRawValue());
 				this.setValueAndSincronizer(getRawValue()); 				 					 							
 			} catch (Exception e) {
 				this.setValueAndSincronizer(null);
 			}
 		} 		
 		super.onBlur(arg0);
 	}
	
	public void setValueAndSincronizer(String value) {
		ClientGrid grid = this.clientField.getGrid();
		ClientProcess process = grid.getProcess();

		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), this.clientField.getServerObjectId(), value );
		this.setValue(value); 		
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
	}	
	
	public ColumnConfig getColumnConfig(){
		if ( this.columnConfig == null ){
			
			UploadFieldCustomized template = (UploadFieldCustomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig( this.getClientField().getName(), this.getClientField().getLabel(), this.getClientField().getTableViewWidth() );  
			this.columnConfig.setEditor(new CellEditor( template ) );
		}
		return this.columnConfig;
	}
	
	@Override
 	protected void onKeyPress(FieldEvent fe) {
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );
 		ClientField.tryDispatchFirstActionIfFieldIsLastField(fe, this, 13);	 	
 		ClientField.goToNextFieldOnKeyPress(fe, this, 13);
 		super.onKeyPress(fe);
	}
	
}

