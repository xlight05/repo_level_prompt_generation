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

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Implementation of the wrap as lazy expression. It wraps the ids in the source collection
 * into lazy values.
 * @param <T> the value type.
 */
public class WrapAsLazy<T extends Serializable> extends Expression {
    private final LinkedList<Lazy<T>> result;
    /**
     * Creates a fetch expression.
     * @param target The target collection. 
     */
    public WrapAsLazy(LinkedList<Lazy<T>> target) {
        this.result = target;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @Override
    public Expression evaluate(Serializable id) {
        result.add(new Lazy<T>(id));
        return null;
    }               
}