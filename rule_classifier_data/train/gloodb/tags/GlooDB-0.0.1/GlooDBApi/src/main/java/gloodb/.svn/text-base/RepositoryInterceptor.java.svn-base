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
package gloodb;

import java.io.Serializable;
import java.lang.reflect.Method;


/**
 * Repository interceptor. 
 */
public abstract class RepositoryInterceptor implements Repository {
    
    private Repository repository;
    
    public RepositoryInterceptor setRepository(Repository repository) {
        try {
            Method setMethod = repository.getClass().getMethod("setInterceptor", RepositoryInterceptor.class);
            setMethod.setAccessible(true);
            setMethod.invoke(repository, this);
        } catch (NoSuchMethodException e) {
            // Not a RepositoryImplementation
        } catch (Exception e) {
            throw new GlooException("Unexpected exception while trying to inject interceptor.", e);
        }
        this.repository = repository;
        return this;
    }
    
    public abstract RepositoryInterceptor newInstance();

    @Override
    public Repository begin() {
        return newInstance().setRepository(repository.begin());
    }

    @Override
    public void commit() {
        repository.commit();
    }

    @Override
    public boolean contains(Serializable... idFields) {
        return repository.contains(idFields);
    }

    @Override
    public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
        return repository.create(persistentObject);
    }

    @Override
    public Class<? extends Serializable> getClassForId(Serializable... idFields) {
        return repository.getClassForId(idFields);
    }

    @Override
    public QueryResult query(Query query) {
        return repository.query(query);
    }

    @Override
    public QueryResult readOnlyQuery(ReadOnlyQuery query) {
        return repository.readOnlyQuery(query);
    }

    @Override
    public QueryResult updateQuery(UpdateQuery query) {
        return repository.updateQuery(query);
    }

    @Override
    public Serializable remove(Serializable... idFields) {
        return repository.remove(idFields);
    }

    @Override
    public Serializable restore(Serializable... idFields) {
        return repository.restore(idFields);
    }

    @Override
    public void rollback() {
        repository.rollback();
    }

    @Override
    public <T extends Serializable> T store(T persistentObject) {
        return repository.store(persistentObject);
    }

    @Override
    public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
        return repository.update(persistentObject);
    }

    @Override
    public <T extends SystemObject> T getSystemObject(Class<T> systemObjectClass) {
        return repository.getSystemObject(systemObjectClass);
    }

    @Override
    public void markForRollback() {
        repository.markForRollback();
    }

    @Override
    public boolean isTopLevel() {
        return repository.isTopLevel();
    }

    @Override
    public boolean canCommit() {
        return repository.canCommit();
    }

    @Override
    public boolean canComplete() {
        return repository.canComplete();
    }
    
    @Override
    public String toString() {
        return repository.toString();
    }
    
    public Repository getIntercepted() {
        return this.repository;
    }
}
