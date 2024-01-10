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

import gloodb.Cloner;
import gloodb.GlooException;
import gloodb.KeyViolationException;
import gloodb.PersistencyAttributes;
import gloodb.PersistentNotFoundException;
import gloodb.storage.Storage;
import gloodb.storage.StorageProxy;
import gloodb.txmgr.TxLogWriteTransaction;
import gloodb.txmgr.TxContext;
import java.io.Serializable;
import java.util.Date;

/**
 * Stores an object as an atomic operation.
 */
class StoreTransaction implements TxLogWriteTransaction, Cloneable {
	private static final long serialVersionUID = -1612020424891749194L;

	private final Serializable persistentObject;
	private final Transaction tx;
	private final StoreOperation operation; // Transaction state.
	private transient ObjectWarehouseTxContext ctx;
	private transient ObjectMap objectMap;
	private transient Serializable identity;
	private transient StorageProxy proxy;

	public StoreTransaction(Serializable persistentObject, Transaction tx, StoreOperation operation) {
		this.tx = tx;
		this.persistentObject = Cloner.deepCopy(persistentObject);
		this.operation = operation;
	}

	public Serializable execute(TxContext context, Serializable managedObject, Date timestamp) {
		prepareTransaction(context, managedObject);
		Serializable oldValue = restoreOldValue();
		if (StoreOperation.Create.equals(this.operation)) {
			return doCreate();
		} else if (StoreOperation.Update.equals(this.operation)) {
			return doUpdate(oldValue);
		} else if (StoreOperation.CreateOrUpdate.equals(this.operation)) {
			return doCreateOrUpdate(oldValue);
		} else {
			throw new GlooException("Unknown store operation: must be Create, Update or CreateOrUpdate");
		}
	}

	private Serializable doCreate() {
		if (this.proxy != null) {
			throw new KeyViolationException();
		}
		this.lockObject();
		Storage storage = this.ctx.storage;
		this.proxy = storage.buildStorageProxy(storage.buildAddress());
		this.proxy.store(this.persistentObject);
		this.objectMap.putStorageProxy(this.identity, this.proxy);

		// backup the old object value. The object backup is used
		// during the restore operation:
		// - restoring from this transaction or its children returns
		// the actual object
		// - restoring from another transaction return the backup object.
		if (tx != null) {
			this.objectMap.backupObject(this.identity, null);
		}

		// Create the rollback transaction
		this.createRollBackTx();
		// Return the persisted value.
		return this.proxy.restore(this.ctx.storage, Serializable.class);
	}

	private void lockObject() {
		// Lock the object to disallow changes by other transactions.
		if (this.tx != null) {
			this.objectMap.lock(this.tx, this.identity);
		}
	}

	private void createRollBackTx() {
		if (this.tx != null) {
			// Create a rollback transaction using the new object value.
			this.objectMap.addRollbackTx(this.tx, new UndoNewTransaction(this.identity));
		}
	}

	private Serializable doCreateOrUpdate(Serializable oldValue) {
		if (this.proxy == null) {
			return doCreate();
		} else {
			return doUpdate(oldValue);
		}
	}

	private Serializable doUpdate(Serializable oldValue) {
		if (this.proxy == null) {
			throw new PersistentNotFoundException();
		}
		if (this.tx != null) {
			// lock the object to prevent changes from other transactions.
			this.objectMap.lock(this.tx, this.proxy.getId());

			// Create a rollback update transaction using the old value of the
			// object.
			this.objectMap.addRollbackTx(this.tx, new UndoUpdateTransaction(this.proxy.getId(), oldValue));

			// backup the old object value. The object backup is used
			// during the restore operation:
			// - restoring from this transaction or its children returns
			// the actual object
			// - restoring from another transaction return the backup object.
			this.objectMap.backupObject(this.identity, oldValue);
		}
		PersistencyAttributes.incrementVersion(oldValue, this.persistentObject);
		this.proxy.store(this.persistentObject);
		// Return the persisted value.
		return this.proxy.restore(this.ctx.storage, Serializable.class);

	}

	private void prepareTransaction(TxContext context, Serializable managedObject) {
		this.ctx = (ObjectWarehouseTxContext) context;
		this.objectMap = (ObjectMap) managedObject;
		this.identity = PersistencyAttributes.getIdForObject(this.persistentObject);
		this.proxy = this.objectMap.getStorageProxy(this.identity);
	}

	private Serializable restoreOldValue() {
		Serializable oldValue = null;
		if (this.proxy != null) {
			oldValue = this.proxy.restore(this.ctx.storage, Serializable.class);
		}
		return oldValue;
	}

	@Override
	public Object clone() {
		Transaction txCopy = Cloner.deepCopy(tx);
		StoreTransaction copy = new StoreTransaction(Cloner.deepCopy(this.persistentObject), txCopy, this.operation);
		return copy;
	}
}
