package dovetaildb.dbservice;

import java.io.Serializable;
import java.util.Map;

public interface TransactionMapper extends Serializable {
	
	/** returns -1 if the bag did not exist at the time of the transaction */
	public long getRevForTxn(String bag, long txnId) throws TxnNotFoundException;
	
	public void addRevsForTxn(long txn, Map<String,Long> revNums);

	public long getHighestTxnId();

	public long reserveTxnId();

}
