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
package gloodb.operators;

import gloodb.Lazy;
import gloodb.Repository;

import java.io.Serializable;

/**
 * Implementation of the Fetch expression. It fetches all the lazy references
 * in the source collection from the repository.
 * <pre>
 * ArrayList<Lazy<MyClass>> source = ...
 * 
 * Expression.iterate(source, new FetchLazy<MyClass>(repository));
 * </pre>
 * @param <T> the value type.
 */
public class FetchLazy<T extends Serializable> extends Expression {
    private final Repository repository;
    /**
     * Creates a fetch expression.
     * @param repository The repository.
     */
    public FetchLazy(Repository repository) {
        this.repository = repository;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Expression evaluate(Serializable reference) {
        ((Lazy<T>)reference).fetch(repository);
        return null;
    }               
}