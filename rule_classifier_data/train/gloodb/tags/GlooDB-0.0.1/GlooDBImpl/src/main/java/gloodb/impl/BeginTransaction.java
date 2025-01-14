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
 * Atomic operation which implements the repository.begin operation.
 */
class BeginTransaction implements TxLogWriteTransaction, Cloneable {
	private static final long serialVersionUID = -3863447979227863940L;
	
	private final Transaction parentTx;

    public BeginTransaction(Transaction parentTx) {
        this.parentTx = parentTx;
    }

    public Serializable execute(TxContext context, Serializable managedObject, Date timestamp) {
        ObjectMap objectMap = (ObjectMap) managedObject;
        return objectMap.begin((ObjectWarehouseTxContext) context, parentTx);
    }

    @Override
    public Object clone() {
        Transaction parentTxCopy = Cloner.deepCopy(parentTx);
        BeginTransaction copy = new BeginTransaction(parentTxCopy);
        return copy;
    }
}
