/* Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 * 
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb;

import java.math.BigInteger;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionalRepositoryTestBase {

    protected final Repository repository;
    private final PersistentFactory factory;
    
    final static String testId = "testId";

    @Before
    public void setup() {

    }

    @After
    public void teardown() {
        repository.remove(testId);
    }

    /**
     * Creates a repository test set, agnostic of the repository configuration
     * used.
     * 
     * @param repository
     *            The repository
     * @param factory
     *            The persistent object factory
     */
    public TransactionalRepositoryTestBase(Repository repository, PersistentFactorySimpleVersioned factory) {
        this.repository = repository;
        this.factory = factory;
    }

    /**
     * Create can commit.
     */
    @Test
    public void testCommitNewTransaction() {
        Repository tx = repository.begin();
        try {
            tx.create(factory.newObject(testId));
            assertTrue(tx.contains(testId));
        } finally {
            tx.commit();
            assertTrue(repository.contains(testId));
        }
    }

    /**
     * Create can rollback.
     */
    @Test
    public void testRollbackNewTransaction() {
        Repository tx = repository.begin();
        try {
            tx.create(factory.newObject(testId));
            assertTrue(tx.contains(testId));
        } finally {
            tx.rollback();
            assertFalse(repository.contains(testId));
        }
    }

    /**
     * New objects are locked until commit / rollback.
     */
    @Test
    public void testLockingWithNewTransaction() {
        Repository tx1 = repository.begin();
        Repository tx2 = repository.begin();
        try {
            try {
                tx1.create(factory.newObject(testId));
                assertTrue(tx1.contains(testId));

                try {
                    tx2.create(factory.newObject(testId));
                    fail();
                } catch (KeyViolationException kve) {
                    // Attempt to store the same object from a different
                    // transaction throws a KeyViolation exception
                    // or a LockingException due to interceptor updates.
                }

                try {
                    tx2.update(factory.newObject(testId));
                    fail();
                } catch (LockingException le) {
                    // Attempt to store the same object from a different
                    // transaction
                    // throws a LockingException
                }
            } finally {
                tx1.commit();
                assertTrue(repository.contains(testId));
            }

            // Attempt to store the same object from a different transaction
            // Since the other transaction committed, this should fail with an
            // optimistic locking exception
            tx2.update(factory.newObject(testId));
        } finally {
            tx2.commit();
        }
    }

    /**
     * Removed object can commit.
     */
    @Test
    public void testCommitRemoveTransaction() {
        repository.create(factory.newObject(testId));

        Repository tx = repository.begin();
        try {
            tx.remove(testId);
            assertFalse(tx.contains(testId));
        } finally {
            tx.commit();
            assertFalse(repository.contains(testId));
        }
    }

    /**
     * Removed object can rollback.
     */
    @Test
    public void testRollbackRemoveTransaction() {
        repository.create(factory.newObject(testId));

        Repository tx = repository.begin();
        try {
            tx.remove(testId);
            assertFalse(tx.contains(testId));
        } finally {
            tx.rollback();
            assertTrue(repository.contains(testId));
        }
    }

    /**
     * Removed objects are locked until commit or rollback.
     */
    @Test
    public void testLockingWithRemoveTransaction() {
        SimpleVersionedSerializable testObject = (SimpleVersionedSerializable) repository.create(factory.newObject(testId));

        Repository tx1 = repository.begin();
        Repository tx2 = repository.begin();
        try {
            try {
                tx1.remove(testId);
                assertFalse(tx1.contains(testId));

                try {
                    tx2.create(factory.newObject(testId));
                    fail();
                } catch (LockingException le) {
                    // Attempt to store the same object from a different
                    // transaction
                    // throws a locking exception
                }
            } finally {
                tx1.commit();
                assertFalse(repository.contains(testId));
            }

            // Attempt to store the same object from a different transaction
            // After the remove committed, it's ok to add this object
            testObject = repository.create(testObject);
            assertEquals(BigInteger.ZERO, testObject.getVersion());
        } finally {
            tx2.commit();
        }

    }

    /**
     * Updates can commit.
     */
    @Test
    public void testCommitUpdateTransaction() {
        repository.create(factory.newObject(testId));

        Repository tx = repository.begin();
        try {
            // Restore and update the test object.
            // After the update the version should be increased by 1
            // Rollback should return the version to its initial value.
            SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx.restore(testId);
            assertEquals(BigInteger.ZERO, obj.getVersion());
            // Update to increase the version
            obj = tx.update(obj);
            assertEquals(BigInteger.ONE, obj.getVersion());
        } finally {
            tx.commit();
        }
        // The commit should've set the version.
        SimpleVersionedSerializable obj = (SimpleVersionedSerializable) repository.restore(testId);
        assertEquals(BigInteger.ONE, obj.getVersion());
    }

    /**
     * Updates can rollback.
     */
    @Test
    public void testRollbackUpdateTransaction() {
        repository.create(factory.newObject(testId));

        Repository tx = repository.begin();
        try {
            // Restore and update the test object.
            // After the update the version should be increased by 1
            // Rollback should return the version to its initial value.
            SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx.restore(testId);
            assertEquals(BigInteger.ZERO, obj.getVersion());
            // Update to increase the version
            obj = tx.update(obj);
            assertEquals(BigInteger.ONE, obj.getVersion());
        } finally {
            // Rollback should return the version number to its initial value.
            tx.rollback();
        }
        // The rollback should've restored the version.
        SimpleVersionedSerializable obj = (SimpleVersionedSerializable) repository.restore(testId);
        assertEquals(BigInteger.ZERO, obj.getVersion());
    }

    /**
     * Updated objects are locked until commit or rollback.
     */
    @Test
    public void testLockingWithUpdateTransaction() {
        repository.create(factory.newObject(testId));

        Repository tx1 = repository.begin();
        Repository tx2 = repository.begin();
        SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx1.restore(testId);
        try {
            try {
                // Restore and update the test object.
                // After the update the version should be increased by 1
                // Rollback should return the version to its initial value.
                assertEquals(BigInteger.ZERO, obj.getVersion());
                // Update to increase the version
                obj = tx1.update(obj);

                try {
                    obj = (SimpleVersionedSerializable) tx1.restore(testId);
                    obj = tx2.update(obj);
                    fail();
                } catch (LockingException le) {
                    // Attempt to update the object from a different transaction
                    // throws a locking exception
                }
            } finally {
                tx1.commit();
            }

            // Attempt to update the object from a different transaction
            // works after the first transaction finished.
            obj = (SimpleVersionedSerializable) tx2.restore(testId);
            assertEquals(BigInteger.ONE, obj.getVersion());
            obj = tx2.update(obj);
        } finally {
            tx2.commit();
        }
    }
}
