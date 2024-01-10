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

import gloodb.txmgr.TxLogReadTransaction;
import gloodb.txmgr.TxContext;
import java.io.Serializable;

/**
 * Restores an object from the repository.
 */
class RestoreTransaction implements TxLogReadTransaction {

    private final Serializable identity;
    private final Transaction tx;

    public RestoreTransaction(Serializable identity, Transaction tx) {
        this.identity = identity;
        this.tx = tx;
    }

    public Serializable execute(TxContext context, Serializable managedObject) {
        ObjectWarehouseTxContext ctx = (ObjectWarehouseTxContext) context;
        ObjectMap objectMap = (ObjectMap)managedObject;
        return objectMap.restore(ctx, tx, identity);
    }
}


