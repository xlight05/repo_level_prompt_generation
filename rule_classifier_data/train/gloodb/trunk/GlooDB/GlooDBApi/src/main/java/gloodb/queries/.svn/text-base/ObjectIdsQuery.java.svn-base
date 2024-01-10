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
package gloodb.queries;

import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.SystemObject;

import java.io.Serializable;
import java.util.List;

/**
 * Query which scans the repository and returns a filtered set of object
 * identities.
 */
public abstract class ObjectIdsQuery implements ReadOnlyQuery<Serializable>, SystemObject {
	private static final long serialVersionUID = -4254255589607057079L;

	/**
	 * Tagging interface for object id filters.
	 */
	public interface Filter extends Serializable {
		/**
		 * Return true to include the id in the result set.
		 * @param id The object id.
		 * @return true to include the id in the result set.
		 */
		boolean match(Serializable... id);
	}

	/**
	 * Scans all object identities in the repository and returns a filtered set
	 * of ids.
	 * 
	 * @param repository
	 *            The repository.
	 * @param filter
	 *            The filter to use for repository scanning.
	 * @return The set of filtered objects.
	 */
	@SuppressWarnings("unchecked")
	public static List<Serializable> fetch(Repository repository, Filter filter) {
		ObjectIdsQuery query = repository.getSystemObject(ObjectIdsQuery.class);
		query.filterWith(filter);
		return (List<Serializable>) repository.query(query).getResult();
	}

	protected abstract ObjectIdsQuery filterWith(Filter filter);
}
