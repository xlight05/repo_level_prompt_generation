package gloodb.queries;

import java.io.Serializable;
import java.util.Map;

import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.queries.ComposableQuery;
import gloodb.queries.CompositeQuery;

@SuppressWarnings("serial")
public abstract class AbstractComposableReadOnlyQuery implements ComposableQuery, ReadOnlyQuery<Serializable> {

	@SuppressWarnings("unchecked")
	@Override
	public Serializable run(Repository repository, Serializable... parameters) throws Exception {
		run(repository, (CompositeQuery)parameters[0], (Map<Serializable, Serializable>)parameters[1], (Map<String, Serializable>)parameters[2]);
		return null;
	}

}
