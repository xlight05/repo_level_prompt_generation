package com.lime.proxy;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import com.lime.listener.ModuleContextObserver;


public class SingletonBeanProxy<T> implements ApplicationContextAware,ServletContextAware {

	private T bean;
	
	private String beanName;
	
	public SingletonBeanProxy(String beanName){
		this.beanName = beanName;
	}
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		bean = (T) arg0.getBean(this.beanName);
	}
	
	public T get(){
		return this.bean;
	}
	
	public void init(ServletContext ctx){
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).registerAware(this);
	}

	public void destroy(ServletContext ctx){
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).deregisterAware(this);		
	}

	public void setServletContext(ServletContext servletContext) {
		init(servletContext);
	}

}
