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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
@SuppressWarnings("unused")
@RunWith(Parameterized.class)
public class PersistentInvalidCallbackTest {
    @SuppressWarnings("serial")
    static class DoublePostCreate implements Serializable {
        @Identity Serializable id; @PostCreate void foo() {}; @PostCreate void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePostUpdate implements Serializable {
        @Identity Serializable id; @PostUpdate void foo() {}; @PostUpdate void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePostRemove implements Serializable {
        @Identity Serializable id; @PostRemove void foo() {}; @PostRemove void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePostRestore implements Serializable {
        @Identity Serializable id; @PostRestore void foo() {}; @PostRestore void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePreCreate implements Serializable {
        @Identity Serializable id; @PreCreate void foo() {}; @PreCreate void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePreUpdate implements Serializable {
        @Identity Serializable id; @PreUpdate void foo() {}; @PreUpdate void bar() {}
    }
    @SuppressWarnings("serial")
    static class DoublePreRemove implements Serializable {
        @Identity Serializable id; @PreRemove void foo() {}; @PreRemove void bar() {}
    }
   
    
    @Parameters
    public static Collection<Object []> data() {
         Object[][] data = new Object [][] {
         {PostCreate.class, new DoublePostCreate()},
         {PostUpdate.class, new DoublePostUpdate()},
         {PostRemove.class, new DoublePostRemove()},
         {PostRestore.class, new DoublePostRestore()},
         {PreCreate.class, new DoublePreCreate()},
         {PreUpdate.class, new DoublePreUpdate()},
         {PreRemove.class, new DoublePreRemove()}};
         
         return Arrays.asList(data);
    }
         
	private Class<? extends Annotation> clazz;
	private Serializable object;
		
	public PersistentInvalidCallbackTest(Class<? extends Annotation> clazz, Serializable object) {
        super();
        this.clazz = clazz;
        this.object = object;
    }

    @Test
	public void testInvalidCallback() {
        try {
            PersistencyAttributes.getCallback(clazz, object);
            fail();
        } catch (GlooException e) {
            // an exception should get thrown.
        }
	    
	}
}