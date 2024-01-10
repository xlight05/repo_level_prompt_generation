package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.persistence.cache.Cache;

public class UsandoClasseComCache extends GenericEntityBeanWindow {
	
	public UsandoClasseComCache(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		setBeanName(Language.class.getName());		
		this.dataList = Cache.getInstance().getDataList(UserInfoAspect.getDatabase(), Language.class);			
		super.defineWindow();
	}
	
	public void setDataToDataList( Class clazz ) throws Exception {}

	@Override
	public void executeQuery() throws Exception {}	
}
