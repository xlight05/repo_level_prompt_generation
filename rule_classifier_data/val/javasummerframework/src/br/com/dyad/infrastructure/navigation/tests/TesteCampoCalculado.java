package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;

public class TesteCampoCalculado extends Window {
	
	public TesteCampoCalculado(HttpSession httpSession) {
		super(httpSession);
	}

	Action teste = new Action(this, "parar"){		
		@Override
		public void onClick() throws Exception {
			stopClientTimer("teste");
		}
		
	};
	
	Action criaTask = new Action(this, "criaTask"){		
		@Override
		public void onClick() throws Exception {
			showProgress("Executando o faturamento", 100, 1);
			Thread thread = new Thread(){
				
				@Override
				public void run() {
					int duration = 100;
					
					for (int i = 0; i < 100; i++) {						
						int j = i + 1;
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						TesteCampoCalculado.this.showProgress("AAAAAAAAAAAAAAAAAAAAA" + j, duration, j);
					}
				}
				
			};
			
			thread.start();
		}
		
	};
	
	Action parar = new Action(this, "teste"){		
		@Override
		public void onClick() throws Exception {
			createClientTimer("teste", "correctPassword", 5000);
			createClientTimer("abc", "correctPassword2", 4000);
		}
		
	};
		
	Interaction backup = new Interaction(this, "Backup"){
		@Override
		public void defineInteraction() throws Exception {
			getWindow().enableAndShowActions( teste );
			getWindow().enableAndShowActions( parar );
			getWindow().enableAndShowActions( criaTask );
			add(new GridTeste(this.getWindow(), "GridDeTeste"));
		}
		
	};
	
	@Override
	public void defineWindow() {				
		add(backup);
		add(parar);
		add(criaTask);
		add(teste);		
	}
	
	public void correctPassword() {
		System.out.println("Mensagem do timer");
		alert("Mensagem do timer");
	}
	
	public void correctPassword2() {
		alert("Mensagem 2");
	}

}
