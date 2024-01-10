package dovetaildb.dbservice;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class DbResultMapView extends AbstractMap {
	
	private final DbResult result;
	
	public DbResultMapView(DbResult result) {
		this.result = result;
	}
	
	static final String EOF_MARKER = new String();
	@Override
	public Set entrySet() {
		final Collection<String> keys = result.getObjectKeys();
		return new AbstractSet() {
			
			@Override
			public Iterator iterator() {
				final Iterator<String> keyItr = keys.iterator();
				return new Iterator() {
					String nxt = keyItr.hasNext() ? keyItr.next() : EOF_MARKER;
					public boolean hasNext() {
						return nxt != EOF_MARKER;
					}
					public Object next() {
						final String key = nxt;
						nxt = keyItr.hasNext() ? keyItr.next() : EOF_MARKER;
						final Object val  = result.derefByKey(key).simplify();
						return new Map.Entry<String, Object>() {
							public String getKey() { return key; }
							public Object getValue() { return val; }
							public Object setValue(Object value) {
								throw new RuntimeException("Not implemented");
							}
						};
					}
					public void remove() {
						throw new RuntimeException("Not Implemented");
					}
				};
			}
			@Override
			public int size() { return size(); }
		};
	}

	@Override
	public boolean containsKey(Object key) {
		return result.containsKey((String)key);
	}

	@Override
	public Object get(Object key) {
		return result.derefByKey((String)key).simplify();
	}

	@Override
	public void clear() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public Object put(Object key, Object value) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public void putAll(Map m) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public Object remove(Object key) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public int size() {
		return result.getObjectKeys().size();
	}

	@Override
	public boolean isEmpty() {
		return result.getObjectKeys().isEmpty();
	}

	public DbResult getDbResult() {
		return result;
	}
	
}
