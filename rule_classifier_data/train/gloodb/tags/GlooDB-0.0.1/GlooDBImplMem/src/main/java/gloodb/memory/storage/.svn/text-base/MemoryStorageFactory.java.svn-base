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
package gloodb.memory.storage;

import java.util.HashMap;

import gloodb.storage.Storage;
import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageInterceptor;

class MemoryStorageFactory implements StorageFactory {
	private static final HashMap<String, MemoryStorage> instances = new HashMap<String, MemoryStorage>();

	@Override
	public Storage buildStorage(StorageConfiguration configuration, StorageInterceptor interceptor) {
		MemoryStorage memoryStorage = instances.get(configuration.getNameSpace());
		if(memoryStorage == null) {
			memoryStorage = new MemoryStorage();
			instances.put(configuration.getNameSpace(), memoryStorage);
		}
		return interceptor != null? interceptor.setStorage(memoryStorage) : memoryStorage;
	}
}
