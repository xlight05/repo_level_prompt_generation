package dovetaildb.iter;

import java.io.IOException;
import java.io.Writer;

public class SlicedIter extends AbstractIter {
	Iter subIter;
	long limit;

	public SlicedIter(Iter subIter, long offset, long limit) {
		while (offset-- > 0) {
			if (! subIter.hasNext()) {
				limit = 0;
				break;
			}
			subIter.next();
		}
		this.subIter = subIter;
		this.limit = limit;
	}
	
	@Override
	public boolean hasNext() {
		return limit > 0 && subIter.hasNext();
	}

	@Override
	public Object next() {
		limit--;
		return subIter.next();
	}

	@Override
	public void remove() {
		throw new RuntimeException("Not supported");
	}

	@Override
	public boolean specialize(Object query) {
		// TODO does this need to check limit?
		return subIter.specialize(query);
	}

}
