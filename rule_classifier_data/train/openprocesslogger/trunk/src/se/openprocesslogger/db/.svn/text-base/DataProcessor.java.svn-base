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
import java.util.Set;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;

public class DataProcessor{
	public static final int CHUNK_SIZE = 2000;
	private static Logger log = Logger.getLogger(DataProcessor.class);
	private Connection dbConnection;
	
	protected DataProcessor(Connection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	public static OPLDatabase instance(){
		return OPLDatabase.instance();
	}
	
	protected void processData(long taskId, Set<String> subscriptions) {
		log.debug("Start processing data for "+taskId);
		if (taskId >0){
			for(String name : subscriptions){
				log.debug("Start processing "+name);
				processData(name, taskId);
				log.debug("finished");
			}			
		}
		log.debug("Data processing finished.");
	}

	private void processData(String name, long logTaskId) {
		ArrayList<Data> datas = new ArrayList<Data>();
		try {			
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND loggtaskid=? ORDER BY timestamp");
			stmt.setString(1, name);
			stmt.setLong(2, logTaskId);
			ResultSet rs = stmt.executeQuery();
			int a = 0;
			while(rs.next()){
				a++;
				datas.add(getData(rs, logTaskId, name));
				if (datas.size() >= CHUNK_SIZE){
					//rs.close();
					//stmt.close();
					
					saveChunk(dbConnection, datas.toArray(new Data[0]), logTaskId, name);
					datas.clear();
					/*
					stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND loggtaskid=? ORDER BY timestamp");
					stmt.setString(1, name);
					stmt.setLong(2, logTaskId);
					rs = stmt.executeQuery();
					*/
					
				}
			}
			rs.close();
			stmt.close();
			
			saveChunk(dbConnection, datas.toArray(new Data[0]), logTaskId, name);
			datas.clear();
			
			stmt = dbConnection.prepareStatement("DELETE FROM Data WHERE name=? AND loggtaskid=?");
			stmt.setString(1, name);
			stmt.setLong(2, logTaskId);
			stmt.execute();
			stmt.close();
			log.debug("Processed "+a +" data points for "+name);
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
		return;
	}
	
	private void saveChunk(Connection dbConnection, Data[] array, long logTaskId, String name) {
		if (array==null || array.length<1 || array[0]==null) return;
		Arrays.sort(array);
		String sqlInsert = "INSERT INTO LogTaskData"+logTaskId+" (name,value,loggtaskid,equipmentid,lineInfo,batchsize, fromts, tots) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);
			try{
				statement.setString(1,name);
				statement.setBlob(2,DataChunk.getBlob(array));
				statement.setLong(3,logTaskId);
				if(array[0].getEquipment()==-1)
					statement.setLong(4,0);
				else
					statement.setLong(4, array[0].getEquipment());
				statement.setInt(5, 0);
				statement.setLong(6, array.length);
				statement.setTimestamp(7, new Timestamp(array[0].getTimestamp().getTime()));
				statement.setTimestamp(8, new Timestamp(array[array.length-1].getTimestamp().getTime()));
				statement.execute();
				statement.close();
				log.debug("Added "+array.length +" in "+logTaskId);
			}catch(SQLException ex){
				ex.printStackTrace();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Data getData(ResultSet rs, long logTaskId, String name) throws SQLException, IOException, ClassNotFoundException{
		return new Data(rs.getLong(1),name,rs.getLong(6),logTaskId,
				getObject(rs.getBlob(3)),new Date(rs.getTimestamp(4).getTime()),rs.getInt(7),rs.getInt(8));
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
