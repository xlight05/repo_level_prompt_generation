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
package gloodb.memory;

import java.util.Collection;

import gloodb.Repository;
import gloodb.RepositoryInitializer;
import gloodb.RepositoryInterceptor;
import gloodb.impl.EmbeddedRepositoryConfiguration;
import gloodb.impl.EmbeddedRepositoryFactory;
import gloodb.impl.ObjectDBRepositoryStrategy;
import gloodb.memory.storage.MemoryStorageStrategy;
import gloodb.memory.txmgr.MemoryTxManagerStrategy;
import gloodb.storage.StorageInterceptor;
import gloodb.txmgr.TxManagerInterceptor;

/**
* Repository factory utility class for memory based repository implementations.
  *
 */
public class MemoryBasedRepositoryFactory {
	
	private EmbeddedRepositoryFactory factory;

	/**
	 * Constructs a memory based repository factory.
	 */
	public MemoryBasedRepositoryFactory() {
        this.factory = new EmbeddedRepositoryFactory();
	}
	

    /**
     * Creates and intercepts in memory repository.
     * @param nameSpace The name space
     * @param interceptors The repository interceptors.
     * @return The repository.
     */
    public Repository buildRepository(String nameSpace, Collection<RepositoryInterceptor> interceptors) {
        return interceptRepository(buildRepository(nameSpace), interceptors);
    }
    
    /**
     * Creates an in memory repository.
     * @param nameSpace The name space
     * @return The repository.
     */
    public Repository buildRepository(String nameSpace) {
        EmbeddedRepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(
                new ObjectDBRepositoryStrategy(),
                new MemoryStorageStrategy(nameSpace, new StorageInterceptor(){}),
                new MemoryTxManagerStrategy(nameSpace, new TxManagerInterceptor(){}));
        
        return this.factory.buildRepository(configuration);
    }
    
    /**
     * Intercepts an in memory repository.
     * @param repository The repository to intercept.
     * @param interceptor The repository interceptors if any.
     * @return The repository.
     */
    public Repository interceptRepository(Repository repository, Collection<RepositoryInterceptor> interceptors) {
    	return this.factory.interceptRepository(repository, interceptors);
    }
    
    /**
     * Sets the initializer.
     * @param initializer The repository initializer.
     */
    public void setInitializer(RepositoryInitializer initializer) {
    	this.factory.setInitializer(initializer);
    }
}
