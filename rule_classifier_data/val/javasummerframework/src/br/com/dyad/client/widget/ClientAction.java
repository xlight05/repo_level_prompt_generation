package br.com.dyad.client.widget;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.widget.customized.ActionButtonCustomized;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

public class ClientAction extends DyadBaseModel {

	/*private ActionButtonCustomized button;*/
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("text")) {
			getButton().setText( (String)value );
		} else if (name.equals("iconStyle")) {
			this.getButton().setIconStyle( (String)value );
		} else if (name.equals("enabled")) {			
			Boolean enabled = (Boolean)value;			
			if ( enabled != null ){
				if ( enabled ){
					this.getButton().enable();
				} else {
					this.getButton().disable();
				}
			}
		} else if (name.equals("visible")) {			
			Boolean visible = (Boolean)value;
			if ( visible != null ){
				if ( visible ){
					this.getButton().show();
				} else {
					this.getButton().hide();
				}
			}
		}		
	}
	
	public ClientAction(String string ) {
		this.setButton(new ActionButtonCustomized( this ));
		this.getButton().setText(string);
	}

	public ClientProcess getProcess() {
		return get("process");
	}

	public void setProcess(ClientProcess process) {
		set("process", process);
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof String ){
			return ((String) obj).equalsIgnoreCase((this.getButton().getText()));
		} 
		return ((ClientAction) obj).getButton().getText().equalsIgnoreCase(this.getButton().getText());
	}

	@SuppressWarnings("unchecked")
	public void onClick(){
		Boolean result = this.verifyFieldsFromLastInteraction();
		if ( result != null && result ){
			List<ClientGrid> grids = (List<ClientGrid>)this.getProcess().getCurrentVisibleInteraction().getClientGrids();
			HashMap fieldValues = new HashMap();
			String gridName = "";
			for (Iterator iterator = grids.iterator(); iterator.hasNext();) {
				ClientGrid clientGrid = (ClientGrid) iterator.next();
				gridName = clientGrid.getName();
				if ( clientGrid instanceof ClientVariableGrid ){
					ClientVariableGrid vars = (ClientVariableGrid) clientGrid;
					if ( vars.getFieldValuesToSave().size() > 0 ){
						fieldValues.putAll(vars.getFieldValuesToSave());
					}
				}
			}			
			this.getProcess().dispatchServerActionClick(this.getProcess().getServerObjectId(), this.getServerObjectId(), fieldValues); 
		} 
	}

	public String getServerObjectId() {
		return get("objectId");
	}

	public void setServerObjectId(String serverObjectId) {
		set("objectId", serverObjectId);
	}

	public Boolean verifyFieldsFromLastInteraction() {
		Boolean result = true;
		if ( getValidateLastInteraction() != null && this.getValidateLastInteraction() ){
			result = this.getProcess().verifyFieldsFromCurrentInteraction();
		} 
		return result;
	}

	public Boolean getValidateLastInteraction() {
		return get("validateLastInteraction");
	}

	public void setValidateLastInteraction(Boolean validateLastInteraction) {
		set("validateLastInteraction", validateLastInteraction);
	}; 

	@SuppressWarnings("unchecked")
	public static void synchronizeActionWithServerInformation( ClientProcess clientProcess, HashMap configAction ) throws Exception {
		String actionServerObjectId = (String)configAction.get("objectId");				
		
		if ( actionServerObjectId == null || actionServerObjectId.equals("") ){
			throw new Exception("Action server object id must not be null or empty.");
		} 
		
		ClientAction clientAction = clientProcess.getActionByServerObjectId( actionServerObjectId );
		
		if ( clientAction == null ){
			clientAction = new ClientAction( (String)configAction.get("text") );
			clientProcess.add(clientAction);
		}
		
		clientAction.setDefaultProperties(configAction);
	}

	public ActionButtonCustomized getButton() {
		return get("button");
	}

	public void setButton(ActionButtonCustomized button) {
		set("button", button);
	}
}