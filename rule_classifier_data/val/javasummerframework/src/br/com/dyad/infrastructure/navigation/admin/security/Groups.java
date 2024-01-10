package br.com.dyad.infrastructure.navigation.admin.security;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.entity.UserGroup;
import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;

public class Groups extends GenericEntityBeanWindow {
	public Groups(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		this.setBeanName( UserGroup.class.getName() );
		super.defineWindow();
	}
};