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

import java.lang.annotation.Annotation;
import java.util.HashSet;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * An observer test set, agnostic of the repository configuration used.
 */
public class InterceptorAndCallbacksTestBase {
    private Repository repository;
    private SimpleIntercepted intercepted;

    public InterceptorAndCallbacksTestBase(Repository repository, SimpleIntercepted intercepted) {
        this.repository = repository;
        this.intercepted = intercepted;
    }

    /**
     * As objects are added to the repositories, observers are called back to
     * update on changes of objects of interest.
     */
    @Test
    public void testInterceptObject() {
        try {
            intercepted = repository.create(intercepted);
            intercepted = repository.update(intercepted);
            intercepted = (SimpleIntercepted)repository.remove(intercepted.getId());

            assertInterceptor(((SimpleInterceptor)repository.restore(SimpleInterceptor.class)).getAfter());
            assertInterceptor(((SimpleInterceptor)repository.restore(SimpleInterceptor.class)).getBefore());
        } finally {
            // Cleanup after.
            this.repository.remove(intercepted.getId());
        }
    }

    private void assertInterceptor(HashSet<Class<? extends Annotation>> annotationSet) {
        assertThat(annotationSet.contains(PostCreate.class), is(true));
        assertThat(annotationSet.contains(PostUpdate.class), is(true));
        assertThat(annotationSet.contains(PostRemove.class), is(true));
        assertThat(annotationSet.contains(PreCreate.class), is(true));
        assertThat(annotationSet.contains(PreUpdate.class), is(true));
        assertThat(annotationSet.contains(PreRemove.class), is(true));
    }
 }
