package dovetaildb.bagindex;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.QueryNode;
import dovetaildb.util.Dirty;

public abstract class BagIndex  extends Dirty.Abstract implements Serializable {
	
	public abstract void setHomedir(String homeDir);
	
	public abstract String getHomedir();
	
	public abstract long getCurrentRevNum();

	public abstract QueryNode getRange(Range range, long revNum);
	
	public abstract QueryNode getTerms(List<Bytes> terms, long revNum);
	
	public QueryNode getTerm(Bytes term, long revNum) {
		return getTerms(Arrays.asList(new Bytes[]{term}), revNum);
	}
	
	public abstract long commitNewRev(Collection<EditRec> edits);

	/** Intended for compaction; it's only valid to call this method when
	 * the BagIndex is empty. Almost all subclasses should override this
	 * method for memory efficency. */
	public long rebuild(Iterator<EditRec> edits) {
		ArrayList<EditRec> allEdits = new ArrayList<EditRec>();
		while(edits.hasNext()) {
			allEdits.add(edits.next());
		}
		return commitNewRev(allEdits);
	}

	public abstract void close();

	protected Object readResolve() throws ObjectStreamException {
		this.setHomedir(this.getHomedir());
		return this;
	}
	
	public Map<String,Object> getMetrics(int detailLevel) {
		return null;
	}

}
