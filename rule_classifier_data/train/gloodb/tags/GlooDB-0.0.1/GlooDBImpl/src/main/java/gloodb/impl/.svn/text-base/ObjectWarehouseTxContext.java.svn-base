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

import gloodb.storage.Storage;
import gloodb.txmgr.TxContext;

/**
 * ObjectWarehouseTxContext implementation. The internal storage is part of this
 * context and made available to object warehouse operations (they need to get
 * to persitent objects).
 */
class ObjectWarehouseTxContext implements TxContext {
	final Storage storage;

	ObjectWarehouseTxContext(Storage storage) {
		this.storage = storage;
	}
}
