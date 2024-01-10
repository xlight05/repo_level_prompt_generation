package dovetaildb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import junit.framework.TestCase;

public class CheckpointableHeapTest extends TestCase {
	static class CkInt implements Checkpointable {
		int val;
		int checkpoint;
		public CkInt(int i) {
			val = i;
		}
		public void checkpoint() {
			checkpoint = val;
		}
		public void rewindToCheckpoint() {
			val = checkpoint;
		}
		public void uncheckpoint() {}
		public String toString() { return Integer.toString(val); }
	}
	Comparator<CkInt> intCmp = new Comparator<CkInt>() {
		public int compare(CkInt o1, CkInt o2) {
			return o1.val - o2.val;
		}
	};
	public void testHeap() {
		CheckpointableHeap<CkInt> heap = new CheckpointableHeap<CkInt>(intCmp);
		heap.insert(new CkInt(5));
		heap.insert(new CkInt(7));
		heap.insert(new CkInt(3));
		assertEquals(3, heap.size());
		assertEquals(3, heap.top().val);
		heap.pop();
		assertEquals(5, heap.top().val);
		heap.pop();
		assertEquals(7, heap.top().val);
		heap.pop();
		
		heap.insert(new CkInt(5));
		heap.insert(new CkInt(7));
		heap.insert(new CkInt(3));
		heap.checkpoint();
		
		heap.insert(new CkInt(6));
		heap.insert(new CkInt(2));
		assertEquals(5, heap.size());
		assertEquals(2, heap.top().val);
		heap.pop();
		assertEquals(3, heap.top().val);
		heap.pop();
		assertEquals(5, heap.top().val);
		heap.pop();
		
		heap.rewindToCheckpoint();
		assertEquals(3, heap.size());
		assertEquals(3, heap.top().val);
		heap.pop();
		assertEquals(5, heap.top().val);
		heap.pop();
		assertEquals(7, heap.top().val);
		heap.pop();
		assertEquals(0, heap.size());
	}
	public void testComplex() {
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		ArrayList<Integer> checkpoint = null;
		CheckpointableHeap<CkInt> heap = new CheckpointableHeap<CkInt>(intCmp);
		Random r = new Random(9388545);
		int NUMITER = 10000;
		for(int i=0; i<NUMITER; i++) {
			int operation = r.nextInt(6);
			if (r.nextBoolean()) {
				operation = (i<NUMITER/2) ? 0 : 1;
			}
			if (! sorted.isEmpty()) {
				assertEquals(sorted.get(0).intValue(), heap.top().val);
			}
			if (operation == 0) {
				// push
				int newval = r.nextInt(1000);
				sorted.add(newval);
				Collections.sort(sorted);
				heap.insert(new CkInt(newval));
			} else if (operation == 1 && sorted.size() > 0) {
				// pop
				int expected = sorted.remove(0);
				assertEquals(expected, heap.pop().val);
			} else if (operation == 2 && !sorted.isEmpty()) {
				// checkpoint
				checkpoint = new ArrayList<Integer>(sorted);
				heap.checkpoint();
			} else if (operation == 3 && !sorted.isEmpty()) {
				// adjust
				int newval = r.nextInt(1000);
				sorted.set(0, newval);
				Collections.sort(sorted);
				CkInt val = heap.getTopForAdjustment();
				val.val = newval;
				heap.adjustTop();
			} else if (operation == 4 && checkpoint != null) {
				// revert
				sorted = checkpoint;
				checkpoint = null;
				heap.rewindToCheckpoint();
			} else if (operation == 5 && checkpoint != null) {
				// uncheckpoint
				checkpoint = null;
				heap.uncheckpoint();
			}
		}
	}
}
