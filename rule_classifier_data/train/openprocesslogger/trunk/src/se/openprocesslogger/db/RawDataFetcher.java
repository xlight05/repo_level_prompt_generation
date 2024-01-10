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
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;

public class RawDataFetcher {

	public static IDataFetcher instance(){
		return OPLDatabase.instance();
	}
	
	Logger log = Logger.getLogger(DataFetcher.class);
	
	private Connection dbConnection;
	
	protected RawDataFetcher(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	protected Data[] getData(String name, long logTaskId) {
		
		ArrayList<Data> datas;
		datas = new ArrayList<Data>();
		try {			
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND loggtaskid=?");
			stmt.setString(1, name);
			stmt.setLong(2, logTaskId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				datas.add(getData(rs, logTaskId, name));
				//datas.add(new Data(set.getLong(1),set.getString(2),set.getLong(6),set.getLong(5),
				//		Data.getObject(set.getBlob(3)),new Date(set.getTimestamp(4).getTime()),set.getInt(7), set.getInt(8)));
			}
			rs.close();
			stmt.close();
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
		return datas.toArray(new Data[0]);
	}
	
	protected boolean hasData(long logTaskId) {
		boolean hasData = false;
		
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT COUNT(*) FROM Data WHERE loggtaskid=?");
			stmt.setLong(1, logTaskId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				hasData = rs.getInt(1)>0;
			}
			rs.close();
			stmt.close();
			
			stmt = dbConnection.prepareStatement(new StringBuilder("SELECT COUNT(*) FROM LogTaskData").append(logTaskId).toString());
			rs = stmt.executeQuery();
			if(rs.next()){
				hasData = hasData || rs.getInt(1)>0;
				rs.close();
				stmt.close();
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasData;
	}

	protected Data[] getBatch(String name, long tsFrom, long tsTo, int maxNbr){
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND timestamp BETWEEN ? AND ? ORDER BY timestamp");
			stmt.setMaxRows(maxNbr);
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsFrom));
			stmt.setTimestamp(3, new Timestamp(tsTo));
			log.debug("Request begins");
			Runtime.getRuntime().gc();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			ResultSet rs = stmt.executeQuery();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			log.debug("Request ended");
			while(rs.next()){
				datas.add(getData(rs,name));
			}
			log.debug("Data parsed");
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

		Data[] result = datas.toArray(new Data[0]);
		Arrays.sort(result);
		return result;
	}
	
	protected Data[] getBatch(String name, long logTaskId, long tsFrom, long tsTo, int maxNbr){
		
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND loggtaskid=? AND timestamp BETWEEN ? AND ? ORDER BY timestamp");
			stmt.setMaxRows(maxNbr);
			stmt.setString(1, name);
			stmt.setLong(2, logTaskId);
			stmt.setTimestamp(3, new Timestamp(tsFrom));
			stmt.setTimestamp(4, new Timestamp(tsTo));
			log.debug("Request begins");
			Runtime.getRuntime().gc();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			ResultSet rs = stmt.executeQuery();
			log.debug("Free "+Runtime.getRuntime().freeMemory()/1000000 +" of " +Runtime.getRuntime().totalMemory()/1000000);
			log.debug("Request ended");
			while(rs.next()){
				datas.add(getData(rs,name));
			}
			log.debug("Data parsed");
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

		Data[] result = datas.toArray(new Data[0]);
		Arrays.sort(result);
		return result;
	}
	
	protected Data[] getEventBatch(String name, long tsFrom, long tsTo, int maxNbr){

		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND timestamp BETWEEN ? AND ? ORDER BY timestamp");
			stmt.setFetchSize(maxNbr);
			stmt.setMaxRows(maxNbr);
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsFrom));
			stmt.setTimestamp(3, new Timestamp(tsTo));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				datas.add(getEventData(rs, name));
			}
			rs.close();
			stmt.close();
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
		
		return datas.toArray(new Data[0]);
	}
	
	protected Data getSampleData(String name){
		Data result = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=?");
			stmt.setFetchSize(1);
			stmt.setMaxRows(1);
			stmt.setString(1, name);
			Runtime.getRuntime().gc();
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				result = getData(rs,name);
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
		return result;
		
	}
	
	protected Data[] getData(String name, long tsFrom, long tsTo) {
		ArrayList<Data> datas = new ArrayList<Data>();
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND timestamp BETWEEN ? AND ?");
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsFrom));
			stmt.setTimestamp(3, new Timestamp(tsTo));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				datas.add(getData(rs,name));
			}
			rs.close();
			stmt.close();
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
		return datas.toArray(new Data[0]);
	}
	
	protected int getDataCount(String name, long logTaskId){
		int nbr = 0;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT COUNT(*) FROM Data WHERE loggtaskid=? AND name=?");
			stmt.setLong(1, logTaskId);
			stmt.setString(2, name);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				nbr = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbr;
	}
	
	protected int getDataCount(String name, long tsFrom, long tsTo){
		int nbr = 0;
		long tBef = System.currentTimeMillis();
		try {
			log.debug("Counting " +name);
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT COUNT(*) FROM Data WHERE name=? AND timestamp BETWEEN ? AND ?");
			stmt.setString(1, name);
			stmt.setTimestamp(2, new Timestamp(tsFrom));
			stmt.setTimestamp(3, new Timestamp(tsTo));
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				nbr = rs.getInt(1);
			}
			log.debug("..."+nbr +" in " +(System.currentTimeMillis()-tBef)+" ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbr;
	}
		
	protected Data getSingleData(long dataId){
		
		Data d = null;
		try {			
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE id=?");
			stmt.setLong(1, dataId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				d = getData(rs);
			}
			rs.close();
			stmt.close();
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

	private Data getData(ResultSet rs) throws SQLException, IOException, ClassNotFoundException{
		return new Data(rs.getLong(1),rs.getString(2),rs.getLong(6),rs.getLong(5),
				getObject(rs.getBlob(3)),new Date(rs.getTimestamp(4).getTime()),rs.getInt(7),rs.getInt(8));
	}
	
	private Data getData(ResultSet rs, long logTaskId, String name) throws SQLException, IOException, ClassNotFoundException{
		return new Data(rs.getLong(1),name,rs.getLong(6),logTaskId,
				getObject(rs.getBlob(3)),new Date(rs.getTimestamp(4).getTime()),rs.getInt(7),rs.getInt(8));
	}
	
	private Data getData(ResultSet rs, String name) throws SQLException, IOException, ClassNotFoundException{
		return new Data(rs.getLong(1),name,rs.getLong(6),rs.getLong(5),
				getObject(rs.getBlob(3)),new Date(rs.getTimestamp(4).getTime()),rs.getInt(7),rs.getInt(8));
	}
	
	private Data getEventData(ResultSet rs, String name) throws SQLException, IOException, ClassNotFoundException{
		return new Data(rs.getLong(1),name,rs.getLong(6),rs.getLong(5),
				rs.getLong(1),new Date(rs.getTimestamp(4).getTime()),rs.getInt(7),rs.getInt(8));
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
