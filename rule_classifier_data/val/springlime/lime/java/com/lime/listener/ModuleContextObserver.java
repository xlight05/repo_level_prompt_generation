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

package com.lime.listener;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Observer for a module's spring context, norifies the registered ApplicationContextAware 
 * when a context is started or restarted.
 * 
 * This is used to permit hot-swapping of implementations at runtime.
 * 
 * An instance of this class is registered in each module's ServletContext
 * on initialization to provide an hook point to the ApplicationContext for 
 * objects external to spring (Servlets, Filters, etc...).
 * 
 * @see <a href="http://static.springsource.org/spring/docs/3.0.0.RELEASE/javadoc-api/org/springframework/web/context/support/WebApplicationContextUtils.html">WebApplicationContextUtils</a>
 * 
 * @author Daniele Milani
 *
 */
public class ModuleContextObserver {
	
	public static final String DEFAULT_OBS_NAME = "moduleContextObserver";
	
	private WeakReference<ApplicationContext> ctx;
	
	private WeakHashMap<ApplicationContextAware, Object> awarelist;
	
	public ModuleContextObserver(){
		awarelist = new WeakHashMap<ApplicationContextAware, Object>();
		contextUnloaded();
	}
	
	public void registerAware(ApplicationContextAware aw){
		awarelist.put(aw, null);
		if(ctx.get()!=null)
			aw.setApplicationContext(ctx.get());
	}
	
	public void deregisterAware(ApplicationContextAware aw){
		awarelist.remove(aw);
	}

	public void contextLoaded(ApplicationContext arg0){		
		ctx = new WeakReference<ApplicationContext>(arg0);
		for(ApplicationContextAware aw : awarelist.keySet())
			aw.setApplicationContext(arg0);
	}
	
	public void contextUnloaded(){
		ctx = new WeakReference<ApplicationContext>(null);
	}
	
	

}
