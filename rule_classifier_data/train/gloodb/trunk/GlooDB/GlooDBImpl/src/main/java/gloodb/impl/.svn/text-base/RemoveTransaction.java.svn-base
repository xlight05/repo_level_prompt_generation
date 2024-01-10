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
import gloodb.storage.StorageProxy;
import gloodb.txmgr.TxContext;
import gloodb.txmgr.TxLogWriteTransaction;
import java.io.Serializable;
import java.util.Date;

/**
 * Removes a single object from the repository.
 */
class RemoveTransaction implements TxLogWriteTransaction, Cloneable {
	private static final long serialVersionUID = 8810077128733078619L;

	private final Serializable identity;
    private final Transaction tx;

    RemoveTransaction(Serializable identity, Transaction tx) {
        this.tx = tx;
        this.identity = identity;
    }

    public Serializable execute(TxContext context, Serializable managedObject, Date timestamp) {
        ObjectWarehouseTxContext ctx = (ObjectWarehouseTxContext) context;
        ObjectMap objectMap = (ObjectMap) managedObject;

        Serializable victim = null;
        StorageProxy proxy = objectMap.getStorageProxy(this.identity);
        if (proxy != null) {
            victim = proxy.restore(ctx.storage, Serializable.class);
            objectMap.removeStorageProxy(this.identity);
            // Remove the object via its proxy.
            if (this.tx != null) {
                objectMap.lock(this.tx, proxy.getId());
                objectMap.addRollbackTx(this.tx, new UndoRemoveTransaction(proxy.getId(), victim));

                // backup the old object value. The object backup is used
            	// during the restore operation:
            	// - restoring from this transaction or its children returns
            	// the actual object
            	// - restoring from another transaction return the backup object.
                objectMap.backupObject(this.identity, victim);                  
            }
            proxy.remove(ctx.storage);
        }
        return victim;
    }

    @Override
    public Object clone() {
        Transaction txCopy = Cloner.deepCopy(tx);
        RemoveTransaction copy = new RemoveTransaction(Cloner.deepCopy(this.identity), txCopy);
        return copy;
    }
}
