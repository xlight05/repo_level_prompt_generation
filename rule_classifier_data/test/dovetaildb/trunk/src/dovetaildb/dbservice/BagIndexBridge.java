package dovetaildb.dbservice;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import dovetaildb.api.ApiBuffer;
import dovetaildb.bagindex.BagIndex;
import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.iter.EmptyIter;
import dovetaildb.iter.Iter;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNodeTemplate;
import dovetaildb.querynode.QueryNode.NextStatus;
import dovetaildb.util.Dirty;
import dovetaildb.util.Util;

public class BagIndexBridge extends Dirty.Abstract implements DbService {

	private static final long serialVersionUID = 92384583422483L;
	
	public BagIndexBridge() {}
	public BagIndexBridge(File homeDir) {
		this.homeDir = homeDir;
	}
	
	File homeDir;

	public String toString() {
		return txnMapper+":"+bagIndexes;
	}
	
	ConcurrentHashMap<String,BagEntry> bagIndexes = 
		new ConcurrentHashMap<String,BagEntry>();
	
	TransactionMapper txnMapper = null;
	
	@Override
	public File getHomeDir() { return homeDir; }
	
	public TransactionMapper getTxnMapper() { return txnMapper; }
	public void setTxnMapper(TransactionMapper txnMapper) { this.txnMapper = txnMapper; }

	private BagEntryFactory bagEntryFactory;
	public BagEntryFactory getBagEntryFactory() {
		return bagEntryFactory;
	}
	public void setBagIndexFactory(BagEntryFactory bagEntryFactory) {
		this.bagEntryFactory = bagEntryFactory;
	}
	
	public BagEntry getBagEntry(String bag) {
		return bagIndexes.get(bag);
	}
	
	public void setBagEntry(String bag, BagEntry bagEntry) {
		bagIndexes.put(bag, bagEntry);
	}
	
	public long capacity() {
		return 500;
	}

	static final ArrayBytes ID_BYTES;
	static final int NUM_ID_BYTES;
	static {
		try {
			ID_BYTES = new ArrayBytes("{id:s".getBytes("utf-8"));
			NUM_ID_BYTES = ID_BYTES.getLength();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private ArrayList<EditRec> encodeBuffer(String bagName, long revNum, ApiBuffer value) {
		ArrayList<EditRec> edits = new ArrayList<EditRec>();

		BagEntry bagRec = bagIndexes.get(bagName);
		BagIndex index = bagRec.getBagIndex();
		QueryNode idQuery = null;
		Map<String,Object> entries = value.getEntries();
		QueryNode data = null;
		if (revNum > 0) {
			data = index.getRange(Range.OPEN_RANGE, revNum);
			int capacity = value.getDeletions().size()+value.getEntries().size();
			if (capacity > 0) {
				ArrayList<Bytes> idTerms = new ArrayList<Bytes>(capacity);
				for(String key : value.getDeletions()) {
					idTerms.add(new CompoundBytes(ID_BYTES, new ArrayBytes(Util.decodeString(key))));
				}
				for(String key : entries.keySet()) {
					idTerms.add(new CompoundBytes(ID_BYTES, new ArrayBytes(Util.decodeString(key))));
				}
				idQuery = index.getTerms(idTerms, revNum);
			}
		}
		long lastDocId = Long.MIN_VALUE;
		if (idQuery != null) {
			long docId = idQuery.doc();
			assert docId > lastDocId;
			lastDocId = docId;
			do {
				Bytes idTerm = idQuery.term();
				assert idTerm.compareToParts(ID_BYTES, 0, 0, NUM_ID_BYTES, NUM_ID_BYTES, 9) == 0;
				byte[] idBytes = idTerm.subBytes(NUM_ID_BYTES, idTerm.getLength()-NUM_ID_BYTES).getBytes();
				String idString = Util.encodeBytes(idBytes);
				Object val = entries.get(idString);
				if (val != null) {
					entries.remove(idString);
					DbServiceUtil.sencodeMulti(ArrayBytes.EMPTY_BYTES, ArrayBytes.EMPTY_BYTES, val, edits, docId, false);
				}
				
				/* QNWORK
				data.seek(docId, ArrayBytes.EMPTY_BYTES);
				do {
					edits.add(new EditRec(docId, data.term().copy(), true));
				} while(data.nextTerm() == NextStatus.NEXT_TERM);
				*/
				if (! data.positionSet(docId, ArrayBytes.EMPTY_BYTES)) {
					assert false;
				}
				do {
					edits.add(new EditRec(docId, data.term().copy(), true));
				} while (data.positionNext());
				docId = idQuery.nextValidDocId(docId + 1);
			} while(docId != Long.MAX_VALUE);
		}
		long insId = -1;
		for(Object val : entries.values()) {
			long docId = insId--;
			DbServiceUtil.sencodeMulti(ArrayBytes.EMPTY_BYTES, ArrayBytes.EMPTY_BYTES, val, edits, docId, false);
		}
		return edits;
	}
	
	public long commit(long fromTxnId, Map<String, ApiBuffer> batch) {
		long commitTxnId = txnMapper.reserveTxnId();
		HashMap<String,Long> bagRevs = new HashMap<String,Long>();
		for(Map.Entry<String,ApiBuffer> entry : batch.entrySet()) {
			String bag = entry.getKey();
			BagEntry rec = bagIndexes.get(bag);
			if (rec == null) {
				rec = bagEntryFactory.makeBagEntry(bag);
				bagIndexes.put(bag, rec);
				setDirty();
			}
			long curRevNum = rec.getBagIndex().getCurrentRevNum();
			ArrayList<EditRec> bufferedData = encodeBuffer(bag, curRevNum, entry.getValue());
//			System.out.println("Commit to "+bag+" : "+bufferedData);
			EditRec.sortByTerm(bufferedData);
			long commitRevNum = rec.getBagIndex().commitNewRev(bufferedData);
			bagRevs.put(bag, commitRevNum);
		}
		txnMapper.addRevsForTxn(commitTxnId, bagRevs);
		return commitTxnId;
	}
	
	public void drop() {
		for(String bag : bagIndexes.keySet()) {
			dropBag(bag);
		}
		File homeDir = getHomeDir();
		if (homeDir != null) Util.deleteDirectory(homeDir);
	}
	
	public void dropBag(String bagName) {
		BagEntry entry = bagIndexes.get(bagName);
		entry.getBagIndex().close();
		bagIndexes.remove(bagName);
	}
	
	public Collection<String> getBags(long txnId) {
		ArrayList<String> bags = new ArrayList<String>();
		for(String bag : bagIndexes.keySet()) {
			try {
				long rev = txnMapper.getRevForTxn(bag, txnId);
				if (rev >= 1) bags.add(bag);
			} catch (TxnNotFoundException e) {
				return null;
			}
		}
		return bags;
	}
	
	public void initialize() {}

	@Override
	public Iter query(String bag, long txnId, Object query, Map<String, Object> options) {
		BagEntry rec = bagIndexes.get(bag);
		if (rec == null) return EmptyIter.EMPTY_ITER;
		BagIndex index = rec.getBagIndex();
		try {
			long revNum = txnMapper.getRevForTxn(bag, txnId);
			if (revNum == -1) return EmptyIter.EMPTY_ITER;
			QueryNodeTemplate templ = DbServiceUtil.applyPatternToBagIndex(query, index, revNum);
			//System.out.println("Query plan: "+templ.queryNode);
			if (templ.queryNode == null) return EmptyIter.EMPTY_ITER;
			if (templ.varMappings.size() == 1) {
				QueryNode display = templ.varMappings.values().iterator().next();
				return new QueryNodeIter(templ.queryNode, display, index, revNum);
			} else {
				throw new RuntimeException("Multiple result returns not yet supported");
			}
		} catch (TxnNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void rollback(long commitId) {
		// TODO
		throw new RuntimeException();
	}
	
	public ApiBuffer createBufferedData(String bagName) {
		return new ApiBuffer();
	}
	
	public long getHighestCompletedTxnId() {
		return txnMapper.getHighestTxnId();
	}
	@Override
	public BagIndex getBag(String bagName) {
		BagEntry entry = bagIndexes.get(bagName);
		if (entry == null) return null;
		return entry.getBagIndex();
	}
	@Override
	public Map<String,Object> getMetrics(int detailLevel) {
		Map<String,Object> m = Util.literalSMap();
		for(Map.Entry<String, BagEntry> entry : bagIndexes.entrySet()) {
			String bagName = entry.getKey();
			m.put(bagName, entry.getValue().getBagIndex().getMetrics(detailLevel));
		}
		return m;
	}

}
