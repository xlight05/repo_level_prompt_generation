package br.com.dyad.infrastructure.navigation;

import javax.servlet.http.HttpSession;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class MenuConfiguration extends Window {
	
	public DataList dataListFiltrado;

	public DataGrid grid = null;
	
	public MenuConfiguration(HttpSession httpSession) {
		super(httpSession);
		dataListFiltrado = DataListFactory.newDataList(getDatabase());
	}
	
	@Override
	public void defineWindow() throws Exception {
		grid = new DataGrid( this, "gridDeDados" );
		dataListFiltrado.executeQuery("from NavigatorEntity" );
		dataListFiltrado.setCommitOnSave(true);
		
		add( new Interaction( this, "exibeVariaveis" ){
			@Override
			public void defineInteraction() throws Exception {
				grid.setBeanName("br.com.dyad.infrastructure.entity.NavigatorEntity");
				grid.setDataList(dataListFiltrado);
				grid.setTitle("Menu Configuration");
				this.add(grid);
			}
		});					
	}		
};