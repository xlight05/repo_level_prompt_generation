package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class TesteEventosField extends Window {	
	
	VariableGrid gridTeste;
	
	Interaction it = new Interaction(TesteEventosField.this){

		@Override
		public void defineInteraction() throws Exception {
			gridTeste = new VariableGrid(this.getWindow(), "Grid de teste") {
				FieldString field1 = new FieldString(this, "field1") {

					public void onAfterChange() {
						System.out.println("Executando o método onAfterChange");
						System.out.println("AFTER=" + getValue());
					}

					public void onBeforeChange() {
						System.out.println("Executando o método onBeforeChange");
						System.out.println("BEFORE=" + getValue());
					}
				};

				@Override
				public void defineGrid() throws Exception {
					
				}

			};	
			
			add(gridTeste);
		}
		
	};
	
	public TesteEventosField(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		add(it);
	}

}
