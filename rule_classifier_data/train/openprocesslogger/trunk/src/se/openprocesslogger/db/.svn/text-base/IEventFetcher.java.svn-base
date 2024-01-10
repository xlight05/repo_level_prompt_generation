package se.openprocesslogger.db;

import se.openprocesslogger.Event;

public interface IEventFetcher {
	/***
	 * 
	 * @param eventType
	 * @return
	 */
	public Event[] getLatestEvents();
	
	/***
	 * 
	 * @param varNames
	 * @return
	 */
	public Event[] getLatestEvents(String[] varNames);
	
	/***
	 * 
	 * @param tsFrom
	 * @param tsTo
	 * @return
	 */
	public Event[] getEvents(long tsFrom, long tsTo);
	
	/***
	 * 
	 * @param description
	 * @return
	 */
	public Event[] getEventsByDescription(String description);
	
	/***
	 * 
	 * @param varName
	 * @param reference
	 * @return
	 */
	public Event[] getEventsByReference(String varName, String reference);
}
