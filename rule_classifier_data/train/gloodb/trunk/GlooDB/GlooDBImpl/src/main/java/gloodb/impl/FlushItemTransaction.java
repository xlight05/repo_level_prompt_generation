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

import gloodb.storage.StorageProxy;
import gloodb.txmgr.TxContext;
import gloodb.txmgr.TxLogWriteTransaction;
import java.io.Serializable;
import java.util.Date;

/**
 * Flushes a single item onto the storage (memory or disk). The related cache
 * may be purged if requested.
 */
class FlushItemTransaction implements TxLogWriteTransaction, Cloneable {
	private static final long serialVersionUID = -6344755634810608597L;

	private final Serializable identity;
    private final boolean removeFromCache;

    FlushItemTransaction(Serializable identity, boolean removeFromCache) {
        this.identity = identity;
        this.removeFromCache = removeFromCache;
    }

    public Serializable execute(TxContext context, Serializable managedObject, Date timestamp) {
        ObjectWarehouseTxContext ctx = (ObjectWarehouseTxContext) context;
        ObjectMap objectMap = (ObjectMap) managedObject;

        StorageProxy proxy = objectMap.getStorageProxy(identity);
        if (proxy != null) {
            proxy.flush(ctx.storage, removeFromCache);
        }
        return null;
    }

    @Override
    public Object clone() {
        return new FlushItemTransaction(this.identity, this.removeFromCache);
    }
}

