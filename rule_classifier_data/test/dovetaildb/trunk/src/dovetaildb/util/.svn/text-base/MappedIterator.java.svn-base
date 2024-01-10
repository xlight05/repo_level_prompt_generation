package dovetaildb.util;

import java.util.Iterator;

public abstract class MappedIterator<F,T> implements Iterator<T> {
	
	Iterator<F> itr;
	
	public MappedIterator(Iterator<F> itr) {
		this.itr = itr;
	}
	
	public abstract T map(F f);
	
	public boolean hasNext() {
		return itr.hasNext();
	}

	public T next() {
		return map(itr.next());
	}

	public void remove() {
		itr.remove();
	}

	
	
}
