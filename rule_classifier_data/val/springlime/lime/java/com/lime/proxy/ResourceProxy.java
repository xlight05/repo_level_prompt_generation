package com.lime.proxy;

import java.lang.ref.WeakReference;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.lime.listener.ModuleContextObserver;
import com.lime.loader.ModularApplicationResourceLoader;

public class ResourceProxy implements WebProxy<Resource>,ApplicationContextAware{
	
	private WeakReference<ModularApplicationResourceLoader> loader;
	private String path;
	private String modulename;
	
	public ResourceProxy(String path,String modulename){
		this.path=path;
		this.modulename=modulename;	
	}
	public ResourceProxy(String path){
		this.path=path;
	}

	public Resource get() {
		if(modulename!=null)return loader.get().getResource(path,modulename);
		else return loader.get().getResource(path);
	}

	public void init(ServletContext ctx) {
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).registerAware(this);
	}

	public void destroy(ServletContext ctx) {
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).deregisterAware(this);	
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ModularApplicationResourceLoader bean = (ModularApplicationResourceLoader)arg0.getBean(ModularApplicationResourceLoader.BEAN_NAME);
		this.loader = new WeakReference<ModularApplicationResourceLoader>(bean);
	}
	
}
