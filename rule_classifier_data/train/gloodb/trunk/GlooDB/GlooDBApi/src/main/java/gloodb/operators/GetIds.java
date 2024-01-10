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
 * @param <T> the value type
 * @param <I> the id type.
 */
public class GetIds<T extends Serializable, I extends Serializable> extends IterativeExpression<Reference<T>> {
	private static final long serialVersionUID = 1L;

	private final Collection<I> target;
    
	/**
     * Create the GetIds expression.
     * @param target The lazy value type.
     */
    public GetIds(Collection<I> target) {
        this.target = target;
    }
    
    @SuppressWarnings("unchecked")
    public void evaluateIteratively(Reference<T> reference) {
        target.add((I)(reference).getId());
    }
}