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
package gloodb.file.storage.block;

import java.io.Serializable;

import gloodb.Cloner;
import gloodb.GlooException;
import gloodb.PersistencyAttributes;
import gloodb.storage.Address;
import gloodb.storage.Storage;
import gloodb.storage.StorageFullException;
import gloodb.storage.StorageProxy;

class BlockStorageProxy implements StorageProxy {
    private static final long serialVersionUID = -8796521238307973821L;

    private Serializable id;
    private Address address;
    private Serializable cachedObject;
    private Class<? extends Serializable> classOfObject;


    /**
     * Creates a new Storage proxy.
     * @param address The object address.
     */
    public BlockStorageProxy(Address address) {
        this.address = address;
        this.id = null;
        this.classOfObject = null;
        this.cachedObject = null;
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#store(java.io.Serializable)
     */
    @Override
    public void store(Serializable persistentObject) throws StorageFullException {
        if (persistentObject != null) {
        	this.id = PersistencyAttributes.getIdForObject(persistentObject);
            this.classOfObject = persistentObject.getClass();
            // store a copy of the persistent object
            // this is required to ensure isolation and
            // to ensure consistent behavior when using
            // gloo over a network or embedded.
            this.cachedObject = copy(persistentObject);
        } else {
            throw new GlooException("Cannot store null object");
        }
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#restore(gloodb.storage.Storage, java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <P extends Serializable> P restore(Storage storage, Class<P> clazz) {
        // return from the cache if anything there
        if (this.cachedObject == null) {
        		this.cachedObject = storage.restore(clazz, this.address);
        }
        // return a copy of the stored object
        // this is required to ensure isolation and
        // to ensure consistent behavior when using
        // gloo over a network or embedded.
        return (P) copy(this.cachedObject);
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#remove(gloodb.storage.Storage)
     */
    @Override
    public void remove(Storage storage) {
		storage.remove(this.address);
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#flush(gloodb.storage.Storage, boolean)
     */
    @Override
    public void flush(Storage storage, boolean removeFromCache) {
        try {
            storage.store(this.cachedObject, this.address);
            if (removeFromCache) {
                this.cachedObject = null;
            }
        } catch (StorageFullException e) {
            throw new GlooException(e);
        }
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#getObjectClass()
     */
    @Override
    public Class<? extends Serializable> getObjectClass() {
		return this.classOfObject;
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#isObjectCached()
     */
    @Override
    public boolean isObjectCached() {
		return this.cachedObject != null;
    }

    /* (non-Javadoc)
     * @see gloodb.storage.StorageProxy#getId()
     */
    @Override
    public Serializable getId() {
		return this.id;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
    }
    
    /**
     * Two proxies are equal if they proxy the same identity.
     * @param obj The right StorageProxy.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlockStorageProxy other = (BlockStorageProxy) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
        }
        return true;
    }

    Serializable copy(Serializable right) {
		return Cloner.deepCopy(right);
    }
}