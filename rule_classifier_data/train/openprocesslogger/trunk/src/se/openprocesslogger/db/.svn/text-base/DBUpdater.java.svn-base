package se.openprocesslogger.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;

public class DBUpdater {

	Logger log = Logger.getLogger(DBUpdater.class);
	private Connection dbConnection;
	private static final String DBNAME = "opl.db";
	private static DBUpdater dbup;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		dbup = new DBUpdater();
		if(dbup.initDatabaseConnection()){
			dbup.updateDB();
		}
	}

	private boolean initDatabaseConnection(){
		String path = "C:\\workspace\\OpenProcessLogger\\Resources\\DB02\\tpcdb_01";
		System.setProperty("derby.system.home", path);
		DriverManager.setLoginTimeout(5);
		File dbEnv = null;
		long tStart;
		if (path==null || path.equals("")){
			log.warn("Attempting to connect to local database path \"database\" ");
			dbEnv = new File("database");
		}else{
			dbEnv = new File(path);
		}
		if(!dbEnv.canRead() && !dbEnv.mkdir()){
			log.debug("Database path unavailable");
		}else{
			File dbFile = new File(dbEnv, DBNAME);
			log.debug("Connecting to database "+dbFile.getAbsolutePath());
			tStart = System.currentTimeMillis();
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

				//this.dbConnection = DriverManager.getConnection("jdbc:derby:"+dbFile.getAbsolutePath());
				this.dbConnection = DriverManager.getConnection("jdbc:derby:"+DBNAME);
				log.debug("Connection made in "+(System.currentTimeMillis()-tStart) +" ms");
				return true;
			} catch (SQLException e) {
				log.debug("Connect failed: "+e.getMessage());
				//createDatabase(dbFile.getAbsolutePath());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private boolean updateDB(){
		try {
			ArrayList<Long> a = new ArrayList<Long>();
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT DISTINCT loggtaskid FROM Data");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				a.add(rs.getLong(1));
			}
			rs.close();
			stmt.close();
			
			for(Long l : a){
				Timestamp start = null;
				Timestamp stop = null;
				stmt = dbConnection.prepareStatement("SELECT MIN(timestamp) FROM Data WHERE loggtaskid=?");
				stmt.setLong(1, l);
				rs = stmt.executeQuery();
				while(rs.next())
					start = rs.getTimestamp(1);
				rs.close();
				stmt.close();
				
				stmt = dbConnection.prepareStatement("SELECT MAX(timestamp) FROM Data WHERE loggtaskid=?");
				stmt.setLong(1, l);
				rs = stmt.executeQuery();
				while(rs.next())
					stop = rs.getTimestamp(1);
				rs.close();
				stmt.close();
				
				stmt = dbConnection.prepareStatement("SELECT DISTINCT Name FROM Data WHERE loggtaskid=?");
				stmt.setLong(1, l);
				rs = stmt.executeQuery();

				ArrayList<String> b = new ArrayList<String>();
				while(rs.next()){
					b.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
				for(String s : b){
					start.getTime();
					processData(s, l, start.getTime(), stop.getTime());
				}
				if(start != null){
					stmt = dbConnection.prepareStatement("UPDATE Logtask SET starttime=? WHERE ID=?");
					stmt.setTimestamp(1, start);
					stmt.setLong(2, l);
					stmt.execute();
					stmt.close();
				}
				if(stop != null){
					stmt = dbConnection.prepareStatement("UPDATE Logtask SET stoptime=? WHERE ID=?");
					stmt.setTimestamp(1, stop);
					stmt.setLong(2, l);
					stmt.execute();
					stmt.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void processData(String name, long logTaskId, long start, long stop) {
		ArrayList<Data> datas = new ArrayList<Data>();
		try {			
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Data WHERE name=? AND loggtaskid=? ORDER BY timestamp");
			stmt.setString(1, name);
			stmt.setLong(2, logTaskId);
			ResultSet rs = stmt.executeQuery();
			int a = 0;
			long nextsave = start+60000;
			while(rs.next()){
				a++;
				datas.add(getData(rs, logTaskId, name));
				if (datas.size() >= 2000 || (rs.getTimestamp(4).getTime() > nextsave)){
					saveChunk(dbConnection, datas.toArray(new Data[0]), logTaskId, name);
					datas.clear();
					while(rs.getTimestamp(4).getTime() > nextsave)
						nextsave += 60000;
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
