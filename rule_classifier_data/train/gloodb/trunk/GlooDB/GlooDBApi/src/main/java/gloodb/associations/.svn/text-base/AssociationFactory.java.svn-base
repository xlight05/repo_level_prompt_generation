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

import gloodb.Repository;
import gloodb.RepositoryStrategy;
import gloodb.SystemObject;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Association factory interface. 
 */
public abstract class  AssociationFactory implements SystemObject, RepositoryStrategy.Instance {
    private static final long serialVersionUID = 5207243640689297810L;

    public static AssociationFactory getInstance(Repository repository) {
        return repository.getSystemObject(AssociationFactory.class);
    }
    
    /**
     * Creates a one to many association object.
     * @param <O> The one value type.
     * @param <M> The many value type.
     * @param oClass The one value class.
     * @param mClass The one value class.
     * @return The association instance.
     */
    public abstract <O extends Serializable, M extends Serializable>OneToManyAssociation<O, M> newOneToManyAssociation(Class<O> oClass, Class<M> mClass);
    
    /**
     * Creates a one to one association object.
     * @param <L> The left value type.
     * @param <R> The right value type.
     * @param lClass The left value class.
     * @param rClass The right value class.
     * @return The association instance.
     */
    public abstract<L extends Serializable, R extends Serializable> OneToOneAssociation<L, R> newOneToOneAssociation(Class<L> lClass, Class<R> rClass);
    
    /**
     * Creates a many to many association object.
     * @param <L> The left value type.
     * @param <R> The right value type.
     * @param lClass The left value class.
     * @param rClass The right value class.
     * @return The association instance.
     */
    public abstract<L extends Serializable, R extends Serializable> ManyToManyAssociation<L, R> newManyToManyAssociation(Class<L> lClass, Class<R> rClass);
    
    /**
     * Creates a new unique index object.
     * @param <T> The value type.
     * @param <V> The sorting value type.
     * @param tClass The value class.
     * @param name The index name.
     * @param comparator The comparator used to sort.
     * @param parameters Parameters used when invoking the SortingCriteria method.
     * @return The unique index instance.
     */
    public abstract <T extends Serializable, V extends Serializable> UniqueIndex<T, V> newUniqueIndex(Class<T> tClass, String name, 
            Comparator<V> comparator, Serializable...parameters);


    /**
     * Creates a new non-unique index object.
     * @param <T> The value type.
     * @param <V> The sorting value type.
     * @param tClass The value class.
     * @param name The index name.
     * @param comparator The comparator used to sort.
     * @param parameters Parameters used when invoking the SortingCriteria method.
     * @return The unique index instance.
     */
    public abstract <T extends Serializable, V extends Serializable> NonUniqueIndex<T, V> newNonUniqueIndex(Class<T> tClass, String name, 
            Comparator<V> comparator, Serializable...parameters);
    
}
