package org.jsemantic.jcontenedor.layer.strategy.standalone;


import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.standalone.StandAloneLayer;
import org.jsemantic.jcontenedor.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.layer.strategy.skeletal.AbstractStrategy;
import org.jsemantic.jservice.core.context.Context;

public class StandaloneLayerStrategy extends AbstractStrategy implements Strategy {
	/**
	 * 
	 */
	protected Layer getLayerInstance(Context context,
			String file, String id, int order) {
		return new StandAloneLayer(id, file, order);
	}
}
