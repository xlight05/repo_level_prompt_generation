package br.com.dyad.client.widget;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.grid.ClientGrid;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.ui.Widget;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientInteraction extends DyadBaseModel {
	
	private void initialize(){
		this.setClientGrids(new ArrayList<ClientGrid>());
		
		this.setPanel(new VerticalPanel());
		this.getPanel().setLayout(new FlowLayout());
		this.getPanel().setAutoHeight(true);
		this.getPanel().setAutoWidth(true);
		this.getPanel().setScrollMode(Scroll.AUTO);
	}
	
	public ClientInteraction(String name) {
		this.initialize();
		this.setName(name);
	}	
	
	public boolean add(Widget widget){
		if ( widget.getParent() == this.getPanel() ){
			return false;
		}
		return this.getPanel().add(widget);
	}
	
	public boolean add( ClientGrid vg ){
		if ( vg.getMainPanel().getParent() != this.getPanel() ){
			vg.setProcess(this.getProcess());
			this.getClientGrids().add(vg);
			//this.add( (Widget) vg.getFormViewPanel() );
			//return this.add( (Widget) vg.getTableViewPanel() );
			return this.add( (Widget) vg.getMainPanel() );
		}
		return false;
	}

	public ClientProcess getProcess() {
		return get("process");
	}

	public void setProcess(ClientProcess process) {
		set("process", process);
	}

	public VerticalPanel getPanel() {
		return get("panel");
	}

	public void setPanel(VerticalPanel panel) {
		set("panel", panel);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}	

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof String ){
			return ((String) obj).equalsIgnoreCase((this.getName()));
		} 
		return ((ClientInteraction) obj).getName().equalsIgnoreCase(this.getName());
	}

	public String getServerObjectId() {
		return get("objectId");
	}

	public void setServerObjectId(String serverObjectId) {
		set("objectId", serverObjectId);
	}

	public List<ClientGrid> getClientGrids() {
		return get("clientGrids");
	}

	public void setClientGrids(List<ClientGrid> clientGrids) {
		set("clientGrids", clientGrids);
	}

	public ClientGrid getGridByServerObjectId( String gridServerObjectId) {
		for (ClientGrid clientGrid : this.getClientGrids()) {
			if ( clientGrid.getServerObjectId().equals( gridServerObjectId ) ){
				return clientGrid;
			}
		}					
		return null;
	}


	@SuppressWarnings("unchecked")
	public static void synchronizeInteractionWithServerInformation( ClientProcess clientProcess, HashMap configInteraction) throws Exception {
		String interactionServerObjectId = (String)configInteraction.get("objectId");
		
		if ( interactionServerObjectId == null || interactionServerObjectId.equals("") ){
			throw new Exception("Interaction server object id must not be null or empty.");
		} 

		System.out.println("A: Tentando achar a interaction: " + interactionServerObjectId);
		ClientInteraction clientInteraction = clientProcess.getInteractionByServerObjectId( interactionServerObjectId );

		if ( clientInteraction == null ){
			@SuppressWarnings("unused")
			String name = (String)configInteraction.get("name");
			clientInteraction = new ClientInteraction( (String)configInteraction.get("name") );
			/*clientInteraction.setServerObjectId( interactionServerObjectId );*/
			clientProcess.add(clientInteraction);
		}
		
		clientInteraction.setDefaultProperties(configInteraction);
		
		if ( configInteraction.containsKey("removedGrids") ){
			ClientInteraction.removeGrids( clientInteraction, (String)configInteraction.get("removedGrids") );
		}

		Integer size = (Integer)configInteraction.get("gridCount");
		size = size == null ? 0 : size;
		System.out.println("Client - gridCount: " + size);
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = configInteraction.get("GRID" + i);
			if ( ob != null ){
				HashMap configGrid = (HashMap)ob;
				if ( ! configGrid.isEmpty() ){
					ClientGrid.synchronizeGridWithServerInformation( clientProcess, clientInteraction, configGrid );
				}	
			}		
		}
	}

	private static void removeGrids(ClientInteraction clientInteraction, String gridIds) {
		if ( gridIds != null && ! gridIds.equals("") ){
			String[] ids = gridIds.split(";");
			for (int i = 0; i < ids.length; i++) {
				ClientGrid grid = clientInteraction.getGridByServerObjectId( ids[i] );
				if ( grid != null ){
					clientInteraction.getClientGrids().remove(grid);
					//clientInteraction.panel.remove( (Widget) grid.getFormViewPanel() );
					//clientInteraction.panel.remove( ( (Widget) grid.getTableViewPanel() ) );
					clientInteraction.getPanel().remove( ( (Widget) grid.getMainPanel() ) );
					grid = null;
				}
			}
		}
	}	
}
