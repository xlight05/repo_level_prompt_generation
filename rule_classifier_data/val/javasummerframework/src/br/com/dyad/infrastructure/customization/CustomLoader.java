package br.com.dyad.infrastructure.customization;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.NotCustomizable;

@NotCustomizable
public class CustomLoader {
	private static Logger log = Logger.getLogger("CUSTOMIZATION");
	private static CustomLoader instance;
	private HashMap<Class, Class> reference;
	private Hashtable<Class, Hashtable<Method, MethodConfig>> methodReference;
	private Hashtable<Method, Method> mappedMethods;
	
	@SuppressWarnings("unchecked")
	private CustomLoader() {
		reference = new HashMap<Class, Class>();
		methodReference = new Hashtable<Class, Hashtable<Method, MethodConfig>>();
		mappedMethods = new Hashtable<Method, Method>();
	}
	
	public static CustomLoader getInstance() {					
		if (instance == null){
			instance = new CustomLoader();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public Class getCustomClass(Class clazz) {
		if (clazz.getAnnotation(NotCustomizable.class) != null){
			return clazz;
		} else {			
			return reference.get(clazz) == null ? clazz : reference.get(clazz);
		}
	}
	
	public void addCustomClass(Class clazz, Class customClass) {
		 if (reference.get(clazz) == null){
			 if (!ReflectUtil.inheritsFrom(clazz, Customizable.class)){
				 log.log(Level.SEVERE, "Class " + clazz.getName() + " is NOT customizable");
			 } else if (!ReflectUtil.inheritsFrom(customClass, clazz)){
				 log.log(Level.SEVERE, "CAN'T CUSTOMIZE class " + customClass.getName() + " because is not a subclass of " + customClass.getName());
			 } else {				 
				 log.log(Level.WARNING, "Class " + customClass.getName() + " overrides " + customClass.getName());
				 reference.put(clazz, customClass);
			 }
		 }
	}
	
	@SuppressWarnings("unchecked")
	public void customizeMethod(Class oldClass, String oldMethod, Class newClass, Method newMethod, Boolean callSuper) {
		if (!ReflectUtil.inheritsFrom(oldClass, Customizable.class)){
			log.log(Level.SEVERE, "Class " + oldClass.getName() + " is NOT customizable");
		} else if (oldClass != null && oldMethod != null && !oldMethod.equals("")
					&& newClass != null && newMethod != null && !newMethod.equals("")){
			
			for (Method m : oldClass.getMethods()){		
				if (!m.getName().equals(oldMethod)){
					continue;
				}
				boolean match = true;
				Class[] newTypes = newMethod.getParameterTypes();
				Class[] oldTypes = m.getParameterTypes();
									
				int inc = 1;
				if (Modifier.isStatic(m.getModifiers())){
					match = newTypes.length == oldTypes.length;
					inc = 0;
				} else {
					match = newTypes.length == oldTypes.length+1;						
				}
				
				if (match){						
					for (int j = 0; j < oldTypes.length; j++) {
						if (!ReflectUtil.inheritsFrom(newTypes[j+inc], oldTypes[j])){
							match = false;
							break;
						}
					}
				}
				
				if (match){			
					if (oldClass.equals(newClass)){
						log.log(Level.SEVERE, "(Method Customization) The class " + newClass.getName() + " can't customize itself");
					} else {							
						log.log(Level.WARNING, "Method " + newClass.getName() + 
								"." + newMethod + " overrides " + oldClass.getName() + 
								"." + oldMethod );
						
						MethodConfig conf = new MethodConfig();
						conf.setCallSuper(callSuper);		
						conf.setOldClass(oldClass);
						conf.setOldMethod(m);
						conf.setNewClass(newClass);
						conf.setNewMethod(newMethod);
						
						addMethodReference(oldClass, conf);
						mappedMethods.put(m, newMethod);
					}						
					
					return;
				}				
			}			
			
			log.log(Level.SEVERE, "(Method Customization) could not find a match for " + 
					newClass.getName() + "." + newMethod.getName() +
					" on class " + oldClass.getName());
		}		
	}
	
	public MethodConfig getNewMethod(Class clazz, Method method) {
		Hashtable<Method, MethodConfig> map = methodReference.get(clazz);
		MethodConfig mConf = null;
		
		if (map != null){			
			mConf = map.get(method);
		}
		
		return mConf;
	}
	
	private void addMethodReference(Class clazz, MethodConfig conf) {
		Hashtable<Method, MethodConfig> map = methodReference.get(clazz);
		
		if (map == null){
			map = new Hashtable<Method, MethodConfig>();
			methodReference.put(clazz, map);
		}
		
		map.put(conf.getOldMethod(), conf);
	}
	
	//Verifica se o método 1 customiza o método 2 ou vice versa
	public boolean isCustomizedBy(Method method1, Method method2) {
		Method mapped = mappedMethods.get(method1);		
		if (mapped == null){
			mapped = mappedMethods.get(method2);
			
			if (mapped != null){
				return method1.equals(mapped);
			}
		} else {
			return method2.equals(mapped);
		}
		
		return false;
	}

}
