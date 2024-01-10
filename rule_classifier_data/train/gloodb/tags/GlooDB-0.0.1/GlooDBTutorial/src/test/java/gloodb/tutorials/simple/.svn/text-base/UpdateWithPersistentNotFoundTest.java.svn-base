package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.PersistentNotFoundException;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UpdateWithPersistentNotFoundTest demonstrates attempting to update an object
 * that doesn't exist in the repository.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/NonTransactionalUpdate">Update
 * a persistent object without using transactions</a><br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalUpdate">Update a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/UpdateWithPersistentNotFoundTest.java"
 * >UpdateWithPersistentNotFoundTest.java</a>
 * 
 */
public class UpdateWithPersistentNotFoundTest {

	private static Repository repository;

	/**
	 * Sets up the repository. Invoke the test with:
	 * <ul>
	 * <li>-Dtype=Memory option for testing against an in memory repository.
	 * <li>-Dtype=File for testing against a file based repository
	 * implementation.
	 * </ul>
	 */
	@BeforeClass
	public static void setUp() {
		repository = RepositoryFactory.createRepository("target/Tutorials/SimpleUpdateTest");

		// The repository should not contain an object with id "1"
		repository.remove("1");
	}

	/**
	 * The repository should not contain another object with the same identity
	 * as the object we will create.
	 */
	@Before
	public void preCondition() {
		assertFalse(repository.contains("1"));
	}

	/**
	 * Restores and updates a persistent object using the top level (null)
	 * transaction.
	 */
	@Test
	public void testNonTransactionalUpdate() {
		SimplePersistent persistentObject = new SimplePersistent("1");
		try {
			repository.update(persistentObject);
			fail("Cannot update an object which doesn't exist in the repository");
		} catch (PersistentNotFoundException pnfe) {
			// Expected
		}
	}

	/**
	 * Restores and updates a persistent object using transactions.
	 */
	@Test
	public void testTransactionalUpdate() {
		SimplePersistent persistentObject = new SimplePersistent("1");

		Repository tx = repository.begin();
		try {
			tx.update(persistentObject);
			fail("Cannot update an object which doesn't exist in the repository");
		} catch (PersistentNotFoundException pnfe) {
			// The Repository is unaffected by the exception and
			// it can still commit.
			assertTrue(tx.canCommit());
			tx.rollback();
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		}
	}

	/**
	 * The object doens't exist in the repository.
	 */
	@After
	public void postCondition() {
		assertFalse(repository.contains("1"));
	}
}
