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

import java.math.BigInteger;

import gloodb.KeyViolationException;
import gloodb.SimpleVersionedSerializable;

import gloodb.PersistentNotFoundException;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxManagerStrategy;
import gloodb.impl.StoreOperation;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A generic test set for data warehousing, agnostic to the storage and
 * transaction management strategies.
 */
public class ObjectWarehouseTestBase {

    private ObjectWarehouse objectWarehouse;
    
    /**
     * Creates the test set for the given strategies.
     * @param storageStrategy The storage strategy
     * @param txStrategy The tx strategy
     */
    public ObjectWarehouseTestBase(StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
        this.objectWarehouse = ObjectWarehouseBuilder.build(storageStrategy, txStrategy);
    }

    /**
     * Contains returns true for objects in the warehouse, false otherwise.
     */
    @Test
    public void testContains() {
        String notThereId = "notThereId";
        String testId = "testId";
        
        try {
            objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.Create);
            
            assertFalse(this.objectWarehouse.contains(notThereId, null));
            assertTrue(this.objectWarehouse.contains(testId, null));
        } finally {
            objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Objects can be removed from the warehouse.
     */
    @Test
    public void testRemove() {
        String testId = "testId";
        int size = objectWarehouse.getIds().size();

        try {
            objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.Create);
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(size + 1, objectWarehouse.getIds().size());
            
            this.objectWarehouse.remove(testId, null);
            assertFalse(this.objectWarehouse.contains(testId, null));
            assertEquals(size, objectWarehouse.getIds().size());
        } finally {
            objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Objects can be restored / fetched from the warehouse.
     */
    @Test
    public void testRestore() {
        String testId = "testId";
        try {
            objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.Create);
            SimpleVersionedSerializable object = (SimpleVersionedSerializable) this.objectWarehouse.restore(testId, null);
            assertEquals(new SimpleVersionedSerializable(testId), object);
            assertEquals(BigInteger.ZERO, object.getVersion());
        } finally {
            objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Objects can be created or updated in the warehouse.
     */
    @Test
    public void testStoreCreateOrUpdate() {
        String testId = "testId";
        try {
            SimpleVersionedSerializable result = this.objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.CreateOrUpdate);        
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(BigInteger.ZERO, result.getVersion());
        
            result = this.objectWarehouse.store(result, null, StoreOperation.Update);        
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(BigInteger.ONE, result.getVersion());
        } finally {
            objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Objects can be created / added to the warehouse
     */
    @Test
    public void testStoreCreate() {
        String testId = "testId";
        try {
            SimpleVersionedSerializable result = this.objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.CreateOrUpdate);        
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(BigInteger.ZERO, result.getVersion());

            try {
                this.objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.Create);
                fail();
            } catch (KeyViolationException kve) {
                // Re-creating throws a KeyViolationException
            }
       } finally {
            objectWarehouse.remove(testId, null);
        }
    }

    /**
     * Objects can be updated in the warehouse.
     */
    @Test
    public void testStoreUpdate() {
        String testId = "testId";
        try {
            SimpleVersionedSerializable result = this.objectWarehouse.store(new SimpleVersionedSerializable(testId), null, StoreOperation.Create);        
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(BigInteger.ZERO, result.getVersion());
            
            result = this.objectWarehouse.store(result, null, StoreOperation.Update);        
            assertTrue(this.objectWarehouse.contains(testId, null));
            assertEquals(BigInteger.ONE, result.getVersion());
        } finally {
            objectWarehouse.remove(testId, null);
        }

        try {
            // Update fails if the object doesn't exist
            this.objectWarehouse.store(new SimpleVersionedSerializable("notThereId"), null, StoreOperation.Update);
            fail();
        } catch (PersistentNotFoundException pnfe) {
            // Update fails when the object doesn't exist in the repository
        }
    }
}