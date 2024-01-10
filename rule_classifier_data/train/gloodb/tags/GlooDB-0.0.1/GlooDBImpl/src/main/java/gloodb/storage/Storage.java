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
 * Physical storage of persistent objects. Objects are stored / restored based
 * on an address.
 */
public interface Storage {

	/**
	 * Address builder method. Creates a new address compatible with this
	 * specific storage type.
	 * 
	 * @return The new address
	 */
	public Address buildAddress();
	
	/**
	 * Creates a storage proxy for the given address.
	 * @param address The storage address.
	 * @return The storage proxy.
	 */
	public StorageProxy buildStorageProxy(Address address);

	/**
	 * Removes an object at the specified address.
	 * 
	 * @param address
	 *            The address to remove.
	 */
	void remove(Address address);

	/**
	 * Restores the object at the specified address.
	 * 
	 * @param clazz
	 *            The return class.
	 * @param address
	 *            The address to restore from.
	 * @param <P>
	 *            The return type.
	 * @return The restored object.
	 */
	<P extends Serializable> P restore(Class<P> clazz, Address address);

	/**
	 * Restores the value of the storage for the specified version. This is
	 * required when loading the state from an existing transaction log.
	 * 
	 * @param version The repository version.
	 */
	void restoreSnapshot(BigInteger version);

	/**
	 * Stores the persistent object.
	 * 
	 * @param persistentObject
	 *            The persistent object to store.
	 * @param address
	 *            The address where the object is stored. This is an in/out
	 *            parameter. The storage attempts to reuse the address (if
	 *            possible) during updates.
	 * @throws StorageFullException
	 *             If the physical storage is full.
	 */
	void store(Serializable persistentObject, Address address) throws StorageFullException;

	/**
	 * Takes a snapshot of the storage. This is required in order to synchronize
	 * transaction logs with storage snapshots.
	 * 
	 * @param version
	 *            The transaction log version to synchronize with.
	 */
	void takeSnapshot(BigInteger version);

	/**
	 * Starts the storage from the given version.
	 * 
	 * @param startVersion
	 *            The storage from the given version.
	 */
	void start(BigInteger startVersion);
	
	void begin(long txId);
	
	void commit(long txId);
	
	void rollback(long txId);
}
