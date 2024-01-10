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
package gloodb.file;

import java.util.Collection;

import gloodb.Repository;
import gloodb.RepositoryConfiguration;
import gloodb.RepositoryInitializer;
import gloodb.RepositoryInterceptor;
import gloodb.RepositoryStrategy;
import gloodb.file.storage.block.BlockStorageConfiguration;
import gloodb.file.storage.block.BlockStorageStrategy;
import gloodb.file.storage.intransaction.InTransactionStorageStrategy;
import gloodb.file.txmgr.DefaultFileTxLogConfiguration;
import gloodb.file.txmgr.FileTxManagerStrategy;
import gloodb.impl.EmbeddedRepositoryConfiguration;
import gloodb.impl.EmbeddedRepositoryFactory;
import gloodb.impl.ObjectDBRepositoryStrategy;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxManagerStrategy;

import javax.crypto.NullCipher;

/**
 * Repository factory utility class for file based repository implementations.
 *
 */
public class FileBasedRepositoryFactory {

	private EmbeddedRepositoryFactory factory;

	/**
	 * Constructs a file based repository factory.
	 */
	public FileBasedRepositoryFactory() {
        this.factory = new EmbeddedRepositoryFactory();
	}
	
    /**
     * Creates an embedded repository using an non-encrypted file based transaction log and object storage.
     * @param nameSpace The name space.
     * @param interceptor The interceptors.
     * @return The repository.
     */
    public Repository buildRepository(String nameSpace) {
        RepositoryStrategy repositoryStrategy = new ObjectDBRepositoryStrategy(); 
        StorageStrategy storageStrategy = new BlockStorageStrategy(new BlockStorageConfiguration(nameSpace, new NullCipher(), new NullCipher()), null);
        TxManagerStrategy txMgrStrategy = new FileTxManagerStrategy(new DefaultFileTxLogConfiguration(nameSpace, new NullCipher(), new NullCipher()), null); 
        
        RepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(repositoryStrategy, storageStrategy, txMgrStrategy); 
        return factory.buildRepository(configuration);
    }

    /**
     * Builds and intercepts a file based repository.
     * @param nameSpace The namespace.
     * @param interceptors The interceptors.
     * @return The intercepted repository.
     */
    public Repository buildRepository(String nameSpace, Collection<RepositoryInterceptor> interceptors) {
    	return interceptRepository(buildRepository(nameSpace), interceptors);
    }
    
    /**
     * Creates an embedded repository using an non-encrypted file based transaction log and objects stored in the transaction log directly.
     * @param nameSpace The name space.
     * @param interceptor The interceptors.
     * @return The repository.
     */
    public Repository buildNoStorageRepository(String nameSpace) {
        RepositoryStrategy repositoryStrategy = new ObjectDBRepositoryStrategy(); 
        StorageStrategy storageStrategy = new InTransactionStorageStrategy(nameSpace, null);
        TxManagerStrategy txMgrStrategy = new FileTxManagerStrategy(new DefaultFileTxLogConfiguration(nameSpace, new NullCipher(), new NullCipher()), null); 
        
        RepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(repositoryStrategy, storageStrategy, txMgrStrategy); 
        return factory.buildRepository(configuration);
    }    

    /**
     * Builds and intercepts a no-storage / file based repository.
     * @param nameSpace The namespace.
     * @param interceptors The interceptors.
     * @return The intercepted repository.
     */
    public Repository buildNoStorageRepository(String nameSpace, Collection<RepositoryInterceptor> interceptors) {
    	return interceptRepository(buildNoStorageRepository(nameSpace), interceptors);
    }
    
    /**
     * Intercepts a file based repository.
     * @param repository The repository to intercept.
     * @param interceptor The repository interceptors if any.
     * @return The repository.
     */
    public Repository interceptRepository(Repository repository, Collection<RepositoryInterceptor> interceptors) {
    	return factory.interceptRepository(repository, interceptors);
    }
    
    /**
     * Sets the initializer.
     * @param initializer The repository initializer.
     */
    public void setInitializer(RepositoryInitializer initializer) {
    	this.factory.setInitializer(initializer);
    }
}
