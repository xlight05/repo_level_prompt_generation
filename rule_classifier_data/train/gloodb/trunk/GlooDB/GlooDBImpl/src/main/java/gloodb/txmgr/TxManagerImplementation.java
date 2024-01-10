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
package gloodb.txmgr;

import gloodb.GlooException;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Transaction manager class. The tx manager guards access to the transaction log
 * with a read / write reentrant lock.
 */
class TxManagerImplementation implements TxManager {

    private final SafeReentrantReadWriteLock lock;
    private final TxLog transactionLog;
    private Serializable managedObject;

    /**
     * Creates the transaction manager.
     * @param txLog The transaction log to manage.
     */
    public TxManagerImplementation(TxLog txLog) {
        this.lock = new SafeReentrantReadWriteLock();
        this.transactionLog = txLog;
    }

    /**
     * Executes a read only transaction.
     * @param ctx The transaction context.
     * @param transaction The read only transaction.
     * @return The transaction result.
     */
    public Serializable execute(TxContext ctx, TxLogReadTransaction transaction) {
        try {
            lock.readLock();
            return transaction.execute(ctx, this.managedObject);
        } finally {
            lock.readUnlock();
        }
    }

    /**
     * Executes a write transaction.
     * @param ctx The transaction context.
     * @param transaction The write transaction.
     * @return The transaction result.
     */
    public Serializable execute(TxContext ctx, TxLogWriteTransaction transaction) {
        try {
            lock.writeLock();
            this.transactionLog.log(transaction);
            return runTransaction(ctx, transaction);
        } finally {
        	lock.writeUnlock();
        }
    }

    /**
     * Returns the managed object's version.
     * @return The managed object's version.
     */
    public BigInteger getManagedObjectVersion() {
        return this.transactionLog.getVersion();
    }
    
    /**
     * Returns the initial managed object's version.
     * @return The managed object's version.
     */
    public BigInteger getManagedObjectStartVersion() {
        return this.transactionLog.getStartVersion();
    }    

    private Serializable runTransaction(TxContext ctx, TxLogWriteTransaction transaction) {
        try {
            return transaction.execute(ctx, this.managedObject, new Date(System.currentTimeMillis()));
        } catch (GlooException ge) {
            throw ge;
        } catch (Exception e) {
            throw new GlooException(e);
        }
    }

    /**
     * Starts the transaction manager. It loads the most recent transaction log
     * and intializes the managed object with the final state.
     * @param <T> The managed object type.
     * @param ctx The transaction context.
     * @param managedObjectInitialState The initial state of the managed object
     * @return The initialized managed object.
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T start(TxContext ctx, T managedObjectInitialState) {
        this.managedObject = this.transactionLog.start(ctx, managedObjectInitialState);
        return (T) this.managedObject;
    }

    /**
     * Takes a snapshot of the transaction log.
     */
    public void takeSnapshot() {
        try {
        	lock.writeLock();
            this.transactionLog.takeSnapshot(this.managedObject);
        } finally {
        	lock.writeUnlock();
        }
    }
}
