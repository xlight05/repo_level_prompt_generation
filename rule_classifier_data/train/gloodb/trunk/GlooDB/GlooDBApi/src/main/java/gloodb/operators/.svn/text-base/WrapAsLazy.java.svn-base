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
import java.util.Collection;

/**
 * Implementation of the wrap as lazy expression. It wraps the ids in the source collection
 * into lazy values.
 * @param <T> the value type.
 */
public class WrapAsLazy<T extends Serializable> extends IterativeExpression<T> {
    private static final long serialVersionUID = 1L;
	
    private final Collection<Lazy<T>> result;
    
    /**
     * Creates a fetch expression.
     * @param target The target collection. 
     */
    public WrapAsLazy(Collection<Lazy<T>> target) {
        this.result = target;
    }
    
    @Override
    public void evaluateIteratively(T id) {
        result.add(new Lazy<T>(id));
    }               
}