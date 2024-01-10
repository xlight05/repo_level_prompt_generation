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
package gloodb.impl;

import gloodb.storage.Storage;
import gloodb.txmgr.TxManager;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * The warehouse implementation aggregates:<ul>
 * <li>a PhysicalObjectWarehouse: implements the mapping between object id and StorageProxy
 * <li>a Storage instance: implements the object storage mechanism.
 * <li>a TxManager instance: implements the transaction logging and synchronization
 * mechanism.
 * </ul>
 * <p>A context - containing specifc object warehouse information - is created and
 * maintained. This context is passed to all object warehouse operations (begin,
 * commit, end, store, restore, etc) since these may use this context to access
 * objects in the storage and such.
 */
class ObjectWarehouse {

    private ObjectMap objectMap;
    private final Storage storage;
    private final TxManager transactionManager;
    private final ObjectWarehouseTxContext ctx;

    /**
     * Creates a object warehouse using the storage and transaction manager provided.
     *
     * @param storage The storage.
     * @param transactionManager The transaction manager.
     */
    public ObjectWarehouse(Storage storage, TxManager transactionManager) {
        this.objectMap = new ObjectMap();
        this.storage = storage;
        this.transactionManager = transactionManager;
        this.ctx = new ObjectWarehouseTxContext(storage);
        
        // Start the storage first. The storage must be restored to the 
        // correct version before the transaction log is applied.
        BigInteger startVersion = this.transactionManager.getManagedObjectStartVersion();
        this.storage.start(startVersion);
        
        // Upon startup the transaction manager may overwrite the object map 
        // with the object map which has been previously persisted. 
        this.objectMap = transactionManager.start(this.ctx, this.objectMap);
        this.objectMap.rollbackPendingTransactions(this.ctx);
        
        // The storage and transaction manager have been started. 
        // Take a snapshot at this time. This is required as a save point for 
        // next time the repository starts.
        this.takeSnapshot();
    }

    /**
     * Begins a new transaction.
     * @return The new transaction.
     */
    public Transaction begin(Transaction parentTx) {
        return (Transaction) this.transactionManager.execute(this.ctx, new BeginTransaction(parentTx));
    }

    /**
     * Commits the transaction provided.
     * @param tx The transaction.
     */
    public void commit(Transaction tx) {
        this.transactionManager.execute(this.ctx, new CommitTransaction(tx));
    }

    /**
     * Checks if an object with the given id exists in the repository.
     * @param identity The object id.
     * @param tx The transaction.
     * @return True if the object exists in the repository.
     */
    public boolean contains(Serializable identity, Transaction tx) {
        return (Boolean)this.transactionManager.execute(this.ctx, new ContainsTransaction(identity, tx));
    }

    /**
     * Flushes the repository onto the storage (disk or memory). If required,
     * it will also remove object from cache.
     * @param removeFromCache True to flush the cache.
     */
    @SuppressWarnings("unchecked")
    public void flush(boolean removeFromCache) {
        // This operation may take a long time. Implement it as a
        // fined grained transaction rather than a single long one.
        // This will allow repository flushing while other operations
        // are ongoing; otherwise everything will get queued up on
        // waiting on access locks.

        // Get all ids existent in the repository.
        // This needs to be executed as a transaction to serialize
        // concurrent acces to the repository.
        List<Serializable> ids = (List<Serializable>) this.transactionManager.execute(this.ctx, new GetKeysTransaction());
        for (Serializable id : ids) {
            flush(id, removeFromCache);
        }
    }

    /**
     * Flushes a single persistent object onto the storage. If required it
     * flushes it from the cache too.
     * @param id The object id.
     * @param removeFromCache True to flush from cache.
     */
    public void flush(Serializable id, boolean removeFromCache) {
        this.transactionManager.execute(this.ctx, new FlushItemTransaction(id, removeFromCache));
    }
    
    /**
     * Flushes the specified percentage of the cache.
     * @param percentage The percentage to flush.
     * @return 
     */
    @SuppressWarnings("unchecked")
    public void flushCache(int percentage) {
        // This operation may take a long time. Implement it as a
        // fined grained transaction rather than a single long one.
        // This will allow repository flushing while other operations
        // are ongoing; otherwise everything will get queued up on
        // waiting on access locks.

        // Get all ids existent in the repository.
        // This needs to be executed as a transaction to serialize
        // concurrent acces to the repository.
        List<Serializable> ids = (List<Serializable>) this.transactionManager.execute(this.ctx, new GetKeysTransaction());
        int counter = ids.size() * percentage / 100;
        for (Serializable id : ids) {
            if(--counter < 0) return;
            flush(id, true);
        }
    }
    

    /**
     * Returns the class of the object with the given id.
     * @param identity The object identity.
     * @return The class of the object with the give id.
     */
    public Class<? extends Serializable> getClassForId(Serializable identity) {
        return this.objectMap.getClassForId(identity);
    }

    /**
     * Returns the set of ids for all objects in the repository.
     * @return The id set.
     */
    public List<Serializable> getIds() {
        return this.objectMap.getIds();
    }

    /**
     * Returns the storage space.
     * @return
     */
    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Checks if an object is cached or not.
     * @param id The object id.
     * @return True if the object is cached.
     */
    boolean isObjectCached(Serializable id) {
        return this.objectMap.isObjectCached(id);
    }

    /**
     * It removes an object from the repository.
     * @param identity The object id.
     * @param tx The transaction.
     * @return The removed object.
     */
    public Serializable remove(Serializable identity, Transaction tx) {
        return this.transactionManager.execute(this.ctx, new RemoveTransaction(identity, tx));
    }

    /**
     * Restores an object. Each restore operation returns a new copy of the repository object.
     * @param identity The object id.
     * @return The restored object.
     */
    public Serializable restore(Serializable identity, Transaction tx) {
        return this.transactionManager.execute(this.ctx, new RestoreTransaction(identity, tx));
    }

    /**
     * Rollbacks the transaction.
     * @param tx The transaction to rollback.
     */
    public void rollback(Transaction tx) {
        this.transactionManager.execute(this.ctx, new RollbackTransaction(tx));
    }

    /**
     * Creates or updates the persistent object.
     * @param <T> The object type.
     * @param persistentObject The persistent object.
     * @param tx The transaction.
     * @param command The store operation: can be Create, Update or CreateOrUpdate.
     * @return The updated object.
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T store(T persistentObject, Transaction tx, StoreOperation command) {
       return (T)this.transactionManager.execute(this.ctx, new StoreTransaction(persistentObject, tx, command));
    }

    /**
     * Takes a repository snapshot.
     */
    public synchronized void takeSnapshot() {
        this.storage.takeSnapshot(this.transactionManager.getManagedObjectVersion());
        this.transactionManager.takeSnapshot();
    }
}
