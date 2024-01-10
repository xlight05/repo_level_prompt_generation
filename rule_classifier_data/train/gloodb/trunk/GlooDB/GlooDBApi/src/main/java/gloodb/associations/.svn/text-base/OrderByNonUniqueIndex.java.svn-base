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
 * OrderBy implementation which uses NonUniqueIndex as a sort order provider.
 *
 * @param <T> The sorted type.
 */
@SuppressWarnings("serial")
public class OrderByNonUniqueIndex<T extends Serializable> extends OrderBy<T> {
	/**
	 * Abstracts away the name of the index access methods.
	 *
	 */
	public static enum Access {
		/**
		 * Invokes NonUniqueIndex.getSortedSet method.
		 */
		SortedSet("getSortedSet"),
		
		/**
		 * Invokes NonUniqueIndex.getSubSet method.
		 */
		SubSet("getSubSet"),
		
		/**
		 * Invokes NonUniqueIndex.getLowerSet method.
		 */
		LowerSet("getLowerSet"),
		
		/**
		 * Invokes NonUniqueIndex.getUpperSet method.
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
	 * @param indexFieldName The name of the NonUniqueIndex field in the provider object.
	 * @param access The access method type.
	 */
	public OrderByNonUniqueIndex(Serializable providerId, String indexFieldName, Access access) {
		super(providerId, indexFieldName, access.getMethodName());
	}
}
