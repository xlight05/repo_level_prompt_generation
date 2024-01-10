package dovetaildb.api;

import java.util.ArrayList;

import dovetaildb.dbrepository.ParsedRequest;

public class PluginList {
	ArrayList<Plugin> plugins = new ArrayList<Plugin>();

	public void add(Plugin plugin) {
		plugins.add(plugin);
	}

	public void validatePlugins() {
		return;
		/*
		for(int curI=0; curI < plugins.size(); curI++) {
			Plugin curPlugin = plugins.get(curI);
			for(int underI=0; underI < curI; underI++) {
				Plugin other = plugins.get(underI);
				if (!curPlugin.allowedOver(other)) {
					throw new ApiException("PluginOrderNotAllowed", "Plugin \""+curPlugin+"\" is not allowed to be placed over this plugin: \""+other+"\"");
				}
			}
			for(int overI=curI+1; overI < plugins.size(); overI++) {
				Plugin other = plugins.get(overI);
				if (!curPlugin.allowedUnder(other)) {
					throw new ApiException("PluginOrderNotAllowed", "Plugin \""+curPlugin+"\" is not allowed to be placed under this plugin: \""+other+"\"");
				}
			}
		}
		*/
	}

	public ApiService wrapApiService(ApiService api, ParsedRequest request) {
		for(Plugin plugin : plugins) {
			api = plugin.wrapApiService(api, request);
			if (api == null) return null;
		}
		return api;
	}
}
