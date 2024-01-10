package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleUpdateRollbackTest demonstrates rolling back an update transaction.
 * <p>
 * Tutorials:<br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalUpdate">Update a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleUpdateRollbackTest.java"
 * >SimpleUpdateRollbackTest.java</a>
 * 
 */
public class SimpleUpdateRollbackTest {

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
	}
}
