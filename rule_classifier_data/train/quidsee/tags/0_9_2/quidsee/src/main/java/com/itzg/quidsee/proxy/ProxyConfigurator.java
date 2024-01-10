package com.itzg.quidsee.proxy;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class ProxyConfigurator {

	protected Logger logger = Logger.getLogger(ProxyConfigurator.class.getName());
	
	public abstract boolean setAutoConfigUrl(String url);

	public abstract void restoreAutoConfigUrl();
}
