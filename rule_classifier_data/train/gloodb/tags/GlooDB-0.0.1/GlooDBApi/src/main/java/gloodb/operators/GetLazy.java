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

import gloodb.Reference;

import java.io.Serializable;
import java.util.Collection;

/**
 * Implementation of the GetLazy expression. It adds all the pre-fetched lazy reference 
 * into a target collection.
  * <pre>
 * ArrayList<Lazy<MyClass>> source = ...
 * ArrayList<MyClass> destination = new ArrayList<MyClass>();
 * 
 * Expression.iterate(source, new FetchLazy<MyClass>(repository),
 *                            new GetLazy<MyClass>(destination));
 * </pre>
 * @param <T> the value type.
 */
public class GetLazy<T extends Serializable> extends Expression {
    private final Collection<T> target;
    /**
     * Creates the Get expression.
     * @param target The target collection.
     */
    public GetLazy(Collection<T> target) {
        this.target = target;
    }
    

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Expression evaluate(Serializable reference) {
        target.add(((Reference<T>)reference).get());
        return null;
    }
}