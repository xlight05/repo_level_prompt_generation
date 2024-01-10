package dovetaildb.util;
/*
 * Copyright 2008 Phillip Schanely
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DeltaMap<K,V> extends AbstractMap<K,V> {

	public static final Object DELETED = new Object();
	public static final Object DONE    = new Object();
	
	Map<K,V> internal;
	HashMap<K,V> delta;

	public DeltaMap(Map<K,V> internal) {
		this.internal = internal;
		delta = new HashMap<K,V>();
	}

	@Override
	public V put(K key, V value) {
		V oldVal = get(key);
		delta.put(key, value);
		return oldVal;
	}
	
	@Override
	public V get(Object key) {
		if (delta.containsKey(key)) return delta.get(key);
		return internal.get(key);
	}
	
	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	@Override
	public V remove(Object key) {
		V oldVal = get(key); 
		delta.put((K)key, null);
		return oldVal;
	}
	
	@Override
	public Set<Map.Entry<K,V>> entrySet() {
		return new AbstractSet<Map.Entry<K,V>>() {
			@Override
			public Iterator<Map.Entry<K,V>> iterator() {
				final Iterator origitr  = internal.entrySet().iterator();
				final Iterator deltaitr = delta.entrySet().iterator();
				return new Iterator<Map.Entry<K,V>>() {
					Map.Entry<K,V> lastItem = null;
					Map.Entry<K,V> curItem = findNext();
					private Map.Entry<K,V> findNext() {
						lastItem = curItem;
						while (origitr.hasNext()) {
							Map.Entry candidate = (Map.Entry)origitr.next();
							K key = (K)candidate.getKey();
							if (! delta.containsKey(key)) return candidate;
						}
						while (deltaitr.hasNext()) {
							Map.Entry entry = (Map.Entry)deltaitr.next();
							if (entry.getValue() != null) return entry;
						}
						return null;
					}
					public boolean hasNext() {
						return curItem != null;
					}
					public Map.Entry<K,V> next() {
						curItem = findNext();
						return lastItem;
					}
					public void remove() {
						delta.put(lastItem.getKey(), null);
					}
				};
			}
			@Override
			public int size() {
				Iterator i = iterator();
				int ct = 0;
				while(i.hasNext()) { i.next(); ct++; }
				return ct;
			}
		};
	}
	

}
