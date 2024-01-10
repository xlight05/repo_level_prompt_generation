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

package gloodb.file.storage.block;

import gloodb.storage.Storage;
import gloodb.storage.StorageConfiguration;
import gloodb.storage.StorageFactory;
import gloodb.storage.StorageInterceptor;

class BlockStorageFactory implements StorageFactory {
	@Override
	public Storage buildStorage(StorageConfiguration configuration, StorageInterceptor interceptor) {
        return interceptor != null? interceptor.setStorage(new BlockStorage((BlockStorageConfiguration) configuration)) : 
                                    new BlockStorage((BlockStorageConfiguration) configuration);
	}

}
