package dovetaildb.querynode;

import dovetaildb.bagindex.Range;

import dovetaildb.bytes.Bytes;

public final class FilteredQueryNode extends AbstractQueryNode {

	final protected QueryNode node;
	final protected Bytes prefix, min, max;
	final protected int minIsExcl, maxIsExcl;
	final protected int prefixLen, minLen, maxLen;

	public FilteredQueryNode(QueryNode node, Range range) {
		this(node, range.prefix, range.minSuffix, range.maxSuffix, ! range.isMinIncluded, ! range.isMaxIncluded);
	}
	
	public FilteredQueryNode(QueryNode node, Bytes prefix, Bytes min, Bytes max, boolean minIsEx, boolean maxIsEx) {
		this.node = node;
		this.prefix = prefix = prefix.flatten();
		this.prefixLen = prefix.getLength();
		if (min != null) {
			min = min.flatten();
			this.minLen = min.getLength();
		} else {
			this.minLen = 0;
		}
		if (max != null) {
			max = max.flatten();
			this.maxLen = max.getLength();
		} else {
			this.maxLen = 0;
		}
		this.min = min;
		this.max = max;
		this.minIsExcl = minIsEx ? 1 : 0;
		this.maxIsExcl = maxIsEx ? 1 : 0;
	}
	
	private static FilteredQueryNode make(QueryNode n, Bytes prefix, Bytes min, Bytes max, boolean minIsEx, boolean maxIsEx) {
		FilteredQueryNode f = new FilteredQueryNode(n, prefix, min, max, minIsEx, maxIsEx);
		while(f.node.doc() != Long.MAX_VALUE && ! f.isAllowed()){
			if (f.node.nextTerm() == NextStatus.AT_END) return null;
		}
		return f;
	}
	public static QueryNode make(QueryNode n, Range range) {
		Bytes prefix = range.prefix;
		Bytes min = range.minSuffix;
		Bytes max = range.maxSuffix;
		if (min == null && max == null && prefix.getLength()==0) return n;
		return make(n, prefix, min, max,
				!range.isMinIncluded, !range.isMaxIncluded);
	}
	public static FilteredQueryNode makeFiltered(QueryNode n, Range range) {
		return make(n, range.prefix, range.minSuffix, range.maxSuffix,
				!range.isMinIncluded, !range.isMaxIncluded);
	}
	
	public long doc() {
		return node.doc();
	}

	private boolean internalNextTerm() {
		while(true) {
			if (node.nextTerm() == NextStatus.AT_END) return false;
			if (isAllowed()) return true;
		}
	}
	
	private boolean internalNextUntil(long docId) {
		do {
			if (! node.next()) return false;
		} while (node.doc() < docId);
		if (isAllowed()) return true;
		else return internalNextTerm();
	}
	
	private boolean isAllowed() {
		Bytes term = node.term();
		if (prefix != null) {
			if (! prefix.isPrefixOf(term)) return false;
		}
		if (min != null) {
			if (min.compareToParts(term, 0, prefixLen, minLen, term.getLength()-prefixLen, 9) + minIsExcl > 0) return false;
		}
		if (max != null) {
			if (max.compareToParts(term, 0, prefixLen, maxLen, term.getLength()-prefixLen, 9) - maxIsExcl < 0) return false;
		}
		return true;
	}
	
	public boolean next() {
		long curDoc = doc();
		return internalNextUntil(curDoc+1);
//		long curDoc = doc();
//		do {
//			if (! internalNextTerm()) return false;
//		} while(curDoc == doc());
//		return true;
	}
	public NextStatus nextTerm() {
		long curDoc = doc();
		if (! internalNextTerm()) return NextStatus.AT_END;
		return (curDoc == doc()) ? NextStatus.NEXT_TERM : NextStatus.NEXT_DOC;
	}
	public void seek(long target, Bytes term) {
		node.seek(target, term);
		if (node.doc() < target) {
			// a faster advance than the term-by-term traversal below
			internalNextUntil(target);
		}
		if (node.doc() != Long.MAX_VALUE && ! isAllowed()) {
			internalNextTerm();
		}
	}
	public Bytes term() {
		return node.term();
	}
	public QueryNode specialize(Range range) {
		QueryNode spec = node.specialize(range);
		if (spec == node) return this;
		if (spec == null) return null;
		return make(spec, range);
	}

	public QueryNode copy() {
		QueryNode innerCopy = node.copy();
		return new FilteredQueryNode(innerCopy, prefix, min, max, this.minIsExcl==1, maxIsExcl==1);
	}

	public String toString() {
		return "FilteredQN('"+prefix.getAsString()+"',"+
		(minIsExcl==1?"('":"['")+(min==null ? "" : min.getAsString())+
		"':'"+(max==null ? "" : max.getAsString())+(maxIsExcl==1?"')":"']")+
		","+this.node+")";
	}

}
