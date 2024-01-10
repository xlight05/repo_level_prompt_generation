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
 * Storage factory. Specialized implementations should implement this interface
 * to provide the specific storage strategy.
 */
public interface StorageFactory {
	/**
	 * Storage builder method. Creates a specific storage. Override this method
	 * to provide the specific storage implementation.
	 * 
	 * @param configuration
	 *            The storage configuration.
	 * @param interceptor
	 *            The storage interceptor if any.
	 * @return The new storage.
	 */
	Storage buildStorage(StorageConfiguration configuration, StorageInterceptor interceptor);
}
