package se.openprocesslogger.db;

import se.openprocesslogger.Data;

public interface IDataFetcher {

	//public Data[] getData(String name, long logTaskId);

	public boolean hasData(long logTaskId);

	public Data[] getBatch(String name, long tsFrom, long tsTo, int maxNbr);
	public Data[] getBatch(String name, long logTaskId, long tsFrom, long tsTo, int maxNbr);
	public Data[] getEventBatch(String name, long tsFrom, long tsTo, int maxNbr);

	public Data getSampleData(String name);

	public Data[] getData(String name, long tsFrom, long tsTo);

	//public int getDataCount(String name, long logTaskId);

	//public int getDataCount(String name, long tsFrom, long tsTo);

	public Data getSingleData(long dataId, String varName, long timestamp);

	public Data[] getSurroundingData(String varName, long timestamp);	

}
