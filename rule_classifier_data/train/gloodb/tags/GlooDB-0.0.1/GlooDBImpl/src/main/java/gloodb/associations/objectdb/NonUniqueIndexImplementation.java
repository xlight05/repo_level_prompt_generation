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
import gloodb.PersistencyAttributes;
import gloodb.associations.NonUniqueIndex;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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
    }

    /* (non-Javadoc)
     * @see gloodb.associations.UniqueIndex#add(T)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        boolean changed = false;
        Set<Method> sortingMethods = PersistencyAttributes.getSortingMethod(value, name);
        if (sortingMethods == null)
            throw new GlooException(String.format(
                    "Class %s must annotate a SortingCriteria({\"%s\", ...} method to support index %s",
                    value.getClass().getName(), name, getClass().getName()));

        Serializable id = PersistencyAttributes.getIdForObject(value);
        changed |= removeById(id);

        V sortingKey = null;
        for (Method sortingMethod : sortingMethods) {
            try {
                sortingKey = (V) sortingMethod.invoke(value, (Object[])parameters);
            } catch (Exception e) {
                throw new GlooException(String.format("Cannot invoke sorting method %s for index %s",
                        sortingMethod != null ? sortingMethod.getName() : "null", name));
            }
            changed |= putId(sortingKey, id);
        }
        return changed;
    }

    /**
     * Clones the unique index.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            NonUniqueIndexImplementation<T, V> copy = (NonUniqueIndexImplementation<T, V>) super.clone();
            copy.indexMap = (TreeMap<V, LinkedHashSet<Serializable>>) indexMap.clone();
            copy.reverseIndexMap = (HashMap<Serializable, V>) reverseIndexMap.clone();
            return copy;
        } catch(CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    @Override
    public boolean contains(Serializable value) {
        return containsById(PersistencyAttributes.getIdFromVariant(tClass, value));
    }

    @Override
    public boolean containsSortKey(Serializable key) {
        return indexMap.containsKey(key);
    }

    @Override
    public LinkedList<Serializable> findIdsBySortKey(V sortKey) {
        LinkedHashSet<Serializable> idSet = indexMap.get(sortKey);
        return (idSet == null) ? new LinkedList<Serializable>(): new LinkedList<Serializable>(idSet);
    }

    /* (non-Javadoc)
     * @see gloodb.associations.NonUniqueIndex#getSortedIdSet(boolean)
     */
    @Override
    public LinkedList<Serializable> getSortedIdSet(boolean ascending) {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        for (Entry<V, LinkedHashSet<Serializable>> e : (ascending ? indexMap.entrySet() : indexMap.descendingMap().entrySet())) {
            result.addAll(e.getValue());
        }
        return result;
    }

    /* (non-Javadoc)
     * @see gloodb.associations.NonUniqueIndex#remove(java.io.Serializable)
     */
    @Override
    public boolean remove(Serializable value) {
        Serializable id = PersistencyAttributes.getIdFromVariant(tClass, value);
        return removeById(id);
    }

    @Override
    public int size() {
       return indexMap.size();
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
}
