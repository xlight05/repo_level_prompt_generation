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

import gloodb.Repository;
import gloodb.RepositoryConfiguration;
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
    /**
     * Creates an embedded repository using an non-encrypted file based transaction log and object storage.
     * @param nameSpace The name space.
     * @param interceptor The interceptor if any.
     * @return The repository.
     */
    public static Repository buildRepository(String nameSpace, RepositoryInterceptor interceptor) {
        EmbeddedRepositoryFactory factory = new EmbeddedRepositoryFactory();
        
        RepositoryStrategy repositoryStrategy = new ObjectDBRepositoryStrategy(); 
        StorageStrategy storageStrategy = new BlockStorageStrategy(new BlockStorageConfiguration(nameSpace, new NullCipher(), new NullCipher()), null);
        TxManagerStrategy txMgrStrategy = new FileTxManagerStrategy(new DefaultFileTxLogConfiguration(nameSpace, new NullCipher(), new NullCipher()), null); 
        
        RepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(repositoryStrategy, storageStrategy, txMgrStrategy); 
        return factory.buildRepository(configuration, interceptor);
    }
    
    /**
     * Creates an embedded repository using an non-encrypted file based transaction log and objects stored in the transaction log directly.
     * @param nameSpace The name space.
     * @param interceptor The interceptor if any.
     * @return The repository.
     */
    public static Repository buildNoStorageRepository(String nameSpace, RepositoryInterceptor interceptor) {
        EmbeddedRepositoryFactory factory = new EmbeddedRepositoryFactory();
        
        RepositoryStrategy repositoryStrategy = new ObjectDBRepositoryStrategy(); 
        StorageStrategy storageStrategy = new InTransactionStorageStrategy(nameSpace, null);
        TxManagerStrategy txMgrStrategy = new FileTxManagerStrategy(new DefaultFileTxLogConfiguration(nameSpace, new NullCipher(), new NullCipher()), null); 
        
        RepositoryConfiguration configuration = new EmbeddedRepositoryConfiguration(repositoryStrategy, storageStrategy, txMgrStrategy); 
        return factory.buildRepository(configuration, interceptor);
    }    
}
