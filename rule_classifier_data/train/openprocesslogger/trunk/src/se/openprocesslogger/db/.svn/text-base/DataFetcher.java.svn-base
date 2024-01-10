package se.openprocesslogger.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;


public class DataFetcher {

	public static IDataFetcher instance(){
		return OPLDatabase.instance();
	}
	
	Logger log = Logger.getLogger(DataFetcher.class);
	
	private Connection dbConnection;
	
	protected DataFetcher(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	protected Data[] getBatch(String name, long tsFrom, long tsTo, int maxNbr) {
		ArrayList<Long> tasks = getLoggingTasksBetween(tsFrom, tsTo);
		Data[] result;
		ArrayList<Data> datas;
		if(tasks.size()==1){ // One table
			datas = getBatch(name, tsFrom, tsTo, maxNbr, tasks.get(0));
		}else{ // Merge needed
			datas = new ArrayList<Data>();
			for(Long task : tasks){
				datas.addAll(getBatch(name, tsFrom, tsTo, maxNbr, task));
			}			 
		}
		result = getTrimmedArray(datas, maxNbr);
		return result;
	}
	
	protected Data[] getBatch(String name, long logTaskId, long tsFrom, long tsTo, int maxNbr) {
		return getTrimmedArray(getBatch(name, tsFrom, tsTo, maxNbr, logTaskId), maxNbr);
	}
	
	protected Data[] getData(String name, long tsFrom, long tsTo) {
		ArrayList<Long> tasks = getLoggingTasksBetween(tsFrom, tsTo);
		Data[] result;
		ArrayList<Data> datas;
		if(tasks.size()==1){ // One table
			datas = getData(name, tsFrom, tsTo, tasks.get(0));
		}else{ // Merge needed
			datas = new ArrayList<Data>();
			for(Long task : tasks){
				datas.addAll(getData(name, tsFrom, tsTo, task));
			}			 
		}
		Collections.sort(datas);
		result = datas.toArray(new Data[0]);
		return result;
	}

	protected Data[] getEventBatch(String name, long tsFrom, long tsTo, int maxNbr) {
		ArrayList<Long> tasks = getLoggingTasksBetween(tsFrom, tsTo);
		Data[] result;
		ArrayList<Data> datas;
		if(tasks.size()==1){ // One table
			datas = getEventBatch(name, tsFrom, tsTo, maxNbr, tasks.get(0));
		}else{ // Merge needed
			datas = new ArrayList<Data>();
			for(Long task : tasks){
				datas.addAll(getEventBatch(name, tsFrom, tsTo, maxNbr, task));
			}			 
		}
		result = getTrimmedArray(datas, maxNbr);
		return result;
	}

	protected Data getSampleData(String name) {
		Data d = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT logtask FROM Subscriptions WHERE name=?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while(d==null && rs.next()){
				d = getSampleData(name, rs.getLong(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	protected Data getSampleData(String name, long logTaskId){
		Data d = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value,tots FROM LogTaskData"+logTaskId+" WHERE (name=?)");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while(d==null && rs.next()){
				Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
				if (batchPiece.length > 0){
					d = batchPiece[0];
				}
			}
			stmt.close();
			rs.close();
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
		return d;
	}
	
	protected Data getSingleData(long dataId, String name, long timestamp) {
		ArrayList<Long> tasks = getLoggingTasksBetween(timestamp, timestamp);
		Data d = null;
		for(Long logTaskId : tasks){
			d = getSingleData(dataId, name, timestamp, logTaskId);
			if(d!=null) return d;
		}
		return d;
	}

	protected int getDataCount(String name, long logTaskId){
		int count = 0;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT SUM(batchsize) FROM LogTaskData"+logTaskId+" WHERE name=?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	protected int getDataCount(String name, long tsFrom, long tsTo) throws Throwable{
		ArrayList<Long> tasks = getLoggingTasksBetween(tsFrom, tsTo);
		int count = 0;
		for(Long task : tasks){
			count += getDataCount(name, task, tsFrom, tsTo);
		}
		return count;
	}
	
	private int getDataCount(String name, long logTaskId, long tsFrom, long tsTo) throws Throwable{
		int count = 0;
		PreparedStatement stmt = dbConnection.prepareStatement("SELECT batchsize,fromts,tots,value FROM LogTaskData"+logTaskId+" WHERE (name=?) AND fromts <= ? AND tots >= ? ORDER BY fromts");
		stmt.setString(1, name);
		stmt.setTimestamp(2, new Timestamp(tsTo));
		stmt.setTimestamp(3, new Timestamp(tsFrom));
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			long batchFromTs = rs.getTimestamp(2).getTime();
			long batchToTs = rs.getTimestamp(3).getTime();
			if(batchFromTs >= tsFrom && batchToTs <= tsTo){ // Whole batch within interval
				count += rs.getInt(1);
			}else{
				count += getCountIn(DataChunk.getChunk(rs.getBlob(4)), tsFrom, tsTo);
			}
		}
		rs.close();
		stmt.close();
		
		return count;
	}
	
	private int getCountIn(Data[] chunk, long tsFrom, long tsTo) {
		int count = 0;
		for(Data d : chunk){
			if(d.getTimestamp().getTime() >= tsFrom && d.getTimestamp().getTime() <= tsTo){
				count++;
			}
		}
		return count;
	}

	protected int getDataCount(long logTaskId){
		int count = 0;
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SUM(batchsize) FROM LogTaskData"+logTaskId);
			if(rs.next()){
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	protected boolean hasData(long logTaskId) {
		return getDataCount(logTaskId) > 0;
	}
	
	private Data[] getTrimmedArray(ArrayList<Data> datas, int maxNbr){
		Data[] result;
		Collections.sort(datas);
		if(datas.size() > maxNbr){
			result = new Data[maxNbr];
			for(int i=0; i<maxNbr; i++){
				result[i] = datas.get(i);
			}
		}else{
			result = datas.toArray(new Data[0]);
		}
		return result;
	}
	
	/***
	 * Get the data points closest before and after a certain time.
	 * It only checks the log tasks that were running at the specified time. 
	 * @param varName The name of the data.
	 * @param timestamp The time in milliseconds.
	 * @return An array of 2 data objects. The first one before and the second after the time. 
	 * One or both of the data objects might be null.
	 */
	protected Data[] getSurroundingData(String varName, long timestamp) {
		ArrayList<Long> tasks = getLoggingTasksBetween(timestamp, timestamp);
		log.debug("\t Found "+tasks.size()+" tasks in this time");
		Data[] d = new Data[2];
		long[] diff = {Long.MAX_VALUE, -Long.MAX_VALUE};
		try {
			for(Long logTaskId : tasks)
			{
				PreparedStatement stmt = dbConnection.prepareStatement("SELECT value FROM LogTaskData"+logTaskId+" WHERE (name=?) AND fromts <= ? AND tots >= ? ORDER BY fromts");
				stmt.setString(1, varName);
				stmt.setTimestamp(2, new Timestamp(timestamp));
				stmt.setTimestamp(3, new Timestamp(timestamp));
				ResultSet rs = stmt.executeQuery();
				while (rs.next()){
					Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
					log.debug("Got batch with size: " +batchPiece.length);
					for (Data data : batchPiece){
						long tmpDiff = timestamp - data.getTimestamp().getTime();
						if (tmpDiff > 0 && tmpDiff < diff[0]){ // data before
							d[0] = data;
							diff[0] = tmpDiff;
						}
						else if (tmpDiff < 0 && tmpDiff > diff[1]){ // data after
							d[1] = data;
							diff[1] = tmpDiff;
						}
						else{ // same ts
							d[0] = data;
							d[1] = data;
							break;
						}
					}
				}
				stmt.close();
			}
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
		return d;
	}
	
	/***
	 * Get Logging task id's where startTime < tsTo AND stopTime > tsFrom
	 * 
	 * @param tsFrom
	 * @param tsTo
	 * @return
	 */
	private ArrayList<Long> getLoggingTasksBetween(long tsFrom, long tsTo){
		ArrayList<Long> logTasks = new ArrayList<Long>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT ID FROM LogTask WHERE startTime < ? AND stopTime > ?");
			stmt.setTimestamp(1, new Timestamp(tsTo));
			stmt.setTimestamp(2, new Timestamp(tsFrom));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				logTasks.add(rs.getLong(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logTasks;
	}
	
	/***
	 * Ok, so this is the deal with batches.
	 * 
	 * Each table row contains an array of Data as value. The name is same for all Data. fromts is the lowest timestamp and tots is the highest.
	 * 
	 * The Data arrays are not sorted amongst other table rows, so the intervals (fromts to tots) for different rows can be overlapping.
	 * 
	 * The analyze part of OPL fetches the data in batches of a certain maximum size, in order to avoid huge amounts of data being transferred at once. 
	 * 
	 * The reasons for this are: 
	 * 	* limitations in servlet (infinite post-get-size is not possible)
	 * 	* user experience (it's nice to see the data successively loading). 
	 * 
	 * The analyzing application (analyzer) first gets a count of all the data in the time interval, i.e. the data amount to be expected (?).
	 * 	// Perhaps this is bad. Counting is not reliable anymore, unless unpacking is performed. Can't it go until a partly-filled batch is received? 
	 *  // Or a null object, like a null-termination ?
	 * 
	 * The analyzer first passes the whole time interval to the DataFetcher in order to retrieve the first batch. 
	 * When a batch has been loaded, the analyzer checks the last timestamp and sets this as fromts of the next batch request.
	 *   
	 * This suggests that the database shall be able to handle large search intervals good.
	 * It is therefore necessary to determine what batches are interesting before starting to unpack data.
	 * 
	 * This can be done by selecting the batch information without retrieving the actual data from the database.
	 */
	
	private long narrowTimeInterval(String name, long tsFrom, long tsTo, int maxNbr, long logTaskId){
		ResultSet rs;
		PreparedStatement stmt;
		int dataCount = 0; // Data points until to
		long to = tsTo;
		try {
			//stmt = dbConnection.prepareStatement("SELECT batchsize,tots,fromts FROM LogTaskData"+logTaskId+" WHERE name=? AND fromts <= ? AND tots >= ? ORDER BY fromts");
			stmt = dbConnection.prepareStatement("SELECT batchsize,tots,fromts FROM LogTaskData"+logTaskId+" WHERE name=?");
			stmt.setString(1, name);
			//stmt.setTimestamp(2, new Timestamp(tsTo));
			//stmt.setTimestamp(3, new Timestamp(tsFrom));
			rs = stmt.executeQuery();
			while(rs.next() && dataCount < maxNbr){
				dataCount += rs.getLong(1);
				//log.debug("\tBatch in interval: "+rs.getLong(1));
				//to = rs.getTimestamp(2).getTime();
				//log.debug("Got batch "+rs.getInt("batchsize")+" from "+getTimeString(rs.getTimestamp("fromts").getTime()) +" to " +getTimeString(rs.getTimestamp("tots").getTime()));
				//log.debug("Batch ended: "+getTimeString(to) +" count is now " +dataCount);
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return to;
	}
	
	/***
	 * Unsorted arraylist of datas.
	 * Where timestamp is between tsFrom and tsto
	 * 
	 * This gived the condition:
	 * tots > tsFrom  && fromts < tsTo
	 */
	private ArrayList<Data> getBatch(String name, long tsFrom, long tsTo, int maxNbr, long logTaskId) {
		long newTsTo = tsTo; //narrowTimeInterval(name, tsFrom, tsTo, maxNbr, logTaskId);
		long lastDataTs = Long.MAX_VALUE;
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value,fromts,tots,batchSize FROM LogTaskData"+logTaskId+" WHERE (name=?) AND fromts <= ? AND tots >= ? ORDER BY fromts");
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(newTsTo));
			stmt.setTimestamp(3, new Timestamp(tsFrom));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				long tmpFrom = rs.getTimestamp(2).getTime();
				//log.debug("\t\tBatch size: "+rs.getLong(4));
				if (datas.size() < maxNbr || lastDataTs > tmpFrom){
					lastDataTs = rs.getTimestamp(3).getTime();
					Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
					for(Data d : batchPiece){
						if(d.getTimestamp().getTime() >= tsFrom && d.getTimestamp().getTime() <= newTsTo ){
							datas.add(d);
						}
					}
				}else{
					//log.debug("Did not add: datas size "+datas.size() +" and first time stamp is "+getTimeString(rs.getTimestamp(2).getTime()));
				}
			}
			rs.close();
			stmt.close();
			Runtime.getRuntime().gc();
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
		return datas;
	}
	
	/***
	 * Unsorted arraylist of datas.
	 * Where timestamp is between tsFrom and tsto
	 * 
	 * This gived the condition:
	 * tots > tsFrom  && fromts < tsTo
	 */
	private ArrayList<Data> getData(String name, long tsFrom, long tsTo, long logTaskId) {
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value,tots,id FROM LogTaskData"+logTaskId+" WHERE (name=?)");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				long id = rs.getLong(3);
				Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
				for(Data d : batchPiece){
					if(d.getTimestamp().getTime() >= tsFrom && d.getTimestamp().getTime() <= tsTo ){
						d.setId(id);
						datas.add(d);
					}					
				}
			}
			rs.close();
			stmt.close();
			Runtime.getRuntime().gc();
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
		return datas;
	}
	
	private ArrayList<Data> getEventBatch(String name, long tsFrom, long tsTo, int maxNbr, long logTaskId){
		long newTsTo = narrowTimeInterval(name, tsFrom, tsTo, maxNbr, logTaskId);
		long lastDataTs = Long.MAX_VALUE;
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value,fromts,tots,id FROM LogTaskData"+logTaskId+" WHERE (name=?) AND fromts <= ? AND tots >= ? ORDER BY fromts");
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(newTsTo));
			stmt.setTimestamp(3, new Timestamp(tsFrom));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				long tmpFrom = rs.getTimestamp(2).getTime();
				if (datas.size() < maxNbr || lastDataTs > tmpFrom){
					lastDataTs = rs.getTimestamp(3).getTime();
					long id = rs.getLong(4);
					Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
					for(Data d : batchPiece){
						if(d.getTimestamp().getTime() >= tsFrom && d.getTimestamp().getTime() <= newTsTo ){
							Data ed = getEventData(d, name, id);
							datas.add(ed);
						}
					}					
				}
			}
			rs.close();
			stmt.close();
			Runtime.getRuntime().gc();
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
		return datas;
	}
	
	private Data getEventData(Data d, String name, long id) throws SQLException, IOException, ClassNotFoundException{
		return new Data(id,d.getName(),d.getEquipment(),d.getLoggingTask(), (Object)id,d.getTimestamp(), (int)d.getLineInfo(),d.getPointType());
	}
	
	private Data getSingleData(long dataId, String name, long timestamp, Long logTaskId) {
		Data d = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value FROM LogTaskData"+logTaskId+" WHERE id=?");
			stmt.setLong(1, dataId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
				if(batchPiece.length >= 1){
					d = batchPiece[0];
					d.setId(dataId);
				}
			}
			rs.close();
			stmt.close();
			Runtime.getRuntime().gc();
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
		return d;
	}
	/*
	private String getTimeString(long ms){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date(ms));
	}
	*/
}
