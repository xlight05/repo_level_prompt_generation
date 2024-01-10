package se.openprocesslogger;

import java.io.Serializable;
import java.util.HashMap;

public class Event implements Serializable, Comparable<Event>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5794674536836528269L;

	protected HashMap<String, String> properties;
	
	public Event(long timestamp){
		properties = new HashMap<String, String>();
		properties.put("timestamp", ""+timestamp);
	}
	public long getTimestamp(){
		return Long.parseLong(properties.get("timestamp"));
	}
	
	public HashMap<String, String> getProperties(){
		return properties;
	}
	
	public void setProperties(HashMap<String, String> map){
		properties = map;
	}
	
	@Override
	public int compareTo(Event o) {
		Long l = Long.parseLong(properties.get("timestamp"));
		Long l2 = Long.parseLong(o.properties.get("timestamp"));
		return l.compareTo(l2);
	}
}
