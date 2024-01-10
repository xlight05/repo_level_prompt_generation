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
 * @param <T> the value type.
 */
public class Clone<T extends Serializable> extends IterativeExpression<T> {
	private static final long serialVersionUID = 1L;
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

    public void evaluateIteratively(T value) {
        target.add(Cloner.deepCopy(value));
    }
}