package dovetaildb.querynode;

import dovetaildb.bagindex.Range;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.QueryNode.NextStatus;

public abstract class WrappingQueryNode extends AbstractQueryNode {

	protected QueryNode subQueryNode;
	
	public WrappingQueryNode(QueryNode inner) {
		this.subQueryNode = inner;
	}
	
	public long doc() { return subQueryNode.doc(); }
	
	public boolean next() {
		return subQueryNode.next();
	}
	
	public void seek(long seekDoc, Bytes seekTerm) {
		subQueryNode.seek(seekDoc, seekTerm);
	}
	
	public NextStatus nextTerm() {
		NextStatus status = subQueryNode.nextTerm();
		return status;
	}
	
	public Bytes term() { return subQueryNode.term(); }

	public abstract QueryNode copy();
	
	public abstract QueryNode specialize(Range range);

	@Override
	public int cost() {
		return subQueryNode.cost();
	}

	@Override
	public boolean positionSet(long docId, Bytes prefix) {
		return subQueryNode.positionSet(docId, prefix);
	}
	
	@Override
	public boolean positionNext() {
		return subQueryNode.positionNext();
	}
	
	@Override
	public long nextValidDocId(long docId) {
		return subQueryNode.nextValidDocId(docId);
	}

	@Override
	public boolean anyNext() {
		return subQueryNode.anyNext();
	}
	
}
