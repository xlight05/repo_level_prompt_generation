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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Physical implementation of the object warehouse. The object map
 * contains the main repository hash map, the rollback segment, the transaction
 * locks. It also controls the transaction ids.
 */
class ObjectMap implements Serializable {
	private static final long serialVersionUID = 7313736592524902831L;

	private long txIdSeq;
    private LinkedHashMap<Serializable, StorageProxy> objectMap;
    private transient RollbackSegment rollbackSegment;
    private transient HashMap<Transaction, ArrayList<Transaction>> transactionRelationships;

    /**
     * Default constructor. Creates an instance and initializes the internal
     * hash map and rollback segment.
     */
    ObjectMap() {
        this.objectMap = new LinkedHashMap<Serializable, StorageProxy>(11, 0.75f, true);
    }

    void addRollbackTx(Transaction tx, UndoTransaction rollbackTx) {
        getRollbackSegment().addUndoTx(tx, rollbackTx);
    }

    void backupObject(Serializable identity, Serializable objectValue) {
		this.rollbackSegment.backupObject(identity, objectValue);
	}

    Transaction begin(ObjectWarehouseTxContext ctx, Transaction parentTx) {
        Transaction tx = new Transaction(this.txIdSeq++, parentTx);
        getRollbackSegment().startTx(tx);
        setChildTxs(parentTx, tx);
        ctx.storage.begin(tx.getTxId());
        return tx;
    }

    void commit(ObjectWarehouseTxContext ctx, Transaction tx) {
        if(!tx.canComplete()) throw new CannotCompleteTxException("Transaction has already completed");
        if(!tx.canCommit()) throw new CannotCompleteTxException("Transaction is marked for rollback only");

        endChildTransactions(ctx, tx, true);

        // If the transaction doesn't exist throw an exception.
        if (!getRollbackSegment().exists(tx)) {
            throw new NoTransactionException(String.format("Trying to commit non existent transaction %d", tx.getTxId()));
        }
        //State changes are permanent. Therefore the rollback segment for this transaction
        //can be discarded.
        getRollbackSegment().finshTx(tx);
        removeChildTxs(tx);
        ctx.storage.rollback(tx.getTxId());
        tx.markCompleted();
    }

    boolean contains(ObjectWarehouseTxContext ctx, Transaction tx, Serializable identity) {
    	if(this.rollbackSegment.shouldRestoreFromBackup(tx, identity)) {
    	    Serializable backupObj = this.rollbackSegment.getObjectBackup(identity);
    		return backupObj != null;	
    	} else {
    		return this.objectMap.containsKey(identity);
    	}
	}

    Class<? extends Serializable> getClassForId(Serializable identity) {
        StorageProxy proxy = getStorageProxy(identity);
        return proxy != null ? proxy.getObjectClass() : null;
    }
    
    LinkedList<Serializable> getIds() {
        return new LinkedList<Serializable>(this.objectMap.keySet());
    }

    StorageProxy getStorageProxy(Serializable id) {
        return this.objectMap.get(id);
    }
    
    boolean isObjectCached(Serializable id) {
        StorageProxy proxy = getStorageProxy(id);
        return proxy != null ? proxy.isObjectCached() : false;
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

        getRollbackSegment().tryLock(tx, id);
    }

    void putStorageProxy(Serializable id, StorageProxy proxy) {
        this.objectMap.put(id, proxy);
    }

    void removeStorageProxy(Serializable id) {
        this.objectMap.remove(id);
    }

    Serializable restore(ObjectWarehouseTxContext ctx, Transaction tx, Serializable identity) {
    	if(this.rollbackSegment.shouldRestoreFromBackup(tx, identity)) {
    		return this.rollbackSegment.getObjectBackup(identity);	
    	} else {
    		StorageProxy proxy = this.objectMap.get(identity);
    		return proxy != null ? proxy.restore(ctx.storage, Serializable.class) : null;
    	}
    }

    void rollback(ObjectWarehouseTxContext ctx, Transaction tx) {
        if(!tx.canComplete()) throw new CannotCompleteTxException("Transaction has already completed");
        endChildTransactions(ctx, tx, false);

        // If the transaction doesn't exist throw an exception.
        if (!getRollbackSegment().exists(tx)) {
            throw new NoTransactionException(String.format("Trying to rollback non existent transaction %d", tx.getTxId()));
        }

        getRollbackSegment().undoTx(ctx, tx, this);
        getRollbackSegment().finshTx(tx);
        removeChildTxs(tx);
        tx.markCompleted();
    }

    void rollbackPendingTransactions(ObjectWarehouseTxContext ctx) {
    	LinkedHashSet<Transaction> transactions = new LinkedHashSet<Transaction>(getRollbackSegment().getPendingTransactions());
        for (Transaction tx : transactions) {
            this.rollback(ctx, tx);
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

    private RollbackSegment getRollbackSegment() {
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
        if(transactionRelationships == null) transactionRelationships = new HashMap<Transaction, ArrayList<Transaction>>();
        ArrayList<Transaction> childTxs = transactionRelationships.get(parentTx);
        if(childTxs == null) {
            childTxs = new ArrayList<Transaction>();
            transactionRelationships.put(parentTx, childTxs);
        }
        childTxs.add(tx);
    }
}
