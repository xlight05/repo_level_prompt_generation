package br.com.dyad.infrastructure.navigation.tests;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.annotations.Singleton;
import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;

@Singleton
public class TempWindow extends GenericEntityBeanWindow {
	
	public TempWindow(HttpSession httpSession) {
		super(httpSession);
	}

	@Authorization(permissionFieldName="teste", permissionLabel="Teste")
	private Action ac;
		
	public Action getAc() {
		return ac;
	}

	public void setAc(Action ac) {
		this.ac = ac;
	}

	@Override
	public void defineWindow() throws Exception {
		setBeanName(EntityTemporario.class.getName());
		ArrayList<EntityTemporario> temp = new ArrayList<EntityTemporario>();
				
		for (long i = 0L; i < 100L; i++) {
			EntityTemporario t = new EntityTemporario();
			t.setNome("Nome: " + t.getId());
			t.setEndereco("Endereço: " + t.getId());
			t.setNumero(i);
			
 			temp.add(t);
		}				
		
		this.dataList.setList(temp);			
		super.defineWindow();
		
		this.grid.addWidgetListener(DyadEvents.onAfterPost, new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				System.out.println("***************************");
				
			}
			
		});
		
		this.addWidgetListener(DyadEvents.onClose, new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				TempWindow.this.alert("Disparou o evento onClose da janela!!!");
				
			}
			
		});
		
		ac = new Action(this.grid, "teste"){
			@Override
			public void onClick() throws Exception {
				System.out.println("Teste da Action na grid!");
			}
		};
		
		this.grid.add(ac);
	}	

}