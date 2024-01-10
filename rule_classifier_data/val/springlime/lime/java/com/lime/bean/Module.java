/*
 Copyright (c) 2010 Daniele Milani

 Permission is hereby granted, free of charge, to any person
 obtaining a copy of this software and associated documentation
 files (the "Software"), to deal in the Software without
 restriction, including without limitation the rights to use,
 copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the
 Software is furnished to do so, subject to the following
 conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 OTHER DEALINGS IN THE SOFTWARE.
 
 */

package com.lime.bean;

import java.lang.ref.WeakReference;

import javax.servlet.ServletContext;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

/**
 *  A bean describing a module.
 * 
 * A bean of this type is initialized by Lime for every module
 * of your application that is deployed. The bean is also registered
 * in the module's context with name "moduleDescriptor"
 * 
 * ServletContext and Classloader of a module are referenced through weak references,
 * to not interfere with their lyfecycle.
 * 
 * @author Daniele Milani
 *
 */
public class Module {

	public final static String BEAN_NAME = "moduleDescriptor";
	
	private WeakReference<ServletContext> servletcontext;
	private WeakReference<ClassLoader> loader;
	private AbstractRefreshableWebApplicationContext modulectx;
	private String name;
	private DefaultResourceLoader resloader;
	private String contextpath;
	
	/**
	 * @param name the name of the module
	 * @param context the servlet context of the module
	 * @param loader the classloader used by the module
	 */
	public Module(String name,ServletContext context,ClassLoader loader) {
		super();
		this.servletcontext = new WeakReference<ServletContext>(context);
		this.loader = new WeakReference<ClassLoader>(loader);
		this.name = name;
		this.resloader = new DefaultResourceLoader(loader);
		contextpath = context.getContextPath();
	}

	/**
	 * @return the servlet context of the module
	 */
	public ServletContext getServletContext() {
		return servletcontext.get();
	}

	/**
	 * @return the name of the module
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the classloader used by the module
	 */
	public ClassLoader getLoader() {
		return loader.get();
	}
	
    
	/**
	 * @return the module's specific spring context
	 */
	public AbstractRefreshableWebApplicationContext getModulectx() {
		return modulectx;
	}
	

	/**
	 * @param modulectx the module's specific spring context
	 */
	public void setModulectx(AbstractRefreshableWebApplicationContext modulectx) {
		this.modulectx = modulectx;
	}

	/**
	 * @return the default resource loader of the module
	 */
	public DefaultResourceLoader getResloader() {
		return resloader;
	}

	/**
	 * @return the context path of the module
	 */
	public String getContextpath() {
		return contextpath;
	}
	
	
	
}
