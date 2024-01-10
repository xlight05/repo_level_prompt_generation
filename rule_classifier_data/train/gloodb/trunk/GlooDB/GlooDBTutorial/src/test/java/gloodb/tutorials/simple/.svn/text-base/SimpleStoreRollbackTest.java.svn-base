package gloodb.tutorials.simple;

import static org.junit.Assert.*;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SimpleStoreRollbackTest demonstrates rolling back a store transaction.
 * <p>Tutorials:<br> 
 * <a href="http://code.google.com/p/gloodb/wiki/TransactionalStore">Store a persistent object using transactions</a><br>
 * </p>
 * <p>Sample code:<br> 
 * <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/test/java/gloodb/tutorials/simple/SimpleStoreRollback.java">SimpleStoreRollback.java</a>
 *
 */
public class SimpleStoreRollbackTest {
	
	private static Repository repository;
	
	/**
	 * Sets up the repository. Invoke the test with:
	 * <ul><li> -Dtype=Memory option for testing against an in memory repository. 
	 * <li>-Dtype=File for testing against a file based repository implementation.
	 * </ul>
	 */
	@BeforeClass
	public static void setUp() {
		repository = RepositoryFactory.createRepository("target/Tutorials/SimpleUpdateTest");	
	
		// The repository should not contain an object with id "1"
		repository.remove("1");
	}
	
	/**
	 * Rolls back a store transaction.
	 */
	@Test
	public void testStoreRollback() {
		Repository tx = repository.begin();
		
		SimplePersistent persistentObject = tx.store(new SimplePersistent("1").setValue("Initial"));
		persistentObject.setValue("First Value");
		tx.store(persistentObject);
		
		tx.rollback();
		assertFalse(repository.contains("1"));	
	}
}
