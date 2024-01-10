package se.openprocesslogger.db;

import org.apache.log4j.Logger;

public class DataSaver extends Thread{
	Logger log = Logger.getLogger(DataSaver.class);
	
	private DataBuffer dataBuffer;
	private long tLastFlush;
	
	public DataSaver(DataBuffer dataBuffer){
		super("DataSaver");
		this.dataBuffer = dataBuffer;
	}

	public void run(){
		tLastFlush = System.currentTimeMillis();
		while(true){
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			long tElapsed = System.currentTimeMillis() - tLastFlush;
			int nbrFullArrays = dataBuffer.nbrChunkArrays();
			//log.debug("Full arrays: "+nbrFullArrays +" ("+dataBuffer.getDataCount()+")");
			if (nbrFullArrays >= 5){
				OPLDatabase.instance().flushFullChunks();
				//tLastFlush = System.currentTimeMillis();
			}else if (tElapsed > 120000 && dataBuffer.getDataCount()>0){
				OPLDatabase.instance().flush();
				tLastFlush = System.currentTimeMillis();
			}else if (tElapsed > 120000){
				tLastFlush = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.debug("DataSaver thread interrupted");
				return;
			}
		}
	}
}
