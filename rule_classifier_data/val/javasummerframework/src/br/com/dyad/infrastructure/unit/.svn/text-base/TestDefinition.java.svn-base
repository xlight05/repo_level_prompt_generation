package br.com.dyad.infrastructure.unit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDefinition {
	
	private Class clazz;
	private HashMap<Method, Boolean> methods = new HashMap<Method, Boolean>();

	public void addClassMethod(Method method) {
		methods.remove(method);
		methods.put(method, false);
	}
	
	public Class getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	public HashMap<Method, Boolean> getMethods() {
		return methods;
	}
	public void setMethods(HashMap<Method, Boolean> methods) {
		this.methods = methods;
	}

	
}
