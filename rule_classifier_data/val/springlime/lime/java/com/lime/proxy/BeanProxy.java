package com.lime.proxy;

import java.lang.ref.WeakReference;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lime.listener.ModuleContextObserver;


public class BeanProxy<T> implements WebProxy<T>,ApplicationContextAware {

	private T bean;
	
	private String beanName;
	
	private WeakReference<ApplicationContext> ctx;
	
	public BeanProxy(String beanName){
		this.beanName = beanName;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ctx = new WeakReference<ApplicationContext>(arg0);
	}
	
	/* (non-Javadoc)
	 * @see com.lime.proxy.WebProxy#get()
	 */
	public T get(){
		if(ctx!=null&&ctx.get()!=null)
			return (T) ctx.get().getBean(this.beanName);
		else return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lime.proxy.WebProxy#init(javax.servlet.ServletContext)
	 */
	public void init(ServletContext ctx){
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).registerAware(this);
	}

	/* (non-Javadoc)
	 * @see com.lime.proxy.WebProxy#destroy(javax.servlet.ServletContext)
	 */
	public void destroy(ServletContext ctx){
		((ModuleContextObserver)ctx.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).deregisterAware(this);		
	}
}
