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
package gloodb.associations;

import java.io.Serializable;

/**
 * OrderBy implementation which uses UniqueIndex as a sort order provider.
 *
 * @param <T> The sorted type.
 */
@SuppressWarnings("serial")
public class OrderByUniqueIndex<T extends Serializable> extends OrderBy<T> {
	/**
	 * Abstracts away the name of the index access methods.
	 *
	 */
	public static enum Access {
		/**
		 * Invokes UniqueIndex.getSortedSet method.
		 */
		SortedSet("getSortedSet"),
		
		/**
		 * Invokes UniqueIndex.getSubSet method.
		 */
		SubSet("getSubSet"),
		
		/**
		 * Invokes UniqueIndex.getLowerSet method.
		 */
		LowerSet("getLowerSet"),
		
		/**
		 * Invokes UniqueIndex.getUpperSet method.
		 */
		UpperSet("getUpperSet");

		private String methodName;
		
		private Access(String methodName) {
			this.methodName = methodName;
		}
		
		/**
		 * Returns the name of the index method.
		 * @return The index method.
		 */
		public String getMethodName() {
			return this.methodName;
		}
	}
	
	/**
	 * Creates an OrderBy instance.
	 * @param providerId The id of the provider object.
	 * @param indexFieldName The name of the UniqueIndex field in the provider object.
	 * @param access The access method type.
	 */
	public OrderByUniqueIndex(Serializable providerId, String indexFieldName, Access access) {
		super(providerId, indexFieldName, access.getMethodName());
	}
}
