package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleRestoreTest demonstrates restoring a persistent object.
 * <p>
 * Tutorials:<br>
 * <a
 * href="http://code.google.com/p/gloodb/wiki/NonTransactionalRestore">Restore a
 * persistent object without using transactions</a><br>
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalRestore">Restore a
 * persistent object using transactions</a><br>
 * </p>
 * <p>
 * Sample code:<br>
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleRestoreTest.java"
 * >SimpleRestoreTest.java</a>
 * 
 */
public class SimpleRestoreTest {

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
	 * Creates a persistent object using the top level (null) transaction.
	 */
	@Test
	public void testRestore() {
		SimplePersistent persistentObject = (SimplePersistent) repository.restore("1");
		assertNotNull(persistentObject);
		assertEquals("Preexistent", persistentObject.getValue());
	}

	/**
	 * An object with the new identity has been created in the repository.
	 */
	@After
	public void postCondition() {
		// Cleanup.
		repository.remove("1");
	}
}
