package se.openprocesslogger.log;

import java.util.Date;

import se.openprocesslogger.Event;

public class TrigEvent extends Event {
	private static final long serialVersionUID = 4266124092628896146L;
	
	public TrigEvent(long timestamp, String varName, String description, long logtask) {
		super(timestamp);
		properties.put("varName", varName);
		properties.put("description", description);
		properties.put("logtask", ""+logtask);
	}

	public void setStartTime(Date timestamp) {
		properties.put("from", ""+timestamp.getTime());
	}

	public void setStopTime(Date timestamp) {
		properties.put("to", ""+timestamp.getTime());
	}

	public String hash() {
		return properties.get("varName") + properties.get("description") + Long.parseLong(properties.get("timestamp"))/1000;
	}
	
	@Override
	public int compareTo(Event o) {
		return super.compareTo(o);
	}
}
