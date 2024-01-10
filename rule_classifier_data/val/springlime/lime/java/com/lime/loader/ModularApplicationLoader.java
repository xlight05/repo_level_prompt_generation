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

package com.lime.loader;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

import com.lime.bean.ModularApplication;
import com.lime.bean.Module;
import com.lime.listener.ModuleContextObserver;

/**
 * Manages the loading of an application, creation and destruction of common and
 * module specific context and registration of beans in each module's
 * ServletContext.
 * 
 * A loader is instanced, for each of the application configured, at server
 * start-up; the loader is invoked every-time a module is deployed or undeployed
 * on the server.
 * 
 * 
 * 
 * @author Daniele Milani
 * 
 */
public class ModularApplicationLoader {

	public final static String SERVLET_CONTEXT_ATTR_NAME = "servletContext";

	private ModularApplication app;

	private HashMap<String, Module> deployedmodules;

	private ExtendedClassLoader springcontextloader;

	private boolean[] deployflags;

	private boolean started;

	private ApplicationContext rootctx;

	private GenericApplicationContext commontctx;

	private static Log log;

	/**
	 * Creates a loader.
	 * 
	 * @param app
	 *            the modular application descriptor
	 * @param rooctx
	 *            the root application context
	 */
	public ModularApplicationLoader(ModularApplication app,
			ApplicationContext rooctx) {
		this.log = LogFactory.getLog(ModularApplicationLoader.class);
		this.app = app;
		this.rootctx = rooctx;
		this.deployflags = new boolean[app.getRequiredModules().length];
		this.deployedmodules = new HashMap<String, Module>();
		this.springcontextloader = new ExtendedClassLoader();
	}

	/**
	 * The method checks whether all of the required modules of the application
	 * have been deployed.
	 * 
	 * @return true if all of required modules are deployed
	 */
	public boolean isfullydeployed() {
		for (boolean flag : deployflags) {
			if (!flag)
				return false;
		}
		return true;
	}

	/**
	 * The method checks if the application is running.
	 * 
	 * @return true if the application is running
	 */
	public boolean isstarted() {
		return started;
	}

	/**
	 * Starts the application : initializes the common context, creates a
	 * specific context for each module and registers beans in each
	 * servletcontext
	 * 
	 */
	public void start() {
		loadCommonContext();
		log.info("Application " + app.getName() + " common context loaded");
		for (Module module : deployedmodules.values()) {
			loadExtContext(module);
			log.info("Application " + app.getName() + " " + module.getName()
					+ " context loaded");
			extendServletContext(module);
			log.info("Application " + app.getName() + " " + module.getName()
					+ " servlet context loaded");
		}
		started = true;
		log.info("Application " + app.getName() + " started");
	}

	/**
	 * Stops the application : Destroy common and specific contexts
	 * 
	 */
	public void stop() {
		for (Module module : deployedmodules.values()) {
			if (module.getModulectx() != null) {
				module.getModulectx().destroy();
			}
			log.info("Application " + app.getName() + " " + module.getName()
					+ " context destroyed");
		}
		commontctx.destroy();
		started = false;
		log.info("Application " + app.getName() + " common context destroyed");
	}

	/**
	 * Invoked when a module is the deployed on the server. If the module
	 * belongs to this application, it is registered in the loader.
	 * 
	 * @param deployed
	 *            the module descriptor.
	 */
	public void moduleDeployed(Module deployed) {
		String name = deployed.getName();
		Module module = deployedmodules.get(name);
		if (module == null) {
			for (int i = 0; i < app.getRequiredModules().length; i++) {
				if (app.getRequiredModules()[i].equals(name)) {
					deployflags[i] = true;
					springcontextloader.addLoader(deployed.getLoader());
					deployedmodules.put(name, deployed);
					log.info("Module " + name + " loaded for application "
							+ app.getName());
					return;
				}
			}
			for (String mod : app.getOptionalModules()) {
				if (mod.equals(name)) {
					deployedmodules.put(name, deployed);
					if (started) {
						loadExtContext(deployed);
						extendServletContext(deployed);
					}
					log.info("Additional Module " + name
							+ " loaded for application " + app.getName());
					return;
				}
			}
		}
	}

	/**
	 * Invoked when a module is the undeployed on the server. If the module is
	 * registered in this loader, it is removed.
	 * 
	 * @param undeployed
	 *            the module descriptor.
	 */
	public void moduleUndeployed(Module undeployed) {
		String name = undeployed.getName();
		Module module = deployedmodules.get(name);
		if (module != null) {
			for (int i = 0; i < app.getRequiredModules().length; i++) {
				if (app.getRequiredModules()[i].equals(name)) {
					deployflags[i] = false;
					break;
				}
			}
			springcontextloader.removeLoader(module.getLoader());
			if (module.getModulectx() != null)
				module.getModulectx().destroy();
			deployedmodules.remove(name);
			log.info("Module " + name + " unloaded for application "
					+ app.getName());
		}
	}

	/**
	 * Creates and initializes the application common context.
	 * 
	 */
	private void loadCommonContext() {
		try {
			commontctx = new GenericApplicationContext(rootctx);
			commontctx.setAllowBeanDefinitionOverriding(false);
			Resource[] descriptors = new PathMatchingResourcePatternResolver(
					springcontextloader)
					.getResources(app.getCommonCtxPattern());
			if (descriptors != null && descriptors.length > 0) {
				XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(
						commontctx);
				xmlReader.loadBeanDefinitions(descriptors);
			} else {
				log.warn(new String("No descriptors found for " + app.getName()
						+ " common context"));
			}
			commontctx.setClassLoader(springcontextloader);
			commontctx.getBeanFactory().setBeanClassLoader(springcontextloader);
			commontctx.getBeanFactory().registerSingleton(
					ModularApplicationResourceLoader.BEAN_NAME,
					new ModularApplicationResourceLoader(deployedmodules));
			commontctx.getBeanFactory().registerSingleton(ModularApplication.BEAN_NAME,
					app);
			commontctx.refresh();
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to load common context for application "
							+ app.getName(), e);
		}
	}

	/**
	 * Creates and initializes a module-specific context.
	 * 
	 * @param module
	 *            the module descriptor.
	 */
	private void loadExtContext(final Module module) {
		try {
			ClassLoader loader = module.getLoader();
			final Resource[] descriptors = new PathMatchingResourcePatternResolver(
					loader).getResources(app.getExtCxtPattern());
			if (descriptors != null && descriptors.length > 0) {
				AbstractRefreshableWebApplicationContext ctx = new AbstractRefreshableWebApplicationContext() {

					@Override
					protected void loadBeanDefinitions(
							DefaultListableBeanFactory arg0)
							throws IOException, BeansException {

						XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(
								arg0);
						xmlReader.setBeanClassLoader(module.getLoader());
						xmlReader.setResourceLoader(new PathMatchingResourcePatternResolver(
								module.getLoader()));
						xmlReader.loadBeanDefinitions(descriptors);
						// arg0.registerSingleton(MODULE_DESCRIPTOR_NAME,module);
					}

				};
				ctx.setAllowBeanDefinitionOverriding(false);
				ctx.setParent(commontctx);
				ctx.setClassLoader(loader);
				ctx.setServletContext(module.getServletContext());
				ctx.refresh();
				module.setModulectx(ctx);
			} else {
				log.warn(new String("No descriptors found for "
						+ module.getName() + " module context"));
			}
		} catch (Exception e) {
			log.error("Unable to load extended context for module "
					+ module.getName(), e);
		}
	}

	/**
	 * Registers beans in the module's ServletContext
	 * 
	 * @param module
	 *            the module descriptor
	 */
	private void extendServletContext(Module module) {
		try {
			ConfigurableListableBeanFactory factory = commontctx
					.getBeanFactory();

			// The resource loader is automatically extended in the
			// servletcontext
			module.getServletContext().setAttribute(ModularApplicationResourceLoader.BEAN_NAME,
					new ModularApplicationResourceLoader(deployedmodules));

			BeanDefinition beandef;
			Object servletCtxAttr;
			for (String beanName : factory.getBeanDefinitionNames()) {
				beandef = factory.getBeanDefinition(beanName);
				System.err.println(beandef.getScope());
				if ((servletCtxAttr = beandef
						.getAttribute(SERVLET_CONTEXT_ATTR_NAME)) != null) {
					if (((String) servletCtxAttr).equalsIgnoreCase("extend")) {
						module.getServletContext().setAttribute(beanName,
								factory.getBean(beanName));
					}
				}
			}

			// The extended context may not be present for this module
			if (module.getModulectx() != null) {
				factory = module.getModulectx().getBeanFactory();
				for (String beanName : factory.getBeanDefinitionNames()) {
					beandef = factory.getBeanDefinition(beanName);
					if ((servletCtxAttr = beandef
							.getAttribute(SERVLET_CONTEXT_ATTR_NAME)) != null) {
						if (((String) servletCtxAttr)
								.equalsIgnoreCase("extend")) {
							module.getServletContext().setAttribute(beanName,
									factory.getBean(beanName));
						}
					}
				}
			}

			if (module.getModulectx() != null) {
				// External objects are notified of the context loading through
				// the Observer
				((ModuleContextObserver) module.getServletContext()
						.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME))
						.contextLoaded(module.getModulectx());
			} else {
				// External objects are notified of the context loading through
				// the Observer
				((ModuleContextObserver) module.getServletContext()
						.getAttribute(ModuleContextObserver.DEFAULT_OBS_NAME))
						.contextLoaded(commontctx);
			}

		} catch (Exception e) {
			log.error("Unable to extend servlet context for module "
					+ module.getName(), e);
		}
	}
}
