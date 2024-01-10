package br.com.dyad.client.gxt.widget.customized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.grid.ClientGrid;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class GridNavigator {

	protected ClientGrid grid;
	protected ToolBar toolBar = new ToolBar();
	protected TextToolItemCustomized actionLicense = new TextToolItemCustomized();
	protected List<ClientAction> actions;
	
	protected SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {  
		public void componentSelected(ButtonEvent ce) {  			
			ClientAction act = ((ActionButtonCustomized)ce.getButton()).getClientAction();
			act.onClick();									
		}
	};

	public GridNavigator() {
		super();
	}

	public ClientGrid getGrid() {
		return grid;
	}

	public void setGrid(ClientGrid grid) {
		this.grid = grid;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public TextToolItemCustomized getActionLicense() {
		return actionLicense;
	}

	public void setActionLicense(TextToolItemCustomized actionLicense) {
		this.actionLicense = actionLicense;
	}

	public List<ClientAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<ClientAction>();
		}
		return actions;
	}

	public void setActions(List<ClientAction> actions) {
		this.actions = actions;
	}	
	
	public void synchronizeNavigatorActions(HashMap config){
		Integer size = (Integer)config.get("actionCount");
		
		if (size != null && size > 0){			
			for (int i = 0; i < size; i++) {
				Object ob;			
				ob = config.get("ACTION" + i);
				if ( ob != null ){
					HashMap configAction = (HashMap)ob;
					if ( !configAction.isEmpty() ){
						addNavigatorAction(configAction);
					}	
				}		
			}
		}
	}
	
	private void addNavigatorAction(HashMap config) {		
		String text = (String)config.get("text");
		ClientAction clientAction = new ClientAction(text);
		clientAction.setServerObjectId((String)config.get("objectId"));
		getActions().add(clientAction);
		
		clientAction.setProcess(this.getGrid().getProcess());		
		clientAction.getButton().setIconStyle("process-action");
		clientAction.getButton().setToolTip(text);
		clientAction.getButton().setText(text);
		clientAction.getButton().addSelectionListener(selectionListener);
		//clientAction.setGrid(grid);
		toolBar.add(clientAction.getButton());    
	}

}