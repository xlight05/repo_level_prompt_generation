package se.openprocesslogger.db;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;

public class DataBuffer {
	public static final int CHUNK_SIZE = 2000;
	HashMap<Long, HashMap<String, ArrayList<Data>>> dataBuffer;
	private Logger log = Logger.getLogger(DataBuffer.class);
	private ArrayList<ArrayList<Data>> chunkArray;
	private int dataCount;
	
	public DataBuffer() {
		super();
		this.dataBuffer = new HashMap<Long, HashMap<String,ArrayList<Data>>>();
		this.chunkArray = new ArrayList<ArrayList<Data>>();
	}
	
	public synchronized void addData(Data data){
		insertData(data);
		dataCount ++;
	}
	
	public synchronized void addData(Data[] data){
		for(Data d : data){
			insertData(d);
			dataCount ++;
		}
	}
	
	public synchronized int nbrChunkArrays(){
		return chunkArray.size();
	}
	
	public synchronized int getDataCount() {
		return dataCount;
	}

	public synchronized ArrayList<ArrayList<Data>> getChunkArrays(ArrayList<ArrayList<Data>> nextChunkArrays){
		log.debug("Clearing full chunk buffer on "+chunkArray.size() +" chunks");
		ArrayList<ArrayList<Data>> temp = chunkArray;
		chunkArray = nextChunkArrays;
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<ArrayList<Data>> getAllChunkArrays(ArrayList<ArrayList<Data>> nextChunkArrays){
		log.debug("Totally emptying buffer on "+dataCount +" datas and "+chunkArray.size() +" chunks");
		ArrayList<ArrayList<Data>> temp = chunkArray;
		chunkArray = nextChunkArrays;
		for (HashMap<String, ArrayList<Data>> logTaskData : dataBuffer.values()){
			for (ArrayList<Data> subData : logTaskData.values()){
				if(subData.size() > 0){
					temp.add((ArrayList<Data>) subData.clone());
					subData.clear();
				}
			}
		}
		dataCount = 0;
		return temp;
	}
	
	public synchronized HashMap<String, ArrayList<Data>> getLogTaskData(long logTaskId){
		HashMap<String, ArrayList<Data>> temp = dataBuffer.get(logTaskId);
		dataBuffer.put(logTaskId, new HashMap<String, ArrayList<Data>>());
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	private void insertData(Data data) {
		HashMap<String, ArrayList<Data>> logTaskData = dataBuffer.get(data.getLoggingTask());
		if(logTaskData == null){
			logTaskData = new HashMap<String, ArrayList<Data>>();
			dataBuffer.put(data.getLoggingTask(), logTaskData);
		}
		ArrayList<Data> subData = logTaskData.get(data.getName());
		if(subData == null){
			subData = new ArrayList<Data>(CHUNK_SIZE);
			logTaskData.put(data.getName(), subData);
		}
		subData.add(data);
		if(subData.size() >= CHUNK_SIZE){ // Move data to full array
			log.debug("Filled array of "+data.getName());
			chunkArray.add((ArrayList<Data>) subData.clone());
			dataCount -= subData.size();
			subData.clear();
		}
	}
}
