package org.jsemantic.jcontenedor.layer.web;

import java.util.Map;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.utils.JContenedorUtils;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.jsemantic.jservice.core.context.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class SpringMVCLayer extends AbstractComponent implements Layer {
	/**
	 * 
	 */
	private Context contenedorContext = null;
	/**
	 * 
	 */
	protected Layer parent = null;

	public SpringMVCLayer(String id, int order,
			Context contenedorContext) {
		this.contenedorContext = contenedorContext;
	}

	public Object getComponent(String name) {
		return getDispatcherApplicationContext().getBean(name);
	}

	public Object getComponent(String name, Class<?> clazz) {
		return getDispatcherApplicationContext().getBean(name, clazz);
	}

	public Map<?, ?> getComponentsOfType(Class<?> clazz) {
		return getDispatcherApplicationContext().getBeansOfType(clazz);
	}

	public Layer getParent() {
		return this.parent;
	}

	public void refresh() {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();

		if (dispatcherApplicationContext != null) {
			dispatcherApplicationContext.refresh();
		}
	}

	public void refresh(String file) {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();
		if (dispatcherApplicationContext != null) {
			((XmlWebApplicationContext) dispatcherApplicationContext)
					.setConfigLocation(file);
			this.refresh();
		}
	}

	public void setParent(Layer parent) {
		this.parent = parent;
	}
	@Override
	public void release() {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();

		if (dispatcherApplicationContext != null) {
			dispatcherApplicationContext.close();
		}
	}

	private ConfigurableApplicationContext getDispatcherApplicationContext() {
		ConfigurableApplicationContext dispatcherApplicationContext = (ConfigurableApplicationContext) JContenedorUtils
				.getServletDispatcherContext((ServletContext) this.contenedorContext
						.getExternal());
		return dispatcherApplicationContext;
	}

	@Override
	public void postConstruct() throws ComponentException {
		// TODO Auto-generated method stub
	}
	
	public ApplicationContext getApplicationContext() {
		return getDispatcherApplicationContext();
	}
}
