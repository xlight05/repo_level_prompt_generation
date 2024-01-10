package org.jsemantic.jcontenedor.layer.strategy;

import java.util.List;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.strategy.exception.StrategyException;
import org.jsemantic.jservice.core.context.Context;

/**
 * 
 * @author adolfo
 * 
 */
public interface Strategy {
	/**
	 * 
	 * @param files
	 * @param applicationContext
	 * @return
	 * @throws StrategyException
	 */
	public List<Layer> resolve(List<String> files, Context context)
			throws StrategyException;

}
