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
import java.util.LinkedList;

/**
 * Interface for unique sorted indexes.
 * 
 * @param <T>
 *            The type of sorted values.
 * @param <V>
 *            The type of the sorting key.
 */
public interface UniqueIndex<T extends Serializable, V extends Serializable> extends Serializable, Cloneable {

    /**
     * Adds a value to the index. The sorting key is calculated using the
     * sorting method of the value object. {@link gloodb.SortingCriteria}.
     * If the add completes without exception, the index has been changed.
     * 
     * @param value
     *            The value to add.
     * @throws KeyViolationException
     *             if the sorting key already exists in the index.
     */
    void add(T value);

    /**
     * Returns set of object ids (as lazy references) in ascending or descending order.
     * 
     * @param ascending
     *            Specifies the sorting order (true for ascending).
     * @return The sorted lazy reference list.
     */
    LinkedList<Lazy<T>> getSortedSet(boolean ascending);

    /**
     * Removes the specified value from the index.
     * 
     * @param value
     *            The value to remove: can be an object assignable from T,
     *            Lazy<T> or the object id.
     * @return True if the index has been modified.
     */
    boolean remove(Serializable value);
    
    /**
     * Returns true if the index contains the specified value.
     * @param value
     *            The to look for: can be an object assignable from T,
     *            Lazy<T> or the object id.
     * @return True if the index contains the value.
     */
    boolean contains(Serializable value);

    /**
     * Returns true if the index contains the specified sort key.
     * @param key
     *            The sort key to look for.
     * @return True if the index contains the value.
     */
    boolean containsSortKey(Serializable key);

    /**
     * Returns the size of the index.
     * @return The size of the index.
     */
    int size();

    /**
     * Finds an id based on a sorting key.
     * @param sortKey The sort key.
     * @return The lazy reference to the object
     */
    Lazy<T> findBySortKey(String sortKey);
    
    /**
     * Clone method.
     * @return A copy of this object.
     */
    public Object clone();
}