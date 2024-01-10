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
import gloodb.LockingException;
import gloodb.txmgr.TxContext;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Rollback segment implementation. The rollback segment temporarily stores the
 * undo operations required to rollback a transaction. On commit, the undo operation
 * list is thrown away. On rollback, the undo operations are executed and the
 * state of the repository is restored back to where it was before the begin
 * transaction statement. Between begin and commit / rollback, all changed objects
 * are locked such that other transactions cannot further change them. Preventing
 * concurrent changes from other transactions allows to safely undo all changes
 * at rollback time.
 * <p>Nested transactions lock create their own rollback segment and object locks.
 * On commit, the rollback segment  and locks are passed to the parent transaction.
 * This allows the parent transaction to rollback all changes if necessary. A parent
 * transaction cannot change any of the objects lock by its nested transactions.
 * In contrast, a nested transaction can take over a lock of its parent at any time.
 * </p>
 */
class RollbackSegment {
    private final HashMap<Transaction, List<UndoTransaction>> rollbackSegment;
    private final HashMap<Transaction, Set<Serializable>> transactionLocks;
    private final HashMap<Serializable, Transaction> lockedObjects;
    private final HashMap<Serializable, Serializable> objectBackups;

    /**
     * Creates the rollback segment.
     */
    public RollbackSegment() {
        this.rollbackSegment = new HashMap<Transaction, List<UndoTransaction>>();
        this.transactionLocks = new HashMap<Transaction, Set<Serializable>>();
        this.lockedObjects = new HashMap<Serializable, Transaction>();
        this.objectBackups = new HashMap<Serializable, Serializable>();
    }

    /**
     * Starts the transaction processing for the specified transaction. A
     * rollback segment is created for this transaction.
     * @param tx The transaction.
     */
    public void startTx(Transaction tx) {
		rollbackSegment.put(tx, new LinkedList<UndoTransaction>());
    }

    /**
     * Finishes the transaction processing for the specified transaction. For top
     * level transactions, the locks, backups and rollback segment are cleared. For
     * nested transactions, the locks, backups and rollback segment are passed to the parent
     * transaction.
     * @param tx The transaction.
     */
    public void finshTx(Transaction tx) {
        // Null transactions don't lock. This is an error
        if (tx == null) {
            throw new GlooException("Illegal unlock. " +
                    "Are you trying to commit / rollback a null transaction?");
        }

        // Remove objects locked by this transaction
        Transaction parentTx = tx.getParentTx();
        Set<Serializable> lockedByTx = getObjectsLockedByTx(tx, true);
        
        // If this is not a top level transaction, the rollback segment is
        // transferred to the parent transaction.
        List<UndoTransaction> childUndoList = this.rollbackSegment.remove(tx);
        if (parentTx != null) {
            // If this transaction has a parent, the locks are transferred back to the parent.
            // When reaching the top level transaction all locks are removed.
            for (Serializable id : lockedByTx) {
                this.lockedObjects.remove(id);
                setLock(id, parentTx);
            }
            // Add this undo list at the head of the parent's undo list.
            if(childUndoList != null) {
                this.rollbackSegment.get(parentTx).addAll(0, childUndoList);
            }
        } else {
        	// If the parent transaction is null, remove the locks and
        	// the object backups.
            for (Serializable id : lockedByTx) {
                this.lockedObjects.remove(id);
                this.objectBackups.remove(id);
            }        	
        }
    }

    /**
     * Returns the set of pending transaction ids.
     * @return The set of pending transaction ids.
     */
    public Set<Transaction> getPendingTransactions() {
		return this.rollbackSegment.keySet();
    }

    /**
     * Checks if the transaction with the specified id exists (if it does
     * it should have rollback segment).
     * @param tx The transaction
     * @return True if the transaction exists.
     */
    public boolean existsTx(Transaction tx) {
		return this.rollbackSegment.containsKey(tx);
   }

    /**
     * Adds an undo transaction to the rollback segment. Various operations
     * use this api to specify how they can get undone.
     * @param tx The transaction.
     * @param rollbackTx The undo transaction.
     */
    public void addUndoTx(Transaction tx, UndoTransaction rollbackTx) {
        List<UndoTransaction> rollbackTxs = this.rollbackSegment.get(tx);
        // Add at the head of the list (the undo list is FIFO).
        rollbackTxs.add(0, rollbackTx);
    }

    /**
     * Runs all the undo transactions in the rollback segment. In the end
     * the state of the repository is restored to its state before the transaction
     * began.
     * @param ctx The transaction context.
     * @param tx The transaction.
     * @param objectWarehouse The object warehouse which stores the objects.
     */
    public void undoTx(TxContext ctx, Transaction tx, ObjectMap objectWarehouse) {
        List<UndoTransaction> rollbackTxs = this.rollbackSegment.get(tx);
        for (UndoTransaction rollbackTx : rollbackTxs) {
            rollbackTx.execute(ctx, objectWarehouse, new Date(System.currentTimeMillis()));
        }
        rollbackTxs.clear();
    }

    /**
     * Locks an object for this transaction.
     * @param id The object id.
     * @param tx The transaction.
     */
    void setLock(Serializable id, Transaction tx) {
        this.lockedObjects.put(id, tx);
        Set<Serializable> lockedByTx = getObjectsLockedByTx(tx, false);
        if (!lockedByTx.contains(id)) {
            lockedByTx.add(id);
        }
    }

    /**
     * Returns the transaction locking the object with the given id.
     * @param id The object id.
     * @return The transaction locking this id.
     */
    Transaction getLockingTx(Serializable id) {
		return this.lockedObjects.get(id);
    }

    Set<Serializable> getObjectsLockedByTx(Transaction tx, boolean remove) {
        Set<Serializable> lockedByTx = this.transactionLocks.get(tx);
        if (lockedByTx == null) {
            lockedByTx = new HashSet<Serializable>();
            this.transactionLocks.put(tx, lockedByTx);
        }
        if (remove) {
            this.transactionLocks.remove(tx);
        }
        return lockedByTx;
    }

	public void backupObject(Serializable identity, Serializable objectValue) {
		if(!this.objectBackups.containsKey(identity)) {
			this.objectBackups.put(identity, objectValue);
		}
	}

	public Serializable getObjectBackup(Serializable identity) {
		// Return a copy to maintain isolation.
		return Cloner.deepCopy(this.objectBackups.get(identity));
	}

	public boolean shouldRestoreFromBackup(Transaction tx, Serializable identity) {
		// If the restore is non-transaction then restore from the repository
		if(tx == null) return false;
		Transaction ownerTx = getLockingTx(identity);
		// No transaction has this object; restore from repository
		if(ownerTx == null) return false;
		if(ownerTx.equals(tx)) return false;
        // Restore from backup only if tx is not the owner transaction 
        // nor one of owner's children.
		for(Transaction txIter = tx; txIter != null; txIter = txIter.getParentTx()) {
			if(txIter.equals(ownerTx)) return false;
		}
		// Restore from backup
		return true;
	}

    public void tryLock(Transaction tx, Serializable id) {
        Transaction lockingTx = getLockingTx(id);
        if (lockingTx == null) {
            // the object is not locked; it's ok to lock it.
            setLock(id, tx);
        } else if (lockingTx.equals(tx)) {
            // The object is locked by this same transaction.
            // No need to do anything.
        } else if (isLockedByAncestor(lockingTx, tx)) {
            // the object is locked by an ancestor. It is ok to lock it
            setLock(id, tx);
        } else {
            // The object is already locked by another transaction
            throw new LockingException();
        }
    }

    private boolean isLockedByAncestor(Transaction lockingTx, Transaction tx) {
        Transaction parentTx = tx.getParentTx();
        if (parentTx == null) {
            return false;
        }
        if (lockingTx.equals(parentTx)) {
            return true;
        }
        return isLockedByAncestor(lockingTx, parentTx);
    }
	
}
