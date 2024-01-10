package dovetaildb.querynode;

import java.util.HashSet;
import java.util.List;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.QueryNode.NextStatus;

public final class FilterLiteralsQueryNode extends WrappingQueryNode {

	final HashSet<Bytes> allowed;

	private FilterLiteralsQueryNode(QueryNode node, List<Bytes> allowed) {
		super(node);
		this.allowed = new HashSet<Bytes>(allowed.size());
		for(Bytes bytes :allowed) {
			this.allowed.add(bytes.flatten());
		}
	}
	
	private FilterLiteralsQueryNode(QueryNode node, HashSet<Bytes> allowed) {
		super(node);
		this.allowed = allowed;
	}

	public static FilterLiteralsQueryNode make(QueryNode n, List<Bytes> allowed) {
		FilterLiteralsQueryNode f = new FilterLiteralsQueryNode(n, allowed);
		while(! f.isAllowed()){
			if (f.subQueryNode.nextTerm() == NextStatus.AT_END) return null;
		}
		return f;
	}

	private QueryNode make(QueryNode n, HashSet<Bytes> allowed) {
		FilterLiteralsQueryNode f = new FilterLiteralsQueryNode(n, allowed);
		while(! f.isAllowed()){
			if (f.subQueryNode.nextTerm() == NextStatus.AT_END) return null;
		}
		return f;
	}

	
	private boolean internalNext() {
		while(subQueryNode.doc() != Long.MAX_VALUE) {
			if (subQueryNode.nextTerm() == NextStatus.AT_END) return false;
			if (isAllowed()) return true;
		}
		return false;
	}
	private boolean isAllowed() {
		Bytes term = subQueryNode.term();
		return (allowed.contains(term));
	}
	
	public boolean next() {
		long curDoc = doc();
		do {
			if (! internalNext()) return false;
		} while(curDoc == doc());
		return true;
	}
	public NextStatus nextTerm() {
		long curDoc = doc();
		if (! internalNext()) return NextStatus.AT_END;
		return (curDoc == doc()) ? NextStatus.NEXT_TERM : NextStatus.NEXT_DOC;
	}
	public void seek(long target, Bytes term) {
		subQueryNode.seek(target, term);
		if (subQueryNode.doc() != Long.MAX_VALUE && ! isAllowed()) {
			internalNext();
		}
	}
	public Bytes term() {
		return subQueryNode.term();
	}
	public QueryNode specialize(Range range) {
		QueryNode spec = subQueryNode.specialize(range);
		if (spec == subQueryNode) return this;
		if (spec == null) return null;
		return make(spec, this.allowed);
	}

	public QueryNode copy() {
		QueryNode innerCopy = subQueryNode.copy();
		FilterLiteralsQueryNode f = new FilterLiteralsQueryNode(innerCopy, allowed);
		return f;
	}

	public String toString() {
		return "FilterLiteralsQN("+allowed+")";
	}

	@Override
	public boolean positionSet(long docId, Bytes prefix) {
		throw new RuntimeException("not implemented");
	}
	
	@Override
	public boolean positionNext() {
		throw new RuntimeException("not implemented");
	}
	
	@Override
	public long nextValidDocId(long docId) {
		this.seek(docId, ArrayBytes.EMPTY_BYTES);
		docId = doc();
		if (docId != Long.MAX_VALUE && ! isAllowed()) internalNext();
		return docId;
	}

}
