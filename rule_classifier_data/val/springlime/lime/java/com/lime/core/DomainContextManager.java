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

package com.lime.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.lime.bean.ModularApplication;
import com.lime.bean.Module;
import com.lime.loader.ModularApplicationLoader;

/**
 * This class acts as a repository for  applications, manages the application loaders
 * and exposes the two static methods moduleDeployed and moduleUndeployed to let modules be registered
 * and unregistered in your domain context.
 * 
 * Note that, due to the use of static properties, this class <b>must</b> be common to all the modules of the same application. 
 * This usually means this class must be deployed in the shared libraries of your server or domain.
 * 
 * On initialization, the class looks for the resource 
 * 
 * 
 * @author Daniele Milani
 *
 */
public class DomainContextManager {

	private static GenericApplicationContext rootCtx;

	private static Map<String, ModularApplication> appdescriptors;

	private static Map<String, ModularApplicationLoader> apploaders;
	
	private static String confPattern = "classpath:lime/root.context.xml";	

    private static Log log ;

	static {
		try {
			log = LogFactory.getLog(DomainContextManager.class);
			
			rootCtx = new GenericApplicationContext();
			
			Resource[] descriptors = new PathMatchingResourcePatternResolver().getResources(confPattern);
			if(descriptors==null || descriptors.length==0)
				throw new Exception("Unable to find lime configuration file : "+confPattern);
			XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(rootCtx);
			xmlReader.loadBeanDefinitions(descriptors);
			rootCtx.refresh();
			
			appdescriptors = rootCtx.getBeansOfType(ModularApplication.class);
			apploaders = new HashMap<String, ModularApplicationLoader>();
			for(String appname:appdescriptors.keySet()){
				apploaders.put(appname, new ModularApplicationLoader(appdescriptors.get(appname),rootCtx));
			}
		} catch (Throwable e) {
			if(log!=null)log.error(e);
			throw new RuntimeException("Unable to initialize lime root Context ",e);
		}
	}

	private DomainContextManager() {
	}

	

	/**
	 * This method registers a module for the lime context.
	 * The module's name is matched with all of the configured applications, if an application
	 * has defined this module as one of its own, the module is passed to the application loader.
	 * 
	 * If the module is the last required for an application, the application context is started.
	 * 
	 * @param module the module to register
	 */
	public static void moduleDeployed(ServletContext ctx) {
		Module module = initModule(ctx);
		log.info(" Module "+module.getName()+" deployed" );
		for (ModularApplicationLoader apploader : apploaders.values()) {
			apploader.moduleDeployed(module);
			if ((!apploader.isstarted()) && apploader.isfullydeployed())
				apploader.start();
		}
	}
	
	/** 
	 * This method unregisters a module for the lime context.
	 * The module's is matched with all of the configured applications, if an application
	 * has this module in its deployed modules, the module is unregistered and removed.
	 * 
	 * If the module is required and the application is running, the application context is stopped.
	 * 
	 * @param module the module to unregister
	 */
	public static void moduleUndeployed(ServletContext ctx) {
		Module module = initModule(ctx);
		log.info(" Module "+module.getName()+" undeployed" );
		for (ModularApplicationLoader apploader : apploaders.values()) {
			apploader.moduleUndeployed(module);
			if (apploader.isstarted() && (!apploader.isfullydeployed()))
				apploader.stop();
		}
	}	
	
	/**
	 * Instantiates a module descriptor. 
	 * 
	 * @param ctx the module's servlet context
	 * @return the module descriptor
	 */
	private static Module initModule(ServletContext ctx){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		String moduleName = ctx.getInitParameter("module");
		if(moduleName == null){
			moduleName = ctx.getContextPath().substring(1);
		}
		
		return new Module(moduleName,ctx,loader);
	}

}
