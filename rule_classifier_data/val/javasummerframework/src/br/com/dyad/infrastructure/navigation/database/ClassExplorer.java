package br.com.dyad.infrastructure.navigation.database;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class ClassExplorer extends Window {
	
	public ClassExplorer(HttpSession httpSession) {
		super(httpSession);
	}

	private DataGrid grid;
	
	@Override
	public void defineWindow() {
		add(new Interaction(this, "default"){

			@Override
			public void defineInteraction() {
				//grid = new DynamicGrid(this.getPanel(), "br.com.dyad.infrastructure.entity.Teste");
				//add(grid);
			}
			
		});		
	}
	

}
