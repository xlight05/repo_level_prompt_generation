package dovetaildb.querynode;

import dovetaildb.bytes.Bytes;

public abstract class HeapQueryNode extends AbstractQueryNode {

	public NodeHeap heap, initialHeap;

	public HeapQueryNode() {
		super();
	}

	protected void init(NodeHeap heap) {
		this.heap = heap;
		this.initialHeap = heap;
//		this.initialHeap = heap.deepcopy();
	}
	
	protected void restart() {
		init(initialHeap);
		throw new RuntimeException("does not work");
	}
	
	public long doc() {
		QueryNode top = heap.top();
		if (heap == null || top == null) {
			throw new RuntimeException();
		}
		return top.doc();
	}

	public Bytes term() {
		return heap.top().term();
	}

	int cost = -1;
	@Override
	public int cost() {
		if (cost == -1) {
			cost = 0;
			Object[] h = heap.heap;
			for(int i=heap.size; i>0; i--) {
				cost += ((QueryNode)h[i]).cost();
			}
		}
		return cost;
	}

}