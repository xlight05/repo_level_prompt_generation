package dovetaildb.iter;

import java.util.NoSuchElementException;

public class EmptyIter extends AbstractIter {

	public static final EmptyIter EMPTY_ITER = new EmptyIter();
	
	public EmptyIter() {}
	
	public boolean hasNext() { return false; }

	public Object next() { throw new NoSuchElementException(); }

	public void remove() { throw new NoSuchElementException(); }

}
