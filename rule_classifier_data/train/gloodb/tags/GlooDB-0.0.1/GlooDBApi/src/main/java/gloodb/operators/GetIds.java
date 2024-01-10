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
 * Implementation of the GetIds expression. It adds the reference ids into a target collection
 * <pre>
 * ArrayList<Lazy<MyClass>> source = ...
 * ArrayList<Serializable> destination = new ArrayList<Serializable>();
 * 
 * Expression.iterate(source, new GetIds<MyClass, Serializable>(destination));
 * </pre>
 * @param <T> the value type
 * @param <I> the id type.
 */
public class GetIds<T extends Serializable, I extends Serializable> extends Expression {
    private final Collection<I> target;
    /**
     * Create the GetIds expression.
     * @param target The lazy value type.
     */
    public GetIds(Collection<I> target) {
        this.target = target;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Expression evaluate(Serializable reference) {
        target.add((I)((Reference<T>)reference).getId());
        return null;
    }
}