/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.queries;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import gloodb.Query;
import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.UpdateQuery;

/**
 * Class implementing composite queries. A composite query consist of a list of composable queries. This list is executed
 * in a sequence (command pattern). 
 * 
 * <p>The composite query maintains its state as a serializable map. This state is accessible
 * to all composable queries.</p>
 * 
 *  <p>Each composable query is wrapped into a repository query as follows:<ul>
 *  <li>if the query implements ReadOnlyQuery interface, the query executes as read only.
 *  <li>if the query implements UpdateQuery interface, the query executes as update.
 *  <li>otherwise, the query executes unguarded. </p>
 *
 */
public class CompositeQuery implements Query<LinkedHashMap<Serializable, Serializable>> {
	private static final long serialVersionUID = 1L;
	private final Collection<ComposableQuery> queries;
	
	/**
	 * Creates a composite query from the collection of composable queries.
	 * @param queries The composable queries.
	 */
	public CompositeQuery(Collection<ComposableQuery> queries) {
		super();
		this.queries = queries;
	}

	/**
	 * Runs a composite query. Each composable query runs as a repository query.
	 * Each composable query is wrapped into a repository query as follows:<ul>
	 *  <li>if the query implements ReadOnlyQuery interface, the query executes as read only.
	 *  <li>if the query implements UpdateQuery interface, the query executes as update.
	 *  <li>otherwise, the query executes unguarded.
	 * @param repository The repository.
	 * @param parameters A map of name value parameters.
	 * @return The end state of the query.
	 * @throws Exception If any of the composable queries throw and exception.
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<Serializable, Serializable> run(Repository repository, Map<String, Serializable> parameters) throws Exception {
		LinkedHashMap<Serializable, Serializable> queryState = new LinkedHashMap<Serializable, Serializable>();
		
		for(ComposableQuery query: queries) {
			if((query instanceof ReadOnlyQuery) || (query instanceof UpdateQuery)) {
				// wrap the query for correct locking.
				repository.query((Query<Serializable>)query, this, queryState, (Serializable)parameters).throwExceptionsIfAny();
			} else {
				// If the query is not guarded, run as part of the parent query (which is unguarded).
				query.run(repository, this, queryState, parameters);
			}
		}
		return queryState;
	}
	

	/**
	 * This method delegates the call to {@link #run(Repository, Map)} with parameters[0] cast to Map<String, Serializable>.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Serializable, Serializable> run(Repository repository, Serializable... parameters) throws Exception {
		return run(repository, (Map<String, Serializable>)parameters[0]);
	}
	
	/**
	 * Returns the collection of composable queries.
	 * @return The composable queries.
	 */
	public Collection<ComposableQuery> getQueries() {
		return queries;
	}
}
