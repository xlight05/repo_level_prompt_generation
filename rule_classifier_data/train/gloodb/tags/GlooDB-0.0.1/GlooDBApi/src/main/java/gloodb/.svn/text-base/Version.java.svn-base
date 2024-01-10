/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tagging annotation for version field. Fields annotated with @Version are used
 * as object version. A {@link VersionManager } implements the versioning mechanism.
 * Every update will increment the object version.
 *
 * Versioned objects are also optimistically locked.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Version {
    /**
     * Specifies the class that manages the version in the annotated field.
     * @return The version manager class.
     */
    Class<? extends VersionManager> manager() default NumberVersionManager.class;
}
