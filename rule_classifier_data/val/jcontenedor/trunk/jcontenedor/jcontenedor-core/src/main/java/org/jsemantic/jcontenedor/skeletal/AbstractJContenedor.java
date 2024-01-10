package org.jsemantic.jcontenedor.skeletal;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.manager.LayerManager;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.context.Context;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractService;

public class AbstractJContenedor extends AbstractService implements JContenedor {

	private LayerManager layerManager = null;
	
	public AbstractJContenedor() {
	}
	
	public Object getBean(String id) {
		return layerManager.getRootLayer().getComponent(id);
	}

	public Component getComponent(String id) throws ComponentException {
		return (Component) layerManager.getRootLayer().getComponent(id);
	}

	public Context getContext() {
		return super.getContext();
	}

	public Service getService(String id) throws ServiceException {
		return layerManager.getRegistry().getService(id);
	}

	@Override
	protected void stopService() throws ServiceException {
		this.layerManager.dispose();
	}

	@Override
	protected void startService() throws ServiceException {
		if (!this.layerManager.isInitialized()) {
			this.layerManager.init();
		}
		this.layerManager.resolve();
	}
	
	public void setLayerManager(LayerManager layerManager) {
		this.layerManager = layerManager;
	}

	public Layer getRoot() {
		return this.layerManager.getRootLayer();
	}
}
