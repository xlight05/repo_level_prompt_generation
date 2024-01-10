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

import gloodb.Cloner;
import gloodb.associations.Tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * Creates the carthesian product of the input collections.
 *
 */
public class Carthesian extends Expression {
	private static final long serialVersionUID = 1L;

	public static interface JoinCriteria extends Serializable {
		boolean areJoined(Tuple tuple);
	}
	
	private final Collection<Tuple> tupleSet;
	private final Collection<Tuple> tupleSetPrototype;
	private final Collection<?> [] product;

	/**
	 * Creates a join expression.
	 * @param tupleSet The resulting tuple set.
	 * @param criteria The join criteria.
	 * @param product sets.
	 */
	@SuppressWarnings("unchecked")
	public Carthesian(Collection<Tuple> tupleSet, Collection<?>...product) {
		this.tupleSet = tupleSet;
		this.product = product;
		// Create a tuple set prototype using cloning.
		this.tupleSetPrototype = (Collection<Tuple>) Cloner.deepCopy((Serializable)this.tupleSet);
		this.tupleSetPrototype.clear();
	}

	/**
	 * Joins the iterated and right collection and returns the result.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void evaluate(Object...parameters) {
		// Create the carthesian product
		Collection<Tuple> result = (Collection<Tuple>) Cloner.deepCopy((Serializable)this.tupleSet);
		for(Collection<?> j: getProductCollections(result)) {
			result = multiplyCollection(result, j);
		}
		this.tupleSet.clear();
		this.tupleSet.addAll(result);
	}

	private Collection<?>[] getProductCollections(Collection<Tuple> result) {
		if(result.size() == 0) {
			for(Object o: this.product[0]) {
				result.add(new Tuple((Serializable)o));
			}
			return Arrays.copyOfRange(product, 1, this.product.length);
		} else {
			return this.product;
		}
	}

	private Collection<Tuple> multiplyCollection(Collection<Tuple> initial, Collection<?> product) {
		Collection<Tuple> result = createTupleCollection();
		for(Tuple t: initial) {
			for(Object p: product) {
				addProductTuple(result, new Tuple(t).add((Serializable)p));
			}
		}
		return result;
	}
	
	protected void addProductTuple(Collection<Tuple> result, Tuple t) {
		result.add(t);
	}

	@SuppressWarnings("unchecked")
	private Collection<Tuple> createTupleCollection() {
		return (Collection<Tuple>) Cloner.deepCopy((Serializable)this.tupleSetPrototype);
	}
}
