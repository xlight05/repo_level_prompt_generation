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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * An observer test set, agnostic of the repository configuration used.
 */
public class InvalidInterceptorTest {
 
     @Test
    public void testInvalidInterceptor() {
        try {
            PersistencyAttributes.getPersistentInfo(new Interceptor<Serializable>() {
                private static final long serialVersionUID = 1L;
                @SuppressWarnings("unused")
                @Identity
                String id = "bla";

                @Override
                public void before(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) {
                    // do nothing
                }

                @Override
                public void after(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) {
                    // do nothing
                }
            });
            fail("Exception expected");
        } catch (GlooException ex) {
            assertEquals("Interceptors must use their class as identity. Annotate the class  (and only the class) with @Identity", ex.getMessage());
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void testInvalidInterceptor2() {
        try {
            PersistencyAttributes.getPersistentInfo(new Interceptor<Serializable>() {
                private static final long serialVersionUID = 1L;
                
                @Identity
                Class<? extends Interceptor<Serializable>> clazz = getClass();
                
                @Identity(idx = 1)
                String id = "bla";

                @Override
                public void before(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) {
                    // do nothing
                }

                @Override
                public void after(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) {
                    // do nothing
                }
            });
            fail("Exception expected");
        } catch (GlooException ex) {
            assertEquals("Interceptors must use their class as identity. Annotate the class  (and only the class) with @Identity", ex.getMessage());
        }
    }
    
    @Test
    public void testNullPersistent() {
        assertNull(PersistencyAttributes.getInterceptorIds(null));
    }
}
