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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 * Test set for PersistencyAttributes utility class.
 * 
 */
@RunWith(Parameterized.class)
public class PersistentIdentityTest {
	
	@Identity
	static class SingletonTest implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
	}

	@Identity(idx = 1)
	static class InheritanceTest1 extends SingletonTest {
		private static final long serialVersionUID = -2949824312635708238L;
	}
	
	@Identity
	static class MixedClassAndFieldIdentityTest1 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity(idx = 1) String id1 = "id1";
		@Identity(idx = 2) String id2 = "id2";
	}
	
	@Identity(idx = 1)
	static class MixedClassAndFieldIdentityTest2 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity String id1 = "id1";
		@Identity(idx = 2) String id2 = "id2";
	}
	
	static class MultipleIdentityFields implements Serializable {
        private static final long serialVersionUID = 1441519828965153431L;
        @Identity String id1 = "id1";
	    @Identity(idx=1) String id2 = "id2";
	}
	
	static class FunctionIdentity implements Serializable {
        private static final long serialVersionUID = 6411077455355255124L;
        @Identity String getId() { return "id1"; }
	}

	@Parameters
	public static Collection<Object []> data() {
	    Object[][] data = new Object [][] {
	            {new SingletonTest(), new Serializable[] {SingletonTest.class}},
	            {new InheritanceTest1(), new Serializable[] {SingletonTest.class, InheritanceTest1.class}},
	            {new MixedClassAndFieldIdentityTest1(), new Serializable[] {MixedClassAndFieldIdentityTest1.class, "id1", "id2"}},
	            {new MixedClassAndFieldIdentityTest2(), new Serializable[] {"id1", MixedClassAndFieldIdentityTest2.class, "id2"}},
	            {new MultipleIdentityFields(), new Serializable[] {"id1", "id2"}},
	            {new FunctionIdentity(), new Serializable[] {"id1"}}
	    };
	    return Arrays.asList(data);
	}
	
	Serializable object;
	Serializable id;
	Serializable [] ids;
	
	public PersistentIdentityTest(Serializable object, Serializable[] ids) {
	    this.object = object;
	    this.ids = ids;
	    this.id =  (ids.length == 1? ids[0]: new AggregateIdentity(ids));
	}
	
	/**
	 * Programmer can get the ids of persistent objects.
	 */
	@Test
	public void testGetId() {
		assertThat(PersistencyAttributes.getIdForObject(object), is(equalTo(id)));
		assertThat(PersistencyAttributes.getIdForObject(object), is(equalTo(PersistencyAttributes.getId(ids))));
	}
}