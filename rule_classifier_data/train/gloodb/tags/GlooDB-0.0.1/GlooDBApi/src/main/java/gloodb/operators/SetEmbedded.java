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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of the SetEmbedded operator. Sets the iterated values into
 * a collection of embedded references.
 *  * <pre>
 * ArrayList<MyClass> values = ...
 * ArrayList<Embedded<MyClass>> embedded = ...
 * 
 * Expression.iterate(values, new SetEmbedded<MyClass>(embedded));
 * </pre>

 * @param <T> The embedded value type.
 */
public class SetEmbedded<T extends Serializable> extends Expression {
    private final Iterator<T> iterator;
    
    public SetEmbedded(Collection<T> collection) {
        this.iterator = collection.iterator();
    }

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Expression evaluate(Serializable reference) {
        ((Embedded<T>)reference).set(iterator.next());
        return null;
    }
}