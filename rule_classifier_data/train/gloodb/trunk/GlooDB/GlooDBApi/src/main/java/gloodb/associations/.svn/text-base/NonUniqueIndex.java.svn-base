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
package gloodb.associations;

import gloodb.KeyViolationException;
import gloodb.Lazy;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interface for a non-unique sorted indexes.
 * 
 * @param <T>
 *            The type of sorted values.
 * @param <V>
 *            The type of the sorting key.
 */
public interface NonUniqueIndex<T extends Serializable, V extends Serializable> extends Serializable, Cloneable {

    /**
     * Adds a value to the index. The sorting key is calculated using the
     * sorting method of the value object. {@link gloodb.SortingCriteria}.
     * If the add completes without exception, the index has been changed.
     * 
     * @param object
     *            The object to inde.
     * @return true if the index has been modified.
     * @throws KeyViolationException
     *             if the sorting key already exists in the index.
     */
     boolean add(T object);

    /**
     * Returns set of object ids in ascending or descending order.
     * @param result The result collection.
     * @param ascending
     *            Specifies the sorting order (true for ascending).
     * @return The sorted id collection.
     */
     <R extends Collection<Lazy<T>>> R getSortedSet(R result, boolean ascending);

    /**
     * Removes the specified value from the index.
     * 
     * @param object
     *            The object to remove: can be an object assignable from T,
     *            Lazy<T> or the object id.
     * @return True if the index has been modified.
     */
    boolean remove(Serializable object);
    
    /**
     * Returns true if the index contains the specified object.
     * @param object
     *            The object to look for: can be an object assignable from T,
     *            Lazy<T> or the object id.
     * @return True if the index contains the value.
     */
    boolean contains(Serializable object);

    /**
     * Returns true if the index contains the specified sort key.
     * @param key
     *            The sort key to look for.
     * @return True if the index contains the value.
     */
    boolean containsSortKey(V key);

    /**
     * Returns the size of the index.
     * @return The size of the index.
     */
    int size();

    /**
     * Finds an id based on a sorting key.
     * @param result The result collection.
     * @param sortKey The sort key.
     * @return The set of object ids for the sort key.
     */
    <R extends Collection<Lazy<T>>> R findBySortKey(R result, V sortKey);

    /**
     * Returns the subset between the specified keys, using the specified sort order.
     * @param result The result collection.
     * @param ascending The sort order.
     * @param fromKey From key
     * @param fromInclusive Includes the from key in the result collection when set to true.
     * @param toKey To key
     * @param toKey Includes the to key in the result collection when set to true.
     * @return The subset between the from and to keys.
     */
    <R extends Collection<Lazy<T>>> R getSubset(R result, boolean ascending, V fromKey, boolean fromInclusive, V toKey, boolean toInclusive);

    /**
     * Returns the head subset to the specified key, using the specified sort order.
     * @param result The result collection.
     * @param ascending The sort order.
     * @param toKey To key
     * @param toKey Includes the to key in the result collection when set to true.
     * @return The head subset.
     */
    <R extends Collection<Lazy<T>>> R getLowerSet(R result, boolean ascending, V toKey, boolean inclusive);
	
    /**
     * Returns the tail subset from the specified key, using the specified sort order.
     * @param result The result collection.
     * @param ascending The sort order.
     * @param fromKey From key.
     * @param fromInclusive Includes the from key in the result collection when set to true.
     * @return The tail subset.
     */
    <R extends Collection<Lazy<T>>> R getUpperSet(R result, boolean ascending, V fromKey, boolean inclusive);

    /**
     * Clone method.
     * @return A copy of this object.
     */
    public Object clone();
}