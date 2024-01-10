package br.com.dyad.infrastructure.widget;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class Action extends BaseAction{	

	private Action(){
		super();
	}

	public Action(BaseServerWidget parent) {
		super(parent);
	}

	public Action(BaseServerWidget parent, String text ) {
		super(parent, text);
	}

	public Action(Window window, String text, String permissionKeyword ) {
		super( window, text, permissionKeyword );
	}

	public void onClick()  throws Exception {};
}
