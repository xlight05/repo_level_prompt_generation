package br.com.dyad.infrastructure.navigation.tests;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class GridTeste extends VariableGrid{

	public GridTeste(Window window, String name) throws Exception {
		super(window, name);
		// TODO Auto-generated constructor stub
	}

	private FieldNumber quantidade;
	private FieldNumber valor;
	private FieldNumber total;
	private Action testeActionGrid = new Action(this, "testeAction") {
		@Override
		public void onClick() throws Exception {
			System.out.println("Clicou na action da grid de variáveis!");
		}
	};
	
	@Override
	public void defineGrid() throws Exception {
		quantidade = new FieldNumber(this, "quantidade");
		valor = new FieldNumber(this, "valor");
		total = new FieldNumber(this, "total"){
			@Override
			public void onCalcField() {
				try {
					if (quantidade.getValue() != null && valor.getValue() != null){						
						total.setValue(quantidade.getDoubleValue() * valor.getDoubleValue());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		total.setIsCalculated(true);
		
	}
	
}