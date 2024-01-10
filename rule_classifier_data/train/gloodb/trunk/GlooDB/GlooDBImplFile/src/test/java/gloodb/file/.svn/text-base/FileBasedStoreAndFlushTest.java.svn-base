package gloodb.file;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Arrays;

import gloodb.Identity;
import gloodb.Repository;
import gloodb.impl.RepositoryAdmin;

import org.junit.Test;

public class FileBasedStoreAndFlushTest {
	
	@SuppressWarnings("serial")
	static class TestClass implements Serializable {
		@Identity String id;
		Serializable value;
		
		
	}
	
    private static final String nameSpace = "target/UnitTests/StoreAndFlush";

    /**
     * Creates a small object then flushes. Updates with a large object (which requires an address update) 
     * and then flushes again. On repository restart the object should restore correctly. 
     */
    @Test
    public void testStoreAndFlush() {
    	Repository repository = new FileBasedRepositoryFactory().buildRepository(nameSpace);
    	
    	repository.remove("1");
    	
    	TestClass t = new TestClass();
    	t.id = "1";
    	t.value = Integer.valueOf(0);
    	repository.create(t);
    	((RepositoryAdmin)repository).flushCache(100);
    	
    	t.value = new Integer[1000];
    	Arrays.fill((Integer[])t.value, Integer.valueOf(1));
    	repository.update(t);

    	((RepositoryAdmin)repository).flushCache(100);
    	
    	
    	repository = new FileBasedRepositoryFactory().buildRepository(nameSpace);
    	t = (TestClass) repository.restore("1");
    	assertEquals(1000, ((Integer[])t.value).length);
    }
}
