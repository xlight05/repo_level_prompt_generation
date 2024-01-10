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
import java.util.Collection;

/**
 * Restores the ids from the iterated collection and adds the values to a target collection.
 * @param <I>
 *            The identity type.
 * @param <V>
 *            The value type.
 */
public class Restore<I extends Serializable, V extends Serializable> extends IterativeExpression<I> {
	private static final long serialVersionUID = 1L;

	private final Repository repository;
    private final Collection<V> values;

    /**
     * Creates the Get expression.
     * 
     * @param repository
     *            The repository 
     * @param values
     *            The value collection.
     */
    public Restore(Repository repository, Collection<V> values) {
        this.repository = repository;
        this.values = values;
    }

    @SuppressWarnings("unchecked")
	public void evaluateIteratively(I id) {
        values.add((V) repository.restore(id));
    }
}