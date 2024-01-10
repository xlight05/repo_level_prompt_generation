package se.openprocesslogger.db;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;

public class ProcessedDataFetcher {
	
	private Logger log = Logger.getLogger(ProcessedDataFetcher.class);
	
	public static IDataFetcher instance(){
		return OPLDatabase.instance();
	}
	private Connection dbConnection;
	
	public ProcessedDataFetcher(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	protected Data[] getData(String name, long logTaskId) {
		Data[] dArr = new Data[0];
		try {
			PreparedStatement stmt = dbConnection.prepareStatement(new StringBuilder("SELECT value FROM LogTaskData").append(logTaskId).append(" WHERE name=?").toString());
			stmt.setString(1,name);
			fetchData(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dArr;
	}
	
	// tots > tsFrom  && fromts < tsTo 
	private ArrayList<Data> getBatch(String name, long tsFrom, long tsTo, int maxNbr, long logTaskId) {
		int count = 0;
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value FROM LogTaskData"+logTaskId+" WHERE (name=?) AND fromts <= ? AND tots >= ? ORDER BY fromts");
			stmt.setMaxRows(maxNbr);
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsTo));
			stmt.setTimestamp(3, new Timestamp(tsFrom));
			log.debug("Request begins");
			Runtime.getRuntime().gc();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			ResultSet rs = stmt.executeQuery();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			log.debug("Request ended");
			while(rs.next() && count < maxNbr){
				Data[] batchPiece = DataChunk.getChunk(rs.getBlob(1));
				log.debug("Got batch: "+batchPiece.length);
				Arrays.sort(batchPiece);
				for(Data d : batchPiece){
					if(count < maxNbr && d.getTimestamp().getTime() >= tsFrom && d.getTimestamp().getTime() <= tsTo ){
						datas.add(d);
						count++;
					}
				}
			}
			log.debug("Data parsed: "+datas.size());
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
	
	private Data[] fetchData(PreparedStatement stmt) throws SQLException{
		Data[] dArr = new Data[0];
		try {			
			ResultSet rs = stmt.executeQuery();			
			ArrayList<Data[]> datas = new ArrayList<Data[]>();
			int totalCount = 0;
			while(rs.next()){
				Blob b = rs.getBlob("value");
				dArr = DataChunk.getChunk(b);
				datas.add(dArr);
				totalCount += dArr.length;
			}
			rs.close();
			stmt.close();
			int index = 0;
			dArr = new Data[totalCount];
			for(Data[] data : datas){
				for(int i=0; i<data.length; i++){
					dArr[index++] = data[i];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dArr;
	}

	protected Data[] getBatch(String name, long tsFrom, long tsTo, int maxNbr) {
		long id = getLogTaskId(name, tsFrom, tsTo, dbConnection);
		ArrayList<Data> datas = getBatch(name, tsFrom, tsTo, maxNbr, id);
		Data[] result = datas.toArray(new Data[0]);
		Arrays.sort(result);
		return result;
	}

	private long getLogTaskId(String name, long tsFrom, long tsTo, Connection dbConnection){
		Long[] ids = getAllLogTaskIds(name, tsFrom, tsTo, dbConnection);
		if (ids.length > 0){
			return ids[0];
		}
		return -1;
	}
	
	private Long[] getAllLogTaskIds(String name, long tsFrom, long tsTo, Connection dbConnection){
		ArrayList<Long> ids = new ArrayList<Long>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT id FROM LogTaskProperties WHERE name=? AND startTime < ? AND stopTime > ? ORDER BY startTime");
			stmt.setString(1, name);
			stmt.setLong(2, tsTo);
			stmt.setLong(3, tsFrom);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ids.add(rs.getLong(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids.toArray(new Long[0]);
	}
	
	protected Data[] getBatch(String name, long logTaskId, long tsFrom, long tsTo, int maxNbr) {
		ArrayList<Data> datas = getBatch(name, tsFrom, tsTo, maxNbr, logTaskId);
		Data[] result = datas.toArray(new Data[0]);
		Arrays.sort(result);
		return result;
	}

	protected Data[] getData(String name, long tsFrom, long tsTo) {
		long id = getLogTaskId(name, tsFrom, tsTo, dbConnection);
		Data[] d = new Data[0];
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM LogTaskData"+id+" WHERE name=? AND fromts < ? AND tots > ? ORDER BY fromts");
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsTo));
			stmt.setTimestamp(3, new Timestamp(tsFrom));
			d = fetchData(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	protected int getDataCount(String name, long logTaskId) {
		int count = 0;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT SUM(batchsize) FROM LogTaskData"+logTaskId+" WHERE name=?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				count = rs.getInt(1);
			}
			log.debug("Processed data count for "+name+" is "+count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public boolean hasData(long logTaskId) {
		int count = 0;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT COUNT(*) FROM LogTaskData"+logTaskId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				count = rs.getInt(1);
			}
			log.debug("Count "+count +" task "+logTaskId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0;
	}

	public Data getSampleData(String name, long logTaskId) {
		Data[] dArr = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT value FROM LogTaskData"+logTaskId+" WHERE name=?");
			stmt.setFetchSize(1);
			stmt.setMaxRows(1);
			stmt.setString(1, name);
			Runtime.getRuntime().gc();
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Blob b = rs.getBlob("value");
				dArr = DataChunk.getChunk(b);
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
		return (dArr == null || dArr.length==0) ? null : dArr[0];
	}
}
