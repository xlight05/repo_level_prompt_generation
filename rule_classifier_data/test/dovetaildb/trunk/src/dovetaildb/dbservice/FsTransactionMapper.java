package dovetaildb.dbservice;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;

import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.FsBlueSteelBagIndex;
import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNode.NextStatus;
import dovetaildb.util.Util;

public class FsTransactionMapper implements TransactionMapper {

	private static final long serialVersionUID = -683617156786989352L;
	
	protected FsBlueSteelBagIndex map;
	transient protected long headTxn = -1;
	transient protected boolean nextReserved = false;

	
	private void readObject(ObjectInputStream in) {
		try {
			in.defaultReadObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// figure out headTxn
		long revNum = map.getCurrentRevNum();
		QueryNode query = map.getRange(Range.OPEN_RANGE, revNum);
		if (query == null) {
			headTxn = -1;
		} else {
			long docId = query.doc();
			do {
				if (!query.positionSet(docId, ArrayBytes.EMPTY_BYTES)) {
					throw new RuntimeException();
				}
				do {
					Bytes nameTxn = query.term();
					int nameTxnLen = nameTxn.getLength();
					long txn = Util.beBytesToLong(nameTxn.getBytes(nameTxnLen - 8, 8), 0);
					if (txn > headTxn) headTxn = txn;
				} while (query.positionNext());
				docId = query.nextValidDocId(docId + 1);
			} while (docId < Long.MAX_VALUE);
//			do {
//				long txn = Util.beBytesToLong(query.term().getBytes(0, 8), 0);
//				if (txn > headTxn) headTxn = txn;
//			} while (query.next());
		}
	}
	
	public FsTransactionMapper(File file, boolean sync) {
		map = new FsBlueSteelBagIndex(sync);
		File mapDir = new File(file, "txnmap");
		if (!mapDir.mkdir()) throw new RuntimeException("Cannot create transaction map");
		map.setHomedir(mapDir.getAbsolutePath());
	}

	public String toString() {
		return "FsTransactionMapper(head="+headTxn+", "+map+")";
	}
	
	public synchronized void addRevsForTxn(long txn, Map<String, Long> revNums) {
		ArrayList<EditRec> edits = new ArrayList<EditRec>(revNums.size());
		for(Map.Entry<String,Long> bagRev : revNums.entrySet()) {
			Bytes bytes = termForTxnBag(bagRev.getKey(), txn);
			edits.add(new EditRec(bagRev.getValue(), bytes, false));
		}
		map.commitNewRev(edits);
		if (txn > headTxn) headTxn = txn;
		nextReserved = false;
	}

	protected Bytes termForTxnBag(String bag, long txn) {
		byte[] bagNameBytes = bag.getBytes();
		byte[] revNumBytes = new byte[9];
		Util.beLongToBytes(txn, revNumBytes, 1);
		return new CompoundBytes(
				new ArrayBytes(bagNameBytes),
				new ArrayBytes(revNumBytes));
	}
	
	public long getRevForTxn(String bag, long txnId)  throws TxnNotFoundException {
		long mapRevNum = map.getCurrentRevNum();
		Range range = new Range(ArrayBytes.EMPTY_BYTES, null, null, true, true);
		range.setBoundsAndExtractPrefix(
				termForTxnBag(bag, 0),
				termForTxnBag(bag, txnId));
		QueryNode query = map.getRange(range, mapRevNum);
		long revNum = 0;
		if (query != null) {
			do {
				long curRev = query.doc();
				if (curRev > revNum) revNum = curRev;
			} while(query.nextTerm() != NextStatus.AT_END);
		}
		return revNum;
		/*
		QueryNode query = map.getTerm(termForTxnBag(txnId, bag), revNum);
		if (query != null) {
			return query.doc();
		} else {
			Range range = new Range(termForTxnBag(txnId, ""), null, null, true, true);
			query = map.getRange(range, revNum);
			if (query == null) {
				throw new TxnNotFoundException(txnId);
			} else {
				return 0;
			}
		}
		*/
	}

	public long reserveTxnId() {
		if (nextReserved) throw new RuntimeException("Previous transaction not resolved");
		nextReserved = true;
		return getHighestTxnId() + 1;
	}

	public long getHighestTxnId() {
		return headTxn;
	}
	
}
