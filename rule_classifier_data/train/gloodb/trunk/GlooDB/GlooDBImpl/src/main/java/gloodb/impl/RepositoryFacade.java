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

import java.io.Serializable;

import gloodb.KeyViolationException;
import gloodb.PersistentNotFoundException;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;
import gloodb.SystemObject;

class RepositoryFacade implements Repository, RepositoryAdmin {
	private Transaction tx;
	private final RepositoryImplementation implementation;
	
	RepositoryFacade(Transaction tx, RepositoryImplementation implementation) {
		this.tx = tx;
		this.implementation = implementation;
	}
	
	@Override
	public Repository begin() {
		return new RepositoryFacade(implementation.begin(this), implementation);
	}

	@Override
	public boolean canCommit() {
		return (tx == null) ? false: tx.canCommit();
	}

	@Override
	public boolean canComplete() {
		return (tx == null) ? false: tx.canComplete();
	}

	@Override
	public void commit() {
		implementation.commit(this);
	}

	@Override
	public boolean contains(Serializable... idFields) {
		return implementation.contains(this, idFields);
	}

	@Override
	public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
		return implementation.create(this, persistentObject);
	}

	@Override
	public Class<? extends Serializable> getClassForId(Serializable... idFields) {
		return implementation.getClassForId(idFields);
	}

	@Override
	public <T extends SystemObject> T getSystemObject(Class<T> systemObjectClass) {
		return implementation.getSystemObject(systemObjectClass);
	}

	@Override
	public boolean isTopLevel() {
		return tx == null;
	}

	@Override
	public void markForRollback() {
		if(tx == null) throw new NoTransactionException("The top level transaction cannot be marked for rollback");
		tx.markForRollback();
	}

	@Override
	public <T extends Serializable> QueryResult<T> query(Query<T> query, Serializable... parameters) {
		return implementation.query(this, query, parameters);
	}

	@Override
	public Serializable remove(Serializable... idFields) {
		return implementation.remove(this, idFields);
	}

	@Override
	public Serializable restore(Serializable... idFields) {
		return implementation.restore(this, idFields);
	}

	@Override
	public void rollback() {
		if(tx == null) throw new NoTransactionException("Cannot rollback top level transaction");
		implementation.rollback(this);
	}

	@Override
	public <T extends Serializable> T store(T persistentObject) {
		return implementation.store(this, persistentObject);
	}

	@Override
	public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
		return implementation.update(this, persistentObject);
	}

	public Transaction getTransaction() {
		return tx;
	}

	public RepositoryImplementation getImplementation() {
		return implementation;
	}
	
	@Override
	public String toString() {
	    return String.format("%s, %s", implementation.toString(), tx == null? "top level": this.tx.toString());
	}
	
	public void setInterceptor(RepositoryInterceptor interceptor) {
	    implementation.setInterceptor(interceptor);
	}

    @Override
    public void flushCache(int percentage) {
        implementation.flushCache(percentage);
    }
    
    @Override
    public void takeSnapshot() {
        implementation.takeSnapshot();
    }
}
