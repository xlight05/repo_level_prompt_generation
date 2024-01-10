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

import gloodb.Embedded;

import java.io.Serializable;

/**
 *  Implementation of the RemoveEmbedded expression. It removes the embedded references.
  * <pre>
 * ArrayList<Embedded<MyClass>> source = ...
 * 
 * Expression.iterate(source, new RemoveEmbedded<MyClass>(repository));
 * </pre>
 * @param <T> the embedded type.
 */
public class RemoveEmbedded<T extends Serializable> extends Expression {
    /*
     * (non-Javadoc)
     * @see gloodb.operators.Expression#evaluate(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Expression evaluate(Serializable reference) {
        ((Embedded<T>)reference).remove();
        return null;
    }
}