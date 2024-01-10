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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gloodb.impl.ObjectWarehouseImplementationTestBase;
import gloodb.memory.storage.MemoryStorageStrategy;
import gloodb.memory.txmgr.MemoryTxManagerStrategy;

/**
 * A data warehousing test set for memory based storage and transaction management
 * strategies.
 */
public class MemoryBasedObjectWarehouseImplementationTest extends ObjectWarehouseImplementationTestBase {
    
    /**
     * Create a data warehousing test set for memory based storage and transaction management
     * strategies.
     */
    public MemoryBasedObjectWarehouseImplementationTest() {
        super(new MemoryStorageStrategy("target/UnitTests/Physical", null), new MemoryTxManagerStrategy("target/UnitTests/Physical", null));
    }
    
    @Override
    void assertFullFlush(String testId) {
        // After a full flush the cache is still populated (in-memory specific behaviour).
        this.objectWarehouse.flush(true);
        assertTrue(this.objectWarehouse.isObjectCached(testId));
       
    }
    
    @Override
    public void assertCacheFlush(int max, String testId) {
        // Flush will not evict any object from cache.
        // Flush 10% of the cache.
    	flushCache(10);
    		
        // The first 10% of the object have been flushed (they are the last recently used).
        for(int i = 0; i < max; i++) {
            assertEquals(true, this.objectWarehouse.isObjectCached(testId + i));
        }
        
        for(int i = max - 1; i >= 0; i--) {
            this.objectWarehouse.restore(testId + i, null);
            assertTrue(this.objectWarehouse.isObjectCached(testId + i));
        }
        // Flush 10% of the cache.
        flushCache(10);
    	// The last 10% of the objects have been flushed (they are the last recently used).
        for(int i = 0; i < max; i++) {
            assertEquals(true, this.objectWarehouse.isObjectCached(testId + i));
        }
    }
    
}
