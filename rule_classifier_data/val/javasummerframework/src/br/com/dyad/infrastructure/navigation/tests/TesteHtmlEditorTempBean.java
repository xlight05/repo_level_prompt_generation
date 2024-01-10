package br.com.dyad.infrastructure.navigation.tests;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;

public class TesteHtmlEditorTempBean extends GenericEntityBeanWindow {

	public TesteHtmlEditorTempBean(HttpSession httpSession) {
		super(httpSession);
	}
	
	@Override
	public void defineWindow() throws Exception {
		setBeanName(BeanEditor.class.getName());
		ArrayList<BeanEditor> temp = new ArrayList<BeanEditor>();
				
		for (int i = 0; i < 10; i++) {
			BeanEditor t = new BeanEditor();
			t.setDescricao("Descrição: " + t.getId());			
 			temp.add(t);
		}
		
		this.dataList.setList(temp);			
		super.defineWindow();
	}

}
