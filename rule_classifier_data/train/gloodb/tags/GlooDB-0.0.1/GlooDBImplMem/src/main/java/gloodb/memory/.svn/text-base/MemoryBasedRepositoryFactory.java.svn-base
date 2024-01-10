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

import gloodb.Repository;
import gloodb.RepositoryFactory;
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
    /**
     * Creates an in memory repository.
     * @param nameSpace The name space
     * @param interceptor The repository interceptor if any.
     * @return The repository.
     */
    public static Repository buildRepository(String nameSpace, RepositoryInterceptor interceptor) {
        RepositoryFactory factory = new EmbeddedRepositoryFactory();
        // For testing purposes inteceptor the storage and transaction managers.
        EmbeddedRepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(
                new ObjectDBRepositoryStrategy(),
                new MemoryStorageStrategy(nameSpace, new StorageInterceptor(){}),
                new MemoryTxManagerStrategy(nameSpace, new TxManagerInterceptor(){}));
        return factory.buildRepository(configuration, interceptor);
    }
}
