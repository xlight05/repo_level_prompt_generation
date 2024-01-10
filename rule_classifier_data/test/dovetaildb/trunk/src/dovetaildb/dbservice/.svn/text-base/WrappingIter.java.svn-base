package dovetaildb.dbservice;

import dovetaildb.iter.AbstractIter;
import dovetaildb.iter.Iter;

public abstract class WrappingIter extends AbstractIter {
	protected Iter subIter;
	protected WrappingIter(Iter subIter){
		this.subIter = subIter;
	}
	public boolean hasNext() { return subIter.hasNext(); }
	public Object next() { return subIter.next(); }
	public void close() {}
}
