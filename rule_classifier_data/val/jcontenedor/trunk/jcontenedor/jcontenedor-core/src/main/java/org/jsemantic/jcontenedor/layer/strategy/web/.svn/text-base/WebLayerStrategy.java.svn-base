package org.jsemantic.jcontenedor.layer.strategy.web;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.layer.strategy.skeletal.AbstractStrategy;
import org.jsemantic.jcontenedor.layer.web.WebLayer;
import org.jsemantic.jservice.core.context.Context;

public class WebLayerStrategy extends AbstractStrategy implements Strategy {

	/**
	 * 
	 */
	protected Layer getLayerInstance(Context context, String file,
			String id, int order) {
		return new WebLayer(id, file, context, order);
	}
	
}
