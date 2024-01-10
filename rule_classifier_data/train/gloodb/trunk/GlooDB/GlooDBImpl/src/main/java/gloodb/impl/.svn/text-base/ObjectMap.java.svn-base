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


import gloodb.CannotCompleteTxException;
import gloodb.LockingException;
import gloodb.storage.StorageProxy;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Physical implementation of the object warehouse. The object map
 * contains the main repository hash map, the rollback segment, the transaction
 * locks. It also controls the transaction ids.
 */
class ObjectMap implements Serializable {
	private static final long serialVersionUID = 7313736592524902831L;

	private final SafeReentrantReadWriteLock lock;
	private long txIdSeq;
    private Map<Serializable, StorageProxy> objectMap;
    private transient RollbackSegment rollbackSegment;
    private transient Map<Transaction, ArrayList<Transaction>> transactionRelationships;

    /**
     * Default constructor. Creates an instance and initializes the internal
     * hash map and rollback segment.
     */
    ObjectMap() {
        this.objectMap = Collections.synchronizedMap(new LinkedHashMap<Serializable, StorageProxy>(11, 0.75f, true));
        this.lock = new SafeReentrantReadWriteLock();
    }

    void addRollbackTx(Transaction tx, UndoTransaction rollbackTx) {
    	try {
    		lock.writeLock();
    		getRollbackSegment().addUndoTx(tx, rollbackTx);
    	} finally {
    		lock.writeUnlock();
    	}
    }

    void backupObject(Serializable identity, Serializable objectValue) {
    	try {
    		lock.writeLock();
    		getRollbackSegment().backupObject(identity, objectValue);
    	} finally {
    		lock.writeUnlock();
    	}
	}

    Transaction begin(ObjectWarehouseTxContext ctx, Transaction parentTx) {
    	try {
    		lock.writeLock();
	        Transaction tx = new Transaction(this.txIdSeq++, parentTx);
	        getRollbackSegment().startTx(tx);
	        setChildTxs(parentTx, tx);
	        ctx.storage.begin(tx.getTxId());
	        return tx;
    	} finally {
    		lock.writeUnlock();
    	}
    }

    void commit(ObjectWarehouseTxContext ctx, Transaction tx) {
        if(!tx.canComplete()) throw new CannotCompleteTxException("Transaction has already completed");
        if(!tx.canCommit()) throw new CannotCompleteTxException("Transaction is marked for rollback only");

    	try {
    		lock.writeLock();
	        endChildTransactions(ctx, tx, true);
	
	        // If the transaction doesn't exist throw an exception.
	        if (!getRollbackSegment().existsTx(tx)) {
	            throw new NoTransactionException(String.format("Trying to commit non existent transaction %d", tx.getTxId()));
	        }
	        //State changes are permanent. Therefore the rollback segment for this transaction
	        //can be discarded.
	        getRollbackSegment().finshTx(tx);
	        removeChildTxs(tx);
	        ctx.storage.rollback(tx.getTxId());
	        tx.markCompleted();
    	} finally {
    		lock.writeUnlock();
    	}
    }

    boolean contains(ObjectWarehouseTxContext ctx, Transaction tx, Serializable identity) {
    	try {
    		lock.readLock();
    		if(getRollbackSegment().shouldRestoreFromBackup(tx, identity)) {
	    	    Serializable backupObj = getRollbackSegment().getObjectBackup(identity);
	    		return backupObj != null;	
	    	} else {
	    		return this.objectMap.containsKey(identity);
	    	}
    	} finally {
    		lock.readUnlock();
    	}
	}

    Class<? extends Serializable> getClassForId(Serializable identity) {
    	try {
    		lock.readLock();
	        StorageProxy proxy = getStorageProxy(identity);
	        return proxy != null ? proxy.getObjectClass() : null;
    	} finally {
    		lock.readUnlock();
    	}
    }
    
    ArrayList<Serializable> getIds() {
    	try {
    		lock.readLock();
    		return new ArrayList<Serializable>(this.objectMap.keySet());
    	} finally {
    		lock.readUnlock();
    	}
    }

    StorageProxy getStorageProxy(Serializable id) {
    	try {
    		lock.readLock();
    		return this.objectMap.get(id);
    	} finally {
    		lock.readUnlock();
    	}
    }
    
    boolean isObjectCached(Serializable id) {
    	try {
    		lock.readLock();
	        StorageProxy proxy = getStorageProxy(id);
	        return proxy != null ? proxy.isObjectCached() : false;
    	} finally {
    		lock.readUnlock();
    	}
    }

    /**
     * Locks the object with the given id for the transaction. If the object
     * was already locked by another transaction the following may happen:
     * <ul>
     * <li>if the locking transaction is an ancestor of this transaction the lock
     * is transferred from ancestor to this transaction. From now on this
     * transaction only may modify the object.
     * <li>the locking transaction is unrelated to this transaction. An exception
     * is thrown
     * </ul>
     * @param tx The transaction which is locking the object.
     * @param id The object id.
     */
    void lock(Transaction tx, Serializable id) {
        if (tx == null) {
            // Cannot lock without a transaction.
            throw new LockingException("A non-top level transaction is required to setup lock.");
        }

    	try {
    		lock.writeLock();
    		getRollbackSegment().tryLock(tx, id);
    	} finally {
    		lock.writeUnlock();
    	}
    	
    }

    void putStorageProxy(Serializable id, StorageProxy proxy) {
    	try {
    		lock.writeLock();
    		this.objectMap.put(id, proxy);
    	} finally {
    		lock.writeUnlock();
    	}
    }

    void removeStorageProxy(Serializable id) {
    	try {
    		lock.writeLock();
    		this.objectMap.remove(id);
    	} finally {
    		lock.writeUnlock();
    	}
    }

    Serializable restore(ObjectWarehouseTxContext ctx, Transaction tx, Serializable identity) {
    	try {
    		lock.readLock();
	    	if(getRollbackSegment().shouldRestoreFromBackup(tx, identity)) {
	    		return getRollbackSegment().getObjectBackup(identity);	
	    	} else {
	    		StorageProxy proxy = this.objectMap.get(identity);
	    		return proxy != null ? proxy.restore(ctx.storage, Serializable.class) : null;
	    	}
    	} finally {
    		lock.readUnlock();
    	}
    }

    void rollback(ObjectWarehouseTxContext ctx, Transaction tx) {
    	try {
    		lock.writeLock();
	        if(!tx.canComplete()) throw new CannotCompleteTxException("Transaction has already completed");
	        endChildTransactions(ctx, tx, false);
	
	        // If the transaction doesn't exist throw an exception.
	        if (!getRollbackSegment().existsTx(tx)) {
	            throw new NoTransactionException(String.format("Trying to rollback non existent transaction %d", tx.getTxId()));
	        }
	
	        getRollbackSegment().undoTx(ctx, tx, this);
	        getRollbackSegment().finshTx(tx);
	        removeChildTxs(tx);
	        tx.markCompleted();
    	} finally {
    		lock.writeUnlock();
    	}
    }

    void rollbackPendingTransactions(ObjectWarehouseTxContext ctx) {
    	try {
    		lock.writeLock();
	    	LinkedHashSet<Transaction> transactions = new LinkedHashSet<Transaction>(getRollbackSegment().getPendingTransactions());
	        for (Transaction tx : transactions) {
	            this.rollback(ctx, tx);
	        }
    	} finally {
    		lock.writeUnlock();
    	}
    }

	private void endChildTransactions(ObjectWarehouseTxContext ctx, Transaction tx, boolean commit) {
        // Rollback all child transactions.
        ArrayList<Transaction> childTxs = getChildTxs(tx);
        if (childTxs != null) {
            for (Transaction childTx : childTxs) {
                if (commit) {
                    commit(ctx, childTx);
                } else {
                    rollback(ctx, childTx);
                }                
            }
        }
    }

    private ArrayList<Transaction> getChildTxs(Transaction tx) {
        if (this.transactionRelationships == null) return null;
        ArrayList<Transaction> childTxs = this.transactionRelationships.get(tx);
        if(childTxs == null) return null;
        return new ArrayList<Transaction>(childTxs);
    }

    private synchronized RollbackSegment getRollbackSegment() {
     if (this.rollbackSegment == null) {
            this.rollbackSegment = new RollbackSegment();
        }
        return this.rollbackSegment;
    }

	private void removeChildTxs(Transaction tx) {
        if(transactionRelationships == null) return;
        transactionRelationships.remove(tx);
        Transaction parentTx = tx.getParentTx();
        if(parentTx != null) {
            transactionRelationships.get(parentTx).remove(tx);
        }
    }

	private void setChildTxs(Transaction parentTx, Transaction tx) {
	    if(tx.isTopLevel()) return;
        if(transactionRelationships == null) transactionRelationships = Collections.synchronizedMap(new HashMap<Transaction, ArrayList<Transaction>>());
        ArrayList<Transaction> childTxs = transactionRelationships.get(parentTx);
        if(childTxs == null) {
            childTxs = new ArrayList<Transaction>();
            transactionRelationships.put(parentTx, childTxs);
        }
        childTxs.add(tx);
    }
}
