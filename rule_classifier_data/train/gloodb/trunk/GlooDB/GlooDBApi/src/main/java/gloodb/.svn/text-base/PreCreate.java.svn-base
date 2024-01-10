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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Tagging annotation for PreCreate callback methods. A single method per class
 * can carry this annotation. Calls to Pre* methods are unguarded, hence the methods must 
 * be thread safe.
 * </p>
 * The method can have any visibility. Its signature must be:
 * <pre>
 public class Foo implements Serializable {
     ...
     &#64;PreCreate
     private void bar(Repository repository) {
        ...
     }
 }
 </pre>
 * or 
 <pre>
 public class Foo implements Serializable {
     ...
     &#64;PreCreate
     private Collection<Dependency> bar(Repository repository) {
        ...
     }
 }
 </pre>
 * <p>
 * The returned dependencies are used for synchronization. The repository will
 * read lock or write lock the returned dependencies.
 * </p>
 * This method is invoked before the object is created in the repository.
 * Throwing an exception from this method prevents the object Create.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreCreate {
}
