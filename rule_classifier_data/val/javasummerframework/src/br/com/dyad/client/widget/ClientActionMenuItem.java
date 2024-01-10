package br.com.dyad.client.widget;


import java.util.HashMap;

import br.com.dyad.client.grid.DynamicGrid;

import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class ClientActionMenuItem extends MenuItem {
	public ClientActionMenuItem(String string) {
		super(string);
	}

	private String serverObjectId;
	private ClientProcess process;
	private DynamicGrid grid;
	
	public ClientProcess getProcess() {
		return process;
	}

	public void setProcess(ClientProcess process) {
		this.process = process;
	}	
	
	public DynamicGrid getGrid() {
		return grid;
	}

	public void setGrid(DynamicGrid grid) {
		this.grid = grid;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof String ){
			return ((String) obj).equalsIgnoreCase((this.getText()));
		} 
		return ((ClientActionMenuItem) obj).getText().equalsIgnoreCase(this.getText());
	}

	public void onClick(){}

	public String getServerObjectId() {
		return serverObjectId;
	}

	public void setServerObjectId(String serverObjectId) {
		this.serverObjectId = serverObjectId;
	};	
	
	@SuppressWarnings("unchecked")
	public static void sincronizeActionMenuItemWithServerInformation( ClientProcess clientProcess, HashMap configActionMenuItem) {
		String actionMenuItemServerObjectId = (String)configActionMenuItem.get("objectId");				
		if ( actionMenuItemServerObjectId == null || actionMenuItemServerObjectId.equals("") ){
			/*try {
				//throw AppException.createException("Action menu item server object id must not be null or empty.");
			} catch (AppException e) {
				e.printStackTrace();
			}*/
		} else {

			ClientActionMenuItem clientActionMenuItem = clientProcess.getActionMenuItemByServerObjectId( actionMenuItemServerObjectId );

			if ( clientActionMenuItem == null ){
				clientActionMenuItem = new ClientActionMenuItem( (String)configActionMenuItem.get("text") );
				clientActionMenuItem.setServerObjectId( actionMenuItemServerObjectId );
				clientProcess.add(clientActionMenuItem);
			}

			String text = (String)configActionMenuItem.get("text");
			if ( text != null ){
				clientActionMenuItem.setText( text );
			}	
				
			String iconStyle = (String)configActionMenuItem.get("iconStyle");
			if ( iconStyle != null ){
				clientActionMenuItem.setIconStyle( iconStyle );
			}	
				
			Boolean enabled = (Boolean)configActionMenuItem.get("enabled");			
			if ( enabled != null ){
				if ( enabled ){
					clientActionMenuItem.enable();
				} else {
					clientActionMenuItem.disable();
				}
			}	

			Boolean visible = (Boolean)configActionMenuItem.get("visible");
			if ( visible != null ){
				if ( visible ){
					clientActionMenuItem.show();
				} else {
					clientActionMenuItem.hide();
				}
			}
		}
	}	

}
