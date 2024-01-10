package org.jsemantic.jcontenedor.parser;

import java.util.List;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.context.impl.JContenedorContextImpl;
import org.jsemantic.jcontenedor.impl.JContenedorImpl;
import org.jsemantic.jcontenedor.layer.manager.LayerManager;
import org.jsemantic.jcontenedor.layer.manager.impl.LayerManagerImpl;
import org.jsemantic.jservice.core.context.Context;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class JContenedorFactoryBean implements FactoryBean, ServletContextAware,
		ApplicationContextAware {

	private JContenedor parent = null;

	private Context context = null;

	public JContenedorFactoryBean() {
		context = new JContenedorContextImpl();
	}

	@SuppressWarnings("unchecked")
	private List layers = null;

	@SuppressWarnings("unchecked")
	public Object getObject() throws Exception {
		LayerManagerImpl layerManager = new LayerManagerImpl();
		if (layers != null) {
			layerManager.setLayerFiles(layers);
		}
		layerManager.setContext(context);
		((JContenedorImpl)parent).setLayerManager(layerManager);
		return parent;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return LayerManager.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setParent(JContenedor parent) {
		this.parent = parent;
	}

	public void setLayers(List<String> layers) {
		this.layers = layers;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setServletContext(ServletContext arg0) {
		((JContenedorContextImpl) context).setServletContext(arg0);
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		((JContenedorContextImpl) context).setApplicationContext(arg0);
	}

}
