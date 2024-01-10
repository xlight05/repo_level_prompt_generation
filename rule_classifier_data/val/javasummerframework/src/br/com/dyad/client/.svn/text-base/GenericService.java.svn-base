package br.com.dyad.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;

public interface GenericService extends RemoteService {
	
	public static final String GET_REQUEST = "GET_REQUEST";
	public static final String GET_DATABASE_FILE = "$$$DATABASE_CONFIG_FILE$$";
	public static final String USER_KEY = "$$userKey$$@";
	public static final String SESSION_ID = "$$sessionId$$@";
	public static final String USER_LANGUAGE = "$$userLanguage$$";
	public static final String USER_LOGIN = "$$userLogin$$";
	public static final String SINGLETON = "$$SINGLETON__PROCESS$$";
	public static final String WINDOW_MAP_TOKEN = "$$WINDOW_MAP$$";
	
	@SuppressWarnings("unchecked")
	public HashMap getServiceValue(String serviceClass, HashMap params) throws Exception;
	
	public void getSystemInfo() throws Exception;
	
}
