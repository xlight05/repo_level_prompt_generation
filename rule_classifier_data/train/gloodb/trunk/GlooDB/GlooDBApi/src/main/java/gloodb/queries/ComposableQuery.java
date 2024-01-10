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

import gloodb.Repository;

import java.io.Serializable;
import java.util.Map;

/**
 * Tagging interface for composable queries. Composable queries can be aggregated into a {@link CompositeQuery}.
 * At runtime, each composable query is wrapped into a repository query as follows:<ul>
 *  <li>if the query implements ReadOnlyQuery interface, the query executes as read only.
 *  <li>if the query implements UpdateQuery interface, the query executes as update.
 *  <li>otherwise, the query executes unguarded.
 * 
 */
public interface ComposableQuery {
	/**
	 * Executes the composable query.
	 * @param repository The repsitory.
	 * @param compositeQuery The parent composite query.
	 * @param queryState The query state.
	 * @param parameters The query parameters.
	 */
	void run(Repository repository, CompositeQuery compositeQuery, Map<Serializable, Serializable> queryState, Map<String, Serializable> parameters) throws Exception;
}
