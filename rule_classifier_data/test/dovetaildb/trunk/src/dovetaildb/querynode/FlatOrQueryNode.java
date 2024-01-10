package dovetaildb.querynode;

import java.util.ArrayList;
import java.util.List;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.QueryNode.NextStatus;

public class FlatOrQueryNode extends AbstractQueryNode {
	
	int idx = 0;
	QueryNode[] nodes;
	
	public FlatOrQueryNode(List<? extends QueryNode> queries) {
		nodes = new QueryNode[queries.size()];
		int i=0;
		for(QueryNode node : queries) {
			nodes[i++] = node;
		}
		setIdx();
	}

	public FlatOrQueryNode(QueryNode[] nodes) {
		this.nodes = nodes;
		setIdx();
	}

	public static QueryNode make(ArrayList<QueryNode> nodes) {
		if (nodes.size() < 2) {
			if (nodes.isEmpty()) {
				return null;
			}
			else return nodes.get(0);
		}
		return new FlatOrQueryNode(nodes);
	}

	@Override
	public long doc() {
		return nodes[idx].doc();
	}

	@Override
	public Bytes term() {
		return nodes[idx].term();
	}
	
	private void setIdx() {
		long smallestDoc = Long.MAX_VALUE;
		Bytes smallestTerm = null;
		for(int curIdx=nodes.length-1; curIdx>=0; curIdx--) {
			QueryNode node = nodes[curIdx];
			long curDoc = node.doc();
			if (curDoc > smallestDoc || curDoc == Long.MAX_VALUE) continue;
			Bytes curTerm = node.term();
			if (smallestTerm == null || 
					curDoc < smallestDoc || 
					curTerm.compareTo(smallestTerm) < 0) {
				smallestDoc = curDoc;
				smallestTerm = curTerm;
				idx = curIdx;
			}
		}
	}

	@Override
	public void seek(long seekDoc, Bytes seekTerm) {
		for(int curIdx=nodes.length-1; curIdx>=0; curIdx--) {
			QueryNode node = nodes[curIdx];
			node.seek(seekDoc, seekTerm);
		}
		setIdx();
	}

	@Override
	public boolean next() {
		throw new RuntimeException("Not supported");
	}

	@Override
	public NextStatus nextTerm() { //TODO kill this and callers of it
		long oldDoc = nodes[idx].doc();
		nodes[idx].nextTerm();
		setIdx();
		long newDoc = nodes[idx].doc();
		if (oldDoc == newDoc) return NextStatus.NEXT_TERM;
		else if (newDoc == Long.MAX_VALUE) return NextStatus.AT_END;
		else return NextStatus.NEXT_DOC;
	}

	@Override
	public QueryNode copy() {
		QueryNode[] nodesCopy = new QueryNode[nodes.length];
		for(int i=0; i<nodesCopy.length; i++) {
			nodesCopy[i] = nodes[i].copy();
		}
		return new FlatOrQueryNode(nodesCopy);
	}

	@Override
	public QueryNode specialize(Range range) {
		boolean anyChange = false;
		ArrayList<QueryNode> clauses = new ArrayList<QueryNode>(nodes.length);
		for(int i=nodes.length-1; i>=0; i--) {
			QueryNode node = nodes[i];
			QueryNode newNode = node.specialize(range);
			if (node == newNode) {
				clauses.add(newNode);
			} else {
				anyChange = true;
				if (newNode != null) {
					clauses.add(newNode);
				}
			}
		}
		if (anyChange) {
			return FlatOrQueryNode.make(clauses);
		} else {
			return this;
		}
	}

	private boolean findNextPositionList() {
		QueryNode node;
		do {
			if (++idx >= nodes.length) return false;
			node = nodes[idx];
			if (node.doc() > positionDoc) continue;
		} while (node.positionSet(positionDoc, positionPrefix) == false);
		return true;
	}
	
	@Override
	public boolean positionSet(long docId, Bytes prefix) {
		idx = 0;
		positionDoc = docId;
		positionPrefix = prefix;
		QueryNode firstNode = nodes[0];
		if (firstNode.positionSet(docId, prefix)) return true;
		return findNextPositionList();
	}
	
	@Override
	public boolean positionNext() {
		if (nodes[idx].positionNext()) return true;
		return findNextPositionList();
	}
	
	@Override
	public long nextValidDocId(long docId) {
//		this.seek(docId, ArrayBytes.EMPTY_BYTES);
//		return doc();
		long smallestDoc = Long.MAX_VALUE;
		for(int curIdx=nodes.length-1; curIdx>=0; curIdx--) {
			QueryNode node = nodes[curIdx];
			long curDoc = node.nextValidDocId(docId);
			if (curDoc < smallestDoc) {
				idx = curIdx;
				if (curDoc == docId) {
					return docId;
				}
				smallestDoc = curDoc;
			}
		}
		return smallestDoc;
	}

	@Override
	public boolean anyNext() {
		boolean cycledOnce = false;
		while (true) {
			if (nodes[idx].anyNext()) return true;
			if (++idx >= nodes.length) {
				idx = 0;
				if (cycledOnce) return false;
				cycledOnce = true;
			}
		}
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("FlatOrQueryNode([\n");
		for(QueryNode node : nodes) {
			if (node == null) continue;
			buf.append("  "+node+"\n");
		}
		buf.append("])\n");
		return buf.toString();
	}
}
