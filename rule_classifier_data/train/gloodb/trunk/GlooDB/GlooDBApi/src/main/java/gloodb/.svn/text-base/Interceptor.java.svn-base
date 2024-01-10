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

/**
 * Tagging interface for persistence interceptors. Implementations will
 * intercept the Pre/Post GlooDB callbacks. To intercept a persistence class,
 * annotate the class with &#64;Intercepted and specify the interceptor class.
 * The interceptor instances must pre-exist before intercepted objects are updated.
 * 
 *<p>The programmer can use the {@link RepositoryInitializer}  to create interceptors
 *on repository startup.</p>
 */
public interface Interceptor<T extends Serializable> extends Serializable {
    /**
     * Called before invoking the intercepted callback.
     * 
     * @param annotationClass
     *            Indicates the persistence callback which is intercepted. Can
     *            be PreCreate.class PostCreate.class, PreUpdateUpdate.class,
     *            PostUpdate.class, PreRemove.class, PostRemove.class,
     *            PostRestore.class
     * @param repository
     *            The repository
     * @param persistentObject
     *            The persistent object affected.
     * @throws Exception if processing fails.
     */
    void before(Class<? extends Annotation> annotationClass, Repository repository, T persistentObject) throws Exception;

    /**
     * Called after invoking the intercepted callback.
     * 
     * @param annotationClass
     *            Indicates the persistence callback which is intercepted. Can
     *            be PreCreate.class PostCreate.class, PreUpdate.class,
     *            PostUpdate.class, PreRemove.class, PostRemove.class,
     *            PostRestore.class
     * @param repository
     *            The repository
     * @param persistentObject
     *            The persistent object affected.
     * @throws Exception if processing fails.
     * 
     */
    void after(Class<? extends Annotation> annotationClass, Repository repository, T persistentObject) throws Exception;
}
