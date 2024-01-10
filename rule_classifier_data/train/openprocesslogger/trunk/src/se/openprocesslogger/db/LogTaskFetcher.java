package se.openprocesslogger.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.log.Subscription;
import se.openprocesslogger.proxy.LoggingTaskData;

//import sun.reflect.Reflection;

public class LogTaskFetcher {
	private Logger log = Logger.getLogger(LogTaskFetcher.class);
	
	public static OPLDatabase instance(){
		return OPLDatabase.instance();
	}
	
	private Connection dbConnection;
	
	protected LogTaskFetcher(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	protected long logTaskExists(String name){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long id = -1;
		log.debug("LogTaskFetcher: LogTaskExists");
		long tStart = System.currentTimeMillis();
		try {
			stmt = dbConnection.prepareStatement("SELECT id FROM LogTask WHERE name=?");
			log.debug("Time to prepare stmt: "+(System.currentTimeMillis()-tStart)+" ms");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()){
				id = rs.getLong("id");
				log.debug("Log task found");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	protected List<LoggingTaskData> getFinishedLogTasks() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Long> taskIds = new ArrayList<Long>();
		ArrayList<LoggingTaskData> result =  new ArrayList<LoggingTaskData>();
		
		try {
			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery("SELECT id FROM LogTask");
			while(rs.next()){
				taskIds.add(rs.getLong("id"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Long id : taskIds){
			LoggingTask task = getLogTask(id);
			if (task != null)
				result.add(new LoggingTaskData(id, task.getName(), task.getVarNames()));
			else
				log.fatal("Could not get data for existing log task!!");
		}
		log.debug(result.size() +" finished log tasks found");
		Collections.sort(result);
		return result;
	}
	
	protected LoggingTask findLogTask(String name) {
		long id = logTaskExists(name);
		LoggingTask task = null;
		if (id > 0){
			task = getLogTask(id);
		}
		return task;
	}	
	
	protected LoggingTask getLogTask(long id){
		LoggingTask l = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM LogTask WHERE id = ?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()){
				log.debug("Log task "+id +" requested not found");
				return l;
			}
			
			l = new LoggingTask(rs.getLong("id"), rs.getString("name"), new HashMap<String, String>());
			l.setStartTime(rs.getTimestamp("startTime").getTime());
			l.setStopTime(rs.getTimestamp("stopTime").getTime());
			rs.close();
			stmt.close();
			
			stmt = dbConnection.prepareStatement("SELECT name,value FROM LogTaskProperties WHERE object=?");
			stmt.setLong(1, l.getTaskId());
			rs = stmt.executeQuery();
			while(rs.next()){
				l.addProperty(rs.getString("name"), rs.getString("value"));
			}
			rs.close();
			stmt.close();			
			
			stmt = dbConnection.prepareStatement("SELECT name FROM Subscriptions WHERE logtask=?");
			stmt.setLong(1, l.getTaskId());
			rs = stmt.executeQuery();
			while(rs.next()){
				l.addSubscription(new Subscription(rs.getString("name")));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//log.error("Error getting log task "+id+": "+e.getMessage());
		}
		return l;
	}

	
}
