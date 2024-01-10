package dovetaildb.bagindex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.AbstractQueryNode;
import dovetaildb.querynode.FilteredQueryNode;
import dovetaildb.querynode.OrderedOrQueryNode;
import dovetaildb.querynode.QueryNode;

/**
 * 
 * Simplest functional in-memory implementation of a BagIndex I can think of.
 * Good for testing and not much else.
 *
 */
public class TrivialBagIndex extends BagIndex {
	
	private static final long serialVersionUID = 629773186741741245L;
	
	ArrayList<ArrayList<EditRec>> revs = new ArrayList<ArrayList<EditRec>>();

	public TrivialBagIndex() {
		revs.add(new ArrayList<EditRec>());
	}
	
	protected ArrayList<EditRec> all(long revNum) { return revs.get((int)revNum); }
	
	@Override
	public void close() {
	}

	long nextDocId = 1;
	
	@Override
	public long commitNewRev(Collection<EditRec> edits) {
		nextDocId = BagIndexUtil.assignIds(edits, nextDocId);
		ArrayList<EditRec> all = revs.get(revs.size()-1);
		ArrayList<EditRec> newAll = new ArrayList<EditRec>(all);
		for(EditRec rec : edits) {
			if (rec.isDeletion) {
				if (all.contains(rec)) {
					newAll.remove(rec);
				} else {
					throw new RuntimeException();
				}
			} else {
				newAll.add(rec);
			}
		}
		EditRec.sortById(newAll);
		revs.add(newAll);
		return revs.size()-1;
	}

	@Override
	public long getCurrentRevNum() {
		return revs.size()-1;
	}

	String homeDir;
	@Override
	public String getHomedir() {
		return homeDir;
	}

	@Override
	public void setHomedir(String homeDir) {
		this.homeDir = homeDir;
	}

	class WorldQueryNode extends AbstractQueryNode {
		ArrayList<EditRec> all;
		int i = 0;
		WorldQueryNode(ArrayList<EditRec> all) {
			this.all = all;
		}
		public long doc() {
			if (i >= all.size()) return Long.MAX_VALUE;
			return all.get(i).docId;
		}
		public boolean next() {
			long orig = all.get(i).docId;
			while(++i < all.size()) {
				long cur = all.get(i).docId;
				if (cur != orig) break;
			}
			return (i<all.size());
		}
		public NextStatus nextTerm() {
			if (i >= all.size()) return NextStatus.AT_END;
			long orig = all.get(i).docId;
			i++;
			if (i >= all.size()) return NextStatus.AT_END;
			long cur = all.get(i).docId;
			return (cur != orig) ? NextStatus.NEXT_DOC : NextStatus.NEXT_TERM;
		}
		public void seek(long seekDoc, Bytes seekTerm) {
			i=0;
			while(compareTo(seekDoc, seekTerm) < 0) {
				i++;
			}
		}
		public Bytes term() { return all.get(i).term; }
		public QueryNode specialize(Range range) {
			// we aren't required to check for nullness (because the subsequent
			// results don't have to fall in the range), but it's nice for testing:
			for(EditRec edit : all) {
				if (range.matches(edit.term)) return this;
			}
			return null;
		}
		public QueryNode copy() {
			return new WorldQueryNode(all);
		}
	}
	
	@Override
	public QueryNode getRange(final Range range, long revNum) {
		ArrayList<EditRec> all = revs.get((int)revNum);
		if (all.size() == 0) return null;
		QueryNode world = new WorldQueryNode(all);
		return FilteredQueryNode.make(world, range);
	}

	@Override
	public QueryNode getTerms(List<Bytes> terms, long revNum) {
		ArrayList<QueryNode> termNodes = new ArrayList<QueryNode>();
		for (Bytes term : terms) {
			QueryNode node = getRange(new Range(term, ArrayBytes.EMPTY_BYTES,
					ArrayBytes.EMPTY_BYTES, true, true), revNum);
			if (node != null) termNodes.add(node);
		}
		return OrderedOrQueryNode.make(termNodes);
	}
	
}
