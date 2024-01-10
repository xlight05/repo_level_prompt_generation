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
import java.io.Serializable;
import java.util.Date;

/**
 * Undoes an update.
 */
class UndoUpdateTransaction implements UndoTransaction, Cloneable {
	private static final long serialVersionUID = -8443507006475171002L;

	private final Serializable id;
    private final Serializable oldValue;

    public UndoUpdateTransaction(Serializable id, Serializable oldValue) {
        this.id = id;
        this.oldValue = oldValue;
    }

    public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        ObjectMap objectMap = (ObjectMap) target;

        // Store the old object value (via its proxy) into the repository.
        StorageProxy proxy = objectMap.getStorageProxy(id);
        proxy.store(oldValue);
        return null;
    }

    @Override
    public Object clone() {
        UndoUpdateTransaction copy = new UndoUpdateTransaction(
                Cloner.deepCopy(this.id),
                Cloner.deepCopy(this.oldValue));
        return copy;
    }
}
