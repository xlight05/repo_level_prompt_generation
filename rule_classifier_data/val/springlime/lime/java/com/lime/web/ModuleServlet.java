package com.lime.web;

import java.lang.ref.WeakReference;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.lime.listener.ModuleContextObserver;

public class ModuleServlet extends DispatcherServlet implements ApplicationContextAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8556508447824874134L;
	

	private WeakReference<ApplicationContext> ctx;
	
	
	

	/**
	 * Retrieve a <code>WebApplicationContext</code> from the
	 * <code>ServletContext</code> attribute with the
	 * {@link #setContextAttribute configured name}. The
	 * <code>WebApplicationContext</code> must have already been loaded and
	 * stored in the <code>ServletContext</code> before this servlet gets
	 * initialized (or invoked).
	 * <p>
	 * Subclasses may override this method to provide a different
	 * <code>WebApplicationContext</code> retrieval strategy.
	 * 
	 * @return the WebApplicationContext for this servlet, or <code>null</code>
	 *         if not found
	 * @see #getContextAttribute()
	 */
	@Override
	protected WebApplicationContext findWebApplicationContext() {
		WebApplicationContext wac;
		String attrName = getContextAttribute();
		if (attrName != null) {
			return super.findWebApplicationContext();
		}
		if(ctx!=null&&ctx.get()!=null){
			wac = createWebApplicationContext(ctx.get());
			if (wac == null) {
				throw new IllegalStateException(
						"Unable to create WebApplicationContext for ModuleServlet");
			}
		}
		else{
			throw  new IllegalStateException(
			"Unable to initialize ModuleServlet : Application context not yet started.");
		}
		return wac;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = new WeakReference<ApplicationContext>(arg0);
	}
	
	@Override
	public void destroy() {
		((ModuleContextObserver)getServletContext().getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).deregisterAware(this);	
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		((ModuleContextObserver)config.getServletContext().getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME)).registerAware(this);
		super.init(config);
	}
	
	

}
