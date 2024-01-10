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
 * Intersects the provided collections. 
 *
 * @param <T> The collection type.
 */
public class Intersect<T extends Serializable> extends Expression {
	private static final long serialVersionUID = 1L;

	private final Collection<T> result;
	private final Collection<T>[] right;
	
	public Intersect(Collection<T> result, Collection<T>... right) {
		this.result = result;
		this.right = right;
	}

	@Override
	public void evaluate(Object...parameters) {
		main: for(Iterator<T> iter = result.iterator(); iter.hasNext(); ) {
			T value = iter.next();
			for(Collection<T> r: right) {
				if(!r.contains(value)) {
					iter.remove();
					continue main;
				}
			}
		}
	}
}
