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
package gloodb.storage;

/**
 * Tagging interface for storage strategies.
 * 
 */
public interface StorageStrategy {
    /**
     * StorageConfiguration getter.
     * @return The storage configuration.
     */
    StorageConfiguration getConfiguration();

    /**
     * Storage factory getter.
     * @return The storage factory.
     */
	StorageFactory getFactory();

	/**
	 * Returns any storage interceptor associated with the storage
	 * @return The storage interceptor
	 */
    StorageInterceptor getInterceptor();
}
