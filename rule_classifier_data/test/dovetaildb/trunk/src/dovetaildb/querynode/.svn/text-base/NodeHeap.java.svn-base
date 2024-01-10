/**
 * 
 */
package dovetaildb.querynode;

import java.util.ArrayList;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;


public final class NodeHeap implements Cloneable {

	public NodeHeap(int size) {
		initialize(size);
	}
	
	public NodeHeap(ArrayList<QueryNode> nodes) {
		this(nodes.size());
		for(QueryNode node: nodes) {
			insert(node);
		}
	}

	public QueryNode[] heap;
	public int size;
	private int maxSize;

	protected final void initialize(int maxSize) {
		size = 0;
		int heapSize = maxSize + 1;
		heap = new QueryNode[heapSize];
		this.maxSize = maxSize;
	}

	/**
	 * Adds an Object to a PriorityQueue in log(size) time.
	 * If one tries to add more objects than maxSize from initialize
	 * a RuntimeException (ArrayIndexOutOfBound) is thrown.
	 */
	public final void put(QueryNode element) {
		size++;
		heap[size] = element;
		upHeap();
	}

	/**
	 * Adds element to the PriorityQueue in log(size) time if either
	 * the PriorityQueue is not full, or not lessThan(element, top()).
	 * @param element
	 * @return true if element is added, false otherwise.
	 */
	public boolean insert(QueryNode element){
		if(size < maxSize){
			put(element);
			return true;
		} else if(size > 0 && compare(element, top()) > 0){

			heap[1] = element;
			adjustTop();
			return true;
		}
		else
			return false;
	}

	public void insertAndGrow(QueryNode element) {
		if (size >= maxSize) {
			int newSize = maxSize * 2;
			QueryNode[] newHeap = new QueryNode[newSize];
			System.arraycopy(heap, 0, newHeap, 0, maxSize);
			heap = newHeap;
		}
		insert(element);
	}


	/** Returns the least element of the PriorityQueue in constant time. */
	public final QueryNode top() {
		if (size > 0)
			return (QueryNode)heap[1];
		else
			return null;
	}

	/** Returns the second least element of the PriorityQueue in constant time. */
	public final QueryNode secondToTop() {
		if (size > 1) {
			QueryNode c1 = (QueryNode)heap[2];
			QueryNode c2 = (QueryNode)heap[3];
			if (c1 == null) return c2;
			if (c2 == null) return c1;
			return (compare(c1,c2)<0 ? c1 : c2);
		} else {
			return null;
		}
	}

	/** Removes and returns the least element of the PriorityQueue in log(size)
    time. */
	public final QueryNode pop() {
		if (size > 0) {
			QueryNode result = (QueryNode)heap[1];			  // save first value
			heap[1] = heap[size];			  // move last to first
			heap[size] = null;			  // permit GC of objects
			size--;
			downHeap(1);				  // adjust heap
			return result;
		} else
			return null;
	}

	public final QueryNode pop(int i) {
		if (size > 0) {
			QueryNode result = (QueryNode)heap[i];			  // save first value
			heap[i] = heap[size];			  // move last to first
			heap[size] = null;			  // permit GC of objects
			size--;
			downHeap(i);				  // adjust heap
			return result;
		} else {
			return null;
		}
	}

	public void overwrite(int i, QueryNode node) {
		heap[i] = node;
		downHeap(i);
	}

	/** Should be called when the Object at top changes values.  Still log(n)
	 * worst case, but it's at least twice as fast to <pre>
	 *  { pq.top().change(); pq.adjustTop(); }
	 * </pre> instead of <pre>
	 *  { o = pq.pop(); o.change(); pq.push(o); }
	 * </pre>
	 */
	public final void adjustTop() {
		downHeap(1);
	}

	public void adjustDownFrom(int i) {
		downHeap(i);
	}

	
	/** Returns the number of elements currently stored in the PriorityQueue. */
	public final int size() {
		return size;
	}

	/** Removes all entries from the PriorityQueue. */
	public final void clear() {
		for (int i = 0; i <= size; i++)
			heap[i] = null;
		size = 0;
	}
	
	public void adjustAt(int i) {
		i = upHeap(i);
		downHeap(i);
	}

	private final void upHeap() {
		upHeap(size);
	}
	private final int upHeap(int i) {
		QueryNode node = (QueryNode)heap[i];			  // save bottom node
		int j = i >>> 1;
		while (j > 0 && compare(node, (QueryNode)heap[j]) < 0) {
			heap[i] = heap[j];			  // shift parents down
			i = j;
			j = j >>> 1;
		}
		heap[i] = node;				  // install saved node
		return i;
	}

	private final void downHeap(int i) {
		QueryNode node = heap[i];			  // save top node
		int j = i << 1;				  // find smaller child
		int k = j + 1;
		if (k <= size && compare(heap[k], heap[j]) < 0) {
			j = k;
		}
		while (j <= size && compare(heap[j], node) < 0) {
			heap[i] = heap[j];			  // shift up child
			i = j;
			j = i << 1;
			k = j + 1;
			if (k <= size && compare(heap[k], heap[j]) < 0) {
				j = k;
			}
		}
		heap[i] = node;				  // install saved node
	}

	public int drainTo(QueryNode[] buffer, boolean invert) {
		int max = buffer.length;
		if (size < max) max = size;
		if (invert) {
			for(int i=max-1; i>=0; i--) buffer[i] = pop();
		} else {
			for(int i=0; i<max; i++) buffer[i] = pop();
		}
		return max;
	}

	private long compare(QueryNode a, QueryNode b) {
		long aDoc = a.doc();
		long docDelta = aDoc - b.doc();
		if (docDelta == 0) {
			if (aDoc == Long.MAX_VALUE) return 0;
			return a.term().compareTo(b.term());
		} else {
			return docDelta;
		}
	}
	
	public NodeHeap deepcopy() {
		NodeHeap clone;
		try {
			clone = (NodeHeap) clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		clone.heap = new QueryNode[heap.length];
		for(int i=heap.length-1; i>=1; i--) {
			QueryNode node = (QueryNode)heap[i];
			if (node != null) {
				clone.heap[i] = node.copy();
			}
		}
		return clone;
	}
	public ArrayList<QueryNode> specialize(Range range) {
		Object[] rawHeap = heap;
		boolean anyChange = false;
		ArrayList<QueryNode> clauses = new ArrayList<QueryNode>(size);
		assert rawHeap[0] == null;
		for(int i=1; i<=size; i++) {
			QueryNode node = (QueryNode)rawHeap[i];
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
		return (anyChange) ? clauses : null;
	}
	public QueryNode specializeChild(int index, Range range) {
		QueryNode orig = (QueryNode)heap[index];
		QueryNode result = orig.specialize(range);
		if (result == orig) return orig;
		if (result == null) {
			pop(index);
		} else {
			long origDoc = orig.doc();
			result.seek(origDoc, ArrayBytes.EMPTY_BYTES);
			if (result.doc() != origDoc) {
				pop(index);
			} else {
				overwrite(index, result);
			}
		}
		return result;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public ArrayList<QueryNode> contents() {
		ArrayList<QueryNode> nodes = new ArrayList<QueryNode>(heap.length);
		for(int i=1; i<=size; i++) {
			nodes.add((QueryNode)heap[i]);
		}
		return nodes;
	}
}
