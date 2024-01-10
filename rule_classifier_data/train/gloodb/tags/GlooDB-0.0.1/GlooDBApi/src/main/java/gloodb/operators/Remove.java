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
 * Implementation of the Remove expression. 
 * Removes the objects with the ids in the input collection from the repository.
  * <pre>
 * ArrayList<Serializable> ids = ...
 * 
 * Expression.iterate(source, new Remove<MyClass>(repository));
 * </pre>
 */
public class Remove extends Expression {
    private final Repository repository;
    public Remove(Repository repository) {
        this.repository = repository;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    public Expression evaluate(Serializable id) {
        repository.remove(id);
        return null;
    }
}