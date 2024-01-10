package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleCreateTest demonstrates creating a new persistent object.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/NonTransactionalCreate">Create
 * a persistent object without using transactions</a><br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalCreate">Create a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleCreateTest.java"
 * >SimpleCreateTest.java</a>
 * </p>
 */
public class SimpleCreateTest {

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
		repository = RepositoryFactory.createRepository("target/Tutorials/SimpleCreateTest");

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
	 * Creates a persistent object using the top level (null) transaction.
	 */
	@Test
	public void testNonTransactionalCreate() {
		SimplePersistent persistentObject = new SimplePersistent("1");
		persistentObject.setValue("First Value");

		repository.create(persistentObject);
	}

	/**
	 * Creates a persistent object using transactions.
	 */
	@Test
	public void testTransactionalCreate() {
		SimplePersistent persistentObject = new SimplePersistent("1");
		persistentObject.setValue("First Value");

		Repository tx = repository.begin();
		try {
			tx.create(persistentObject);
			tx.commit();
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		}
	}

	/**
	 * An object with the new identity has been created in the repository.
	 */
	@After
	public void postCondition() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		assertNotNull(persistentObject);
		assertEquals("First Value", persistentObject.getValue());

		// Cleanup.
		repository.remove("1");
	}
}
