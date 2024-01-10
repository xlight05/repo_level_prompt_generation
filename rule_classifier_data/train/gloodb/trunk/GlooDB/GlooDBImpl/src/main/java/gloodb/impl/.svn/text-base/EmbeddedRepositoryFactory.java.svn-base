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
package gloodb.impl;

import java.util.Collection;

import gloodb.Repository;
import gloodb.RepositoryConfiguration;
import gloodb.RepositoryFactory;
import gloodb.RepositoryInitializer;
import gloodb.RepositoryInterceptor;

/**
 * Repository factory implementation which produces repositories directly
 * embeddable in the client application.
 */
public class EmbeddedRepositoryFactory implements RepositoryFactory {
	private RepositoryInitializer initializer = null;
	
	/**
	 * Builds and intercepts a repository embeddable in the client application.
	 * 
	 * @param configuration
	 *            The embedded repository configuration.
	 * @param interceptors
	 * 			  The repository interceptors.
	 */
	@Override
	public Repository buildRepository(RepositoryConfiguration configuration, Collection<RepositoryInterceptor> interceptors) {
		return interceptRepository(buildRepository(configuration), interceptors);
	}

	/**
	 * Builds a repository embeddable in the client application.
	 * 
	 * @param configuration
	 *            The embedded repository configuration.
	 */
	@Override
	public Repository buildRepository(RepositoryConfiguration configuration) {
		EmbeddedRepositoryConfiguration embeddedConfiguration = (EmbeddedRepositoryConfiguration) configuration;
		RepositoryImplementation implementation = RepositoryImplementation.getInstance(
		        embeddedConfiguration.getRepositoryStrategy(),
		        embeddedConfiguration.getStorageStrategy(), 
		        embeddedConfiguration.getTxStrategy());
		Repository repository = new RepositoryFacade(null, implementation);
		if(this.initializer != null) this.initializer.initialize(repository);
		return repository;
	}

	/**
	 * Intercepts a repository embeddable in the client application.
	 * 
	 * @param Interceptors
	 * 			  The repository interceptors.
	 * @return The intercepted repository.
	 */
	@Override
	public Repository interceptRepository(Repository repository, Collection<RepositoryInterceptor> interceptors) {
    	Repository intercepted = repository;
		for(RepositoryInterceptor interceptor: interceptors) {
			intercepted = interceptor.setRepository(repository);
		}
		return intercepted;
    }

	/*
	 * (non-Javadoc)
	 * @see gloodb.RepositoryFactory#setInitializer(gloodb.RepositoryInitializer)
	 */
	@Override
	public void setInitializer(RepositoryInitializer initializer) {
		this.initializer = initializer;
	}
}
