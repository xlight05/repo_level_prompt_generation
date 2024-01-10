package se.openprocesslogger.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;

import se.openprocesslogger.Event;
import se.openprocesslogger.OplController;
import se.openprocesslogger.log.TrigEvent;

public class EventSaver{
	private Logger log = Logger.getLogger(EventSaver.class);
	private Map<String, TrigEvent> trigEvents = Collections.synchronizedMap(new HashMap<String, TrigEvent>());
	
	public static OPLDatabase instance(){
		return OPLDatabase.instance();
	}
	private Connection con;
	
	protected EventSaver(Connection con){
		this.con = con;
	}
	protected synchronized void storeEvent(Event e) {
		if(e instanceof TrigEvent){
			TrigEvent trig = (TrigEvent) e;
			//if(trigEvents.get(trig.hash()) == null){
			if(trigEvents.put(trig.hash(), trig) == null){
				log.debug("Scheduling triggevent on " +e.getProperties().get("varName"));
				final EventSaver evSaver = this;
				final String key = trig.hash();//TrigEvent event = trig;
				TimerTask timerTask = new TimerTask(){
					public void run(){
						trigEvents.remove(key);
						//
					}
				};
				OplController.getController().getExecutionTimer().schedule(timerTask, 10000);
				evSaver.storeTrigEvent(trig);
				//start a saver 5 seconds wait
			}
		}
	}

	private void storeTrigEvent(Event e) {
		log.debug("Adding triggevent on " +e.getProperties().get("varName"));
		try {
			//"INSERT INTO Events (name,reason,timestamp,from,to,logtask) values(?,?,?,?,?,?)"
			PreparedStatement s = con.prepareStatement("INSERT INTO Events (etype,value,timestamp,refid) values(?,?,?,?)");
			s.setString(1, e.getProperties().get("varName"));
			s.setBlob(2, getEventBlob(e.getProperties()));
			s.setTimestamp(3,new Timestamp(e.getTimestamp()));
			s.setLong(4, new Long(e.getProperties().get("logtask")));
			s.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private Blob getEventBlob(HashMap<String, String> map)throws SQLException, IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(map);
		SerialBlob sb = new SerialBlob(bos.toByteArray());
		return sb;
	}
}
