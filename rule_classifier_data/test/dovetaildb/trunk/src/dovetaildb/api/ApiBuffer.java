package dovetaildb.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.TrivialBagIndex;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.dbservice.BagEntry;
import dovetaildb.dbservice.BagIndexBridge;
import dovetaildb.dbservice.DbServiceUtil;
import dovetaildb.dbservice.ProcessTransactionMapper;
import dovetaildb.iter.Iter;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNodeTemplate;
import dovetaildb.util.LiteralHashMap;
import dovetaildb.util.Util;

public class ApiBuffer implements Serializable {

	private static final long serialVersionUID = 6917420917913595400L;
	
	private final HashSet<String> deletions = new HashSet<String>();
	private final Map<String,Object> entries = new HashMap<String,Object>();
	
	public HashSet<String> getDeletions() { return deletions; }

	public Map<String,Object> getEntries() { return entries; }

	public Iter query(Object query, Map<String, Object> options) {
		// TODO: this work should be done once and saved somehow
		// just need to figure out how to invalidate when something
		// changes
		ArrayList<EditRec> buffer = new ArrayList<EditRec>();
		long tmpId = -1000;
		for(Object entry : entries.values()) {
			DbServiceUtil.sencodeMulti(ArrayBytes.EMPTY_BYTES, ArrayBytes.EMPTY_BYTES, entry, buffer, tmpId--, false);
		}
		TrivialBagIndex puts = new TrivialBagIndex();
		long revNum = puts.commitNewRev(buffer);
		BagIndexBridge bridge = new BagIndexBridge();
		bridge.setTxnMapper(new ProcessTransactionMapper());
		bridge.setBagEntry("",new BagEntry(puts));
		HashMap<String,Long> revs = new HashMap<String,Long>();
		revs.put("", revNum);
		bridge.getTxnMapper().addRevsForTxn(0, revs);
		return bridge.query("", 0, query, options);
	}

	public void put(Map<String, Object> entry) {
		Object idVal = entry.get("id");
		if (!(idVal instanceof String)) {
			throw new ApiException("IdTypeError", "The \"id\" property must be a string; the bad entry was: "+entry);
		}
		put((String)entry.get("id"), entry);
	}
	public void put(String id, Map<String,Object> entry) {
		if (deletions.contains(id)) {
			deletions.remove(id);
		}
		entries.put(id, entry);
		if (entry.containsKey("id")) {
			if (!id.equals(entry.get("id"))) {
				throw new RuntimeException("Conflicting IDs specified: "+id+" versus the id declared in this entry "+entry);
			}
		} else {
			entry.put("id", id);
		}
	}

	public boolean isEmpty() {
		return entries.isEmpty() && deletions.isEmpty();
	}

	public int estimatedNewDeadSpace() {
		return deletions.size()*4 + entries.size();
	}
	
	public String toString() {
		return "ApiBuffer(entries="+entries+", dels="+deletions+")";
	}
}
