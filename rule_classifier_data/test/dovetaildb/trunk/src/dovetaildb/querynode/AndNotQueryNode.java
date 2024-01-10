package dovetaildb.querynode;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;

public class AndNotQueryNode extends AbstractQueryNode {

	QueryNode candidates;
	QueryNode exclusions;

	public static QueryNode make(QueryNode candidates, QueryNode exclusions) {
		if (exclusions == null) return candidates;
		if (candidates == null) return null;
		AndNotQueryNode node = new AndNotQueryNode();
		node.candidates = candidates;
		node.exclusions = exclusions;
		if (!node.sync()) return null;
		return node;
	}

	private boolean sync() {
		if (exclusions == null) return true;
		long candidate = candidates.doc();
		if (exclusions.doc() < candidate) {
			exclusions.seek(candidate, ArrayBytes.EMPTY_BYTES);
		}
		while (candidates.doc() == exclusions.doc() ) {
			if (candidate == Long.MAX_VALUE) return false;
			if (! candidates.next()) return false;
			candidate = candidates.doc();
			exclusions.seek(candidate, ArrayBytes.EMPTY_BYTES);
		}
		return true;
	}
	
	public long doc() {
		return candidates.doc();
	}

	public boolean next() {
		if (! candidates.next()) return false;
		return sync();
	}

	@Override
	public void seek(long target, Bytes bytes) {
		candidates.seek(target, bytes);
		sync();
	}

	public NextStatus nextTerm() {
		return candidates.nextTerm();
	}

	public Bytes term() {
		return candidates.term();
	}

	public QueryNode copy() {
		return AndNotQueryNode.make(candidates.copy(), exclusions.copy());
	}

	public QueryNode specialize(Range range) {
		QueryNode specialized = candidates.specialize(range);
		if (specialized == candidates) return this;
		return AndNotQueryNode.make(specialized, exclusions.copy());
	}
	
}
