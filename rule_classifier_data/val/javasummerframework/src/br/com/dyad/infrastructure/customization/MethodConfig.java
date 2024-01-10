package br.com.dyad.infrastructure.customization;

import java.lang.reflect.Method;

public class MethodConfig {
	
	private Class oldClass;
	private Method oldMethod;
	private Class newClass;
	private Method newMethod;
	private Boolean callSuper;
	
	public Class getOldClass() {
		return oldClass;
	}
	
	public void setOldClass(Class oldClass) {
		this.oldClass = oldClass;
	}
	
	public Method getOldMethod() {
		return oldMethod;
	}
	
	public void setOldMethod(Method oldMethod) {
		this.oldMethod = oldMethod;
	}
	
	public Class getNewClass() {
		return newClass;
	}
	
	public void setNewClass(Class newClass) {
		this.newClass = newClass;
	}
	
	public Method getNewMethod() {
		return newMethod;
	}
	
	public void setNewMethod(Method newMethod) {
		this.newMethod = newMethod;
	}
	
	public Boolean getCallSuper() {
		return callSuper;
	}
	
	public void setCallSuper(Boolean callSuper) {
		this.callSuper = callSuper;
	}	

}
