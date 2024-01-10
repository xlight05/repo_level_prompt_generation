package org.zls.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Server {

	private static Server instance;
	private ServerServiceAsync proxy;
	
	private Server() {
		
		proxy = (ServerServiceAsync) GWT.create(ServerService.class);
		((ServiceDefTarget) proxy).setServiceEntryPoint(GWT.getModuleBaseURL() + "server");
	}
	
	public static Server getInstance() {
		if(instance == null)
			instance = new Server();
		return instance;
	}
	
	public void randomFunction(AsyncCallback callback) {
		
		proxy.randomFunction(callback);
	}
}
