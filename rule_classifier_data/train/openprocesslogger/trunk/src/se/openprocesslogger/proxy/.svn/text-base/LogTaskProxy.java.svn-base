package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LogTaskProxy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608004281736680265L;

	private String name;
	private boolean started;
	private long id;
	private long startTime;
	private long stopTime;
	
	HashMap<String, String> properties = new HashMap<String, String>();
	HashMap<String, SubscriptionProxy> subscriptions = new HashMap<String, SubscriptionProxy>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties.putAll(properties);
	}
	public Map<String, SubscriptionProxy> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(Map<String, SubscriptionProxy> subs) {
		this.subscriptions.putAll(subs);
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStopTime() {
		return stopTime;
	}
	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
}
