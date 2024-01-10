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

/**
 * Implementation of the FillEmbedded operator. Adds the iterated values into
 * a collection of embedded references.
 * @param <T> The embedded value type.
 */
public class FillEmbedded<T extends Serializable> extends IterativeExpression<T> {
	private static final long serialVersionUID = 1L;

	private final Collection<Embedded<T>> collection;
    private final Serializable containerId;
    private final String idPrefix;
    
    /**
     * Creates an instance of the fill embedded expression
     * @param containerId The container id to use when creating the embedded object.
     * @param idPrefix The id prefix to use when creating the embedded object. The expression will the index after the prefix.
     * @param collection The target collection to fill. Must be empty to begin with.
     */
    public FillEmbedded(Serializable containerId, String idPrefix, Collection<Embedded<T>> collection) {
        this.collection = collection;
        this.containerId = containerId;
        this.idPrefix = idPrefix;
    }

	@Override
    public void evaluateIteratively(T value) {
        this.collection.add(new Embedded<T>(containerId, String.format("%s%d", idPrefix, this.collection.size())).set(value));
    }
}