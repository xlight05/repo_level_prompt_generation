package dovetaildb.iter;

public class LiteralIter extends AbstractIter {

	final Object[] items;
	int i = 0;
	public LiteralIter(Object[] items) {
		this.items = items;
	}
	
	public boolean hasNext() { return i < items.length; }

	public Object next() { return items[i++]; }

}
