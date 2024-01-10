package se.openprocesslogger.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import se.openprocesslogger.Event;
import se.openprocesslogger.log.TrigEvent;

public class EventFetcher{

	Logger log = Logger.getLogger(EventFetcher.class);
	
	private Connection dbConnection;
	
	protected EventFetcher(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	public static OPLDatabase instance(){
		return OPLDatabase.instance();
	}
	
	protected Event[] getEvents(long tsFrom, long tsTo) {
		PreparedStatement s = null;
		ResultSet set = null;
		ArrayList<Long> eventIds = new ArrayList<Long>();
		
		try {
			s = dbConnection.prepareStatement("SELECT id FROM Events WHERE timestamp >= ? AND timestamp <= ?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			s.setTimestamp(1, new Timestamp(tsFrom));
			s.setTimestamp(2, new Timestamp(tsTo));
			set = s.executeQuery();
			while(set.next()){
				eventIds.add(set.getLong("id"));
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Event> result =  new ArrayList<Event>();
		
		for(Long id : eventIds){
			Event event = getEvent(dbConnection, id);
			HashMap<String, String> map = event.getProperties();
			map.put("id", ""+id);
			event.setProperties(map);
			result.add(event);
		}
		
		Event[] res = result.toArray(new Event[0]);
		return res;
	}

	protected Event[] getEventsByDescription(String description) {
		
		PreparedStatement s = null;
		ResultSet set = null;
		ArrayList<Long> eventIds = new ArrayList<Long>();
		
		try {
			s = dbConnection.prepareStatement("SELECT id FROM Events WHERE description=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			s.setString(1, description);
			set = s.executeQuery();
			while(set.next()){
				eventIds.add(set.getLong("id"));
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Event> result =  new ArrayList<Event>();
		
		for(Long id : eventIds){
			Event event = getEvent(dbConnection, id);
			HashMap<String, String> map = event.getProperties();
			map.put("id", ""+id);
			event.setProperties(map);
			result.add(event);
		}
		
		Event[] res = result.toArray(new Event[0]);
		return res;
	}

	protected Event[] getEventsByReference(String varName, String reference) {
		PreparedStatement s = null;
		ResultSet set = null;
		ArrayList<Long> eventIds = new ArrayList<Long>();
		
		try {
			s = dbConnection.prepareStatement("SELECT id FROM Events WHERE etype=? AND refid=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			s.setString(1, varName);
			s.setString(2, reference);
			set = s.executeQuery();
			while(set.next()){
				eventIds.add(set.getLong("id"));
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Event> result =  new ArrayList<Event>();
		
		for(Long id : eventIds){
			Event event = getEvent(dbConnection, id);
			HashMap<String, String> map = event.getProperties();
			map.put("id", ""+id);
			event.setProperties(map);
			result.add(event);
		}
		
		Event[] res = result.toArray(new Event[0]);
		return res;
	}

	protected Event[] getLatestEvents() {
		return getLatestEvents(10);
	}
	
	protected Event[] getLatestEvents(String[] names) {
		return getLatestEvents(10, names);
	}
	
	private Event[] getLatestEvents(int amount, String[] names) {
		PreparedStatement s = null;
		ResultSet set = null;
		ArrayList<Long> eventIds = new ArrayList<Long>();
		log.debug("Getting latest events");
		try {
			StringBuilder sb = new StringBuilder("SELECT id FROM Events WHERE etype=?");
			for(int i=1; i<names.length; i++){
				sb.append(" OR etype=?");
			}
			sb.append(" ORDER BY timestamp DESC");
			log.debug("Get events: "+sb.toString());
			s = dbConnection.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			for(int i=1; i<=names.length; i++){
				s.setString(i, names[i-1]);
			}
			s.setMaxRows(amount);
			set = s.executeQuery();
			while(set.next()){
				eventIds.add(set.getLong("id"));
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Got "+eventIds.size() +" events");
		ArrayList<Event> result =  new ArrayList<Event>();
		
		for(Long id : eventIds){
			Event event = getEvent(dbConnection, id);
			HashMap<String, String> map = event.getProperties();
			map.put("id", ""+id);
			event.setProperties(map);
			result.add(event);
		}
		
		Event[] res = result.toArray(new Event[0]);
		
		return res;
	}
	
	private Event[] getLatestEvents(int amount) {		
		PreparedStatement s = null;
		ResultSet set = null;
		ArrayList<Long> eventIds = new ArrayList<Long>();
		log.debug("Getting latest events");
		try {
			s = dbConnection.prepareStatement("SELECT id FROM Events ORDER BY timestamp DESC ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			s.setMaxRows(amount);
			set = s.executeQuery();
			while(set.next()){
				eventIds.add(set.getLong("id"));
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Got "+eventIds.size() +" events");
		ArrayList<Event> result =  new ArrayList<Event>();
		
		for(Long id : eventIds){
			Event event = getEvent(dbConnection, id);
			HashMap<String, String> map = event.getProperties();
			map.put("id", ""+id);
			event.setProperties(map);
			result.add(event);
		}
		
		Event[] res = result.toArray(new Event[0]);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private Event getEvent(Connection dbConnection, Long id) {
		PreparedStatement s = null;
		ResultSet set = null;
		Event event = null;
		try {
			s = dbConnection.prepareStatement("SELECT * FROM Events WHERE id=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			s.setLong(1, id);
			set = s.executeQuery();
			while(set.next()){
				HashMap<String, String> properties = (HashMap<String, String>) getObject(set.getBlob("value"));
				event = new TrigEvent(Long.parseLong(properties.get("timestamp")),
						properties.get("varName"), properties.get("description"), 
						Long.parseLong(properties.get("logtask")));
				event.setProperties(properties);
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return event;
	}

	private Object getObject(Blob b) throws IOException, SQLException, ClassNotFoundException{
		InputStream bis = b.getBinaryStream();
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = ois.readObject();
		bis.close();
		ois.close();
		return o;
	}
	
}
