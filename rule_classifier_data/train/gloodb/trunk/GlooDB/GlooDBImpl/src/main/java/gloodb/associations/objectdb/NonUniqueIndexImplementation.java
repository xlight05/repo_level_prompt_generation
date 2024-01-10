/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb.associations.objectdb;

import gloodb.GlooException;
import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.associations.NonUniqueIndex;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.NavigableMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import static gloodb.operators.Iterators.*;

/**
 * Implementation of a non-unique sorted index.
 * 
 * @param <T>
 *            The type of sorted values.
 * @param <V>
 *            The type of the sorting key.
 */
class NonUniqueIndexImplementation<T extends Serializable, V extends Serializable> implements Cloneable, NonUniqueIndex<T, V> {
    private static final long serialVersionUID = -3480616499227766886L;

    private final String name;

    private TreeMap<V, LinkedHashSet<Serializable>> indexMap;
    private HashMap<Serializable, V> reverseIndexMap;
    private final Class<T> tClass;
    private final Serializable [] parameters;
    private SafeReentrantReadWriteLock lock;

    /**
     * Creates a unique index.
     * 
     * @param tClass
     *            The sorted value class.
     * @param name
     *            The index name.
     * @param comparator
     *            The comparator used by the index.
     */
    public NonUniqueIndexImplementation(Class<T> tClass, String name, Comparator<V> comparator, Serializable... parameters) {
        if(comparator == null) 
            throw new GlooException(String.format("Class %s must specify a non-null comparator when creating index %s", getClass().getName(), name));
        
        this.name = name;
        this.indexMap = new TreeMap<V, LinkedHashSet<Serializable>>(comparator);
        this.reverseIndexMap = new HashMap<Serializable, V>();
        this.tClass = tClass;
        this.parameters = parameters;
        this.lock = new SafeReentrantReadWriteLock();
    }

    /* (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#add(T)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        boolean changed = false;
        Method sortingMethod = PersistencyAttributes.getSortingMethod(value, name);
        if (sortingMethod == null)
            throw new GlooException(String.format(
                    "Class %s must annotate a SortingCriteria({\"%s\", ...} method to support index %s",
                    value.getClass().getName(), name, getClass().getName()));

        Serializable id = PersistencyAttributes.getIdForObject(value);
        try {
	        lock.writeLock();	
	        changed |= removeById(id);
	
			V sortingKey = null;
		    try {
		        sortingKey = (V) sortingMethod.invoke(value, (Object[])parameters);
		    } catch (Exception e) {
		        throw new GlooException(String.format("Cannot invoke sorting method %s for index %s",
		                sortingMethod != null ? sortingMethod.getName() : "null", name));
		    }
		    changed |= putId(sortingKey, id);
	        return changed;
        } finally {
        	lock.writeUnlock();
        }
    }

    /**
     * Clones the unique index.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
        	lock.readLock();
            NonUniqueIndexImplementation<T, V> copy = (NonUniqueIndexImplementation<T, V>) super.clone();
            copy.indexMap = (TreeMap<V, LinkedHashSet<Serializable>>) indexMap.clone();
            copy.reverseIndexMap = (HashMap<Serializable, V>) reverseIndexMap.clone();
            copy.lock = new SafeReentrantReadWriteLock();
            return copy;
        } catch(CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        } finally {
        	lock.readUnlock();
        }
    }

    @Override
    public boolean contains(Serializable value) {
        try {
        	lock.readLock();
        	return containsById(PersistencyAttributes.getIdFromVariant(tClass, value));
        } finally {
        	lock.readUnlock();
        }
    }

    @Override
    public boolean containsSortKey(Serializable key) {
        try {
        	lock.readLock();
        	return indexMap.containsKey(key);
        } finally {
        	lock.readUnlock();
        }
    }

    @Override
    public <R extends Collection<Lazy<T>>> R findBySortKey(R result, V sortKey) {
        result.clear();
        try {
        	lock.readLock();
	        LinkedHashSet<Serializable> idSet = indexMap.get(sortKey);
	        if(idSet != null) {
	        	iterate(idSet, wrapIds(result));
	        }
	        return result;
        } finally {
        	lock.readUnlock();
        }
    }

    /* (non-Javadoc)
     * @see gloodb.associations.NonUniqueIndex#getSortedIdSet(boolean)
     */
    @Override
    public <R extends Collection<Lazy<T>>> R getSortedSet(R result, boolean ascending) {
        result.clear();
        ArrayList<Serializable> ids = new ArrayList<Serializable>();
        try {
        	lock.readLock();
	        for (Entry<V, LinkedHashSet<Serializable>> e : (ascending ? indexMap.entrySet() : indexMap.descendingMap().entrySet())) {
	            ids.addAll(e.getValue());
	        }
	        iterate(ids, wrapIds(result));
	        return result;
        } finally {
        	lock.readUnlock();
        }
    }

    /* (non-Javadoc)
     * @see gloodb.associations.NonUniqueIndex#remove(java.io.Serializable)
     */
    @Override
    public boolean remove(Serializable value) {
        Serializable id = PersistencyAttributes.getIdFromVariant(tClass, value);
        try {
        	lock.writeLock();
        	return removeById(id);
        } finally {
        	lock.writeUnlock();
        }
    }

    @Override
    public int size() {
       try {
    	   lock.readLock();
    	   return indexMap.size();
       } finally {
    	   lock.readUnlock();
       }
    }

    private boolean containsById(Serializable id) {
        return reverseIndexMap.containsKey(id);
    }

    private boolean putId(V sortingKey, Serializable id) {
        LinkedHashSet<Serializable> idSet = indexMap.get(sortingKey);
        boolean changed = false;
        if(idSet == null) {
            idSet = new LinkedHashSet<Serializable>();
            indexMap.put(sortingKey, idSet);
            changed = true;
        }
        idSet.add(id);
        changed |= (reverseIndexMap.put(id, sortingKey) != null);
        return changed;
    }

    private boolean removeById(Serializable id) {
        Serializable oldKey = reverseIndexMap.remove(id);
        if (oldKey != null) {
            LinkedHashSet<Serializable> idSet = indexMap.get(oldKey);
            if(idSet != null) return idSet.remove(id);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.NonUniqueIndex#getSubset(boolean, java.io.Serializable, boolean, java.io.Serializable, boolean)
     */
	@Override
	public <R extends Collection<Lazy<T>>>R getSubset(R result, boolean ascending, V fromKey, boolean fromInclusive, V toKey, boolean toInclusive) {
    	try {
    		lock.readLock();
    		NavigableMap<V, LinkedHashSet<Serializable>> map = ascending? indexMap: indexMap.descendingMap();

    		// Compile all ids inferred from the fromKey / toKey submap into a single list
        	return compileIds(result, map.subMap(fromKey, fromInclusive, toKey, toInclusive).values());
    	} finally {
    		lock.readUnlock();
    	}
	}

	@Override
	public <R extends Collection<Lazy<T>>> R getLowerSet(R result, boolean ascending, V toKey, boolean inclusive) {
    	try {
    		lock.readLock();
    		NavigableMap<V, LinkedHashSet<Serializable>> map = ascending? indexMap: indexMap.descendingMap();

    		// Compile all ids inferred from toKey head map into a single list 
        	return compileIds(result, map.headMap(toKey, inclusive).values());
    	} finally {
    		lock.readUnlock();
    	}
	}

	@Override
	public <R extends Collection<Lazy<T>>> R getUpperSet(R result, boolean ascending, V fromKey, boolean inclusive) {
    	try {
    		lock.readLock();
	    	NavigableMap<V, LinkedHashSet<Serializable>> map = ascending? indexMap: indexMap.descendingMap();
	    	
	    	// Compile all ids inferred from toKey tail map into a single list 
	    	return compileIds(result, map.tailMap(fromKey, inclusive).values());
    	} finally {
    		lock.readUnlock();
    	}
	}

	private <R extends Collection<Lazy<T>>> R compileIds(R result, Collection<LinkedHashSet<Serializable>> ids) {
    	result.clear();
    	for(LinkedHashSet<Serializable> idsForOneKeyValue: ids) {
    		for(Serializable id: idsForOneKeyValue) {
    			result.add(new Lazy<T>(id));
    		}
    	}
    	return result;
	}
}
