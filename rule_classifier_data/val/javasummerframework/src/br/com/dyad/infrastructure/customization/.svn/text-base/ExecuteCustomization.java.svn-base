package br.com.dyad.infrastructure.customization;

import java.lang.reflect.Method;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import br.com.dyad.infrastructure.annotations.Customize;
import br.com.dyad.infrastructure.widget.WidgetListener;

public class ExecuteCustomization {
	
	private static ClasspathIterator classpath;

	static{
		classpath = new ClasspathIterator();		
		classpath.addListener(new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				customizeClass((String)sender);
			}
			
		});
	}
	
	public static void executeCustomization() {			
		classpath.listClasses();
	}
	
	private static void customizeClass(String className) throws ClassNotFoundException {
		//Não pode carregar nenhuma classe no pacote client
		if (!StringUtils.startsWith(className, "br.com.dyad.client")){
			//Class clazz = Class.forName(className);
			Class clazz = ClassUtils.getClass(className, false);
			Customize an = (Customize)clazz.getAnnotation(Customize.class);	
			
			//Customização de classe
			if (an != null && !an.clazz().equals(Class.class) && an.type().equals(CustomType.CLASS)){				
				CustomLoader.getInstance().addCustomClass(an.clazz(), clazz);
			}
			
			//Customização de métodos
			if (an != null && !an.clazz().equals(Class.class) && an.type().equals(CustomType.METHOD)){
				//Pesquisa os métodos da classe anotada 
				for (Method m : clazz.getMethods()){
					Customize customMethod = m.getAnnotation(Customize.class);
					if (customMethod != null){
												
						CustomLoader.getInstance().customizeMethod(an.clazz(), 
								customMethod.method().equals("") ? m.getName() : customMethod.method(), 
								clazz, 
								m, 
								customMethod.callSuper());
						
					}
				}
			}
		}
	}	

}
