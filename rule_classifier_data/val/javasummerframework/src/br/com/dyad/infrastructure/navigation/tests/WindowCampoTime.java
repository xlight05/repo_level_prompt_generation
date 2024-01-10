package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldTime;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class WindowCampoTime extends Window {
	
	public WindowCampoTime(HttpSession httpSession) {
		super(httpSession);
		
		mostrar = new Action(this, "mostrar"){
			@Override
			public void onClick() throws Exception {
				Grid grid = WindowCampoTime.this.getGrids().get(0);
				Field fld = grid.getFieldByName("timeField");
				System.out.println("fld.getValue()" + fld.getValue());
				
			}
		};
		
		teste = new Interaction(this, "teste"){
			@Override
			public void defineInteraction() throws Exception {
				getWindow().enableAndShowActions(mostrar);
				add(new GridTime(this.getWindow(), "GridDeTeste"));
			}
			
		};
	}
	
	Action mostrar;		
	Interaction teste;
	
	@Override
	public void defineWindow() {				
		//add(teste);		
	}
}


class GridTime extends VariableGrid{

	public GridTime(Window window, String name) throws Exception {
		super(window, name);
	}

	@Override
	public void defineGrid() throws Exception {
		FieldTime timeField = new FieldTime(this, "timeField");
		//add(timeField);
	}
	
}