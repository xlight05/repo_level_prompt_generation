package br.com.dyad.client.gxt.widget.customized;

import br.com.dyad.client.widget.grid.ClientGrid;

import com.extjs.gxt.ui.client.widget.form.FormPanel;

public class FormPanelCustomized extends FormPanel {
	
	ClientGrid clientGrid;
	
	public FormPanelCustomized(){
		super();
		//this.setCollapsible(true);
		
	}

	/*public FormPanelCustomized( String headingAndTitle ){
		super();
		//this.setHeading(headingAndTitle);
		//this.setCollapsible(true);
	}*/

	public ClientGrid getClientGrid() {
		return clientGrid;
	}

	public void setClientGrid(ClientGrid clientGrid) {
		this.clientGrid = clientGrid;
	}
}
