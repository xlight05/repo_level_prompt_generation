package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleStoreTest demonstrates storing (create or update) a persistent object.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/NonTransactionalStore">Store a
 * persistent object without using transactions</a><br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalStore">Store a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleStoreTest.java"
 * >SimpleStoreTest.java</a>
 * 
 */
public class SimpleStoreTest {

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
	 * Creates and updates a persistent object using the top level (null)
	 * transaction.
	 */
	@Test
	public void testNonTransactionalStore() {
		SimplePersistent persistentObject = new SimplePersistent("1").setValue("Initial");
		repository.store(persistentObject);

		persistentObject = (SimplePersistent) repository.restore("1");
		assertNotNull(persistentObject);
		assertEquals("Initial", persistentObject.getValue());

		// A second call to store will update the object.
		persistentObject.setValue("First Value");
		repository.store(persistentObject);
	}

	/**
	 * Functionally identical to testNonTransactionalStore, but written in a
	 * more concise way.
	 */
	@Test
	public void testNonTransactionalStore2() {
		SimplePersistent persistentObject = repository.store(new SimplePersistent("1").setValue("Initial"));
		assertNotNull(persistentObject);
		assertEquals("Initial", persistentObject.getValue());

		// A second call to store will update the object.
		persistentObject.setValue("First Value");
		repository.store(persistentObject);
	}

	/**
	 * Creates and then updates a persistent object using transactions.
	 */
	@Test
	public void testTransactionalStore() {
		Repository tx = repository.begin();
		try {
			SimplePersistent persistentObject = tx.store(new SimplePersistent("1").setValue("Initial"));
			persistentObject.setValue("First Value");
			tx.store(persistentObject);
			tx.commit();
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		}
	}

	/**
	 * Creates and then updates a persistent object using transactions.
	 */
	@Test
	public void testStoreRollback() {
		Repository tx = repository.begin();

		SimplePersistent persistentObject = tx.store(new SimplePersistent("1").setValue("Initial"));
		persistentObject.setValue("First Value");
		tx.store(persistentObject);

		tx.rollback();
		assertFalse(repository.contains("1"));

		// Update to the expected value
		testTransactionalStore();
	}

	/**
	 * The object value has been updated in the repository.
	 */
	@After
	public void postCondition() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		assertNotNull(persistentObject);
		assertEquals("First Value", persistentObject.getValue());

		// Cleanup
		repository.remove("1");
	}
}
