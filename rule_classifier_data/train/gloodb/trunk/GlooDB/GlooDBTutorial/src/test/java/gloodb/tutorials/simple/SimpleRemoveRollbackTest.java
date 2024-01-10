package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleRemoveRollbackTest demonstrates rolling back a remove transaction.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalRemove">Remove a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleRemoveRollbackTest.java"
 * >SimpleRemoveRollbackTest.java</a>
 * </p>
 * 
 */
public class SimpleRemoveRollbackTest {

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
	}

	/**
	 * An object with the same identity as the new object we want to remove
	 * should already exist in the repository.
	 */
	@Before
	public void preCondition() {
		// The repository should contain an object with id "1"
		SimplePersistent persistentObject = repository.store(new SimplePersistent("1").setValue("Preexistent"));
		assertNotNull(persistentObject);
		assertEquals("Preexistent", persistentObject.getValue());
	}

	/**
	 * Rollbacks a remove transaction.
	 */
	@Test
	public void testRemoveRollback() {
		Repository tx = repository.begin();
		SimplePersistent removedObject = (SimplePersistent) tx.remove("1");
		assertNotNull(removedObject);
		assertEquals("Preexistent", removedObject.getValue());
		assertFalse(tx.contains("1"));

		tx.rollback();
		assertTrue(repository.contains("1"));
	}

	@AfterClass
	public static void cleanUp() {
		repository.remove("1");
	}
}
