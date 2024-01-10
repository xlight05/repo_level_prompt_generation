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

import gloodb.impl.ObjectWarehouseTestBase;
import gloodb.memory.storage.MemoryStorageStrategy;
import gloodb.memory.txmgr.MemoryTxManagerStrategy;

/**
 * A generic test set for warehousing, using memory based storage
 * and transaction management.
 */
public class MemoryBasedObjectWarehouseTest extends ObjectWarehouseTestBase {

    /**
     * Creates a generic test set for warehousing, using memory based storage
     * and transaction management.
     */
    public MemoryBasedObjectWarehouseTest() {
        super(new MemoryStorageStrategy("target/UnitTests/Physical", null), new MemoryTxManagerStrategy("target/UnitTests/Physical", null));
    }
}
