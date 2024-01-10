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

import gloodb.Repository;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Iterator expression. Jointly iterates the input collection and the provided target collection.
 * @param <I>
 *            The input type.
 * @param <V>
 *            The target collection type.
 */
public abstract class JointIterator<I extends Serializable, V extends Serializable> extends IterativeExpression<I> {
	private static final long serialVersionUID = 1L;

	protected final Repository repository;
    private final Iterator<V> iterator;

    /**
     * Creates the joined iterator operator.
     * @param repository The repository used during the iteration.
     * @param target The target collection.
     */
    public JointIterator(Repository repository, Iterable<V> target) {
        this.repository = repository;
        this.iterator = target.iterator();
    }

    @Override
    public void evaluateIteratively(I input) {
        run(input, iterator.next());
    }

    /**
     * Override this method to implement specific iteration functionality.
     * @param input The input value
     * @param value The iterated value.
     */
    public abstract void run(I input, V value);

}