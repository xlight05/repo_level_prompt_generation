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
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of the SetEmbedded operator. Sets the iterated values into
 * a collection of embedded references.
 * @param <T> The embedded value type.
 */
public class SetEmbedded<T extends Serializable> extends IterativeExpression<Embedded<T>> {
	private static final long serialVersionUID = 1L;
	
	private final Iterator<T> iterator;
    
    public SetEmbedded(Collection<T> collection) {
        this.iterator = collection.iterator();
    }

    @Override
    public void evaluateIteratively(Embedded<T> reference) {
        reference.set(iterator.next());
    }
}