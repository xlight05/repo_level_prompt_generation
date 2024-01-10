/**
 * 
 */
package org.jsemantic.jcontenedor.layer.manager.impl;

import java.util.List;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.manager.LayerManager;
import org.jsemantic.jcontenedor.layer.manager.exception.LayerManagerException;
import org.jsemantic.jcontenedor.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.layer.strategy.standalone.StandaloneLayerStrategy;
import org.jsemantic.jcontenedor.layer.strategy.web.WebLayerStrategy;
import org.jsemantic.jcontenedor.registry.ServiceRegistry;
import org.jsemantic.jcontenedor.registry.impl.ServiceRegistryImpl;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
/**
 * 
 * @author aestevez
 * @see <<Insertar clases relacionadas>>
 */
public class LayerManagerImpl extends AbstractComponent implements LayerManager {
	
	/**
	 * 
	 */
	public static final String SERVICE_LAYER_FILE = "classpath:META-INF/default/contenedor/core/layers/common-services.xml";
	/**
	 * Strategy resolver for web/classpath layer
	 */
	private Strategy layerResolver = null;
	/**
	 * Array containing the application layers
	 */
	private List<Layer> layers = null;
	/**
	 * Layer configuration files
	 */
	private List<String> layerFiles = null;
	/**
	 * 
	 */
	private ServiceRegistry registry = null;

	public void setLayerFiles(List<String> layerFiles) {
		this.layerFiles = layerFiles;
	}

	public Strategy getLayerResolver() {
		return layerResolver;
	}

	public void setLayerResolver(Strategy layerResolver) {
		this.layerResolver = layerResolver;
	}
	
	@Override
	protected void release() {
		if (this.layers != null)
			this.layers.clear();

		this.layers = null;
		this.layerResolver = null;
	}

	@Override
	protected void postConstruct() {
		if (this.layerResolver == null) {
			if (super.getContext() != null) {
				if (super.getContext().isWebContext()) {
					this.layerResolver = new WebLayerStrategy();
					return;
				}
			}
			this.layerResolver = new StandaloneLayerStrategy();
		}
	}

	public void resolve() throws LayerManagerException {
		this.layers = layerResolver
				.resolve(this.layerFiles, super.getContext());
		this.refreshLayers();

		this.registry = new ServiceRegistryImpl(this.getLayer(0));
		this.registry.setContainerContext(super.getContext());
	}

	public Layer getLayer(int order) throws LayerManagerException {
		return this.layers.get(order);
	}

	public void refreshLayer(int id) throws LayerManagerException {
		Layer layer = getLayer(id);
		if (layer != null) {
			layer.refresh();
		}
	}

	public void disposeLayer(int id) throws LayerManagerException {
		Layer layer = getLayer(id);
		if (layer != null) {
			layer.dispose();
		}
	}

	public void refreshLayers() {
		for (Layer layer : layers) {
			layer.refresh();
		}
	}

	public ServiceRegistry getRegistry() throws LayerManagerException {
		return this.registry;
	}

	public Layer getRootLayer() throws LayerManagerException {
		return getLayer(this.layers.size() - 1);
	}

	public void refreshRootLayer() throws LayerManagerException {
		Layer root = this.getRootLayer();
		root.refresh();
	}

	public void refreshRegistryLayer() throws LayerManagerException {
		Layer registry = this.getLayer(0);
		registry.refresh();
	}

}
