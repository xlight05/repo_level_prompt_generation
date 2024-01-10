package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleUpdateTest demonstrates updating a persistent object.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/NonTransactionalUpdate">Update
 * a persistent object without using transactions</a><br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalUpdate">Update a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleUpdateTest.java"
 * >SimpleUpdateTest.java</a>
 * 
 */
public class SimpleUpdateTest {

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
	 * An object with the same identity as the new object we want to create
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
	 * Restores and updates a persistent object using the top level (null)
	 * transaction.
	 */
	@Test
	public void testNonTransactionalUpdate() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		persistentObject.setValue("First Value");

		repository.update(persistentObject);
	}

	/**
	 * Restores and updates a persistent object using transactions.
	 */
	@Test
	public void testTransactionalUpdate() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		persistentObject.setValue("First Value");

		Repository tx = repository.begin();
		try {
			tx.update(persistentObject);
			tx.commit();
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		}
	}

	/**
	 * Rollbacks an update operation
	 */
	@Test
	public void testUpdateRollback() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		persistentObject.setValue("First Value");

		Repository tx = repository.begin();
		tx.update(persistentObject);
		assertEquals("First Value", ((SimplePersistent) tx.restore("1")).getValue());
		tx.rollback();
		assertEquals("Preexistent", ((SimplePersistent) repository.restore("1")).getValue());

		// Set the value to the expected one.
		testTransactionalUpdate();
	}

	/**
	 * The object value has been updated in the repository.
	 */
	@After
	public void postCondition() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		assertNotNull(persistentObject);
		assertEquals("First Value", persistentObject.getValue());
	}
}
