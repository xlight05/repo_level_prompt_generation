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
 * The repository will automatically restore the interceptor (which must be a
 * persistent singleton) and invoke the before / after callbacks.
 * 
 *<p>The repository will automatically create the interceptor using the default 
 * constructor and assuming the interceptor is a singleton (the class is annotated
 * with &#64;Identity)</p>
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
