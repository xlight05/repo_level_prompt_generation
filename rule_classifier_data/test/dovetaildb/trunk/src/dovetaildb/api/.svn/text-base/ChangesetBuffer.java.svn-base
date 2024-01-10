package dovetaildb.api;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dovetaildb.dbservice.DbService;
import dovetaildb.iter.Iter;
import dovetaildb.iter.MergeIter;
import dovetaildb.util.Util;

public class ChangesetBuffer implements ApiService {

	/**
	 * Remembers edits within a transaction.
	 * Bridges an ApiService to a DbService.
	 */

	protected DbService db;
	protected Map<String,ApiBuffer> buffer;
	protected long readTxnId;
	
	public ChangesetBuffer(DbService db){
		this.db = db;
		this.readTxnId = db.getHighestCompletedTxnId();
		this.buffer = new HashMap<String,ApiBuffer>();
	}
	
	public String toString() {
		return "ChangesetBuffer("+buffer+", readTxn="+readTxnId+", buffer="+buffer+")";
	}

	public boolean isEmpty() {
		if (buffer.isEmpty()) return true;
		for(ApiBuffer apiBuffer: buffer.values()) {
			if (! apiBuffer.isEmpty()) return false;
		}
		return true;
	}
	
	public void commit() {
		if (isEmpty()) return;
		db.commit(readTxnId, buffer);
		rollback();
	}

	public void rollback() {
		this.buffer = new HashMap<String,ApiBuffer>();
		this.readTxnId = db.getHighestCompletedTxnId();
	}

	private ApiBuffer getDataForBag(String bag) {
		ApiBuffer data = buffer.get(bag);
		if (data == null) {
			data = db.createBufferedData(bag);
			buffer.put(bag, data);
		}
		return data;
	}
	
	@Override
	public Iter query(String bag, Object query, Map<String,Object> options) {
		ApiBuffer data = getDataForBag(bag);
		if (data.isEmpty()) {
			return db.query(bag, readTxnId, query, options); 
		} else {
			Iter i1 = data.query(query, options);
			List idList = new ArrayList();
			idList.add("|");
			idList.addAll(data.getDeletions());
			idList.addAll(data.getEntries().keySet());
			List notClause = Util.literalList().a("!").a(Util.literalMap().p("id", idList)); 
			query = Util.literalList().a("&").a(query).a(notClause);
			Iter i2 = db.query(bag, readTxnId, query, options);
			return new MergeIter(i1, i2);
		}
	}

	public void remove(String bag, String id) {
		ApiBuffer data = getDataForBag(bag);
		data.getDeletions().add(id);
	}

	public void put(String bag, Map entry) {
		if (! entry.containsKey("id")) {
			throw new ApiException("MissingId", "This object must have an \"id\" property: "+entry);
		}
		ApiBuffer data = getDataForBag(bag);
		data.put(entry);
	}

	public Map<String,Object> getMetrics(int detailLevel) {
		return db.getMetrics(detailLevel);
	}
	
}
