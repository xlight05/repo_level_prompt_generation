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
package gloodb.impl;

import java.io.Serializable;
import java.math.BigInteger;

import gloodb.LockingException;
import gloodb.storage.Address;
import gloodb.storage.Storage;
import gloodb.storage.StorageFullException;
import gloodb.storage.StorageProxy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ObjectMapTest {
    private ObjectMap objectMap;
    private ObjectWarehouseTxContext ctx;

    @Before
    public void setup() {
        objectMap = new ObjectMap();
        ctx = new ObjectWarehouseTxContext(new Storage(){

            @Override
            public Address buildAddress() {
                return null;
            }

            @Override
            public StorageProxy buildStorageProxy(Address address) {
                return new StorageProxy() {
                    private static final long serialVersionUID = 3508190832256159855L;

                    @Override
                    public void store(Serializable persistentObject) throws StorageFullException {
                    }

                    @Override
                    public <P extends Serializable> P restore(Storage storage, Class<P> clazz) {
                        return null;
                    }

                    @Override
                    public void remove(Storage storage) {
                    }

                    @Override
                    public void flush(Storage storage, boolean removeFromCache) {
                    }

                    @Override
                    public Class<? extends Serializable> getObjectClass() {
                        return null;
                    }

                    @Override
                    public boolean isObjectCached() {
                        return false;
                    }

                    @Override
                    public Serializable getId() {
                        return null;
                    }
                };
                    
            }

            @Override
            public void remove(Address address) {
            }

            @Override
            public <P extends Serializable> P restore(Class<P> clazz, Address address) {
                return null;
            }

            @Override
            public void restoreSnapshot(BigInteger version) {
            }

            @Override
            public void store(Serializable persistentObject, Address address) throws StorageFullException {
            }

            @Override
            public void takeSnapshot(BigInteger version) {
            }

            @Override
            public void start(BigInteger startVersion) {
            }

            @Override
            public void begin(long txId) {
            }

            @Override
            public void commit(long txId) {
            }

            @Override
            public void rollback(long txId) {
            }});
    }

    @Test
    public void testBeginTransaction() {
        Transaction tx = objectMap.begin(ctx, null);
        assertThat(tx.canCommit(), is(true));
        assertThat(tx.canComplete(), is(true));

        Transaction tx2 = objectMap.begin(ctx, null);
        assertThat(tx, is(not(equalTo(tx2))));
        assertThat(tx, is(not(sameInstance(tx2))));
    }

    public void testLockFromTopTransaction() {
        try {
            objectMap.lock(null, "one");
            fail();
        } catch (LockingException ge) {
            // expected;
        }
    }

    @Test
    public void testLock() {
        Transaction tx = objectMap.begin(ctx, null);
        Transaction tx2 = objectMap.begin(ctx, null);
        objectMap.lock(tx, "one");
        objectMap.lock(tx2, "two");

        try {
            objectMap.lock(tx2, "one");
            fail();
        } catch (LockingException ge) {
            // expected;
        }
    }

    @Test
    public void testCommitCreateObject() {
        Transaction tx = objectMap.begin(ctx, null);
        Transaction tx2 = objectMap.begin(ctx, null);

        objectMap.lock(tx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(tx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(false));
        assertThat(objectMap.contains(ctx, tx, "one"), is(true));

        objectMap.commit(ctx, tx);

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(true));
    }

    @Test
    public void testNestedCommitCreateObject() {
        Transaction parentTx = objectMap.begin(ctx, null);
        Transaction childTx = objectMap.begin(ctx, parentTx);
        Transaction tx2 = objectMap.begin(ctx, null);

        objectMap.lock(childTx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(childTx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(false));
        assertThat(objectMap.contains(ctx, childTx, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(false));

        objectMap.commit(ctx, childTx);

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(false));

        objectMap.commit(ctx, parentTx);
        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(true));

    }

    @Test
    public void testParentCommitCreateObject() {
        Transaction topLevelTx = objectMap.begin(ctx, null);
        Transaction parentTx = objectMap.begin(ctx, topLevelTx);
        Transaction childTx = objectMap.begin(ctx, parentTx);
        Transaction tx2 = objectMap.begin(ctx, parentTx);

        objectMap.lock(childTx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(childTx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(false));
        assertThat(objectMap.contains(ctx, childTx, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(false));

        objectMap.commit(ctx, parentTx);

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx2, "one"), is(true));

    }

    @Test
    public void testRollbacktCreateObject() {
        Transaction tx = objectMap.begin(ctx, null);

        objectMap.lock(tx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(tx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, tx, "one"), is(true));

        objectMap.rollback(ctx, tx);

        assertThat(objectMap.contains(ctx, null, "one"), is(false));
    }

    @Test
    public void testNestedRollbackCreateObject() {
        Transaction parentTx = objectMap.begin(ctx, null);
        Transaction childTx = objectMap.begin(ctx, parentTx);

        objectMap.lock(childTx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(childTx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(false));
        assertThat(objectMap.contains(ctx, childTx, "one"), is(true));

        objectMap.rollback(ctx, childTx);

        assertThat(objectMap.contains(ctx, null, "one"), is(false));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(false));
    }

    @Test
    public void testParentRollbackCreateObject() {
        Transaction parentTx = objectMap.begin(ctx, null);
        Transaction childTx = objectMap.begin(ctx, parentTx);

        objectMap.lock(childTx, "one");
        StorageProxy proxy = ctx.storage.buildStorageProxy(null);
        objectMap.putStorageProxy("one", proxy);
        objectMap.backupObject("one", null);
        objectMap.addRollbackTx(childTx, new UndoNewTransaction("one"));

        assertThat(objectMap.contains(ctx, null, "one"), is(true));
        assertThat(objectMap.contains(ctx, parentTx, "one"), is(false));
        assertThat(objectMap.contains(ctx, childTx, "one"), is(true));

        objectMap.rollback(ctx, parentTx);

        assertThat(objectMap.contains(ctx, null, "one"), is(false));
    }
}
