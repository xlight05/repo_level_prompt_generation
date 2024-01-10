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
import gloodb.storage.Storage;
import gloodb.storage.StorageProxy;
import gloodb.txmgr.TxContext;
import java.io.Serializable;
import java.util.Date;

/**
 * Undoes an object remove.
 */
class UndoRemoveTransaction implements UndoTransaction, Cloneable {
	private static final long serialVersionUID = -4002228300963740616L;

	private final Serializable id;
    private final Serializable object;

    public UndoRemoveTransaction(Serializable id, Serializable object) {
        this.id = id;
        this.object = Cloner.deepCopy(object);
    }

    public Serializable execute(TxContext context, Serializable target, Date timestamp) {
        ObjectMap objectMap = (ObjectMap) target;
        ObjectWarehouseTxContext ctx = (ObjectWarehouseTxContext) context;

        // Store the object (via its proxy) into the repository.
        Storage storage = ctx.storage;
        StorageProxy proxy = storage.buildStorageProxy(storage.buildAddress());
        proxy.store(object);
        objectMap.putStorageProxy(id, proxy);

        return null;
    }

    @Override
    public Object clone() {
        UndoRemoveTransaction copy = new UndoRemoveTransaction(
                id,
                Cloner.deepCopy(object));
        return copy;
    }
}
