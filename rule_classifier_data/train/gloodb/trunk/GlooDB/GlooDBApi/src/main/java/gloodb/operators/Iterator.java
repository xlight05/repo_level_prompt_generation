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

import java.io.Serializable;
import java.util.Collection;

/**
 * Iterates a collection of values and applies the iterative expressions.
 */
public class Iterator<T extends Serializable> extends Expression {
	private static final long serialVersionUID = 1L;

	private final Collection<T> collection;
	private final IterativeExpression<?> [] expressions;
	
	/**
	 * Creates an iterator instance.
	 * 
	 * @param collection
	 *            The collection to iterate.
	 * @param expressions
	 *            The expressions applied to each collection element.
	 * @throws Exception 
	 */
    public Iterator(Collection<T> collection, IterativeExpression<?>... expressions) {
		super();
		this.collection = collection;
		this.expressions = expressions;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void evaluate(Object...parameters) throws Exception {
		for (T value : collection) {
            for (IterativeExpression expression : expressions) {
            	expression.evaluateIteratively(value);
            }
        }
	}
}
