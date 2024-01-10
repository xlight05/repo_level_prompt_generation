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

import gloodb.associations.Tuple;

import java.io.Serializable;
import java.util.Collection;

/**
 * Applies the join criteria to the tuple set.
 *
 */
public class Join extends Carthesian {
	private static final long serialVersionUID = 1L;

	public static interface JoinCriteria extends Serializable {
		boolean areJoined(Tuple tuple);
	}
	
	private final JoinCriteria criteria;

	/**
	 * Creates a join expression.
	 * @param tupleSet The resulting tuple set.
	 * @param criteria The join criteria.
	 * @param joining sets.
	 */
	public Join(Collection<Tuple> tupleSet, JoinCriteria criteria, Collection<?>...joining) {
		super(tupleSet, joining);
		this.criteria = criteria;
	}
	
	protected void addProductTuple(Collection<Tuple> result, Tuple t) {
		if(criteria.areJoined(t)) result.add(t);
	}
}
