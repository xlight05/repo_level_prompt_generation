package br.com.dyad.infrastructure.unit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Test;
import br.com.dyad.infrastructure.customization.ClasspathIterator;
import br.com.dyad.infrastructure.widget.WidgetListener;

public class TestCaseRunner {
	private List<WidgetListener> listeners = new ArrayList<WidgetListener>();	
	private HashMap<String, TestDefinition> classes = new HashMap<String, TestDefinition>();
	
	public void addListener(WidgetListener listener) {
		listeners.add(listener);
	}
	
	private void callListeners(TestResult result) throws Exception {
		for (WidgetListener temp : listeners) {
			temp.handleEvent(result);
		}
	}
		
	public List<WidgetListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<WidgetListener> listeners) {
		this.listeners = listeners;
	}

	public HashMap<String, TestDefinition> getClasses() {
		return classes;
	}

	public void setClasses(HashMap<String, TestDefinition> classes) {
		this.classes = classes;
	}

	public void executeTests(final String database) throws Exception {
		listClasses();
		
		for (TestDefinition testDef : classes.values()) {
			Object newInstance = testDef.getClazz().newInstance();
			
			for (Method m : testDef.getMethods().keySet()) {				
				try{								
					m.invoke(newInstance, null);
					callListeners(new TestResult(testDef.getClazz().getName(), m.getName(), true, null));
				} catch(Exception e) {
					callListeners(new TestResult(testDef.getClazz().getName(), m.getName(), false, e));
					e.printStackTrace();
				}
			}
		}
	}
	
	public TestResult executeTest(Object obj, Method m) throws Exception {
		try{								
			m.invoke(obj, null);
			TestResult testResult = new TestResult(obj.getClass().getName(), m.getName(), true, null);
			callListeners(testResult);
			return testResult;
		} catch(Exception e) {
			TestResult testResult = new TestResult(obj.getClass().getName(), m.getName(), false, e);
			callListeners(testResult);
			return testResult;
		}
	}
	
	public void listClasses() {
		if (classes.size() > 0) {
			return;
		}
		
		ClasspathIterator classpath = new ClasspathIterator();
		classpath.addListener(new WidgetListener() {

			@Override
			public void handleEvent(Object sender) throws Exception {
				String className = (String)sender;
				//Não pode carregar nenhuma classe no pacote client
				if (!StringUtils.startsWith(className, "br.com.dyad.client")){
					//Class clazz = Class.forName(className);
					Class clazz = ClassUtils.getClass(className, false);
					
					if (ReflectUtil.inheritsFrom(clazz, TestCase.class) && !clazz.equals(TestCase.class)){
						TestDefinition testDef = new TestDefinition();
						testDef.setClazz(clazz);
						
										
						for (Method m : clazz.getMethods()) {					
							Test annotationTest = (Test)m.getAnnotation(Test.class);
							
							if (annotationTest != null) {
								if (m.getParameterTypes().length == 0) {
									testDef.addClassMethod(m);
								}
							}
						}
						
						TestCaseRunner.this.classes.put(clazz.getName(), testDef); 
					}
					
				}				
								
			}

		});
		
		classpath.listClasses();
	}

}
