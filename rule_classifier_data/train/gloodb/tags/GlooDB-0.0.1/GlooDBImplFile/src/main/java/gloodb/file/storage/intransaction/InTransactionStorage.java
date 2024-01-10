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
package gloodb.file.storage.intransaction;

import gloodb.storage.Address;
import gloodb.storage.Storage;
import gloodb.storage.StorageFullException;
import gloodb.storage.StorageProxy;

import java.io.Serializable;
import java.math.BigInteger;

class InTransactionStorage implements Storage {
	public InTransactionStorage() {}

	@Override
	public Address buildAddress() {
		return new InTransactionAddress();
	}

	@Override
	public void remove(Address address) {
		
	}

	@Override
	public <P extends Serializable> P restore(Class<P> clazz, Address address) {
		return null;
	}

	@Override
	public void restoreSnapshot(BigInteger version) {
	}

	@Override
	public void start(BigInteger startVersion) {
	}

	@Override
	public void store(Serializable persistentObject, Address address) throws StorageFullException {
		
	}

	@Override
	public void takeSnapshot(BigInteger version) {
		
	}

    @Override
    public StorageProxy buildStorageProxy(Address address) {
        return new InTransactionStorageProxy(address);
    }

    @Override
    public void begin(long txId) {
        // Non-transactional storage
    }

    @Override
    public void commit(long txId) {
        // Non-transactional storage
    }

    @Override
    public void rollback(long txId) {
        // Non-transactional storage
    }
}
