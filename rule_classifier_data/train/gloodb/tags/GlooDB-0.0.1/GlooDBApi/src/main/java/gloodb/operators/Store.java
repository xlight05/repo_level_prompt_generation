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
 *  * <pre>
 * ArrayList<MyClass> source = ...
 * 
 * Expression.iterate(source, new Store(repository));
 * </pre>
 */
public class Store extends Expression {
    private Repository repository;
    public Store(Repository repository) {
        this.repository = repository;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    public Expression evaluate(Serializable value) {
        repository.store(value);
        return null;
    }
}