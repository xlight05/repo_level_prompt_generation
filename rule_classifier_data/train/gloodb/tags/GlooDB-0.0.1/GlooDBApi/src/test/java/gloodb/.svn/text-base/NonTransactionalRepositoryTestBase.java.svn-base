/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 * 
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb;

import gloodb.Repository;
import gloodb.KeyViolationException;
import gloodb.PersistentNotFoundException;
import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * A repository test set, agnostic of the repository configuration used. Depending on
 * the PersistentFactory provided it can test with multiple types of objects.
 */
public class NonTransactionalRepositoryTestBase {
    private final Repository repository;
    private final PersistentFactory factory;
    private final static String testId = "testId";
    
    @Before
    public void setup() {
        
    }
    
    @After
    public void teardown() {
        repository.remove(testId);   
    }
    
	/**
	 * Creates a repository test set, agnostic of the repository configuration
	 * used.
	 * 
	 * @param repository
	 *            The repository
	 * @param factory
	 *            The persistent object factory
	 */
	public NonTransactionalRepositoryTestBase(Repository repository, PersistentFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	/**
	 * Contains returns true if there is an object with the given identity in
	 * the repository. False otherwise.
	 */
	@Test
    public void testContains() {    
        repository.store(factory.newObject(testId));
        assertThat(repository.contains(testId), is(true));    
    }


	/**
	 * Objects can be removed from the repository.
	 */
	@Test
	public void testRemove() {
	    Serializable object = null;
	  
        object = repository.create(factory.newObject(testId));
        assertThat(repository.contains(testId), is(true));
        assertThat(repository.restore(testId), is(equalTo(object)));
	}

	/**
	 * Objects can be restored from the repository.
	 */
	@Test
	public void testRestore() {
        String testId2 = "testId2";
        try {
            Serializable object = repository.create(factory.newObject(testId));            
            assertNotSame(object, repository.restore(testId));
            assertEquals(object, repository.restore(testId));
            
            object = repository.restore(testId);
            assertNotSame(object, repository.restore(testId));
            assertEquals(object, repository.restore(testId));
                        
            repository.create(factory.newObject(testId2));
            assertThat(object, is(not(repository.restore(testId2))));
        } finally {         
            repository.remove(testId2);
        }
	}

	/**
	 * Objects can be updated in the repository.
	 */
	@Test
	public void testStore() {
        assertThat(repository.contains(testId), is(false));
        Serializable object = repository.store(factory.newObject(testId));
        assertThat(object, is(anything()));
        
        assertThat(repository.contains(testId), is(true));
        object = repository.store(factory.newObject(testId));
        assertThat(object, is(anything()));            
        assertThat(object, is(equalTo(repository.restore(testId))));
	}

	/**
	 * Objects can be created.
	 */
	@Test
    public void testCreate() {
        assertThat(repository.contains(testId), is(false));
        Serializable object = repository.create(factory.newObject(testId));
        assertThat(object, is(anything()));
        assertThat(testId, is(equalTo(PersistencyAttributes.getIdForObject(object))));
    }

    /**
     * Duplicate identities are not allowed and a
     * KeyViolationException is thrown.
     */
    @Test
    public void testCreateDuplicates() {
        try {
            repository.create(factory.newObject(testId));
            assertThat(repository.contains(testId), is(true));
            repository.create(factory.newObject(testId));
            fail();
        } catch (KeyViolationException kve) {
            // Re-creating throws a KeyViolationException
        }
    }

    /**
	 * Objects can be udpated.
	 */
	@Test
	public void testUpdate() {
        assertThat(repository.contains(testId), is(false));
        Serializable object = repository.create(factory.newObject(testId));
        assertThat(object, is(anything()));
        
        assertThat(repository.contains(testId), is(true));
        object = repository.update(factory.newObject(testId));
        assertThat(object, is(anything()));            
        assertThat(object, is(equalTo(repository.restore(testId))));    
	}

    /**
     * Non-existent objects cannot be updated.
     */
    @Test
    public void testUpdateNonExistent() {
        try {
            assertThat(repository.contains(testId), is(false));
            repository.update(factory.newObject(testId));
            fail();
        } catch (PersistentNotFoundException pnfe) {
            // Cannot update non-existent objects
        }
    }
}
