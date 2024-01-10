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
package gloodb.file.storage.intransaction;

import java.util.HashMap;

import gloodb.storage.Storage;
import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageInterceptor;

class InTransactionStorageFactory implements StorageFactory {
	private static final HashMap<String, InTransactionStorage> instances = new HashMap<String, InTransactionStorage>();

	@Override
	public Storage buildStorage(StorageConfiguration configuration, StorageInterceptor interceptor) {
		InTransactionStorage storage = instances.get(configuration.getNameSpace());
		if(storage == null) {
			storage = new InTransactionStorage();
			instances.put(configuration.getNameSpace(), storage);
		}
		return interceptor!= null? interceptor.setStorage(storage) : storage;
	}
}
