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
import java.util.Iterator;

/**
 * Iterator expression. Jointly iterates the input collection and the provided target collection.
 * 
 * <pre>
 * ArrayList<MyClass> source = ...
 * ArrayList<MyClass> collection = ..,
 * 
 * Expression.iterate(source, new JointIterator<MyClass, MyClass>(collection) {
 *      public Expression run(MyClass input, MyClass value) {
 *          // ... iteration code here
 *          
 *          // return null to continue the iteration
 *          return null;
 *      } 
 * });
 * </pre>

 * 
 * @param <I>
 *            The input type.
 * @param <V>
 *            The target collection type.
 */
public abstract class JointIterator<I extends Serializable, V extends Serializable> extends Expression {
    protected final Repository repository;
    private final Iterator<V> iterator;

    /**
     * Creates the joined iterator operator.
     * @param repository The repository used during the iteration.
     * @param target The target collection.
     */
    public JointIterator(Repository repository, Collection<V> target) {
        this.repository = repository;
        this.iterator = target.iterator();
    }

    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Expression evaluate(Serializable input) {
        return run((I)input, iterator.next());
    }

    /**
     * Override this method to implement specific iteration functionality.
     * @param input The input value
     * @param value The iterated value.
     * @return next expression or null to continue the iteration.
     */
    public abstract Expression run(I input, V value);

}