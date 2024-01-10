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

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Storage interceptor class.
 *
 */
public class StorageInterceptor implements Storage {
    
    private Storage storage;
    
    /**
     * Sets the intercepted storage
     * @param storage The storage to intercept.
     * @return The intercepted storage.
     */
    public Storage setStorage(Storage storage) {
        this.storage = storage;
        return this;
    }

    @Override
    public Address buildAddress() {
        return storage.buildAddress();
    }

    @Override
    public StorageProxy buildStorageProxy(Address address) {
        return storage.buildStorageProxy(address);
    }

    @Override
    public void remove(Address address) {
        storage.remove(address);
    }

    @Override
    public <P extends Serializable> P restore(Class<P> clazz, Address address) {
        return storage.restore(clazz, address);
    }

    @Override
    public void restoreSnapshot(BigInteger version) {
        storage.restoreSnapshot(version);
    }

    @Override
    public void store(Serializable persistentObject, Address address) throws StorageFullException {
        storage.store(persistentObject, address);
    }

    @Override
    public void takeSnapshot(BigInteger version) {
        storage.takeSnapshot(version);
    }

    @Override
    public void start(BigInteger startVersion) {
        storage.start(startVersion);
    }

    @Override
    public void begin(long txId) {
        storage.begin(txId);
    }

    @Override
    public void commit(long txId) {
        storage.begin(txId);
    }

    @Override
    public void rollback(long txId) {
        storage.rollback(txId);
    }

}
