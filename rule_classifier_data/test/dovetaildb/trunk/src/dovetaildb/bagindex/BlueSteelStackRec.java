package dovetaildb.bagindex;

import dovetaildb.bagindex.BlueSteelBagIndex.PostingNode;

final class BlueSteelStackRec implements Cloneable {
	PostingNode initial;
	private PostingNode current;
	PostingNode prev;
	boolean hasPrev;
	private boolean hasCurrent;
	long cap;
	private long initialCap;
	
	BlueSteelStackRec() {}
	void set(PostingNode pointer, long cap) {
		initial = (pointer == null) ? null : pointer.copy();
//		prev = null;
		hasPrev = false;
		hasCurrent = false;
//		current = null;
		this.cap = cap;
		this.initialCap = cap;
	}
	public void setAsPushFrom(PostingNode parent, long cap) {
		initial = parent.copyPushLeadInto(initial);
//		prev = null;
		hasPrev = false;
		hasCurrent = false;
//		current = null;
		this.cap = cap;
		this.initialCap = cap;
	}
	public void next() {
		if (! hasCurrent) {
			current = initial.copyInto(current);
			hasCurrent = true;
		} else {
			prev = current.copyInto(prev);
			hasPrev = true;
			current = current.destructiveNext();
			if (current == null) {
				hasCurrent = false;
				return; // TODO seems as though this is never or rarely the case
			}
			if (current.getCount() > 1) hasPrev = false;
		}
		cap -= current.getCount();
	}
	public PostingNode getCurrent() {
		return (hasCurrent) ? current : null;
	}
	public void clearCurrent() {
		hasPrev = false;
		hasCurrent = false;
	}
	public void rewindCurrentFrame() {
		cap = initialCap;
		hasPrev = false;
		hasCurrent = false;
	}
	public BlueSteelStackRec copy() {
		BlueSteelStackRec copy = new BlueSteelStackRec();
		if (initial != null) copy.initial = initial.copy();
		if (prev != null) copy.prev = prev.copy();
		if (current != null) copy.current = current.copy();
		copy.hasPrev = hasPrev;
		copy.hasCurrent = hasCurrent;
		copy.cap = cap;
		copy.initialCap = initialCap;
		return copy;
	}
	public String toString() {
		return "cap:"+cap+", "+getCurrent();
	}
}