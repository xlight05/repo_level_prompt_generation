package dovetaildb.dbservice;

import java.io.File;
import java.util.Collection;
import java.util.Map;


import dovetaildb.api.ApiBuffer;
import dovetaildb.bagindex.BagIndex;
import dovetaildb.iter.Iter;
import dovetaildb.util.Dirty;

public abstract class WrappingDbService extends Dirty.Abstract implements DbService {

	protected DbService subService;
	public WrappingDbService(DbService subService) {
		this.subService = subService;
		subService.setDirtyListener(this);
	}
	
	public void initialize() {
	}
	
	public Iter query(String bag, long txnId, Object query, Map<String, Object> options) {
		return subService.query(bag, txnId, query, options);
	}

	public long capacity() {
		return subService.capacity();
	}
	
	public Collection<String> getBags(long commitId) {
		return subService.getBags(commitId);
	}

	public ApiBuffer createBufferedData(String bagName) {
		return subService.createBufferedData(bagName);
	}
	
	public long commit(long fromTxnId, Map<String, ApiBuffer> batch) {
		return subService.commit(fromTxnId, batch);
	}

	public void rollback(long commitId) throws TxnNotFoundException {
		subService.rollback(commitId);
	}

	public void dropBag(String bagName) {
		subService.dropBag(bagName);
	}
	
	public void drop() {
		subService.drop();
	}
	
	public File getHomeDir() {
		return subService.getHomeDir();
	}

	public long getHighestCompletedTxnId() {
		return subService.getHighestCompletedTxnId();
	}
	
	@Override
	public BagIndex getBag(String bagName) {
		return subService.getBag(bagName);
	}

	@Override
	public Map<String,Object> getMetrics(int detailLevel) {
		return subService.getMetrics(detailLevel);
	}
}
