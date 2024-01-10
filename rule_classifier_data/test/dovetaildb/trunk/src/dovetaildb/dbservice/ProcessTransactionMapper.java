package dovetaildb.dbservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dovetaildb.bagindex.BagIndex;

public class ProcessTransactionMapper implements TransactionMapper {
	
	long headTxn = -1;
	boolean nextReserved = false;
	ConcurrentHashMap<Long, Map<String,Long>> txnMap = new ConcurrentHashMap<Long, Map<String,Long>>();
	ConcurrentHashMap<String,Long> headMap = new ConcurrentHashMap<String,Long>(); 

	public ProcessTransactionMapper() {
	}

	public void addRevsForTxn(long txn, Map<String, Long> revNums) {
		headMap.putAll(revNums);
		txnMap.put(txn, new ConcurrentHashMap<String,Long>(headMap));
		if (txn > headTxn) headTxn = txn;
		nextReserved = false;
	}
	
	public long getRevForTxn(String bag, long txnId)  throws TxnNotFoundException {
		Map<String,Long> bagMap = txnMap.get(txnId);
		if (bagMap == null) return 0;
		Long revNum = bagMap.get(bag);
		if (revNum == null) return 0;
		else return revNum.longValue();
	}

	public void introduceBag(String bagName, BagIndex bagIndex) {
		long initialRev = bagIndex.getCurrentRevNum();
		headMap.put(bagName, initialRev);
	}

	public long reserveTxnId() {
		if (nextReserved) throw new RuntimeException("Previous transaction not resolved");
		nextReserved = true;
		return getHighestTxnId() + 1;
	}
	
	public long getHighestTxnId() {
		if (headTxn == -1) {
			long maxTxn = -1;
			for (long txn : txnMap.keySet()) {
				if (txn > maxTxn) maxTxn = txn;
			}
			headTxn = maxTxn;
		} 
		return headTxn;
	}
	
}
