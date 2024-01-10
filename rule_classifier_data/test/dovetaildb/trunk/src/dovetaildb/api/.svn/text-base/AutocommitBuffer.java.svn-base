package dovetaildb.api;

import java.util.HashMap;

import java.util.Map;

import dovetaildb.dbservice.DbService;

public class AutocommitBuffer {

	private DbService dbService;
	private Map<String, ApiBuffer> bags = new HashMap<String, ApiBuffer>();
	private int threshold;
	private int ctr=0;
	private long readTxnId;
	
	public AutocommitBuffer(DbService dbService, int threshold) {
		this.dbService = dbService;
		this.threshold = threshold;
		readTxnId = dbService.getHighestCompletedTxnId();
	}
	
	public void putEntryIntoBag(Map entry, String bag) {
		if (! bags.containsKey(bag)) {
			bags.put(bag, dbService.createBufferedData(bag));
		}
		ApiBuffer api = bags.get(bag);
		api.put(entry);
		if (++ctr >= threshold) {
			flush();
		}
	}
	
	public void flush() {
		readTxnId = dbService.commit(readTxnId, bags);
		bags.clear();
		ctr = 0;
	}

	
}
