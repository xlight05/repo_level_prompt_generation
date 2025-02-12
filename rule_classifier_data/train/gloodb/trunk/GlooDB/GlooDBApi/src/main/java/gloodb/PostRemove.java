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
 * Tagging annotation for PostRemove callback methods. A single method per class
 * can have this annotation. The method can have any visibility. Its signature must be:
 * <pre>
 public class Foo implements Serializable {
     ...
     &#64;PostRemove
     private void bar(Repository repository) {
        ... 
     }
 }
 </pre>
 * This method is invoked after the object is removed from the repository.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostRemove {
}
