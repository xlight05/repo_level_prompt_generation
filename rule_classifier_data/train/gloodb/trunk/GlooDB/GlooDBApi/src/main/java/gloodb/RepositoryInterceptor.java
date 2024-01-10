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
    
    /**
     * Sets the intercepted repository.
     * @param repository The intercepted repository.
     * @return This repository interceptor.
     */
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
    
    /**
     * Creates a new instance of the interceptor. The repository uses this
     * to wrap the repository implementation before invoking callbacks and
     * listeners.
     * @return The repository interceptor instance.
     */
    public abstract RepositoryInterceptor newInstance();

    /**
     * Invokes the intercepted begin method.
     */
    @Override
    public Repository begin() {
        return newInstance().setRepository(repository.begin());
    }

    /**
     * Invokes the intercepted commit method.
     */
    @Override
    public void commit() {
        repository.commit();
    }

    /**
     * Invokes the intercepted contains method.
     */
    @Override
    public boolean contains(Serializable... idFields) {
        return repository.contains(idFields);
    }

    /**
     * Invokes the intercepted create method.
     */
    @Override
    public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
        return repository.create(persistentObject);
    }

    /**
     * Invokes the intercepted getClassForId method.
     */
    @Override
    public Class<? extends Serializable> getClassForId(Serializable... idFields) {
        return repository.getClassForId(idFields);
    }

    /**
     * Invokes the intercepted query method.
     */
    @Override
    public <T extends Serializable> QueryResult<T> query(Query<T> query, Serializable... parameters) {
        return repository.query(query, parameters);
    }

    /**
     * Invokes the intercepted remove method.
     */
    @Override
    public Serializable remove(Serializable... idFields) {
        return repository.remove(idFields);
    }

    /**
     * Invokes the intercepted restore method.
     */
    @Override
    public Serializable restore(Serializable... idFields) {
        return repository.restore(idFields);
    }

    /**
     * Invokes the intercepted rollback method.
     */
    @Override
    public void rollback() {
        repository.rollback();
    }

    /**
     * Invokes the intercepted store method.
     */
    @Override
    public <T extends Serializable> T store(T persistentObject) {
        return repository.store(persistentObject);
    }

    /**
     * Invokes the intercepted update method.
     */
    @Override
    public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
        return repository.update(persistentObject);
    }

    /**
     * Invokes the intercepted getSystemObject method.
     */
    @Override
    public <T extends SystemObject> T getSystemObject(Class<T> systemObjectClass) {
        return repository.getSystemObject(systemObjectClass);
    }

    /**
     * Invokes the intercepted markForRollback method.
     */
    @Override
    public void markForRollback() {
        repository.markForRollback();
    }

    /**
     * Invokes the intercepted isTopLevel method.
     */
    @Override
    public boolean isTopLevel() {
        return repository.isTopLevel();
    }

    /**
     * Invokes the intercepted canCommit method.
     */
    @Override
    public boolean canCommit() {
        return repository.canCommit();
    }

    /**
     * Invokes the intercepted canComplete method.
     */
    @Override
    public boolean canComplete() {
        return repository.canComplete();
    }
    
    /**
     * Invokes the intercepted toString method.
     */
    @Override
    public String toString() {
        return repository.toString();
    }
    
    /**
     * Invokes the intercepted getIntercepted method.
     */
    public Repository getIntercepted() {
        return this.repository;
    }
}
