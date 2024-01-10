package dovetaildb.dbservice;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import dovetaildb.api.ApiBuffer;
import dovetaildb.bagindex.BagIndex;
import dovetaildb.iter.EmptyIter;
import dovetaildb.iter.Iter;
import dovetaildb.util.Dirty;
import dovetaildb.util.Util;

public class EmptyDbService extends Dirty.Abstract implements DbService {

	private static final long serialVersionUID = -7974288928068032148L;

	public long capacity() { return 0; }

	public long commit(long fromTxnId, Map<String, ApiBuffer> batch) {
		throw new UnsupportedOperationException();
	}

	public ApiBuffer createBufferedData(String bagName) {
		throw new UnsupportedOperationException();
	}

	public void drop() {}

	public void dropBag(String bagName) {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getBags(long commitId) {
		return new ArrayList<String>();
	}

	public long getHighestCompletedTxnId() {
		return 0;
	}

	public File getHomeDir() {
		return null;
	}

	public void initialize() {}

	public Iter query(String bag, long txnId, Object query, Map<String, Object> options) {
		return new EmptyIter();
	}

	public void rollback(long commitId) throws TxnNotFoundException {}

	@Override
	public BagIndex getBag(String bagName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String,Object> getMetrics(int detailLevel) {
		return Util.literalSMap().p("type", "EmptyDbService");
	}
}
