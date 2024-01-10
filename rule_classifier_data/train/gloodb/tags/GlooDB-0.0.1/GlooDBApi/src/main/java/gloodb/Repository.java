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
package gloodb;

import java.io.Serializable;

/**
 * Repository interface. The programmer uses this interface to perform
 * persistent operations: create, retrieve, update, delete, query.
 */
public interface Repository {

	/**
	 * Begins a new transaction and associates it with the returned repository.
	 * The transaction is nested in this repository's transaction.
	 * 
	 * @return The new repository associated with the transaction.
	 */
	Repository begin();

	/**
	 * Commits the transaction associated with this repository. All nested
	 * transactions are committed.
	 */
	void commit();

	/**
	 * Returns true if the repository contains the specified object.
	 * 
	 * @param idFields
	 *            The fields defining the identity of the object.
	 * @return True if the object exists in the repository.
	 */
	public boolean contains(Serializable... idFields);

	/**
	 * Creates a new persistent object in the repository.
	 * 
	 * @param <T>
	 *            The persistent object type.
	 * @param persistentObject
	 *            The persistent object.
	 * @return The persisted object.
	 * @throws KeyViolationException
	 *             If an object with the same identity exists in the repository.
	 */
	<T extends Serializable> T create(T persistentObject) throws KeyViolationException;

	/**
	 * Returns the class of the object with the specified id.
	 * 
	 * @param idFields
	 *            The fields defining the identity of the object.
	 * @return The class of the persistent object.
	 */
	Class<? extends Serializable> getClassForId(Serializable... idFields);

	/**
	 * Queries the repository and returns the query result. The query run is
	 * completely unguarded and will run concurrently with other queries. This
	 * may result in locking exceptions.
	 * 
	 * @param query
	 *            The query.
	 * @return The query result.
	 */
	QueryResult query(Query query);

	/**
	 * Queries the repository and returns the query result. The query must be
	 * read only, otherwise a deadlock will occur. The query is guarded by a
	 * read lock and it can run concurrently with other read queries, but not
	 * with update queries.
	 * 
	 * @param query
	 *            The query.
	 * @return The query result.
	 */
	QueryResult readOnlyQuery(ReadOnlyQuery query);

    /**
     * Runs an update query on the repository and returns a query result. The
     * query is guarded by a write. It will concurrently run against any
     * unguarded query and sequentially against other read or update queries.
     * 
     * @param query
     *            The query.
     * @return The query result.
     */
	QueryResult updateQuery(UpdateQuery query);

	/**
	 * Removes the object with the given id from the repository.
	 * 
	 * @param idFields
	 *            The fields defining the identity of the object.
	 * @return The removed object.
	 */
	Serializable remove(Serializable... idFields);

	/**
	 * Restores a copy of the persistent object in the repository.
	 * 
	 * @param idFields
	 *            The fields defining the identity of the object.
	 * @return The restored object copy.
	 */
	Serializable restore(Serializable... idFields);

	/**
	 * Rolls back a transaction and all its child transactions.
	 */
	void rollback();

	/**
	 * Stores (creates or updates) an object in the repository.
	 * 
	 * @param <T>
	 *            The object type.
	 * @param persistentObject
	 *            The persistent object.
	 * @return The updated object value.
	 */
	<T extends Serializable> T store(T persistentObject);

	/**
	 * Updates an existent object in the repository.
	 * 
	 * @param <T>
	 *            The object type.
	 * @param persistentObject
	 *            The persistent object.
	 * @return The updated object value.
	 * @throws PersistentNotFoundException
	 *             If the object does not exist in the repository.
	 */
	<T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException;

	/**
	 * Returns a system object.
	 * 
	 * @param <T>
	 *            The system object type.
	 * @param systemObjectClass
	 *            The system object class.
	 * @return The system object.
	 */
	<T extends SystemObject> T getSystemObject(Class<T> systemObjectClass);

	/**
	 * Marks the transaction associated with this repository for rollback only.
	 * Top level transaction cannot be marked for rollback only.
	 */
	void markForRollback();

	/**
	 * Returns true if the repository is associated with the top level
	 * transaction.
	 * 
	 * @return true If the repository is associated with the top level
	 *         transaction.
	 */
	boolean isTopLevel();

	/**
	 * Returns true if the transaction associated with the repository can
	 * commit.
	 * 
	 * @return true If the repository can commit its transaction.
	 */
	boolean canCommit();

	/**
	 * Returns true if the transaction associated with the repository can
	 * complete.
	 * 
	 * @return true If the repository can complete its transaction.
	 */
	boolean canComplete();
}
