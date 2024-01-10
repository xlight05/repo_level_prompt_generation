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
package gloodb.utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import gloodb.Embedded;
import gloodb.Repository;
import gloodb.SimpleSerializableEmbedded;
import gloodb.SimpleSerializableEmbedded;
import gloodb.operators.Clone;
import gloodb.operators.FetchLazy;
import gloodb.operators.FlushEmbedded;
import gloodb.operators.Get;
import gloodb.operators.GetLazy;
import gloodb.operators.JointIterator;
import gloodb.operators.Expression;
import gloodb.operators.Remove;
import gloodb.operators.RemoveEmbedded;
import gloodb.operators.ResetEmbedded;
import gloodb.operators.Store;
import gloodb.operators.SetEmbedded;
import static gloodb.operators.Expression.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unused")
public class EmbeddedTestBase {
	private Repository repository;

	/**
	 * Creates a repository test set, agnostic of the repository configuration
	 * used.
	 * 
	 * @param repository
	 *            The repository
	 */
	public EmbeddedTestBase(Repository repository) {
		this.repository = repository;
	}

	/**
	 * Embedded object can be created.
	 */
	@Test
	public void createEmbeddedObject() {
	    String testId = "testId";
		try {
		   SimpleSerializableEmbedded object = new SimpleSerializableEmbedded(testId);
		   assertThat(object.getEmbeddedValue().isDirty(), is(true));
		   object = repository.create(object);
		   assertThat(repository.contains(object.getEmbeddedId()), is(true));
		} finally {
		    repository.remove(testId);
		}
	}

	
	@Test
	public void testEmbeddedToString() {
	    String testId = "testId";
	    try {
	        repository.create(new SimpleSerializableEmbedded(testId));
	        SimpleSerializableEmbedded object = (SimpleSerializableEmbedded) repository.restore(testId);
    		assertEquals("ID: [class gloodb.Embedded, testId, 1] (not fetched)",object.getEmbeddedValue().toString());
    		object.getEmbeddedValue().fetch(repository);
    		assertEquals("0", object.getEmbeddedValue().toString());
	    } finally {
	        repository.remove(testId);
	    }
	}
	
	@Test
	public void testEquals() {
		// Embedded value equality
		Embedded<Serializable> l = new Embedded<Serializable>("parent", "id1");
		assertThat(l.equals(l), is(true));
		assertThat(l.equals(new Embedded<Serializable>("parent", "id1")), is(true));
		assertThat(l.equals(new Embedded<Serializable>("parent", "id2")), is(false));
		assertThat(l.equals(new Embedded<Serializable>("parent", "bla")), is(false));
		assertThat(l.equals(null), is(false));
		assertThat(l.equals("bla"), is(false));
		assertThat(new Embedded<Serializable>(null, null), is(not(equalTo((l)))));
        assertThat(new Embedded<Serializable>(null, null), is(equalTo((new Embedded<Serializable>(null, null)))));
		
	}

	@Test
	public void testEmbeddedCollection1() {
	    String testId = "testId";
	    String testId2 = "testId2";
	    try {
    	    repository.create(new SimpleSerializableEmbedded(testId));
    	    repository.create(new SimpleSerializableEmbedded(testId2));
    	    
    	    ArrayList<Embedded<Long>> collection = new ArrayList<Embedded<Long>>();
    	    collection.add(new Embedded<Long>(testId, 1));
    	    collection.add(new Embedded<Long>(testId2, 1));
    
    	    final ArrayList<Long> values = new ArrayList<Long>();
    	    final ArrayList<Embedded<Long>> cloneCollection = new ArrayList<Embedded<Long>>(); 
            
    	    Expression.iterate(collection, 
    	            new FetchLazy<Long>(repository),
    	            new GetLazy<Long>(values));
    	    
    	    Expression.iterate(collection, 
    	            new JointIterator<Embedded<Long>, Long>(repository, values) {
    	                @Override
                        public Expression run(Embedded<Long> reference, Long value) {
    	                    assertThat(value, is(equalTo(0L)));
                            reference.set(20L);
                            return new FlushEmbedded<Long>(repository);
                        }
    	            },
    	            new Clone<Embedded<Long>>(cloneCollection));
    	    
    	    Expression.iterate(collection,
    	            new JointIterator<Embedded<Long>, Embedded<Long>>(repository, cloneCollection) {
    	                @Override
                        public Expression run(Embedded<Long> reference, Embedded<Long> copy) {
                            assertThat(reference, is(equalTo(copy)));
                            assertThat(20L, is(equalTo(copy.fetch(repository).get()))); 
                            return null;
                        }
                    });  
            assertThat(repository.contains(testId), is(true));
            assertThat(repository.contains(new Embedded<Long>(testId, 1).getId()), is(true));
            
            assertThat(repository.contains(testId2), is(true));
            assertThat(repository.contains(new Embedded<Long>(testId2, 1).getId()), is(true));
	    } finally {
	        repository.remove(testId);
	        repository.remove(testId2);
	        
	        assertThat(repository.contains(testId), is(false));
	        assertThat(repository.contains(new Embedded<Long>(testId, 1).getId()), is(false));
	        
            assertThat(repository.contains(testId2), is(false));
            assertThat(repository.contains(new Embedded<Long>(testId2, 1).getId()), is(false));
	    }
	}
	
}
