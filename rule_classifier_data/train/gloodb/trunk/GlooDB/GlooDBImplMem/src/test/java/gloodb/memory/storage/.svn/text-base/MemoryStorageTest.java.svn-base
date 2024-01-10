/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.memory.storage;

import gloodb.storage.StorageTestBase;
import org.junit.Before;

/**
 * A storage test set, using the memory strategy.
 * 
 */
public class MemoryStorageTest extends StorageTestBase {
    /**
     * Inject the memory storage strategy.
     */
    @Before
    public void setUp() {
    	MemoryStorageFactory storageFactory = new MemoryStorageFactory();
        this.storage = storageFactory.buildStorage(new MemoryStorageConfiguration(nameSpace), null);
    }

    @Override
    public void storeAndRestore() {
        // memory storage does nothing
        storage.store(null, null);
        storage.restore(null, null);
    }

    @Override
    public void updateAndRestore() {
        // memory storage does nothing
        storage.store(null, null);
        storage.restore(null, null);
    }

    @Override
    public void remove() {
        // memory storage does nothing
        storage.remove(null);
    }

    @Override
    public void snapshots() {
        // memory storage does nothing
        storage.takeSnapshot(null);
        storage.restoreSnapshot(null);
    }

    @Override
    public void multipleSnapshots() {
        // memory storage does nothing
        storage.takeSnapshot(null);
        storage.restoreSnapshot(null);
        storage.takeSnapshot(null);
        storage.restoreSnapshot(null);
    }

    @Override
    public void randomRun() {
        // memory storage does nothing
    }
    
}