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

import gloodb.Cloner;

import java.io.Serializable;
import java.util.Collection;

/**
 * Implementation of the Clone expression. It adds clones of the input values
 * to the target collection
 * <pre>
 * ArrayList<MyClass> source = ...
 * ArrayList<MyClass> destination = new ArrayList<MyClass>();
 * 
 * Expression.iterate(source, new Clone<MyClass>(destination));
 * </pre>
 * @param <T> the value type.
 */
public class Clone<T extends Serializable> extends Expression {
    private final Collection<T> target;

    /**
     * Creates the Clone expression.
     * 
     * @param target
     *            The target collection.
     */
    public Clone(Collection<T> target) {
        this.target = target;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Expression evaluate(Serializable value) {
        target.add((T)Cloner.deepCopy(value));
        return null;
    }
}