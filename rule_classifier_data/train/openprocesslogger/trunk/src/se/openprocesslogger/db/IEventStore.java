package se.openprocesslogger.db;

import se.openprocesslogger.Event;

public interface IEventStore {
	public void storeEvent(Event e);
}
