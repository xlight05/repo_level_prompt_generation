package dovetaildb.querynode;

import java.util.ArrayList;

import java.util.Collection;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;

public class AndQueryNode extends HeapQueryNode {

	public AndQueryNode(NodeHeap heap) { 
		init(heap);
	}

	public AndQueryNode(QueryNode queryNode) {
		// odd special case
		NodeHeap heap = new NodeHeap(1);
		heap.insert(queryNode);
		init(heap);
	}

	public static QueryNode make(Collection<QueryNode> nodes) {
		if (nodes.size() < 2) {
			if (nodes.isEmpty()) return null;
			else return nodes.iterator().next();
		}
		NodeHeap heap = new NodeHeap(nodes.size());
		for(QueryNode node : nodes) {
			if (node == null) return null;
			heap.insert(node);
		}
		AndQueryNode andNode = new AndQueryNode(heap);
		if (!andNode.ensureAligned()) return null;
		else return andNode;
	}
	
	private boolean ensureAligned() {
		return ensureAligned(getMaxDocId(), ArrayBytes.EMPTY_BYTES);
	}

//	private boolean ensureAligned(long maxDoc, Bytes bytes) {
//		Object[] h = heap.heap;
//		while (true) {
//			int targetIdx = -1;
//			int targetCost = Integer.MAX_VALUE;
//			for(int i=heap.size; i>0; i--) {
//				QueryNode node = (QueryNode)h[i];
//				if (node.compareTo(maxDoc, bytes) < 0) {
//					int nodeCost = node.cost();
//					if (nodeCost < targetCost) {
//						targetCost = nodeCost;
//						targetIdx = i;
//						if (nodeCost <= 1) break;
//					}
//				}
//			}
//			if (targetIdx == -1) return maxDoc != Long.MAX_VALUE;
//			QueryNode node = (QueryNode)h[targetIdx];
//			node.seek(maxDoc, bytes);
//			heap.adjustAt(targetIdx);
//			long newDoc = node.doc();
//			if (newDoc > maxDoc) {
//				maxDoc = newDoc;
//			}
//		}
//	}
	
	private boolean ensureAligned(long maxDoc, Bytes bytes) {
		QueryNode top = (QueryNode)heap.top();
		long topDoc = top.doc();
		while(topDoc < maxDoc) {
			top.seek(maxDoc, bytes);
			topDoc = top.doc();
			if (topDoc > maxDoc) maxDoc = topDoc;
			heap.adjustTop();
			top = (QueryNode)heap.top();
			topDoc = top.doc();
		}
		return maxDoc != Long.MAX_VALUE;
	}
	
	private long getMaxDocId() {
		long maxDoc = 0;
		for(int i=1+heap.size/2; i<=heap.size; i++) {
			long doc = ((QueryNode)heap.heap[i]).doc();
			if (doc > maxDoc) maxDoc = doc;
		}
		return maxDoc;
	}
	
	public boolean next() {
		return ensureAligned(doc()+1, ArrayBytes.EMPTY_BYTES);
	}

	public NextStatus nextTerm() {
		QueryNode top = heap.top();
		NextStatus status = top.nextTerm();
		long doc = top.doc();
		if (status == NextStatus.AT_END) {
			heap.clear();
			return status;
		}
		heap.adjustTop();
		return (top.doc() == doc) ? NextStatus.NEXT_TERM : NextStatus.NEXT_DOC;
	}

	@Override
	public void seek(long target, Bytes bytes) {
		long curDocId = doc();
		if (curDocId >= target) {
			restart();
		}
		ensureAligned(target, bytes);
	}

	public QueryNode copy() {
		throw new RuntimeException();
	}

	public QueryNode specialize(Range range) {
		ArrayList<QueryNode> clauses = heap.specialize(range);
		return (clauses == null) ? this : make(clauses);
	}
	
	@Override
	public long nextValidDocId(long docId) {
		for(int i=1; i<=heap.size; i++) {
			QueryNode node = (QueryNode)heap.heap[i];
			long nextValid = node.nextValidDocId(docId);
			if (nextValid > docId) {
				docId = nextValid;
				if (nextValid == Long.MAX_VALUE) break;
				if (i > 1) {
					// promote up one position (since it seems to be selective)
					heap.heap[i] = heap.heap[i-1];
					heap.heap[i-1] = node;
				}
				if (i > 2) {
					i = 0; // (with loop incr, starts from beginning)
				} else {
					i = 1; // (with loop incr, starts at 2nd position .. because 1st position is what we just checked)
				}
			}
		}
		return docId;
	}

}
