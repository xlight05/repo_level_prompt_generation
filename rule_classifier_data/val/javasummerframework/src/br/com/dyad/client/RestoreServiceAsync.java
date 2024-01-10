package br.com.dyad.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RestoreServiceAsync {

	public void executeRestore(HashMap<String, String> params, AsyncCallback<Object> callback);
	public void login(String user, String password, AsyncCallback<Boolean> callback);
	
}
