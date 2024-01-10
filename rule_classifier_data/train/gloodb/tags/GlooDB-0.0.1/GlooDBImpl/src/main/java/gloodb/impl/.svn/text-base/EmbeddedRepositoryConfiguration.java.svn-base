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

import gloodb.RepositoryConfiguration;
import gloodb.RepositoryStrategy;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxManagerStrategy;

/**
 * Embedded repository configuration class.
 */
public class EmbeddedRepositoryConfiguration implements RepositoryConfiguration {

	private final StorageStrategy storageStrategy;
	
	private final TxManagerStrategy txStrategy;

    private final RepositoryStrategy repositoryStrategy;

	/**
	 * Creates an embedded repository configuration
	 * @param storageStrategy The storage strategy
	 * @param txStrategy The transaction strategy
	 */
	public EmbeddedRepositoryConfiguration(RepositoryStrategy repositoryStrategy,
	        StorageStrategy storageStrategy, 
			TxManagerStrategy txStrategy) {
		this.storageStrategy = storageStrategy;
		this.txStrategy = txStrategy;
		this.repositoryStrategy = repositoryStrategy;
	}

	/**
	 * Storage strategy getter
	 * @return The storage strategy.
	 */
	public StorageStrategy getStorageStrategy() {
		return storageStrategy;
	}

	/**
	 * TxManager strategy getter
	 * @return The tx manager strategy
	 */
	public TxManagerStrategy getTxStrategy() {
		return txStrategy;
	}

	/**
	 * Returns the repository strategy.
	 */
    public RepositoryStrategy getRepositoryStrategy() {
        return repositoryStrategy;
    }
}
