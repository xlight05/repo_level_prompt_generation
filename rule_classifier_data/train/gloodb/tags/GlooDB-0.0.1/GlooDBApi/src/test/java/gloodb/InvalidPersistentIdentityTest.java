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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 * Test set for PersistencyAttributes utility class.
 * 
 */
@RunWith(Parameterized.class)
public class InvalidPersistentIdentityTest {
	@Identity(idx = 3)
	static class IncosistentSingletonTest implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
	}

	static class DuplicatedFieldIdentityTest1 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity Serializable id1;
		@Identity(idx = 1)Serializable id2;
		@Identity(idx = 1) Serializable id3;
	}
	
	@Identity
	static class DuplicatedMixedClassAndFieldIdentityTest1 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity Serializable id1;
		@Identity(idx = 2) Serializable id2;
	}
	
	@Identity(idx = 1)
	static class DuplicatedMixedClassAndFieldIdentityTest2 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity Serializable id1;
		@Identity(idx = 1) Serializable id2;
	}
	
	@Identity(idx = 4)
	static class IncosistentMixedClassAndFieldIdentityTest1 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity Serializable id1;
		@Identity(idx = 1) Serializable id2;
	}
	
	@Identity
	static class IncosistentMixedClassAndFieldIdentityTest2 implements Serializable {
		private static final long serialVersionUID = -2949824312635708238L;
		@Identity(idx = 2) Serializable id1;
		@Identity(idx = 3) Serializable id2;
	}	
	
   @Parameters
   public static Collection<Object []> data() {
        Object[][] data = new Object [][] {
                {new IncosistentSingletonTest(), "Missing identity field index 0 in class gloodb.InvalidPersistentIdentityTest$IncosistentSingletonTest"},
                {new DuplicatedFieldIdentityTest1(), "Duplicated index 1 found for identity field " +
                		"java.io.Serializable gloodb.InvalidPersistentIdentityTest$DuplicatedFieldIdentityTest1.id3 " +
                		"in class gloodb.InvalidPersistentIdentityTest$DuplicatedFieldIdentityTest1"},
                {new DuplicatedMixedClassAndFieldIdentityTest1(), "Duplicated index 0 found for identity field " +
                		"java.io.Serializable gloodb.InvalidPersistentIdentityTest$DuplicatedMixedClassAndFieldIdentityTest1.id1 " +
                		"in class gloodb.InvalidPersistentIdentityTest$DuplicatedMixedClassAndFieldIdentityTest1"},
                {new DuplicatedMixedClassAndFieldIdentityTest2(), "Duplicated index 1 found for identity field " +
                        "java.io.Serializable gloodb.InvalidPersistentIdentityTest$DuplicatedMixedClassAndFieldIdentityTest2.id2 " +
                        "in class gloodb.InvalidPersistentIdentityTest$DuplicatedMixedClassAndFieldIdentityTest2"},
                {new IncosistentMixedClassAndFieldIdentityTest1(), "Missing identity field index 2 in class " +
                        "gloodb.InvalidPersistentIdentityTest$IncosistentMixedClassAndFieldIdentityTest1"},
                {new IncosistentMixedClassAndFieldIdentityTest2(), "Missing identity field index 1 in class " +
                        "gloodb.InvalidPersistentIdentityTest$IncosistentMixedClassAndFieldIdentityTest2"}
        };
        return Arrays.asList(data);
    }

	
	private Serializable object;
	private String message;
	
    public InvalidPersistentIdentityTest(Serializable object, String message) {
        super();
        this.object = object;
        this.message = message;
    }


    @Test
    public void testInvalidIdentity() {
        try {
            PersistencyAttributes.getIdForObject(object);
            fail();
        } catch (GlooException e) {
            // An exception is expected
            assertThat(message, is(equalTo(e.getMessage())));
        }
    }   
}