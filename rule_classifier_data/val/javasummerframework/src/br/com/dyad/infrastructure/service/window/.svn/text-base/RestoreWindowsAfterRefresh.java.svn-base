package br.com.dyad.infrastructure.service.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class RestoreWindowsAfterRefresh extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap result = new HashMap();
		List<HashMap> windows = new ArrayList<HashMap>();
		Session session = PersistenceUtil.getSession(getDatabase());
		session.beginTransaction();
		
		try {		
			String classes = "";
			
			for (Window w : getWindowMap().values()) {
				classes+= "'" + w.getClass().getName() + "',";
			}
			
			getWindowMap().clear();
			
			if (classes.length() > 0) {				
				Query query = session.createQuery("from NavigatorEntity where window in (" + classes.substring(0, classes.length() - 1) + ")");
				List<NavigatorEntity> list = query.list();
				
				for (NavigatorEntity navigator : list) {
					HashMap window = new HashMap();
					window.put("windowId", navigator.getId());
					window.put("windowName", navigator.getWindow());
					window.put("styleName", navigator.getCssName());
					windows.add(window);
				}
			}
			
			result.put("windows", windows);			
			session.getTransaction().rollback();
		} catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}		
		
		return result;
	}

}
