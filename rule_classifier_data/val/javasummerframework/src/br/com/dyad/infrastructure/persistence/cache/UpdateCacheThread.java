package br.com.dyad.infrastructure.persistence.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.infrastructure.annotations.CacheClass;
import br.com.dyad.infrastructure.customization.ClasspathIterator;
import br.com.dyad.infrastructure.service.ConnectionService;
import br.com.dyad.infrastructure.widget.WidgetListener;

public class UpdateCacheThread extends Thread {
	protected ClasspathIterator classpath = new ClasspathIterator();
	protected List<Class> classes = new ArrayList<Class>();
	public static final long UPDATE_TIME = (1000 * 60 * 10); 
	
	@Override
	public void run() {
		classpath.addListener(new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				String className = (String)sender;
				
				if (!StringUtils.startsWith(className, "br.com.dyad.client")){
					Class clazz = ClassUtils.getClass(className, false);
					CacheClass annotation = (CacheClass)clazz.getAnnotation(CacheClass.class);
					
					if (annotation != null) {
						if (classes.indexOf(clazz) == -1) {							
							classes.add(clazz);
						}
					}
				}
			}
			
		});
		
		//Percorre o classpath
		classpath.listClasses();		
		
		ConnectionService conService = null;
		HashMap map = null;
		List connections = null;
		
		try{
			conService = new ConnectionService();
			//pega todos os bancos cadastrados
			map = conService.getServiceValue(null);			
			connections = (List)map.get("connections");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		//cada banco tem sua thread independente, se der erro em um não afeta o outro
		for(Object con : connections) {
			DyadBaseModel model = (DyadBaseModel)con;
			UpdateListFromDb updateList = new UpdateListFromDb((String)model.get("connection"), classes);
			updateList.start();
		}
	}
	
}


class UpdateListFromDb extends Thread {
	
	public static final long UPDATE_TIME = (1000 * 60); 
	private String database;
	private List<Class> classes;

	public UpdateListFromDb(String database, List<Class> classes) {
		this.database = database;
		this.classes = classes;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				sleep(UPDATE_TIME);
				
				for (Class clazz : classes) {									
					Cache.getInstance().synchronizeDataList(database, clazz);				
				}
				
			} catch (Exception e) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.log(Level.FATAL, e);
				throw new RuntimeException(e);				
			}
		}
	}
}