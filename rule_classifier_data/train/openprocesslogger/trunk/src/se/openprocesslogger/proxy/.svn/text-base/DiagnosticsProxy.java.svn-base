package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.HashMap;

public class DiagnosticsProxy implements Serializable{
	private static final long serialVersionUID = -2320495435543646386L;
	
	public void startPolling(){
		Diagnostics.instance().startPolling();	
	}
	
	public void stopPolling(){
		Diagnostics.instance().stopPolling();
	}
	
	public Object getValue(String addressName){
		return Diagnostics.instance().getValue(addressName);
	}
	
	public HashMap<String, Object> getAllValues(){
		return Diagnostics.instance().getAllValues();
	}
}
