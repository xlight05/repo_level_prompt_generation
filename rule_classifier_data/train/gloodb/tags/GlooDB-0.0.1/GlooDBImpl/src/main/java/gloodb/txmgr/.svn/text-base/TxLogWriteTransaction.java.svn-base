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
import java.util.Date;

/**
 * Represents a write transaction. Write transactions have exclusive access to 
 * the repository. No other transactions can run concurrently. Write transactions 
 * must wait until all read transactions conclude before they proceed.
 */
public interface TxLogWriteTransaction extends Serializable {

    /**
     * Runs the transaction on the managed object and returns a result.
     * @param ctx The transaction context.
     * @param target The target managed object.
     * @param timestamp The timestamp of this transaction.
     * @return The transaction result.
     */
    Serializable execute(TxContext ctx, Serializable target, Date timestamp);
}
