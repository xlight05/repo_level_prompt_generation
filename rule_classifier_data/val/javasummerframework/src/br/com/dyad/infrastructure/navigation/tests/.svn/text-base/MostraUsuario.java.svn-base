package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;

public class MostraUsuario extends Window {

	private Action mostraUsuario = new Action(this, "mostraUsuario"){
		@Override
		public void onClick() throws Exception {
			alert(UserInfoAspect.getUser());
		}
	};
	
	private Interaction interaction = new Interaction(this, "it1"){

		@Override
		public void defineInteraction() throws Exception {
			enableAndShowActions(mostraUsuario);			
		}
		
	};
	
	public MostraUsuario(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {		
	}

}
