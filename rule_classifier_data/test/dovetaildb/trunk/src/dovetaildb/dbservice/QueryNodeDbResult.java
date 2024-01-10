package dovetaildb.dbservice;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.querynode.FilteredQueryNode;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNode.NextStatus;


public class QueryNodeDbResult extends AbstractDbResult {
	
	final Bytes prefix, suffix;
	QueryNode node;
	HashMap<String, QueryNodeDbResult> byObjectKey;
	ArrayList<QueryNodeDbResult> byArrayIndex;
	final DbResultMapView mapView = new DbResultMapView(this);
	final DbResultListView listView = new DbResultListView(this);
	

	// these change per result:
	Status status;
	Bytes firstTerm;
	long docId;
	char type;
	
	private enum Status {clear, initialized, materialized};
	
	public QueryNodeDbResult(QueryNode node) {
		prefix = ArrayBytes.EMPTY_BYTES;
		suffix = ArrayBytes.EMPTY_BYTES;
		clear();
		this.node = node;
	}
	
	protected QueryNodeDbResult(QueryNode parent, Bytes prefix, Bytes suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		Range subRange = new Range(prefix, ArrayBytes.SINGLE_BYTE_OBJECTS[0], null, true, true);
		node = parent.specialize(new Range(subRange));
		if (node == null) {
			initializeAsNull();
		} else {
			if (node.doc() == Long.MAX_VALUE) {
				initializeAsNull();
			} else {
				if (node == null) {
					initializeAsNull();
				} else {
					status = Status.clear;
//					initialize(docId);
				}
			}
		}
	}
	

	public void clear() {
		node = null;
		byObjectKey = null;
		byArrayIndex = null;
		//firstTerm = null;
		type = ' ';
		status = Status.clear;
	}
	
	public void reset() {
		type = ' ';
		//firstTerm = null;
		status = Status.clear;
		if (byObjectKey != null) {
			for(QueryNodeDbResult result : byObjectKey.values()) {
				if (result.status != Status.clear) result.reset();
			}
		}
		if (byArrayIndex != null) {
			for(QueryNodeDbResult result : byArrayIndex) {
				if (result.status != Status.clear) result.reset();
			}
		}
	}
	
	private void initializeAsNull() {
		this.type = 'l';
		this.status = Status.initialized;
		//this.firstTerm = null;
	}

	public boolean initialize(long docId) {
		this.docId = docId;
		if (node.positionSet(docId, prefix)) {
			int suffixLen = suffix.getLength();
			int usedBytes = prefix.getLength() + suffixLen;
			do {
				Bytes term = node.term();
				int curLen = term.getLength();
				if (curLen > usedBytes && 
					term.compareToParts(suffix, curLen-suffixLen, 0, suffixLen, suffixLen, 9)==0) {
					firstTerm = term.copyInto(firstTerm);
					type = (char)firstTerm.get(prefix.getLength());
					this.status = Status.initialized;
					return true;
				}
			} while (node.positionNext());
		}
		initializeAsNull();
		return false;
	}
/*
	public boolean initialize(long docId) {
		this.docId = docId;
		node.seek(docId, prefix);
		if (node.doc() == docId) {
			int suffixLen = suffix.getLength();
			int usedBytes = prefix.getLength() + suffixLen;
			do {
				Bytes term = node.term();
				int curLen = term.getLength();
				if (! prefix.isPrefixOf(term)) break;
				if (curLen > usedBytes && 
					term.compareToParts(suffix, curLen-suffixLen, 0, suffixLen, suffixLen, 9)==0) {
					firstTerm = term.copyInto(firstTerm);
					type = (char)firstTerm.get(prefix.getLength());
					this.status = Status.initialized;
					return true;
				}
			} while (node.nextTerm() == NextStatus.NEXT_TERM);
		}
		initializeAsNull();
		return false;
	}
		
*/
	
//		Bytes aTerm = null;
//		boolean startedEarlyEnough = node.compareTo(docId, prefix) <= 0;
//		if (node.doc() <= docId) {
//			// if we're mid way though, just finish the current pass before rewinding
//			aTerm = scatterFind();
//		}
//		if (aTerm == null && ! startedEarlyEnough) {
//			// we may have been part way into the current doc, rewind and try again:
//			node.seek(docId, prefix);
//			aTerm = scatterFind();
//		}
//		if (aTerm == null) {
//			initializeAsNull();
//			return false;
//		} else {
//			firstTerm = aTerm.copyInto(firstTerm);
//			type = (char)firstTerm.get(prefix.getLength());
//			this.status = Status.initialized;
//			return true;
//		}
//	}
	
	private Bytes scatterFind() {
		while (true) {
			Bytes aTerm = node.findAnyMatching(docId, prefix);
			if (aTerm == null) return null;
			if (suffix.isSuffixOf(aTerm)) {
				return aTerm;
			}
		}
	}
	
	public Object simplify() {
		switch (type) {
		case '[': return listView;
		case '{': return mapView;
		case 'f': return Boolean.FALSE;
		case 'l': return null;
		case 'n': return getDouble();
		case 's': return getString();
		case 't': return Boolean.TRUE;
		default:
			throw new RuntimeException();
		}
	}
	
	public char getType() { return type; }

	public QueryNodeDbResult derefByKey(String key) {
		QueryNodeDbResult result = getByKey(key);
		if (result.status == Status.clear) {
			result.initialize(docId);
		}
		return result;
	}

	public boolean containsKey(String key) {
		QueryNodeDbResult ret = derefByKey(key); // ensures proper initialization
		return (ret.type != ' '); // was the null initialized by an actual null term?
	}

	protected QueryNodeDbResult getByKey(String key) {
		if (byObjectKey == null) {
			byObjectKey = new HashMap<String, QueryNodeDbResult>();
		}
		QueryNodeDbResult result = byObjectKey.get(key);
		if (result == null) {
			Bytes byteKey = new CompoundBytes(DbServiceUtil.HEADER_BYTE_MAPOPEN,
					new CompoundBytes(DbServiceUtil.sencodeMapKey(key),
							DbServiceUtil.HEADER_BYTE_COLON));
			Bytes subPrefix = new CompoundBytes(prefix, byteKey).flatten();
			result = new QueryNodeDbResult(node, subPrefix, suffix);
			byObjectKey.put(key, result);
		}
		return result;
	}

	public DbResult derefByIndex(int index) {
		materialize();
		QueryNodeDbResult result = getByIndex(index);
		if (result.status == Status.clear) {
			if (!result.initialize(docId)) { 
				throw new IndexOutOfBoundsException(Integer.toString(index));
			}
		}
		return result;
	}
	
	protected QueryNodeDbResult getByIndex(int index) {
		// index dref requires materialization
		if (byArrayIndex == null) {
			byArrayIndex = new ArrayList<QueryNodeDbResult>();
		}
		if (index >= byArrayIndex.size()) {
			byArrayIndex.ensureCapacity(index*2+1);
			for(int addIdx=byArrayIndex.size(); addIdx<=index; addIdx++) {
				byArrayIndex.add(null);
			}
		}
		QueryNodeDbResult result = byArrayIndex.get(index);
		if (result == null) {
			Bytes newPrefix = new CompoundBytes(prefix, DbServiceUtil.HEADER_BYTE_LISTOPEN).flatten();
			Bytes newSuffix = new CompoundBytes(DbServiceUtil.sencodeListIndex(index),suffix).flatten();
			result = new QueryNodeDbResult(node, newPrefix, newSuffix);
			byArrayIndex.set(index, result);
		}
		return result;
	}

	/*
	 * This is not supposed to modify the node position of this result or its children
	 */
	private void materialize(Bytes term) {
		int startIdx = prefix.getLength();
		int suffixLen = suffix.getLength();
		if (status == Status.clear) {
			firstTerm = term.copyInto(firstTerm);
			type = (char)firstTerm.get(prefix.getLength());
		}
		if (type == '{') {
			// empty obj term can have nothing after the '{'
			int termLen = term.getLength(); 
			if (termLen-suffixLen != startIdx+1) {
				String key = DbServiceUtil.sdecodeMapKey(term, startIdx+1);
				QueryNodeDbResult item = getByKey(key);
//				if (item.status==Status.clear) item.initialize(docId);
				item.materialize(term);
			}
		} else if (type == '[') {
			// empty array term can have nothing after the '['
			int termLen = term.getLength(); 
			if (termLen-suffixLen != startIdx+1) {
				int index = DbServiceUtil.sdecodeListIndex(term, termLen-(suffixLen+2));
				QueryNodeDbResult item = getByIndex(index);
//				if (item.status==Status.clear) item.initialize(docId);
				item.materialize(term);
			}
		}
		status = Status.materialized;
	}

	private void materialize() {
		if (status == Status.materialized) return;
		status = Status.materialized;
		if (node.positionSet(docId, prefix)) {
			do {
				materialize(node.term());
			} while(node.positionNext());
		} else {
			this.initializeAsNull();
		}
		/* TODO QNWORK
		node.seek(docId, prefix);
		if (node.doc() != docId) {
			this.initializeAsNull();
			status = Status.materialized;
			return;
		}
		do {
			Bytes term = node.term();
			if (! prefix.isPrefixOf(term)) break;
			materialize(term);
		} while(node.nextTerm() == NextStatus.NEXT_TERM);
		*/
	}
	
	public int getArrayLength() {
		materialize();
		if (type != '[') throw new WrongTypeException(type, '[');
		if (byArrayIndex == null) return 0;
		int arrMax = byArrayIndex.size();
		for(int i=0; i<arrMax; i++) {
			QueryNodeDbResult item = byArrayIndex.get(i);
			if (item == null || item.status == Status.clear) return i;
		}
		return arrMax;
	}
	
	public Collection<String> getObjectKeys() {
		materialize();
		if (type != '{') {
			throw new WrongTypeException(type, '{');
		}
		ArrayList<String> validKeys = new ArrayList<String>();
		if (byObjectKey != null) {
			for(Map.Entry<String, QueryNodeDbResult> entry : byObjectKey.entrySet()) {
				if (entry.getValue().status != Status.clear)
					validKeys.add(entry.getKey());
			}
		}
		return validKeys;
	}	
	
	public String getString() {
		if (type != 's') throw new WrongTypeException(type, 's');
		int startIdx = prefix.getLength() + 1;
		int unusedBytes = startIdx + suffix.getLength(); 
		byte[] bytes = firstTerm.getBytes(startIdx, firstTerm.getLength() - unusedBytes);
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public double getDouble() {
		if (type != 'n') throw new WrongTypeException(type, 'n');
		Bytes term = firstTerm;
		int s = prefix.getLength() + 1;
		long bits = (((long)term.get(s+0)) << 8 * 7) | 
					(((long)term.get(s+1)&0xFF) << 8 * 6) |
					(((long)term.get(s+2)&0xFF) << 8 * 5) |
					(((long)term.get(s+3)&0xFF) << 8 * 4) |
					(((long)term.get(s+4)&0xFF) << 8 * 3) |
					(((long)term.get(s+5)&0xFF) << 8 * 2) |
					(((long)term.get(s+6)&0xFF) << 8 * 1) |
					(((long)term.get(s+7)&0xFF) << 8 * 0);
		
		// see sencode for why we do these bit manipulations:
		if ((bits & 0x8000000000000000L) == 0) {
			bits ^= 0xFFFFFFFFFFFFFFFFL;
		} else {
			bits ^= 0x8000000000000000L;
		}
		return Double.longBitsToDouble(bits);
	}
	
	
}




