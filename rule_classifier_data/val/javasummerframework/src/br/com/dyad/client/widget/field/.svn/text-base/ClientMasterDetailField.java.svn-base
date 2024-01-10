package br.com.dyad.client.widget.field;

import java.util.HashMap;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.grid.ClientDataGrid;
import br.com.dyad.client.widget.grid.ClientGrid;

public class ClientMasterDetailField extends ClientField {
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		if ( name.equals("readOnly") ){
			Boolean readOnly = (Boolean)value;
			if ( readOnly ){
				getClientDataGrid().getFormViewPanel().disable();
				getClientDataGrid().setEnabledFromClass(false);
			} else {
				getClientDataGrid().enable();
				getClientDataGrid().setEnabledFromClass(true);
			}
		}
	}
	
	public ClientDataGrid getClientDataGrid() {
		return get("clientDataGrid");
	}
	
	public void setClientDataGrid(ClientDataGrid clientDataGrid) {
		set("clientDataGrid", clientDataGrid);
	}
	
	public ClientMasterDetailField() {
		this.initializeClientMasterDetailField();
	}
	
	@SuppressWarnings("unchecked")
	public ClientMasterDetailField(ClientProcess process,String name, Integer columnCount, HashMap colSpanConfig){
		this.setName(name);
		setClientDataGrid(new ClientDataGrid(process,"clientDetailGrid", columnCount, colSpanConfig));
		this.initializeClientMasterDetailField();
	}

	private void initializeClientMasterDetailField(){
		getClientDataGrid().setClientField(this);
	}

	public void setWidget(ClientDataGrid widget) {
		setClientDataGrid(widget);
	}	

	public void setLabel(String label) {
		set("label", label);
	}	

	public void setWidth(Integer width) {
		set("width", width);
	}

	public void setTableViewWidth(Integer tableViewWidth) {
		set("tableViewWidth", tableViewWidth);
	}
	
	public void setName(String name) {
		set("name", name);
	}
	
	public void setVisible(Boolean visible) {
		set("visible", visible);
	}
	
	public void setTableViewVisible(Boolean tableViewVisible) {
		set("tableViewVisible", tableViewVisible);
	}

	@Override
	public void setOldRawValue(String value) {		
	}
	
}