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
import gloodb.txmgr.TxLogReadTransaction;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Returns the set of ids of objects in the datawarehouse.
 */
class GetKeysTransaction implements TxLogReadTransaction {

    public Serializable execute(TxContext ctx, Serializable managedObject) {
        ObjectMap objectMap = (ObjectMap) managedObject;
        return new LinkedList<Serializable>(objectMap.getIds());
    }
}
