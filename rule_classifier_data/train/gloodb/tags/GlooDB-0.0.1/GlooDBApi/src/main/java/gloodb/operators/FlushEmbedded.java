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

import gloodb.Embedded;
import gloodb.Repository;

import java.io.Serializable;

/**
 * Implementation of the Flush expression. It flushes the collection of embedded references into
 * the repository.
 * <pre>
 * ArrayList<Embedded<MyClass>> source = ...
 * 
 * Expression.iterate(source, new FlushEmbedded<MyClass>(repository));
 * </pre>
 * @param <T> the value type
 */
public class FlushEmbedded<T extends Serializable> extends Expression {
    private final Repository repository;
    /**
     * Creates a flush expression.
     * @param repository The repository.
     */
    public FlushEmbedded(Repository repository) {
        this.repository = repository;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Expression evaluate(Serializable reference) {
        ((Embedded<T>)reference).flush(repository);
        return null;
    }
}