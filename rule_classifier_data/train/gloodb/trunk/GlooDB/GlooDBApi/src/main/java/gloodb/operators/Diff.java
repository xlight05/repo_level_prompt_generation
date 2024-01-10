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
import java.util.Iterator;

/**
 * Calculates the difference set.
 *
 * @param <T> The collection type.
 */
public class Diff<T extends Serializable> extends Expression {
	private static final long serialVersionUID = 1L;

	private final Collection<T> result;
	private final Collection<T> [] subtract;
	
	/**
	 * Creates the difference set expression.
	 * @param result The result set.
	 * @param right The right collection.
	 */
	public Diff(Collection<T> result, Collection<T>... subtract) {
		this.result = result;
		this.subtract = subtract;
	}

	@Override
	public void evaluate(Object...parameters) {
		main: for(Iterator<T> iter = result.iterator(); iter.hasNext();) {
			T value = iter.next();
			// Exclude values contained in the right collection
			for(Collection<T> right: subtract) {
				if(right.contains(value))  {
					iter.remove();
					continue main;
				}
			}
		}
	}

}
