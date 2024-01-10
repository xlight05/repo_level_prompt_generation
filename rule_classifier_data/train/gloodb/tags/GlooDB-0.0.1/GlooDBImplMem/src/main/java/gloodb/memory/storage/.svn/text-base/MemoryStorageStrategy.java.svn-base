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
package gloodb.memory.storage;

import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageInterceptor;
import gloodb.storage.StorageStrategy;

/**
 * This storage implementation strategy stores all object in memory.
 * 
 */
public class MemoryStorageStrategy implements StorageStrategy {
	private final StorageFactory factory = new MemoryStorageFactory();
	
	private final StorageConfiguration configuration;
	private final StorageInterceptor interceptor;
	
	
    public MemoryStorageStrategy(String nameSpace, StorageInterceptor interceptor) {
		super();
		this.configuration = new MemoryStorageConfiguration(nameSpace);
		this.interceptor = interceptor;
	}

	/**
     * Configuration getter. No configuration required. It always returns null.
     * @return Always null
     */
    public StorageConfiguration getConfiguration() {
        return configuration;
    }

	@Override
	public StorageFactory getFactory() {
		return factory;
	}

    @Override
    public StorageInterceptor getInterceptor() {
        return interceptor;
    }
}
