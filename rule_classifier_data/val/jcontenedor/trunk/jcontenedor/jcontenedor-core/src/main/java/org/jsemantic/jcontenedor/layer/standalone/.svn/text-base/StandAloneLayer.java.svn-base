package org.jsemantic.jcontenedor.layer.standalone;

import java.util.Map;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StandAloneLayer extends AbstractComponent implements Layer {
	
	/**
	 * 
	 */
	protected String file = null;

	/**
	 * 
	 */
	protected ConfigurableApplicationContext applicationContext = null;
	/**
	 * 
	 */
	protected Layer parent = null;
	
	public StandAloneLayer (ConfigurableApplicationContext context) {
		this.applicationContext = context;
		init();
	}
	
	public StandAloneLayer(String id, String file, int order) {
		this.file = file;
	}
	@Override
	protected void postConstruct() {
		this.applicationContext = new ClassPathXmlApplicationContext();
		//((ClassPathXmlApplicationContext) this.context).setId(super.getId());
		((ClassPathXmlApplicationContext) this.applicationContext)
				.setConfigLocation(this.file);
		this.applicationContext.registerShutdownHook();
	}

	public Object getComponent(String name) {
		return this.applicationContext.getBean(name);
	}

	public Object getComponent(String name, Class<?> clazz) {
		return this.applicationContext.getBean(name, clazz);
	}

	public Map<?, ?> getComponentsOfType(Class<?> clazz) {
		return this.applicationContext.getBeansOfType(clazz);
	}

	public void refresh() {
		this.applicationContext.refresh();
	}

	public void setParent(Layer parent) {
		this.parent = parent;
		this.applicationContext.setParent((ConfigurableApplicationContext) parent
				.getApplicationContext());
	}

	public Layer getParent() {
		return this.parent;
	}
	@Override
	protected void release() {
		this.applicationContext.close();
	}

	public void refresh(String file) {
		this.file = file;
		((ClassPathXmlApplicationContext) this.applicationContext)
		.setConfigLocation(this.file);
		this.refresh();	
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
}
