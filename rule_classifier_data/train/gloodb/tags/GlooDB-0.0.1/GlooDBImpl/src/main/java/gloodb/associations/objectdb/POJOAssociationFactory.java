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

import gloodb.associations.AssociationFactory;
import gloodb.associations.NonUniqueIndex;
import gloodb.associations.OneToManyAssociation;
import gloodb.associations.OneToOneAssociation;
import gloodb.associations.UniqueIndex;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Association factory implementation using java.util classes to implement associations.
 */
public class POJOAssociationFactory extends AssociationFactory {
    private static final long serialVersionUID = -178929490361397967L;
   
    /**
     * Returns a pojo based one to many association.
     */
    @Override
    public <O extends Serializable, M extends Serializable> OneToManyAssociation<O, M> newOneToManyAssociation(Class<O> oClass, Class<M> mClass) {
        return new OneToManyAssociationImplementation<O, M>(oClass, mClass);
    }

    /**
     * Returns a pojo based one to one association.
     */
    @Override
    public <L extends Serializable, R extends Serializable> OneToOneAssociation<L, R> newOneToOneAssociation(Class<L> lClass, Class<R> rClass) {
        return new OneToOneAssociationImplementation<L, R>(lClass, rClass);
    }

    /**
     * Returns a pojo based unique index.
     */
    @Override
    public <T extends Serializable, V extends Serializable> UniqueIndex<T, V> newUniqueIndex(Class<T> tClass, String name, Comparator<V> comparator, Serializable... parameters) {
        return new UniqueIndexImplementation<T, V>(tClass, name, comparator, parameters);
    }

    /**
     * Returns a pojo based non-unique index.
     */
    @Override
    public <T extends Serializable, V extends Serializable> NonUniqueIndex<T, V> newNonUniqueIndex(Class<T> tClass, String name,
            Comparator<V> comparator, Serializable... parameters) {
        return new NonUniqueIndexImplementation<T, V>(tClass, name, comparator, parameters);
    }
}
