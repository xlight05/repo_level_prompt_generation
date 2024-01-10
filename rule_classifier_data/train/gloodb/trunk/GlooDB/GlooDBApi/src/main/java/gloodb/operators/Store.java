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

/**
 * Iterates a collection of values and stores them in the repository.
 */
public class Store<T extends Serializable> extends IterativeExpression<T> {
	private static final long serialVersionUID = 1L;

	private Repository repository;
    
	public Store(Repository repository) {
        this.repository = repository;
    }

    public void evaluateIteratively(T value) {
        repository.store(value);
    }
}