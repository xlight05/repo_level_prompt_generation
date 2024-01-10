package gloodb.impl;

import gloodb.storage.Storage;
import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxLogConfiguration;
import gloodb.txmgr.TxLogFactory;
import gloodb.txmgr.TxManager;
import gloodb.txmgr.TxManagerFactory;
import gloodb.txmgr.TxManagerStrategy;

class ObjectWarehouseBuilder {
	private ObjectWarehouseBuilder() {
		// Private default constructor of factory class.
	}
    /**
     * Constructs a new warehouse based on the specified storage and tx management 
     * strategies.
     * @param storageFactory The storage factory.
     * @param storageStrategy The storage strategy.
     * @param managerFactory The tx log factory.
     * @param managerStrategy The tx log configuration.
     * @return The new warehouse.
     */
    public static ObjectWarehouse build(StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
    	StorageFactory storageFactory = storageStrategy.getFactory();
    	StorageConfiguration storageConfiguration = storageStrategy.getConfiguration();
        Storage storage = storageFactory.buildStorage(storageConfiguration, storageStrategy.getInterceptor());
        
        TxLogFactory logFactory = txStrategy.getFactory();
        TxLogConfiguration logConfiguration = txStrategy.getConfiguration();
        
        TxManagerFactory managerFactory = new TxManagerFactory();
        TxManager txManager = managerFactory.buildTxManager(logFactory, logConfiguration, txStrategy.getInterceptor());
        return new ObjectWarehouse(storage, txManager);
    }	
}
