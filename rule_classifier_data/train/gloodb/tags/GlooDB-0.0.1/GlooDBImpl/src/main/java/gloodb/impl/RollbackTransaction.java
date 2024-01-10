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

import gloodb.Cloner;
import gloodb.txmgr.TxContext;
import gloodb.txmgr.TxLogWriteTransaction;

import java.io.Serializable;
import java.util.Date;

/**
 * Atomic rollback operation.
 */
class RollbackTransaction implements TxLogWriteTransaction, Cloneable {

	private static final long serialVersionUID = 7323902457382737472L;
	private final Transaction tx;
    public RollbackTransaction(Transaction tx) {
        this.tx = tx;
    }

    public Serializable execute(TxContext ctx, Serializable managedObject, Date timestamp) {
        ObjectMap objectMap = (ObjectMap) managedObject;
        objectMap.rollback((ObjectWarehouseTxContext) ctx, tx);
        return null;
    }


    @Override
    public Object clone() {
        Transaction txCopy = Cloner.deepCopy(tx);
        RollbackTransaction copy = new RollbackTransaction(txCopy);
        return copy;
    }
}
