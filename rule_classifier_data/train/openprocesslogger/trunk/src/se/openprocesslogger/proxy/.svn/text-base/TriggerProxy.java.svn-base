package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.HashMap;

public class TriggerProxy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 352145293920972741L;

	private String trigOption;
	
	private HashMap<String, Object> settings;

	private long triggerDelay;
	
	public TriggerProxy(){
		settings = new HashMap<String, Object>();
	}
	
	public String getTrigOption() {
		return trigOption;
	}
	public void setTrigOption(String trigOption) {
		this.trigOption = trigOption;
	}
	
	public void setTriggerDelay(long trigDelay){
		this.triggerDelay = trigDelay;
	}
	public long getTriggerDelay(){
		return this.triggerDelay;
	}
	
	
	public void setProperty(String name, Object val){
		settings.put(name, val);
	}
	
	public Object getProperty(String name){
		return settings.get(name);
	}
	
	public HashMap<String, Object> getSettings() {
		return settings;
	}
	public void setSettings(HashMap<String, Object> settings) {
		this.settings = settings;
	}
	
}
