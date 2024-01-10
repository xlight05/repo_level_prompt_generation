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

import gloodb.GlooException;
import gloodb.Reference;
import gloodb.Repository;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import static gloodb.operators.SetOperators.*;

/**
 * OrderBy class implements persistent data sorting. The implementation uses gloodb indexes (UniqueIndex, NonUniqueIndex) as sort order providers.
 *
 * @param <T> The sorted data type.
 */
@SuppressWarnings("serial")
public class OrderBy<T extends Serializable> implements Serializable {
	private final Serializable providerId;
	private final String indexFieldName;
	private final String indexMethodName;
	
	/**
	 * Creates an OrderBy instance which specifies the field and method name which will be invoked on 
	 * the provider object to retrieve the sort data.
	 * @param providerId The id of the provider object.
	 * @param indexFieldName The name of the index field in the provider object.
	 * @param indexMethodName The name of the index method used to retrieve the sorted data from the index.
	 */
	public OrderBy(Serializable providerId, String indexFieldName, String indexMethodName) {
		this.providerId = providerId;
		this.indexFieldName = indexFieldName;
		this.indexMethodName = indexMethodName;
	}
	
	/**
	 * Retrieves the sorted object collection. Object are wrapped in References. When no provider is found,
	 * an empty collection is returned.
	 * @param result The result collection.
	 * @param repository The repository.
	 * @param providerId The id of the persistent object which contains the provider associations.
	 * @param parameters The parameters used to invoke the index method.
	 * @return The sorted object collection.
	 */
	public <R extends Collection<? extends Reference<T>>> R getOrderedCollection(R result, Repository repository, Object...parameters) {
		result.clear();
		Serializable container = repository.restore(providerId);
		if(container == null) return result;
		try {
			Field f = container.getClass().getField(this.indexFieldName);
			f.setAccessible(true);
			return accessCollection(container, f, parameters);
		} catch(GlooException e) {
			throw e;
		} catch (Exception e) {
			throw new GlooException(String.format("Cannot find index field %s", indexFieldName), e);
		} 
		
	}
	
	@SuppressWarnings("unchecked")
	public <L extends Reference<T>, R extends Collection<L>> R sortCollection(R result, Collection<L> unorderedCollection, Repository repository, Object...parameters ) {
		getOrderedCollection(result, repository, parameters);
		return intersect(result, unorderedCollection);
	}
	
	@SuppressWarnings("unchecked")
	private <R extends Collection<? extends Reference<T>>> R accessCollection(Object container, Field f, Object...parameters) {
		try {
			Object index = f.get(container);
			Class<?> [] parameterTypes = new Class<?>[parameters.length];
			for(int i = 0; i < parameterTypes.length; i++) parameterTypes[i] = parameters[i].getClass();
			Method m = index.getClass().getMethod(this.indexMethodName, parameterTypes);
			m.setAccessible(true);
			return (R)m.invoke(index, parameters);
		} catch (IllegalArgumentException e) {
			throw new GlooException(String.format("Cannot access index field %s", indexFieldName), e);
		} catch (IllegalAccessException e) {
			throw new GlooException(String.format("Cannot access index field %s", indexFieldName), e);
		} catch (SecurityException e) {
			throw new GlooException(String.format("Cannot find indexing method %s", indexMethodName), e);
		} catch (NoSuchMethodException e) {
			throw new GlooException(String.format("Cannot find indexing method %s", indexMethodName), e);
		} catch (InvocationTargetException e) {
			throw new GlooException(String.format("Cannot invoke indexing method %s", indexMethodName), e);
		}
	}
}
