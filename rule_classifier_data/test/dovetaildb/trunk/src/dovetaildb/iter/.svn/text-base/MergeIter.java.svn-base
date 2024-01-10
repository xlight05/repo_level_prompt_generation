package dovetaildb.iter;

import java.util.ArrayList;
import java.util.List;


public class MergeIter extends AbstractIter {

	protected final List<Iter> iters;
	int idx = 0;
	
	public MergeIter(List<Iter> iters) {
		this.iters = iters;
	}
	
	public MergeIter(Iter i1, Iter i2) {
		iters = new ArrayList<Iter>(2);
		iters.add(i1);
		iters.add(i2);
	}

	public boolean hasNext() {
		return idx < iters.size() && iters.get(idx).hasNext();
	}

	public Object next() {
		Iter top = iters.get(0);
		Object ret = top.next();
		if (!top.hasNext()) {
			idx++;
		}
		return ret;
	}

	public void remove() {
		throw new RuntimeException();
	}

}
