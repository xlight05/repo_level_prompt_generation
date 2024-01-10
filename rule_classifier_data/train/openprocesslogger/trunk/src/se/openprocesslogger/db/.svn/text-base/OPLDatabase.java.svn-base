package se.openprocesslogger.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;
import se.openprocesslogger.Event;
import se.openprocesslogger.OplProperties;
import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.proxy.LoggingTaskData;

public class OPLDatabase implements IDataFetcher, IEventFetcher, IEventStore{
	private static final String DBNAME = "opl.db";
	
	Connection dbConnection;
	
	Logger log = Logger.getLogger(OPLDatabase.class);
	
	LogTaskFetcher lf;
	DataFetcher df;
	EventFetcher ef;
	
	DataSaver ds;
	LogTaskSaver ls;
	EventSaver es;
	
	DataProcessor dp;
	DataBuffer dataBuffer;
	private ArrayList<ArrayList<Data>> nextChunkArray;
	boolean initialized = false;
	
	private OPLDatabase(){
		dataBuffer = new DataBuffer();
		log.debug("Starting initialization thread");
		initDatabaseConnection();
		initSubSystems();
		log.debug("Finished initialization thread");
		initialized = true;
	}
	
	private void initSubSystems(){
		lf = new LogTaskFetcher(dbConnection);
		df = new DataFetcher(dbConnection);
		ef = new EventFetcher(dbConnection);
		ls = new LogTaskSaver(dbConnection);
		es = new EventSaver(dbConnection);
		dp = new DataProcessor(dbConnection);
		
		nextChunkArray = new ArrayList<ArrayList<Data>>();
		ds = new DataSaver(dataBuffer);
		ds.setPriority(Thread.NORM_PRIORITY+1);
		ds.start();
	}
		
	private void awaitInit(){
		while(!initialized){
			try {
				log.debug("Awaiting init..");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static OPLDatabase instance;
	
	public static OPLDatabase instance(){
		if (instance == null){
			synchronized(OPLDatabase.class){ // Synchronize while creating
				instance = (instance == null) ? new OPLDatabase() : instance;
			}
		}
		return instance;
	}

	public static DataBuffer getDataBuffer() {
		return OPLDatabase.instance().dataBuffer;
	}
	
	@Override
	public synchronized Data[] getBatch(String name, long tsFrom, long tsTo, int maxNbr) {
		startTimer("OPLDatabase: GetBatch.."+name);
		awaitInit();
		Data[] d = df.getBatch(name, tsFrom, tsTo, maxNbr);
		printTimer("OPLDatabase: Got batch in");
		return d;
	}
	
	@Override
	public synchronized Data[] getBatch(String name, long logTaskId, long tsFrom, long tsTo,
			int maxNbr) {
		startTimer("OPLDatabase: GetBatch (for task).."+name);
		awaitInit();
		printTimer("OPLDatabase: Got batch in");
		return df.getBatch(name, logTaskId, tsFrom, tsTo, maxNbr);
	}

	
	@Override
	public synchronized Data[] getData(String name, long tsFrom, long tsTo) {
		awaitInit();
		return df.getData(name, tsFrom, tsTo);
	}
	
	@Override
	public synchronized Data[] getSurroundingData(String varName, long timestamp) {
		awaitInit();
		startTimer("OPLDatabase: Get surrounding data: "+varName +" around " +(new Date(timestamp)).toString());
		Data[] res = df.getSurroundingData(varName, timestamp);
		printTimer("Got data in ");
		return res;
	}
	
	public synchronized int getDataCount(String name, long logTaskId) {
		startTimer("OPLDatabase: GetDataCount (for task).."+name);
		awaitInit();
		int count = df.getDataCount(name, logTaskId);
		printTimer("OPLDatabase: Got count in");
		return count;
	}
	
	public synchronized int getDataCount(String name, long tsFrom, long tsTo) {
		startTimer("OPLDatabase: GetDataCount.."+name);
		awaitInit();
		int count = 0;
		try {
			count = df.getDataCount(name, tsFrom, tsTo);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printTimer("OPLDatabase: Got count in");
		return count;
	}
	
	
	@Override
	public synchronized Data[] getEventBatch(String name, long tsFrom, long tsTo, int maxNbr) {
		startTimer("Get event batch "+name);
		awaitInit();
		Data[] d = df.getEventBatch(name, tsFrom, tsTo, maxNbr);
		printTimer("Got event batch in");
		return d;
	}

	@Override
	public synchronized Data getSampleData(String name) {
		startTimer("Get sample data "+name);
		awaitInit();
		Data d= df.getSampleData(name);
		printTimer("Got sample data in");
		return d;
	}

	public synchronized Data getSampleData(String name, long logTaskId) {
		startTimer("Get sample data for task "+name);
		awaitInit();
		Data d= df.getSampleData(name, logTaskId);
		printTimer("Got sample data in");
		return d;
	}
	
	@Override
	public synchronized Data getSingleData(long dataId, String varName, long timestamp) {
		startTimer("Get single data "+dataId);
		awaitInit();
		Data d= df.getSingleData(dataId, varName, timestamp);
		printTimer("Got single data in");
		return d;
	}

	@Override
	public synchronized boolean hasData(long logTaskId) {
		startTimer("Method hasData");
		awaitInit();
		boolean res= df.hasData(logTaskId);
		printTimer("");
		return res;
	}
	
	public synchronized void addLogTask(LoggingTask loggingTask) {
		log.debug("Method addLogTask");
		awaitInit();
		try {
			ls.addLogTask(loggingTask);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("AddLogTask done. Id: "+loggingTask.getTaskId());
		
	}

	public synchronized boolean deleteLogTask(LoggingTask task) {
		log.debug("Method deleteLogTask");
		awaitInit();
		return ls.deleteLogTask(task);
	}

	@Override
	public synchronized Event[] getEvents(long tsFrom, long tsTo) {
		log.debug("Method getEvents");
		awaitInit();
		return ef.getEvents(tsFrom, tsTo);
	}

	@Override
	public synchronized Event[] getEventsByDescription(String description) {
		log.debug("Method getEventsByDescription");
		awaitInit();
		return ef.getEventsByDescription(description);
	}

	@Override
	public synchronized Event[] getEventsByReference(String varName, String reference) {
		log.debug("Method getEventsByReference");
		awaitInit();
		return ef.getEventsByReference(varName, reference);
	}

	@Override
	public synchronized Event[] getLatestEvents() {
		log.debug("Method getLatestEvents");
		awaitInit();
		return ef.getLatestEvents();
	}

	@Override
	public synchronized Event[] getLatestEvents(String[] varNames) {
		awaitInit();
		return ef.getLatestEvents(varNames);
	}
	
	@Override
	public synchronized void storeEvent(Event e) {
		log.debug("Method storeEvent");
		awaitInit();
		es.storeEvent(e);
	}

	public synchronized LoggingTask findLogTask(String name) {
		log.debug("Method findLogTask");
		awaitInit();
		return lf.findLogTask(name);
	}

	public synchronized LoggingTask getLogTask(long id) {
		log.debug("Method getLogTask");
		awaitInit();
		return lf.getLogTask(id);
	}

	public synchronized long logTaskExists(String name) {
		log.debug("Method logTaskExists");
		awaitInit();
		return lf.logTaskExists(name);
	}

	public synchronized List<LoggingTaskData> getFinishedLogTasks() {
		log.debug("Method getFinishedLogTasks");
		awaitInit();
		return lf.getFinishedLogTasks();
	}

	public synchronized void flush(){
		awaitInit();
		flushChunks(dataBuffer.getAllChunkArrays(nextChunkArray));
	}
	
	public synchronized void flushFullChunks() {
		flushChunks(dataBuffer.getChunkArrays(nextChunkArray));		
	}
	
	private void flushChunks(ArrayList<ArrayList<Data>> chunks){
		long tTotal = System.currentTimeMillis();
		log.debug("Flushing "+chunks.size() +" chunks");
		for (ArrayList<Data> subData : chunks){
			//log.debug("Start saving chunk");
			//long tStart = System.currentTimeMillis();
			saveChunk(subData.toArray(new Data[0]));
			//log.debug("Chunk saved in "+(tStart-System.currentTimeMillis() +" ms"));
		}
		chunks.clear();
		nextChunkArray = chunks;
		log.debug("Chunks flushed in "+(System.currentTimeMillis()-tTotal));
	}
	
	private void saveChunk(Data[] array) {
		if (array==null || array.length<1 || array[0]==null) return;
		Arrays.sort(array);
		String sqlInsert = "INSERT INTO LogTaskData"+array[0].getLoggingTask()+" (name,value,batchsize, fromts, tots) VALUES(?,?,?,?,?)";
		try {
			long tStart = System.currentTimeMillis();
			PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);
			log.debug("Time to prepare stmt: "+(System.currentTimeMillis()-tStart));
			try{
				statement.setString(1,array[0].getName());
				statement.setBlob(2,DataChunk.getBlob(array));
				statement.setInt(3, array.length);
				statement.setTimestamp(4, new Timestamp(array[0].getTimestamp().getTime()));
				statement.setTimestamp(5, new Timestamp(array[array.length-1].getTimestamp().getTime()));
				statement.execute();
				statement.close();
				//log.debug("Added "+array.length +" in "+array[0].getLoggingTask());
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

	private void initDatabaseConnection(){
		log.debug("Derby home: "+System.getProperty("derby.system.home"));
		System.setProperty("derby.system.home", OplProperties.getDbPath());
		log.debug("Derby home: "+System.getProperty("derby.system.home"));
		String path = OplProperties.getDbPath();
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
			} catch (SQLException e) {
				log.debug("Connect failed: "+e.getMessage());
				createDatabase(dbFile.getAbsolutePath());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createDatabase(String dbPath) {
		String[] cmds = new String[6];
		cmds[1] = "CREATE TABLE LogTask(id BIGINT GENERATED BY DEFAULT AS IDENTITY CONSTRAINT LogTask_PK PRIMARY KEY, name VARCHAR(255),info VARCHAR(500), startTime TIMESTAMP NOT NULL, stopTime TIMESTAMP NOT NULL)";
		cmds[0] = "CREATE TABLE LogTaskProperties (id BIGINT GENERATED BY DEFAULT AS IDENTITY CONSTRAINT LogTaskProperties_PK PRIMARY KEY, name VARCHAR(500),value VARCHAR(500),object BIGINT NOT NULL)";
		cmds[2] = "CREATE TABLE Equipment(id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY CONSTRAINT Equipment_PK PRIMARY KEY, name VARCHAR(255) NOT NULL CONSTRAINT Equipment_UC UNIQUE)";
		cmds[3] = "CREATE TABLE Data(id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY CONSTRAINT Data_PK PRIMARY KEY,name VARCHAR(500),value BLOB,timestamp TIMESTAMP NOT NULL, loggtaskid BIGINT NOT NULL, equipmentid BIGINT,lineInfo INT, pointType INT)";
		cmds[4] = "CREATE TABLE Subscriptions(id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY CONSTRAINT Subscriptions_PK PRIMARY KEY, name VARCHAR(500),logtask BIGINT NOT NULL)";
		cmds[5] = "CREATE TABLE Events(id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY CONSTRAINT Events_PK PRIMARY KEY, etype VARCHAR(500), value BLOB, timestamp TIMESTAMP NOT NULL, refid BIGINT NOT NULL)";
		log.debug("Attempting to create database in "+dbPath);
		long tStartAll = System.currentTimeMillis();
		long tStart = System.currentTimeMillis();
		try {
			log.debug("Login timeout: " +DriverManager.getLoginTimeout());
			//DriverManager.registerDriver(Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance());
			//this.dbConnection = DriverManager.getConnection("jdbc:derby:"+dbPath+";create=true");
			this.dbConnection = DriverManager.getConnection("jdbc:derby:"+DBNAME+";create=true");
			log.debug("Time to create connection: " +(System.currentTimeMillis()-tStart));
			tStart = System.currentTimeMillis();
			Statement stmt = dbConnection.createStatement();
			log.debug("Time to create statement: " +(System.currentTimeMillis()-tStart));
			tStart = System.currentTimeMillis();
			for (String s : cmds){
				stmt.execute(s);
			}
			dbConnection.commit();
			log.debug("Time to create database: " +(System.currentTimeMillis()-tStart));
			log.debug("Database created in "+(System.currentTimeMillis()-tStartAll)+" ms");
		} catch (SQLException e) {
			log.debug("Creating database failed: "+e.getMessage());
			//e.printStackTrace();
		}
	}
	
	long tStart;
	private void startTimer(String string) {
		log.debug(string);
		tStart = System.currentTimeMillis();
	}
	private void printTimer(String string) {
		log.debug(string+" " +(System.currentTimeMillis()-tStart));
	}

}
