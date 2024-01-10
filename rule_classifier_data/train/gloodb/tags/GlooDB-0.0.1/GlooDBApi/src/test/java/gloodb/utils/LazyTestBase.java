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

import gloodb.Lazy;
import gloodb.Reference;
import gloodb.Repository;
import gloodb.SimpleValued;
import gloodb.SimpleSerializableLazy;
import gloodb.operators.Clone;
import gloodb.operators.FetchLazy;
import gloodb.operators.FlushEmbedded;
import gloodb.operators.Get;
import gloodb.operators.GetLazy;
import gloodb.operators.GetIds;
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
public class LazyTestBase {
	private Repository repository;

	/**
	 * Creates a repository test set, agnostic of the repository configuration
	 * used.
	 * 
	 * @param repository
	 *            The repository
	 */
	public LazyTestBase(Repository repository) {
		this.repository = repository;
	}

	/**
	 * Lazy object can be created.
	 */
	@Test
	public void createLazyObject() {
		Reference<SimpleValued> test = new Lazy<SimpleValued>("SomeId");
		assertThat((String)test.getId(), is(equalTo("SomeId")));
	}

	
	@Test
	public void testLazyToString() {
	    String testId = "testId";
	    try {
    	    repository.create(new SimpleValued(testId));
    		Lazy<SimpleValued> lazy = new Lazy<SimpleValued>(testId);
    		assertThat("ID: testId (not fetched)", is(equalTo(lazy.toString())));
    		lazy.fetch(repository);
    		assertThat("ID: testId, value: 0", is(equalTo(lazy.toString())));
	    } finally {
	        repository.remove(testId);
	    }
	}
	
	@Test
	public void testEquals() {
		// Lazy value equality
		Lazy<Serializable> l = new Lazy<Serializable>("id1");
		assertThat(l.equals(l), is(true));
		assertThat(l.equals(new Lazy<Serializable>("id1")), is(true));
		assertThat(l.equals(new Lazy<Serializable>("id2")), is(false));
		assertThat(l.equals(new Lazy<Serializable>("bla")), is(false));
		assertThat(l.equals(null), is(false));
		assertThat(l.equals("bla"), is(false));
		assertThat(new Lazy<Serializable>((Serializable)null), is(not(equalTo((l)))));
		l.setReference(null);
        assertThat(new Lazy<Serializable>((Serializable)null), is(equalTo((l))));
		
	}
	
	@Test
	public void testHashCode() {
		// Lazy Values hashing
		Reference<Serializable> l = new Lazy<Serializable>("id1");
		assertThat(l.hashCode(), is(equalTo(new Lazy<Serializable>("id1").hashCode())));
		assertThat(l.hashCode(), is(not(equalTo(new Lazy<Serializable>("id2").hashCode()))));
		assertThat(l.hashCode(), is(not(equalTo("bla".hashCode()))));
		assertThat(new Lazy<Serializable>((Serializable)null).hashCode(), is(not(equalTo(l.hashCode()))));
	
	}
	@Test
	public void testLazyCollection1() {
	    String testId = "testId";
	    String testId2 = "testId2";
	    try {
    	    repository.create(new SimpleValued(testId));
    	    repository.create(new SimpleValued(testId2));
    	    
    	    ArrayList<Lazy<SimpleValued>> collection = new ArrayList<Lazy<SimpleValued>>();
    	    collection.add(new Lazy<SimpleValued>(testId));
    	    collection.add(new Lazy<SimpleValued>(testId2));
    
    	    final ArrayList<SimpleValued> values = new ArrayList<SimpleValued>();
    	    final ArrayList<Lazy<SimpleValued>> cloneCollection = new ArrayList<Lazy<SimpleValued>>(); 
            
    	    Expression.iterate(collection, 
    	            new FetchLazy<SimpleValued>(repository),
    	            new GetLazy<SimpleValued>(values));
    	    
    	    Expression.iterate(collection, 
    	            new JointIterator<Lazy<SimpleValued>, SimpleValued>(repository, values) {
    	                @Override
                        public Expression run(Lazy<SimpleValued> reference, SimpleValued value) {
                            value.setValue(20L);
                            repository.update(value); 
                            return null;
                        }
    	            },
    	            new Clone<Lazy<SimpleValued>>(cloneCollection));
    	    
    	    Expression.iterate(collection,
    	            new JointIterator<Lazy<SimpleValued>, Lazy<SimpleValued>>(repository, cloneCollection) {
    	                @Override
                        public Expression run(Lazy<SimpleValued> reference, Lazy<SimpleValued> copy) {
                            assertThat(reference, is(equalTo(copy)));
                            assertThat(20L, is(equalTo(copy.fetch(repository).get().getValue()))); 
                            return null;
                        }
                    });  
	    } finally {
	        repository.remove(testId);
	        repository.remove(testId2);
	    }
	}
	
	@Test
    public void testIdCollection1() {
        String testId = "testId";
        String testId2 = "testId2";
        repository.create(new SimpleValued(testId));
        repository.create(new SimpleValued(testId2));

        try {
            ArrayList<String> collection = new ArrayList<String>();
            collection.add(testId);
            collection.add(testId2);
    
            final ArrayList<SimpleValued> values = new ArrayList<SimpleValued>();
            
            iterate(collection,
                    new Get<String, SimpleValued>(repository, values));
            
            iterate(values,
                    new Expression() {
                        @Override
                        public Expression evaluate(Serializable value) {
                            ((SimpleValued)value).setValue(20L);
                            return new Store(repository);
                        }
                    },
                    new Expression() {
                        @Override
                        public Expression evaluate(Serializable value) {
                            assertThat(20L, is(equalTo((((SimpleValued)value).getValue()))));
                            return null;
                        }              
                    });
            
            iterate(collection,
                    new JointIterator<String, SimpleValued>(repository, values) {
                        @Override
                        public Expression run(String id, SimpleValued value) {
                            assertThat(repository.contains(id), is(true));
                            assertThat(id, is(equalTo(value.getId())));
                            return null;
                        }
                    },
                    new Remove(repository),
                    new Expression() {
    
                        @Override
                        public Expression evaluate(Serializable id) {
                            assertThat(repository.contains(id), is(false));
                            return null;
                        }
                    });
        } finally {
            repository.remove(testId);
            repository.remove(testId2);
        }
    }
	
}
