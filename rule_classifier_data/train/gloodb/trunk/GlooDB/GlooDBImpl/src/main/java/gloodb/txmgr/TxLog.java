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
 * Represents a transaction log. The transaction log stores the initial state and
 * the sequence of transactions that change a managed - serializable - object. Upon startup,
 * the current state of the managed object is restored by applying the logged
 * transactions in the right sequence to the inital state of the managed object 
 * (the command pattern).
 * 
 */
public interface TxLog {

    /**
     * Returns the version of the transaction log. Each transaction increments this
     * version. Hence, the version is equal to the number of transactions that
     * changed the managed object.
     * @return The version of the transaction log.
     */
    BigInteger getVersion();
    
    /**
     * Returns the initial version of the managed object.
     * @return The initial version of the managed object.
     */
    BigInteger getStartVersion();    

    /**
     * Creates a new entry in the transaction log. The transaction log version
     * is incremented.
     * @param value The transaction log entry.
     */
    void log(Serializable value);

    /**
     * Starts a transaction log. The initial state of the managed object is restored from
     * the transaction log. Then the sequence of logged transactions are applied
     * to the managed object. In the end the current state of the managed object
     * is obtained.
     * @param ctx The transaction context.
     * @param startState The managed object.
     * @param <T> The managed object type.
     * @return The restored state of the managed object.
     *
     */
    <T extends Serializable> T start(TxContext ctx, T startState);

    /**
     * Closes the current transaction log and starts a new one. The current 
     * state of the managed object is the inital state of the new transaction log.
     * @param startState The startState of the new transaction log.
     */
    void takeSnapshot(Serializable startState);
}
