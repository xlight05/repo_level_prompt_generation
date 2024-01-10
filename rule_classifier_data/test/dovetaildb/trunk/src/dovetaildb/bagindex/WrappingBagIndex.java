package dovetaildb.bagindex;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.QueryNode;

public abstract class WrappingBagIndex extends BagIndex {

	BagIndex subBagIndex;
	public WrappingBagIndex(BagIndex inner) {
		this.subBagIndex = inner;
		inner.setDirtyListener(this);
	}
	
	@Override
	public void close() { subBagIndex.close(); }

	@Override
	public long commitNewRev(Collection<EditRec> edits) {
		return subBagIndex.commitNewRev(edits);
	}
	
	@Override
	public long getCurrentRevNum() { return subBagIndex.getCurrentRevNum(); }

	@Override
	public String getHomedir() { return subBagIndex.getHomedir(); }

	@Override
	public QueryNode getRange(Range range, long revNum) {
		return subBagIndex.getRange(range, revNum);
	}

	@Override
	public QueryNode getTerms(List<Bytes> terms, long revNum) {
		return subBagIndex.getTerms(terms, revNum);
	}
	
	@Override
	public void setHomedir(String homeDir) {
		subBagIndex.setHomedir(homeDir);
	}

	@Override
	public Map<String,Object> getMetrics(int detailLevel) {
		return subBagIndex.getMetrics(detailLevel);
	}
}
