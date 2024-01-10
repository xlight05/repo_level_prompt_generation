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
 * Atomic operation which implements the repository.commit operation.
 */
class CommitTransaction implements TxLogWriteTransaction, Cloneable {
	private static final long serialVersionUID = 3550504835978552717L;
	private final Transaction tx;

    public CommitTransaction(Transaction tx) {
        this.tx = tx;
    }

    public Serializable execute(TxContext ctx, Serializable managedObject, Date timestamp) {
        ObjectMap objectMap = (ObjectMap) managedObject;
        objectMap.commit((ObjectWarehouseTxContext) ctx, tx);
        return null;
    }

    @Override
    public Object clone() {
        Transaction txCopy = Cloner.deepCopy(tx);
        CommitTransaction copy = new CommitTransaction(txCopy);
        return copy;
    }
}
