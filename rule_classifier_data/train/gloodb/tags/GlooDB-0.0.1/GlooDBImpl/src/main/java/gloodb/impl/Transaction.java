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

import gloodb.LockingException;

import java.io.Serializable;

/**
 * Implementation of ACID transactions. Gloo database can operate in both
 * transactional (allows rollbacks) and non-transactional mode (changes are immediate).
 * The two modes are exclusive, the repository will not allow transacted operations
 * and non-transacted operation same time (they result in update and locking ambiguity).
 *
 * For transactional mode, begin a transaction and pass the transaction around.
 * When done, commit or rollback the transaction. For non-transactional mode,
 * pass null as a transaction.
 *
 * In transactional mode, modified objects are locked by the owner transaction.
 * No other transaction can modify locked objects until the owner transaction is
 * commited or rolled back (an {@link LockingException} is thrown)
 *
 * In non-transactional mode, an optimistic locking mechanism is implemented.
 */
class Transaction implements Serializable, Cloneable {
	private static final long serialVersionUID = -8730761538027066021L;

	private final long txId;
    private final Transaction parentTx;
    private boolean mustRollback;
    private boolean completed;

    /**
     * Builds a new transaction object.
     * @param txId The transaction id.
     */
    public Transaction(long txId, Transaction parentTx) {
        this.txId = txId;
        this.parentTx = parentTx;
        this.mustRollback = false;
        this.completed = false;
    }

    /**
     * Mark transactions for rollback only. Transaction cannot commit from
     * hereon.
     */
    public void markForRollback() {
        this.mustRollback = true;
    }

    /**
     * Marks the transaction as commited.
     */
    public void markCompleted() {
        this.completed = true;
    }

    /**
     * Returns true if the transaction wasn't marked for rollback and it can
     * commit.
     * @return True if the transaction can commit
     */
    public boolean canCommit() {
        return !this.mustRollback && !this.completed;
    }

    /**
     * Returns true if the transaction has been commited
     * @return True if the transaction has been commited.
     */
    public boolean canComplete() {
        return !this.completed;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object) 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (this.txId != other.txId) {
            return false;
        }
        return true;
    }

    /**
     * Transaction id getter.
     * @return The transaction id.
     */
    public long getTxId() {
        return this.txId;
    }

    /**
     * Returns the parent transaction.
     * @return The parent transaction.
     */
    public Transaction getParentTx() {
        return this.parentTx;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.txId ^ this.txId >>> 32);
        return hash;
    }

    @Override
    public Object clone() {
        Transaction parentTxCopy = (Transaction)(parentTx != null? parentTx.clone(): null);
        Transaction copy = new Transaction(this.txId, parentTxCopy);
        copy.completed = this.completed;
        copy.mustRollback = this.mustRollback;
        return copy;
    }
    
    @Override
    public String toString() {
        return !isTopLevel()? String.format("TxID: %d (parent: %s)", txId, parentTx.toString()):
            String.format("TxID: top level");
    }

    public boolean isTopLevel() {
        return parentTx == null;
    }

}
