package br.com.dyad.infrastructure.reflect;

import java.util.HashMap;

public class SingletonLoader {
	
	private static SingletonLoader instance;
	
	public static SingletonLoader getInstance(){
		return (instance == null ? new SingletonLoader() : instance);
	}
	
	
	private HashMap<String, Object> objects;
	
	private SingletonLoader() {
		objects = new HashMap<String, Object>();
	}	
	
	@SuppressWarnings("unchecked")
	public Object getClassInstance(Class clazz) throws Exception{
		//try{			
			Object object = objects.get(clazz.getName());		
			return object != null ? object : clazz.newInstance(); 
		//}catch(Exception e){
			//throw AppException.createException(e);
		//}
	}

}
