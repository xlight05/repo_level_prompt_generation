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
import gloodb.KeyViolationException;
import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.associations.UniqueIndex;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Implementation of a unique sorted index.
 * 
 * @param <T>
 *            The type of sorted values.
 * @param <V>
 *            The type of the sorting key.
 */
class UniqueIndexImplementation<T extends Serializable, V extends Serializable> implements Cloneable, UniqueIndex<T, V> {
        private static final long serialVersionUID = 3060717940993995603L;

    private final String name;

    private TreeMap<V, Serializable> indexMap;
    private HashMap<Serializable, V> reverseIndexMap;
    private final Class<T> tClass;
    private final Serializable [] parameters;

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
    public UniqueIndexImplementation(Class<T> tClass, String name, Comparator<V> comparator, Serializable... parameters) {
        if(comparator == null) 
            throw new GlooException(String.format("Class %s must specify a non-null comparator when creating index %s", getClass().getName(), name));
        this.name = name;
        this.indexMap = new TreeMap<V, Serializable>(comparator);
        this.reverseIndexMap = new HashMap<Serializable, V>();
        this.tClass = tClass;
        this.parameters = parameters;
    }

    /* (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#add(T)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(T value) {
        Set<Method> sortingMethods = PersistencyAttributes.getSortingMethod(value, name);
        if (sortingMethods == null)
            throw new GlooException(String.format(
                    "Class %s must annotate a SortingCriteria({\"%s\", ...} method to support index %s",
                    value.getClass().getName(), name, getClass().getName()));

        Serializable id = PersistencyAttributes.getIdForObject(value);
        removeById(id);

        V sortingKey = null;
        for (Method sortingMethod : sortingMethods) {
            try {
                sortingKey = (V) sortingMethod.invoke(value, (Object[])parameters);
            } catch (Exception e) {
                throw new GlooException(String.format("Cannot invoke sorting method %s for index %s",
                        sortingMethod != null ? sortingMethod.getName() : "null", name), e);
            }

            if (indexMap.containsKey(sortingKey))
                throw new KeyViolationException(String.format("Duplicated sorting key found in index %s", name));
            putId(sortingKey, id);
        }
    }

    /**
     * Clones the unique index.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            UniqueIndexImplementation<T, V> copy = (UniqueIndexImplementation<T, V>) super.clone();
            copy.indexMap = (TreeMap<V, Serializable>) indexMap.clone();
            copy.reverseIndexMap = (HashMap<Serializable, V>) reverseIndexMap.clone();
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#contains(java.io.Serializable)
     */
    @Override
    public boolean contains(Serializable value) {
        return containsById(PersistencyAttributes.getIdFromVariant(tClass, value));
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#containsSortKey(java.io.Serializable)
     */
    @Override
    public boolean containsSortKey(Serializable key) {
        return indexMap.containsKey(key);
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#findBySortKey(java.lang.String)
     */
    @Override
    public Lazy<T> findBySortKey(String sortKey) {
        return new Lazy<T>(indexMap.get(sortKey));
    }

    /* (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#getSortedSet(boolean)
     */
    @Override
    public LinkedList<Lazy<T>> getSortedSet(boolean ascending) {
        LinkedList<Lazy<T>> result = new LinkedList<Lazy<T>>();
        for (Entry<V, Serializable> e : (ascending ? indexMap.entrySet() : indexMap.descendingMap().entrySet())) {
            result.add(new Lazy<T>(e.getValue()));
        }
        return result;
    }

    /* (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#remove(java.io.Serializable)
     */
    @Override
    public boolean remove(Serializable value) {
        Serializable id = PersistencyAttributes.getIdFromVariant(tClass, value);
        return removeById(id);
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#size()
     */
    @Override
    public int size() {
       return indexMap.size();
    }

    private boolean containsById(Serializable id) {
        return reverseIndexMap.containsKey(id);
    }

    private void putId(V sortingKey, Serializable id) {
        indexMap.put(sortingKey, id);
        reverseIndexMap.put(id, sortingKey);
    }

    private boolean removeById(Serializable id) {
        Serializable oldKey = reverseIndexMap.remove(id);
        if (oldKey != null) {
            indexMap.remove(oldKey);
            return true;
        }
        return false;
    }
}
