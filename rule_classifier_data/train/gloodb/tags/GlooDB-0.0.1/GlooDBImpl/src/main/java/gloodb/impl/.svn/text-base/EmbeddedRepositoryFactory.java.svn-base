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

import gloodb.Repository;
import gloodb.RepositoryConfiguration;
import gloodb.RepositoryFactory;
import gloodb.RepositoryInterceptor;

/**
 * Repository factory implementation which produces repositories directly
 * embeddable in the client application.
 */
public class EmbeddedRepositoryFactory implements RepositoryFactory {

	/**
	 * Builds a repository embeddable in the client application.
	 * 
	 * @param configuration
	 *            The embedded repository configuration.
	 */
	@Override
	public Repository buildRepository(RepositoryConfiguration configuration, RepositoryInterceptor interceptor) {
		EmbeddedRepositoryConfiguration embeddedConfiguration = (EmbeddedRepositoryConfiguration) configuration;
		RepositoryImplementation implementation = new RepositoryImplementation(
		        embeddedConfiguration.getRepositoryStrategy(),
		        embeddedConfiguration.getStorageStrategy(), 
		        embeddedConfiguration.getTxStrategy());
		Repository repository = new RepositoryFacade(null, implementation);
		return interceptor != null? interceptor.setRepository(repository): repository;
	}
}
