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
package gloodb.file.storage.block;

import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageInterceptor;
import gloodb.storage.StorageStrategy;

/**
 * Based on this storage strategy, objects are serialized into a single file as
 * a sequence of fixed length blocks.
 * 
 */
public class BlockStorageStrategy implements StorageStrategy {
    private final StorageConfiguration configuration;
    private final StorageFactory factory;
    private final StorageInterceptor interceptor;
    
    /**
     * Constructs a block storage based on the configuration provided.
     * @param configuration The block storage configuration.
     */
    public BlockStorageStrategy(BlockStorageConfiguration configuration, StorageInterceptor interceptor) {
        this.configuration = configuration;
        this.factory = new BlockStorageFactory();
        this.interceptor = interceptor;
    }
    
    /**
     * Configuration getter.
     * @return The block storage configuration.
     */
    @Override
    public StorageConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Storage factory getter.
     */
	@Override
	public StorageFactory getFactory() {
		return factory;
	}

    @Override
    public StorageInterceptor getInterceptor() {
        return interceptor;
    }
}
