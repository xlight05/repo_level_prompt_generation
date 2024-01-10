package dovetaildb.querynode;

import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.Range;
import dovetaildb.bytes.Bytes;

public class LiteralRangeQueryNode extends AbstractQueryNode {

	EditRec[] items;
	int idx;
	int checkpoint = -1;
	public LiteralRangeQueryNode(EditRec[] items) {
		this.items = items;
		idx = 0;
	}

	public long doc() {
		if (idx >= items.length) return Long.MAX_VALUE;
		return items[idx].getDocId();
	}

	public boolean next() {
		long curDocId = (idx<0 || idx>=items.length) ? -1 : doc();
		do {
			idx++;
			if (idx >= items.length) return false;
		} while(doc() == curDocId);
		return true;
	}

	public NextStatus nextTerm() {
		if (idx+1 >= items.length) {
			idx = items.length;
			return NextStatus.AT_END;
		}
		long curDocId = doc();
		idx++;
		return (curDocId == doc()) ? NextStatus.NEXT_TERM : NextStatus.NEXT_DOC;
	}

	public void resetTerms() {
		idx = checkpoint;
	}

	public void checkpoint() {
		checkpoint = idx;
	}
	
	@Override
	public void seek(long target, Bytes term) {
		idx = 0;
		while(super.compareTo(target, term) < 0) {
			if (nextTerm() == NextStatus.AT_END) return;
		}
	}

	public Bytes term() {
		return items[idx].getTerm();
	}

	public QueryNode copy() {
		LiteralRangeQueryNode copy = new LiteralRangeQueryNode(items);
		copy.idx = idx;
		copy.checkpoint = checkpoint;
		return copy;
	}

	public QueryNode specialize(Range range) {
		boolean hitAny = false;
		for(EditRec edit : items) {
			if (range.matches(edit.getTerm())) {
				hitAny = true;
			}
		}
		if (! hitAny) return null;
		else return this;
	}

}
