package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleCreateRollbackTest demonstrates rolling back a create transaction.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalCreate">Create a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleCreateRollbackTest.java"
 * >SimpleCreateRollbackTest.java</a>
 * </p>
 * 
 */
public class SimpleCreateRollbackTest {

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
	 * Rollbacks a create transaction.
	 */
	@Test
	public void testCreateRollback() {
		SimplePersistent persistentObject = new SimplePersistent("1");
		persistentObject.setValue("First Value");

		Repository tx = repository.begin();

		tx.create(persistentObject);
		assertTrue(tx.contains("1"));
		tx.rollback();
		assertFalse(repository.contains("1"));
	}
}
