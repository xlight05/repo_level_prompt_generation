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
import gloodb.Interceptor;
import gloodb.KeyViolationException;
import gloodb.Query;
import gloodb.PersistentNotFoundException;
import gloodb.Repository;
import gloodb.GlooException;
import gloodb.PersistencyAttributes;
import gloodb.PostCreate;
import gloodb.PostRemove;
import gloodb.PostRestore;
import gloodb.PostUpdate;
import gloodb.PreCreate;
import gloodb.PreRemove;
import gloodb.PreUpdate;
import gloodb.QueryResult;
import gloodb.RepositoryInterceptor;
import gloodb.SystemObject;
import gloodb.associations.AssociationFactory;
import gloodb.queries.ObjectIdsQuery;
import gloodb.storage.StorageStrategy;
import gloodb.txmgr.TxManagerStrategy;
import gloodb.RepositoryStrategy;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Repository implementation class. The repository guards access to the data
 * warehouse with a read/write reentrant locks. It also provides complex / atomic
 * operation (each one may trigger multiple object warehouse operation). Further on
 * it invokes persistent object callbacks on updates, remove and restores.
 */
class RepositoryImplementation {

    private final ReentrantReadWriteLock lock;
    private final ObjectWarehouse objectWarehouse;
    private final String nameSpace;
    private RepositoryInterceptor interceptor;
    private final AssociationFactory associationFactory;

    /**
     * Creates a repository based on the strategies provided.
     * @param repositoryStrategy The repository strategy.
     * @param storageFactory The storage factory
     * @param storageStrategy The storage strategy
     * @param logFactory The log factory
     * @param logConfiguration The log configuration
     */
    public RepositoryImplementation(RepositoryStrategy repositoryStrategy, StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
		this.objectWarehouse = ObjectWarehouseBuilder.build(storageStrategy, txStrategy);
        this.lock = new ReentrantReadWriteLock();
        this.createSystemObjects();
        this.nameSpace = storageStrategy.getConfiguration().getNameSpace();
        this.associationFactory = repositoryStrategy.getStrategyInstance(AssociationFactory.class); 
    }
    
    

    /**
     * Begins a transaction nested in the parent transaction. If the parent
     * transaction is null, the repository starts a top level transaction.
     * @param runtime The runtime repository
     * @return The new transaction.
     */
    public Transaction begin(RepositoryFacade runtime) {
        try {
            this.lock.writeLock().lock();
            return this.objectWarehouse.begin(runtime.getTransaction());
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Commits a transaction and all its child transactions.
     * @param runtime The runtime repository
     */
    public void commit(RepositoryFacade runtime) {
    	if(runtime.getTransaction() == null) throw new CannotCompleteTxException("Cannot commit null transaction.");
        try {
            this.lock.writeLock().lock();
            this.objectWarehouse.commit(runtime.getTransaction());
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Checks if the repository contains the object with the given identity.
     * @param runtime The runtime repository
     * @param identity The object id.
     * @return True if the repository contains the object.
     */
    public boolean contains(RepositoryFacade runtime, Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	
    	Serializable identity = PersistencyAttributes.getId(idFields);
        try {
            this.lock.readLock().lock();
            return this.objectWarehouse.contains(identity, runtime.getTransaction());
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * Creates a new object in the repository.
     * @param <T> The object type.
     * @param persistentObject The persistent object value.
     * @param runtime The runtime repository.
     * @return The persisted object.
     * @throws KeyViolationException If an object with the same id already exists
     * in the repository.
     */
    public <T extends Serializable> T create(RepositoryFacade runtime, T persistentObject) throws KeyViolationException {
    	if(persistentObject == null) throw new IllegalArgumentException("Cannot create null object.");
        try {
            this.lock.writeLock().lock();
            return createObject(persistentObject, runtime);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private void firePostObjectRemoveEvent(Serializable victimObject, Repository runtime) {
        invokeCallback(PostRemove.class, victimObject, runtime);
    }

    private void firePostObjectRestoreEvent(Serializable persistentObject, Repository runtime) {
        if (persistentObject != null) {
            invokeCallback(PostRestore.class, persistentObject, runtime);
        }
    }

    private void firePostObjectCreateEvent(Serializable updatedObject, Repository runtime) {
        invokeCallback(PostCreate.class, updatedObject, runtime);
    }

    private void firePostObjectStoreEvent(Serializable updatedObject, Repository runtime) {
        invokeCallback(PostUpdate.class, updatedObject, runtime);
    }

    private void firePreObjectRemoveEvent(Serializable victimObject, Repository runtime) {
        invokeCallback(PreRemove.class, victimObject, runtime);
    }

    private void firePreObjectStoreEvent(Serializable newObject, Repository runtime) {
        invokeCallback(PreUpdate.class, newObject, runtime);
    }

    private void firePreObjectCreateEvent(Serializable newObject, Repository runtime) {
        invokeCallback(PreCreate.class, newObject, runtime);
    }

    /**
     * Returns a copy of the set of object ids.
     * @return The object id set.
     */
    public List<Serializable> getIds() {
        try {
            this.lock.readLock().lock();
            return this.objectWarehouse.getIds();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    private void invokeCallback(Class<? extends Annotation> clazz, Serializable persistent, Repository uninterceptedRuntime) {
        Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
        Method method = null;
        try {
            ArrayList<Interceptor<Serializable>> interceptors = getInterceptorsAndCreateInstances(persistent, runtime);
            invokeBeforeInterceptors(interceptors, clazz, persistent, runtime);
            method = PersistencyAttributes.getCallback(clazz, persistent);
            if (method != null) {
                method.invoke(persistent, runtime);
            }
            invokeAfterInterceptors(interceptors, clazz, persistent, runtime);
        } catch (GlooException gex) {
            gex.addMessagePrefix(String.format("Cannot invoke pre/post method %s", getMethodMessage(method)));
            throw gex;
        } catch (Exception ex) {
        	if((ex instanceof InvocationTargetException) && ex.getCause() != null) {
        		// If the callback failed because the method threw and exception, throw the actual cause and not
        		// the wrapper InvocationTargetException. 
        	    if(ex.getCause() instanceof GlooException) {
        	        GlooException gex = (GlooException) ex.getCause();
        	        gex.addMessagePrefix(String.format("Cannot invoke pre/post method %s", getMethodMessage(method)));
                    throw gex;
        	    } else {
        	        throw new GlooException(String.format("Cannot invoke pre/post method %s", getMethodMessage(method)), ex.getCause());
        	    }
        	}
            throw new GlooException(String.format("Cannot invoke pre/post method %s", getMethodMessage(method)), ex);
        }
    }
    
    private String getMethodMessage(Method method) {
        return method != null? method.toString(): "unknown (method = null). An interceptor throwning an exception can cause this behaviour.";
    }
    
    @SuppressWarnings("unchecked")
    private ArrayList<Interceptor<Serializable>> getInterceptorsAndCreateInstances(Serializable persistent, Repository runtime) {
        ArrayList<Interceptor<Serializable>> result = new ArrayList<Interceptor<Serializable>>();
        List<Class<? extends Interceptor<? extends Serializable>>> ids = PersistencyAttributes.getInterceptorIds(persistent);
        if(ids != null) {
            for(Class<? extends Interceptor<? extends Serializable>> id : ids) {
                Interceptor<Serializable> interceptor = (Interceptor<Serializable>) runtime.restore(id);
                if(interceptor == null) {
                    interceptor = createInterceptorInstance(runtime, id);
                }
                result.add(interceptor);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Interceptor<Serializable> createInterceptorInstance(Repository runtime,
            Class<? extends Interceptor<? extends Serializable>> id) {
        try {
            return (Interceptor<Serializable>) runtime.create(id.newInstance());
        } catch (Exception e) {
            throw new GlooException(String.format("Cannot create interceptor %s", id.toString()), e);
        }
    }

    private void invokeBeforeInterceptors(ArrayList<Interceptor<Serializable>> interceptors, Class<? extends Annotation> clazz,
            Serializable persistent, Repository runtime) throws Exception {
        if(PostRestore.class.equals(clazz)) return;
        for (Interceptor<Serializable> interceptor : interceptors) {
            interceptor.before(clazz, runtime, persistent);
        }
    }

    private void invokeAfterInterceptors(ArrayList<Interceptor<Serializable>> interceptors, Class<? extends Annotation> clazz,
            Serializable persistent, Repository runtime) throws Exception {
        if(PostRestore.class.equals(clazz)) return;
        for (Interceptor<Serializable> interceptor : interceptors) {
            interceptor.after(clazz, runtime, persistent);
        }
    }


    /**
     * Runs a read only query on the repository.
     * @param uninterceptedRuntime The runtime repository
     * @param query The query to run.
     * @return The query result.
     */
    public QueryResult readOnlyQuery(Repository uninterceptedRuntime, Query query) {
    	if(query == null) throw new IllegalArgumentException("Cannot run null query.");
        try {
            Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
            this.lock.readLock().lock();
            return new QueryResult(query.run(runtime));
        } catch (Exception e) {
            return new QueryResult(e);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * Runs an unguarded query on the repository.
     * @param query The query to run.
     * @param uninterceptedRuntime The runtime repository
     * @return The query result.
     */
    public QueryResult query(Repository uninterceptedRuntime, Query query) {
    	if(query == null) throw new IllegalArgumentException("Cannot run null query.");
        try {
            Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
            return new QueryResult(query.run(runtime));
        } catch (Exception e) {
            return new QueryResult(e);
        }
    }

    /**
     * Runs an update query on the repository.
     * @param query The query to run.
     * @param uninterceptedRuntime The runtime repository
     * @return The query result.
     */
    public QueryResult updateQuery(Repository uninterceptedRuntime, Query query) {
    	if(query == null) throw new IllegalArgumentException("Cannot run null query.");
        try {
            Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
            this.lock.writeLock().lock();
            return new QueryResult(query.run(runtime));
        } catch (Exception e) {
            return new QueryResult(e);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Removes the object with the given id.
     * @param identity The object id.
     * @param runtime The runtime repository
     * @return The removed object.
     */
    public Serializable remove(RepositoryFacade runtime, Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	Serializable identity = PersistencyAttributes.getId(idFields);
        try {
            this.lock.writeLock().lock();
            return removeObject(identity, runtime);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Restores the object with the given identity.
     * @param identity The object id.
     * @return The restored object.
     */
    @SuppressWarnings("unchecked")
    public Serializable restore(RepositoryFacade runtime, Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	// Create any interceptors if required.
    	if(idFields.length == 1 && (idFields[0] instanceof Class) && Interceptor.class.isAssignableFrom((Class<? extends Interceptor<? extends Serializable>>)idFields[0])) {
            if(!runtime.contains(idFields[0])) {
                createInterceptorInstance(runtime, (Class<? extends Interceptor<? extends Serializable>>) idFields[0]);
            }                
    	}
    	// Restore the object
    	Serializable identity = PersistencyAttributes.getId(idFields);
        try {
            
            this.lock.readLock().lock();
            Serializable result = this.objectWarehouse.restore(identity, runtime.getTransaction());
            firePostObjectRestoreEvent(result, runtime);
            return result;
        } finally {
            this.lock.readLock().unlock();
        }
    }
    
    

    /**
     * Rollbacks the given transaction and all its child transactions.
     * @param runtime The runtime repository
     */
    public void rollback(RepositoryFacade runtime) {
    	if(runtime.getTransaction() == null) throw new IllegalArgumentException("Cannot rollback null transaction.");
        try {
            this.lock.writeLock().lock();
            this.objectWarehouse.rollback(runtime.getTransaction());
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Creates or updates an existent object. If the object doesn't exist it's
     * created anew. Otherwise it is updated. A locking exception may be thrown if the object
     * is locked or it was concurrently updated. Also, the pre callbacks may throw
     * exception to prevent updates.
     * @param <T> The object type.
     * @param persistentObject The new object value.
     * @param runtime The runtime repository
     * @return The updated object.
     */
    public <T extends Serializable> T store(RepositoryFacade runtime, T persistentObject) {
    	if(persistentObject == null) throw new IllegalArgumentException("Cannot store a null object.");
        try {
            this.lock.writeLock().lock();
            if (!this.objectWarehouse.contains(PersistencyAttributes.getIdForObject(persistentObject), 
            		runtime.getTransaction())) {
                return createObject(persistentObject, runtime);
            } else {
                return updateObject(persistentObject, runtime);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Updates an existent object. A locking exception may be thrown if the object
     * is locked or it was concurently updated. Also, the pre callbacks may throw
     * exception to prevent updates.
     * @param <T> The object type.
     * @param persistentObject The new object value.
     * @param runtime The runtime repository
     * @return The updated object.
     * @throws PersistentNotFoundException If the object is no longer in the repository
     * and therefore it cannot be updated.
     */
    public <T extends Serializable> T update(RepositoryFacade runtime, T persistentObject) throws PersistentNotFoundException {
    	if(persistentObject == null) throw new IllegalArgumentException("Cannot update with a null object.");
        try {
            this.lock.writeLock().lock();
            return updateObject(persistentObject, runtime);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Flushes the specified percentage of the cache.
     * @param percentage The percentage of the cache to flush.
     */
    public void flushCache(int percentage) {
        try {
            this.lock.readLock().lock();
            this.objectWarehouse.flushCache(percentage);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * Returns the class for the object with the given identity.
     * @param id The object identity.
     * @return The class of the object.
     */
    public Class<? extends Serializable> getClassForId(Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	Serializable identity = PersistencyAttributes.getId(idFields);
        try {
            this.lock.readLock().lock();
            return this.objectWarehouse.getClassForId(identity);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    private <T extends Serializable> T createObject(T persistentObject, RepositoryFacade runtime) throws KeyViolationException {
        T result;
        firePreObjectCreateEvent(persistentObject, runtime);
        try {
            result = this.objectWarehouse.store(persistentObject, runtime.getTransaction(), StoreOperation.Create);
        } catch (PersistentNotFoundException ex) {
            throw new GlooException("Unexpected exception caught", ex);
        }
        firePostObjectCreateEvent(result, runtime);
        return result;
    }

    private <T extends Serializable> T updateObject(T persistentObject, RepositoryFacade runtime) {
        firePreObjectStoreEvent(persistentObject, runtime);
        T updatedObject;
        try {
            updatedObject = this.objectWarehouse.store(persistentObject, runtime.getTransaction(), StoreOperation.Update);
        } catch (KeyViolationException ex) {
            throw new GlooException("Unexpected exception caught", ex);
        }
        firePostObjectStoreEvent(updatedObject, runtime);
        return updatedObject;
    }

    private Serializable removeObject(Serializable identity, RepositoryFacade runtime) {
    	Transaction tx = runtime.getTransaction();
        // if the object is not in the repository return without any callbacks.
        if (!this.objectWarehouse.contains(identity, tx)) {
            return null;
        }
        Serializable removedPersistentObject = this.objectWarehouse.restore(identity, tx);
        if (removedPersistentObject != null) {
            firePreObjectRemoveEvent(removedPersistentObject, runtime);
            this.objectWarehouse.remove(identity, tx);
            firePostObjectRemoveEvent(removedPersistentObject, runtime);
        }
        return removedPersistentObject;
    }

    private void createSystemObjects() {
    	// no system objects
    }

	@SuppressWarnings("unchecked")
	public <T extends SystemObject> T getSystemObject(Class<T> systemObjectClass) {
    	if(systemObjectClass == null) throw new IllegalArgumentException("Object class cannot be null.");
		if(systemObjectClass.equals(ObjectIdsQuery.class)) {
			return (T)new ObjectIdsQueryImplementation();
		}
		if(systemObjectClass.equals(AssociationFactory.class)) {
		    return (T) associationFactory;
		}
		return null;
	}
	
	@Override
	public String toString() {
	    return String.format("Repository %s", nameSpace);
	}
	
	public void setInterceptor(RepositoryInterceptor interceptor) {
	    this.interceptor = interceptor;
	}
	
	public void takeSnapshot() {
	    try {
	        this.lock.writeLock().lock();
	        this.objectWarehouse.takeSnapshot();
	    } finally {
	        this.lock.writeLock().unlock();
	    }
	}
}
