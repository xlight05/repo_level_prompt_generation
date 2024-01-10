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

import gloodb.txmgr.TxContext;

import java.io.Serializable;
import java.util.Date;

/**
 * Undoes an object create.
 */
class UndoNewTransaction implements UndoTransaction, Cloneable {
	private static final long serialVersionUID = -5058749525468715325L;

	private final Serializable id;
    
    public UndoNewTransaction(Serializable id) {
        this.id = id;
    }

    public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        ObjectMap objectMap = (ObjectMap)target;
        objectMap.removeStorageProxy(this.id);
        return null;
    }
    
    @Override
    public Object clone() {
    	return new UndoNewTransaction(id);
    }
}
