package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldHtmlEditor;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class TesteHtmlEditor extends Window {
	
	FieldHtmlEditor upload;
	
	public TesteHtmlEditor(HttpSession httpSession) {
		super(httpSession);
	}
	
	Action mostraValue = new Action(this, "mostraValue"){
		@Override
		public void onClick() throws Exception {
			System.out.println("Conteudo do editor: " + upload.getValue());
		}
	};

	protected VariableGrid grid = null; 
		
	Interaction backup = new Interaction(this, "Backup"){
		@Override
		public void defineInteraction() throws Exception {
			getWindow().enableAndShowActions(mostraValue);
			add(grid);
		}
		
	};
	
	@Override
	public void defineWindow() throws Exception {				
		//add(backup);				
		//add(create);
		//add(download);
		//add(mostraValue);
		grid = new VariableGrid(this, "grid"){

			@Override
			public void defineGrid() throws Exception {
				setTitle("Database Backup");
				
				upload = new FieldHtmlEditor(this, "editor");
				upload.setRequired(true);
				upload.setName("Editor");
				upload.setWidth(600);
				upload.setOrder(1);						
			}
			
		};
	}

}
