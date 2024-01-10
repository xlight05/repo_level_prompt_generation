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
package gloodb.impl;

/**
 * Enumeration of store operations:
 * <ul><li>Create: creates a new object. Throws a KeyViolationException if the
 * object already exists in the repository.
 * <li>Update: updates an existent object. Throws a PersistentNotFound if the object
 * doesn't exist in the repository.
 * <li>CreateOrUpdate: updates an existent object or, if the repository doesn't
 * contain an object with the same identity, it creates a new object.
 * </ul>
 */
enum StoreOperation {

    /**
     * Create operation: creates a new object. Throws a KeyViolationException if the
     * object already exists in the repository.
     */
    Create,
    /**
     * Update: updates an existent object. Throws a PersistentNotFound if the object
     * doesn't exist in the repository.
     */
    Update,
    /**
     * CreateOrUpdate: updates an existent object or, if the repository doesn't
     * contain an object with the same identity, it creates a new object.
     */
    CreateOrUpdate
}
