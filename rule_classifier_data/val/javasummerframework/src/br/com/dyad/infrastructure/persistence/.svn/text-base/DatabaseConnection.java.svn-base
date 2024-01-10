package br.com.dyad.infrastructure.persistence;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import javax.persistence.Entity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreLoadEventListener;
import org.hibernate.event.PreUpdateEventListener;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.customization.ClasspathIterator;
import br.com.dyad.infrastructure.widget.WidgetListener;

public class DatabaseConnection {
	
	public static final String SEPARATOR = "|";
	public static final String MAPPING = "mapping";
	public static final String LISTENER = "listener";
	public static final String MAPPING_FILE = "mapping.cfg";
	public static final String EQUALS = "=";
	public static final String PROPERTY = "property";
	
	public static Vector<Class> mappedClasses = new Vector<Class>();
	public static Hashtable<String, Class> mappedListeners = new Hashtable<String, Class>();
	
	public static void addMappedClass(String className) throws ClassNotFoundException{
		//Não pode carregar nenhuma classe no pacote client
		if (!StringUtils.startsWith(className, "br.com.dyad.client")){
			//Class clazz = Class.forName(className);
			Class clazz = ClassUtils.getClass(className, false);
			Entity an = (Entity)clazz.getAnnotation(Entity.class);			
			
			//Customização de classe
			if (an != null){		
				mappedClasses.add(clazz);
			}
		}
	}
	
	public static void addMappedListener(String className) throws ClassNotFoundException{
		//Não pode carregar nenhuma classe no pacote client
		if (!StringUtils.startsWith(className, "br.com.dyad.client")){
			Class clazz = Class.forName(className);
					
			if (ReflectUtil.inheritsFrom(clazz, PreDeleteEventListener.class)){
				mappedListeners.put("pre-delete", clazz);
			}
			
			if (ReflectUtil.inheritsFrom(clazz, PreInsertEventListener.class)){
				mappedListeners.put("pre-insert", clazz);
			}
			
			if (ReflectUtil.inheritsFrom(clazz, PreUpdateEventListener.class)){
				mappedListeners.put("pre-update", clazz);
			}
			
			if (ReflectUtil.inheritsFrom(clazz, PreLoadEventListener.class)){
				mappedListeners.put("pre-load", clazz);
			}
			
		}
	}
	
	public static DatabaseConfig connect(String database, boolean updateSchema){
		DatabaseConfig databaseConfig = new DatabaseConfig(database);
		getConnectionProperties(databaseConfig, updateSchema);
		getHibernateProperties(databaseConfig);				 
		databaseConfig.setSessionFactory(databaseConfig.getConf().buildSessionFactory());
		return databaseConfig;
	}
	
	private static void getConnectionProperties(DatabaseConfig databaseConfig, boolean updateSchema) {
		AnnotationConfiguration conf = databaseConfig.getConf();
		
		String configFile = null;
		
		if (SystemInfo.getInstance().getApplicationPath() == null){
			String appPath = System.getProperty("user.dir");
			configFile = appPath + File.separator + "war" + File.separator + "connections" + 
			 			 File.separator + databaseConfig.getConfigName() + ".cfg";
		} else {			
			configFile = SystemInfo.getInstance().getApplicationPath() + "connections" + 
						 File.separator + databaseConfig.getConfigName() + ".cfg";
		}

		try{			
			String fileContent = FileUtils.readFileToString(new File(configFile));
			String[] lines = StringUtils.split(fileContent, "\n");
			fileContent = null;
			configFile = null;
			
			for (String item : lines) {
				//property=hibernate.connection.url|value=jdbc:postgresql://10.85.8.236:5432/INFRA
				
				if (item != null && !item.trim().equals("")){
					item = item.trim();					
					String type = StringUtils.substringBefore(item, EQUALS);
										
					if (type.equalsIgnoreCase(PROPERTY)) {
						//Add properties						
						String[] props = StringUtils.split(item, SEPARATOR);
						if (props.length > 1){								
							conf.setProperty(StringUtils.substringAfter(props[0], EQUALS), StringUtils.substringAfter(props[1], EQUALS));
						}
					}
				}
			}
			
			if (updateSchema){
				conf.setProperty("hibernate.hbm2ddl.auto", "update");
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*private static void getHibernateProperties(DatabaseConfig databaseConfig) {
		AnnotationConfiguration conf = databaseConfig.getConf();
		try {
			String configFile = null; 
			
			if (SystemInfo.getInstance().getApplicationPath() == null){
				String appPath = System.getProperty("user.dir");
				configFile = appPath + File.separator + "war" + File.separator + MAPPING_FILE;
			} else {
				configFile = SystemInfo.getInstance().getApplicationPath() + MAPPING_FILE;
			}
			
			String fileContent = FileUtils.readFileToString(new File(configFile));
			String[] lines = StringUtils.split(fileContent, "\n");
			fileContent = null;
			configFile = null;
			
			for (String item : lines) {
				
				if (item != null && !item.trim().equals("")){
					item = item.trim();					
					String type = StringUtils.substringBefore(item, EQUALS);
										
					if (type.equalsIgnoreCase(MAPPING)){
						//Add mapped classes
						String className = StringUtils.substringAfter(item, EQUALS); 
						Class clazz = Class.forName(className);
						System.out.println("mapping class: " + clazz.getName());
						conf.addAnnotatedClass(clazz);
					} else if (type.equalsIgnoreCase(LISTENER)) {
						//Add listeners						
						String[] props = StringUtils.split(item, SEPARATOR);
						if (props.length > 1){								
							conf.setListener(StringUtils.substringAfter(props[0], EQUALS), StringUtils.substringAfter(props[1], EQUALS));
						}
					}
				}
			}			
			
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}*/
	
	private static void getHibernateProperties(DatabaseConfig databaseConfig) {
		if (mappedClasses.size() == 0){
			loadFromClassPath();
		}
		
		AnnotationConfiguration conf = databaseConfig.getConf();
		try {
			for (Class clazz : mappedClasses) {
				conf.addAnnotatedClass(clazz);
			}	
			
			for (String string : mappedListeners.keySet()) {
				conf.setListener(string, ((Class)mappedListeners.get(string)).getName());
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	private static void loadFromClassPath() {
		ClasspathIterator classpath = new ClasspathIterator();
		classpath.addListener(new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				DatabaseConnection.addMappedClass((String)sender);
				DatabaseConnection.addMappedListener((String)sender);
			}
			
		});
		classpath.listClasses();
		
	}

}
