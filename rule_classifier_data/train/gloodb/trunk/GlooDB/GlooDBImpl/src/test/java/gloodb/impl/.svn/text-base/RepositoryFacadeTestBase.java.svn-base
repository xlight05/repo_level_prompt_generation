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

import gloodb.CannotCompleteTxException;
import gloodb.KeyViolationException;
import gloodb.LockingException;
import gloodb.Repository;
import gloodb.SimpleVersionedSerializable;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A generic test set for top level transactions, agnostic to the storage and
 * transaction management strategies.
 */
public class RepositoryFacadeTestBase {

	/**
	 * Parent transaction. Super classes can set this in order to test nested
	 * transactions. By default this is set to null.
	 */
	protected Repository repository;

	/**
	 * Creates a generic test set for nested transactions, agnostic to the
	 * storage and transaction management strategies.
	 * 
	 * @param repository
	 *            The parent repository
	 */
	public RepositoryFacadeTestBase(Repository repository) {
		this.repository = repository;
	}

	/**
	 * New objects can be committed to the repository
	 */
	@Test
	public void testCommitNewTransaction() {
	    String testId = "testId";
		Repository tx = repository.begin();
		try {
			tx.store(new SimpleVersionedSerializable(testId));
			assertTrue(tx.contains(testId));
			assertTrue(tx.canCommit());
			assertTrue(tx.canComplete());
		} finally {
			tx.commit();
			assertFalse(tx.canComplete());
			assertFalse(tx.canCommit());
			assertTrue(tx.contains(testId));
			repository.remove(testId);
		}
	}

	/**
	 * New objects can be rolled back from the repository
	 */
	@Test
	public void testRollbackNewTransaction() {
	    String testId = "testId";
		Repository tx = repository.begin();
		try {
			tx.store(new SimpleVersionedSerializable(testId));
			assertTrue(tx.contains(testId));
			assertTrue(tx.canCommit());
			assertTrue(tx.canComplete());
			tx.markForRollback();
		} finally {
			assertFalse(tx.canCommit());
			assertTrue(tx.canComplete());
			tx.rollback();
			assertFalse(tx.canComplete());
			assertFalse(tx.contains(testId));
			repository.remove(testId);
		}
	}

	/**
	 * New objects are locked until committed or rolled back.
	 */
	@Test
	public void testLockingWithNewTransaction() {
	    String testId = "testId";
		Repository tx1 = repository.begin();
		Repository tx2 = repository.begin();
		try {
			try {
				tx1.create(new SimpleVersionedSerializable(testId));
				assertTrue(tx1.contains(testId));
				// Changes are visible from the top level transaction
				// but not from other concurrent transactions
				assertFalse(tx2.contains(testId));
				assertTrue(repository.contains(testId));

				try {
					tx2.create(new SimpleVersionedSerializable(testId));
					fail();
				} catch (LockingException le) {
					// Attempt to store the same object from a different
					// transaction throws a locking exception
				}
				
				try {
					tx2.update(new SimpleVersionedSerializable(testId));
					fail();
				} catch (LockingException le) {
					// Attempt to update the same object from a different
					// transaction a locking exception
				}
			} finally {
				tx1.commit();
				assertFalse(tx1.canComplete());
				assertTrue(tx2.contains(testId));
			}

			// Attempt to create the same object from a different transaction
			// Since the other transaction committed, this should fail with an
			// key violation
			tx2.create(new SimpleVersionedSerializable(testId));
			fail();
		} catch (KeyViolationException kve) {
			// Expected
		} finally {
			tx2.rollback();
			repository.remove(testId);
		}
	}

	/**
	 * Removed objects should be locked until committed or rolled back.
	 */
	@Test
	public void testCommitRemoveTransaction() {
	    String testId = "testId";
		Repository tx = repository.begin();
		try {
			tx.remove(testId);
			assertFalse(repository.contains(testId));
		} finally {
			tx.commit();
			assertFalse(tx.canComplete());
			assertFalse(repository.contains(testId));
			repository.remove(testId);
		}
	}

	/**
	 * Removed objects can be rolled back.
	 */
	@Test
	public void testRollbackRemoveTransaction() {
	    String testId = "testId";
	    repository.create(new SimpleVersionedSerializable(testId));
		Repository tx = repository.begin();
		try {
			tx.remove(testId);
			assertFalse(tx.contains(testId));
		} finally {
			tx.rollback();
			assertFalse(tx.canComplete());
			assertTrue(repository.contains(testId));
			repository.remove(testId);
		}
	}

	/**
	 * Removed objects are locked until committed or rolled back.
	 */
	@Test
	public void testLockingWithRemoveTransaction() {
	    String testId = "testId";
		SimpleVersionedSerializable testObject = repository.create(new SimpleVersionedSerializable(testId));
		Repository tx1 = repository.begin();
		Repository tx2 = repository.begin();
		try {
			try {
				tx1.remove(testId);
				assertFalse(tx1.contains(testId));
				assertTrue(tx2.contains(testId));
				assertFalse(repository.contains(testId));

				try {
					tx2.create(new SimpleVersionedSerializable(testId));
					fail();
				} catch (LockingException le) {
					// Attempt to store the same object from a different
					// Repository
					// throws a locking exception
				}
			} finally {
				tx1.commit();
				assertFalse(tx2.contains(testId));
				assertFalse(tx1.canComplete());
			}

			// Attempt to store the same object from a different Repository
			// After the remove committed, it's ok to add this object
			testObject = tx2.create(testObject);
			assertEquals(BigInteger.ZERO, testObject.getVersion());
		} finally {
			tx2.commit();
			repository.remove(testId);
		}

	}

	/**
	 * Updated objects can be committed to the repository.
	 */
	@Test
	public void testCommitUpdateTransaction() {
	    String testId = "testId";
	    repository.create(new SimpleVersionedSerializable(testId));
        Repository tx = repository.begin();
		try {
			// Restore and update the test object.
			// After the update the version should be increased by 1
			// Roll back should return the version to its initial value.
			SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx.restore(testId);
			assertEquals(BigInteger.ZERO, obj.getVersion());
			// Update to increase the version
			obj = tx.update(obj);
			assertEquals(BigInteger.ONE, obj.getVersion());
		} finally {
			tx.commit();
	        // The commit should've set the version.
	        SimpleVersionedSerializable obj = (SimpleVersionedSerializable) repository.restore(testId);
            repository.remove(testId);

            assertFalse(tx.canComplete());
	        assertEquals(BigInteger.ONE, obj.getVersion());
		}
	}

	/**
	 * Updated object can be rolled back.
	 */
	@Test
	public void testRollbackUpdateTransaction() {
	    String testId = "testId";
	    repository.create(new SimpleVersionedSerializable(testId));
		Repository tx = repository.begin();
		try {
			// Restore and update the test object.
			// After the update the version should be increased by 1
			// Roll back should return the version to its initial value.
			SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx.restore(testId);
			assertEquals(BigInteger.ZERO, obj.getVersion());
			// Update to increase the version
			obj = tx.update(obj);
			assertEquals(BigInteger.ONE, obj.getVersion());
		} finally {
			// Roll back should return the version number to its initial value.
			tx.rollback();
			assertFalse(tx.canComplete());
	        // The rollback should've restored the version.
	        SimpleVersionedSerializable obj = (SimpleVersionedSerializable) repository.remove(testId);
	        assertEquals(BigInteger.ZERO, obj.getVersion());
		}
	}

	/**
	 * Updated objects are locked until committed or rolled back.
	 */
	@Test
	public void testLockingWithUpdateTransaction() {
	    String testId = "testId";
	    repository.create(new SimpleVersionedSerializable(testId));
	    
		Repository tx1 = repository.begin();
		Repository tx2 = repository.begin();
		SimpleVersionedSerializable obj = (SimpleVersionedSerializable) tx1.restore(testId);
		SimpleVersionedSerializable obj2 = (SimpleVersionedSerializable) tx2.restore(testId);
		try {
			try {
				// Restore and update the test object.
				// After the update the version should be increased by 1
				// Rollback should return the version to its initial value.
				assertEquals(BigInteger.ZERO, obj.getVersion());
				// Update to increase the version
				obj = tx1.update(obj);
				assertEquals(BigInteger.ZERO, (((SimpleVersionedSerializable)tx2.restore(testId)).getVersion()));
				assertEquals(obj.getVersion(), ((SimpleVersionedSerializable)repository.restore(testId)).getVersion());

				try {
					tx2.update(obj);
					fail();
				} catch (LockingException le) {
					// Attempt to update the object from a different Repository
					// throws a locking exception
				}
			} finally {
				tx1.commit();
				assertFalse(tx1.canComplete());
			}

			// Attempt to update the object from a different Repository
			// works after the first Repository finished. Since the other
			// transaction committed this should fail with an LockingException
			tx2.update(obj2);
			fail();
		} catch (LockingException le) {
			// Expected
		} finally {
			tx2.commit();
			repository.remove(testId);
		}
	}
	@Test
	public void testCommitingRollbackOnlyTx() {
		Repository tx = repository.begin();

		try {
			tx.markForRollback();
			assertFalse(tx.canCommit());
			assertTrue(tx.canComplete());
			tx.commit();

			fail();
		} catch (CannotCompleteTxException ex) {
			tx.rollback();
		}
	}

	@Test
	public void testCommitingCompletedTx() {
		Repository tx = repository.begin();

		try {
			tx.commit();
			assertFalse(tx.canCommit());
			assertFalse(tx.canComplete());
			tx.commit();

			fail();
		} catch (CannotCompleteTxException ex) {
			// expected
		}
	}

	@Test
	public void testRollbackOfCompletedTx() {
		Repository tx = repository.begin();

		try {
			tx.commit();
			assertFalse(tx.canCommit());
			assertFalse(tx.canComplete());
			tx.rollback();

			fail();
		} catch (CannotCompleteTxException ex) {
			// expected
		}
	}
	
	@Test
	public void testRepositoryState() {
		assertTrue(repository.isTopLevel());
		assertFalse(repository.canCommit());
		assertFalse(repository.canComplete());
		
		
		try {
			repository.commit();
			fail();
		} catch (CannotCompleteTxException ex) {
			// Expected
		}

		try {
			repository.rollback();
			fail();
		} catch (NoTransactionException ex) {
			// Expected
		}

		try {
			repository.markForRollback();
			fail();
		} catch (NoTransactionException ex) {
			// Expected
		}
	}
	
	@Test
	public void testTransactionState() {
		Repository tx = repository.begin();
		assertFalse(tx.isTopLevel());
		assertNotSame(tx, repository);
		assertFalse(tx.equals(repository));
		
		assertTrue(tx.canCommit());
		assertTrue(tx.canComplete());
		tx.markForRollback();
		assertFalse(tx.canCommit());
		assertTrue(tx.canComplete());
		
		try {
			tx.commit();
		} catch(CannotCompleteTxException ccte) {
			// Expected
		}
		
		tx.rollback();
		assertFalse(tx.canCommit());
		assertFalse(tx.canComplete());

		try {
			tx.commit();
		} catch(CannotCompleteTxException ccte) {
			// Expected
		}
	}
}
