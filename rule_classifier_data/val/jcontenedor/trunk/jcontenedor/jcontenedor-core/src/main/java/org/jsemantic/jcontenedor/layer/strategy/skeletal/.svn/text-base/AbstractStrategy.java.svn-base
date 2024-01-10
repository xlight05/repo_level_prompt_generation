package org.jsemantic.jcontenedor.layer.strategy.skeletal;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsemantic.jcontenedor.layer.Layer;
import org.jsemantic.jcontenedor.layer.manager.impl.LayerManagerImpl;
import org.jsemantic.jcontenedor.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.layer.strategy.exception.StrategyException;
import org.jsemantic.jcontenedor.layer.web.SpringMVCLayer;
import org.jsemantic.jservice.core.context.Context;
/**
 * 
 * @author adolfo
 *
 */
public abstract class AbstractStrategy implements Strategy {
	/**
	 * 
	 */
	public List<Layer> resolve(List<String> files, Context context)
			throws StrategyException {

		List<Layer> layers = new LinkedList<Layer>();
		this.processFiles(files);

		Layer parent = null;
		int i = 0;
		for (String layerFile : files) {
			Layer temp = getLayerInstance(context, layerFile, layerFile, i++);
			temp.init();
			layers.add(temp);
			if (parent != null) {
				temp.setParent(parent);
			}
			parent = temp;
		}
		
		Layer dispatcherlayer = processDispatcherLayer("dispatcher-servlet", i,
				context);
		
		if (dispatcherlayer != null) {
			dispatcherlayer.setParent(parent);
			layers.add(dispatcherlayer);
		}
		
		return layers;
	}

	private Layer processDispatcherLayer(String id, int order,
			Context context) {
		Layer layer = null;
		if (context == null)
			return layer;
		if (context.isWebContext()) {
			layer = new SpringMVCLayer(id, order, context);
			return layer;
		}
		return layer;
	}

	/**
	 * 
	 * @param regExp
	 * @param layerFiles
	 * @return
	 */
	private int findFilePosition(String regExp, List<String> layerFiles) {
		Pattern pattern = Pattern.compile(regExp);
		int i = 0;
		for (String file : layerFiles) {
			Matcher matcher = pattern.matcher(file);
			if (matcher.find()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * 
	 * @param files
	 * @return
	 */
	private List<String> processFiles(List<String> files) {

		if (files == null) {
			//files = new ArrayList<String>();
			throw new StrategyException();
		}

		//int position = this.findFilePosition("services-*", files);
		int position = this.findFilePosition("^*-registry.xml$", files);
		String serviceLayerFile = null;
		if (position == -1) {
			serviceLayerFile = LayerManagerImpl.SERVICE_LAYER_FILE;
			files.add(0, serviceLayerFile);
		} else {
			serviceLayerFile = files.get(position);
			files.remove(position);
			files.add(0, serviceLayerFile);
		}
		return files;
	}

	/**
	 * 
	 * @param applicationContext
	 * @param file
	 * @param id
	 * @param order
	 * @return
	 */
	protected abstract Layer getLayerInstance(Context context,
			String file, String id, int order);

}
