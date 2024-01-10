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

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Transaction manager interface. The transaction manager overlooks transactions 
 * (i.e. state changes) on a single serializable managed object. 
 * The transaction manager maintains an initial snapshot of the managed object and 
 * a transaction log. It also ensures that write transactions are serialized while
 * read transactions can run concurrently.
 * 
 */
public interface TxManager {

    /**
     * Executes a read only transaction (i.e. query) on the managed object.
     * Read only transactions can run concurrently.
     * @param ctx The transaction context.
     * @param transaction The transaction to run.
     * @return The transaction result.
     */
    Serializable execute(TxContext ctx, TxLogReadTransaction transaction);

    /**
     * Executes a write transaction. Since this is a
     * write transaction, its access to the managed object is synchronized.
     * @param ctx The transaction context.
     * @param transaction The transaction to run.
     * @return The transaction's result.
     */
    Serializable execute(TxContext ctx, TxLogWriteTransaction transaction);

    /**
     * Returns the version of the managed object.
     * @return The version of the managed object.
     */
    BigInteger getManagedObjectVersion();

    /**
     * Returns the initial version of the managed object.
     * @return The initial version of the managed object.
     */
    BigInteger getManagedObjectStartVersion();
    
    /**
     * Starts a new transaction manager to manage the provided serializable object. 
     * A new transaction log is started around the managed object. It returns
     * the up to date state of managed object. The state is calculated from the lates log file,
     * by applying all recorded transactions to the initial state as this was saved
     * when the log was created.
     *
     * The correct usage of this method is:
     * <pre>
        managedObject = new ManagedObject();
        transactionManager = buildTransactionManager(...);
        ctx = buildTransactionContext(...);
        managedObject = transactionManager.start(ctx, managedObject);
     * </pre>
     * @param ctx The transaction context.
     * @param managedObject The managed object.
     * @param <T> The managed object type;
     * @return The up to date state of the managed object.
     */
    public <T extends Serializable> T start(TxContext ctx, T managedObject);

    /**
     * Starts a new transaction log.
     */
    void takeSnapshot();
}
