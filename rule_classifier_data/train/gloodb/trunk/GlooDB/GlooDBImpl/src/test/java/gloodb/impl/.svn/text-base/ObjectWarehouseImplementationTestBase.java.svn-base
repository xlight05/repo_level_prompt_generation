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

import java.io.Serializable;
import java.util.List;

import gloodb.Cloner;
import gloodb.SimpleValued;
import gloodb.storage.Storage;
import gloodb.storage.StorageProxy;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxManagerStrategy;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test set for object warehouse implementation.
 * 
 */
public class ObjectWarehouseImplementationTestBase {

    final ObjectWarehouse objectWarehouse;

    /**
     * Creates a test instance, agnostic from the storage and transaction management
     * strategies used.
     * @param storageStrategy The storage strategy
     * @param txStrategy The tx strategy
     */
    public ObjectWarehouseImplementationTestBase(StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
        this.objectWarehouse = ObjectWarehouseBuilder.build(storageStrategy, txStrategy);
    }

    /**
     * The cached objects can be flushed onto disk. Cache purges
     * are optional.
     */
    @Test
    public void testFlush() {
        String testId = "testId";
        
        try {
            this.objectWarehouse.store(new SimpleValued(testId), null, StoreOperation.Create);                    
            
            long startTime;
            long endTime;
            this.objectWarehouse.restore(testId, null);
            // After a restore, the object should be cached in the proxy.
            assertTrue(this.objectWarehouse.isObjectCached(testId));
    
            // Time a restore with a cached object.
            startTime = System.nanoTime();
            this.objectWarehouse.restore(testId, null);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Restore with cached object: %d microseconds", getClass().getName(), (endTime - startTime)/1000));
    
            // After a disk only flush to the disk the object should be cached.
            this.objectWarehouse.flush(false);
            assertTrue(this.objectWarehouse.isObjectCached(testId));
    
            assertFullFlush(testId);
    
            // Time a restore with no cached object.
            startTime = System.nanoTime();
            this.objectWarehouse.restore(testId, null);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Restore with NO cached object: %d microseconds", getClass().getName(), (endTime - startTime)/1000));
        } finally {
            this.objectWarehouse.remove(testId, null);
        }
    }

    void assertFullFlush(String testId) {
        // After a full flush to the disk the cache should be empty
        this.objectWarehouse.flush(true);
        assertFalse(this.objectWarehouse.isObjectCached(testId));
    }

    /**
     * A single object can be flushed onto disk. Its cache may or may not
     * be purged.
     */
    @Test
    public void testFlush_Serializable() {
        String testId = "testId";
        try {
            this.objectWarehouse.store(new SimpleValued(testId), null, StoreOperation.Create);
            assertTrue(this.objectWarehouse.isObjectCached(testId));
            
            assertFullFlush(testId);
        } finally {
            this.objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Test cache flush.
     */
    @Test
    public void testCacheFlush() {
        final int max = 10;
        String testId = "flushId";
        try {
            for(int i = 0; i < max; i++) {
                this.objectWarehouse.store(new SimpleValued(testId + i), null, StoreOperation.Create);
                assertTrue(this.objectWarehouse.isObjectCached(testId + i));
            }
            assertCacheFlush(max, testId);            
        } finally {
            for(int i = 0; i < max; i++) this.objectWarehouse.remove(testId + 1, null);
        }
    }
   
    
    void assertCacheFlush(int max, String testId) {
        // Flush 10% of the cache.
        flushCache(10);
        // The first 10% of the object have been flushed (they are the last recently used).
        for(int i = 0; i < max; i++) {
            assertEquals(i >= max / 10, this.objectWarehouse.isObjectCached(testId + i));
        }
        
        for(int i = max - 1; i >= 0; i--) {
            this.objectWarehouse.restore(testId + i, null);
            assertTrue(this.objectWarehouse.isObjectCached(testId + i));
        }
        // Flush 10% of the cache.
        flushCache(10);
        // The last 10% of the objects have been flushed (they are the last recently used).
        for(int i = 0; i < max; i++) {
            assertEquals(i < max * 9 / 10, this.objectWarehouse.isObjectCached(testId + i));
        }
    }

    /**
     * Objects are stored indirectly, via proxies. Proxies are equal if
     * the id of the proxied objects are equal. Proxies can be cloned.
     */
    @Test
    public void testStorageProxyEqualityAndCloning() {
        String testId = "testId";
        String testId2 = "testId2";
        
        try {
            this.objectWarehouse.store(new SimpleValued(testId), null, StoreOperation.Create);
            this.objectWarehouse.store(new SimpleValued(testId2), null, StoreOperation.Create);
                
            Storage storage = this.objectWarehouse.getStorage();
            StorageProxy s1 = storage.buildStorageProxy(storage.buildAddress());
            StorageProxy s2 = storage.buildStorageProxy(storage.buildAddress());
            s1.store(this.objectWarehouse.restore(testId, null));
            s2.store(this.objectWarehouse.restore(testId2, null));
            assertFalse(s1.equals(s2));
            assertEquals(s1, Cloner.deepCopy(s1));
        } finally {
            this.objectWarehouse.remove(testId, null);
            this.objectWarehouse.remove(testId2, null);
        }
    }
    
    void flushCache(int percentage) {
        List<Serializable> ids = this.objectWarehouse.getIds();
        int counter = ids.size() * percentage / 100;

        for (Serializable id : ids) {
            if(--counter < 0) return;
            this.objectWarehouse.flush(id, true);
        }
    }
    


}