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
 * Implementation of the GetLazy expression. It adds all the pre-fetched references 
 * into a target collection.
 * @param <T> the value type.
 */
public class ReferenceGet<T extends Serializable> extends IterativeExpression<Reference<T>> {
	private static final long serialVersionUID = 1L;

	private final Collection<T> target;
    
	/**
     * Creates the Get expression.
     * @param target The target collection.
     */
    public ReferenceGet(Collection<T> target) {
        this.target = target;
    }
    
    public void evaluateIteratively(Reference<T> reference) {
        target.add(reference.get());
    }
}