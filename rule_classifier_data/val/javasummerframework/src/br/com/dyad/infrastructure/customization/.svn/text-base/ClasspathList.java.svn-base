package br.com.dyad.infrastructure.customization;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.persistence.cache.UpdateCacheThread;
import br.com.dyad.infrastructure.service.GenericServiceImpl;

public class ClasspathList implements ServletContextListener {

	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//Configurando o path da aplicação		
		String path = sce.getServletContext().getRealPath("/");
		if ((path.charAt(path.length() - 1) + "").equals(File.separator)){
			path = path.substring(0, path.length() - 1);
		}
		//System.setProperty("app.dyad.path", path);
		
		String classpath = path + File.separator + "WEB-INF" + File.separator + "classes";
		System.setProperty("app.dyad.classpath", classpath);
		System.setProperty("app.dyad.projectClasspath", path);
		
		ExecuteCustomization.executeCustomization();
		
		//Atualiza o cache no tempo determinado, iniciando neste momento
		if (SystemInfo.getInstance().getApplicationPath() == null || SystemInfo.getInstance().getApplicationPath().equals("")){
			try {
				GenericServiceImpl.getSystemInfo(sce.getServletContext());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		UpdateCacheThread updateCacheThread = new UpdateCacheThread();
		updateCacheThread.start();
	}
	
	 

}