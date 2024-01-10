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
import gloodb.Dependency;
import gloodb.Interceptor;
import gloodb.KeyViolationException;
import gloodb.Query;
import gloodb.PersistentNotFoundException;
import gloodb.ReadOnlyQuery;
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
import gloodb.UpdateQuery;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Repository implementation class. The repository guards access to the data
 * warehouse with a read/write reentrant locks. It also provides complex / atomic
 * operation (each one may trigger multiple object warehouse operation). Further on
 * it invokes persistent object callbacks on updates, remove and restores.
 */
class RepositoryImplementation {
	
	public static RepositoryImplementation getInstance(RepositoryStrategy repositoryStrategy, StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
		RepositoryImplementation implementation = new RepositoryImplementation(repositoryStrategy, storageStrategy, txStrategy);
		return new RepositoryImplementationSyncInterceptor(implementation);
	}

    private final ObjectWarehouse objectWarehouse;
    private final String nameSpace;
    private RepositoryInterceptor interceptor;
    private final AssociationFactory associationFactory;

    /**
     * Protected constructor; use only when implementing interceptors.
     */
    protected RepositoryImplementation() {
    	this.objectWarehouse = null;
    	this.nameSpace = null;
    	this.associationFactory = null;
    }
    
    /**
     * Creates a repository based on the strategies provided.
     * @param repositoryStrategy The repository strategy.
     * @param storageFactory The storage factory
     * @param storageStrategy The storage strategy
     * @param logFactory The log factory
     * @param logConfiguration The log configuration
     */
    private RepositoryImplementation(RepositoryStrategy repositoryStrategy, StorageStrategy storageStrategy, TxManagerStrategy txStrategy) {
		this.objectWarehouse = ObjectWarehouseBuilder.build(storageStrategy, txStrategy);
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
        return this.objectWarehouse.begin(runtime.getTransaction());
    }

    /**
     * Commits a transaction and all its child transactions.
     * @param runtime The runtime repository
     */
    public void commit(RepositoryFacade runtime) {
    	if(runtime.getTransaction() == null) throw new CannotCompleteTxException("Cannot commit null transaction.");
        this.objectWarehouse.commit(runtime.getTransaction());
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
        return this.objectWarehouse.contains(identity, runtime.getTransaction());
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
        return createObject(persistentObject, runtime);
    }

    /**
     * Flushes the specified percentage of the cache.
     * @param percentage The percentage of the cache to flush.
     */
    public void flushCache(int percentage) {
        // This operation may take a long time. Implement it as a
        // fined grained transaction rather than a single long one.
        // This will allow repository flushing while other operations
        // are ongoing; otherwise everything will get queued up on
        // waiting on access locks.

        // Get all ids existent in the repository
    	List<Serializable> ids = getIds();
	    int counter = ids.size() * percentage / 100;
    	for (Serializable id : ids) {
        	if(--counter < 0) return;
    		flushObject(id);
	    }
    }
    
    protected void flushObject(Serializable id) {
		this.objectWarehouse.flush(id, true);
    }

    /**
     * Returns the class for the object with the given identity.
     * @param id The object identity.
     * @return The class of the object.
     */
    public Class<? extends Serializable> getClassForId(Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	Serializable identity = PersistencyAttributes.getId(idFields);
        return this.objectWarehouse.getClassForId(identity);
    }

    /**
     * Returns a copy of the set of object ids.
     * @return The object id set.
     */
    public List<Serializable> getIds() {
        return this.objectWarehouse.getIds();
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

    /**
     * Runs an unguarded query on the repository.
     * @param query The query to run.
     * @param uninterceptedRuntime The runtime repository
     * @param parameters Query parameters.
     * @return The query result.
     */
    public <T extends Serializable> QueryResult<T> query(Repository uninterceptedRuntime, Query<T> query, Serializable...parameters) {
    	if(query == null) throw new IllegalArgumentException("Cannot run null query.");
    	if(query instanceof ReadOnlyQuery) {
    		return runReadOnlyQuery(uninterceptedRuntime, (ReadOnlyQuery<T>)query, parameters);
    	} else if(query instanceof UpdateQuery) {
    		return runUpdateQuery(uninterceptedRuntime, (UpdateQuery<T>)query, parameters);
    	}
    	return runQuery(uninterceptedRuntime, query, parameters);
    }
    
    protected <T extends Serializable> QueryResult<T> runReadOnlyQuery(Repository uninterceptedRuntime, ReadOnlyQuery<T> query, Serializable... parameters) {
    	return runQuery(uninterceptedRuntime, query, parameters);
    }

    protected <T extends Serializable> QueryResult<T> runUpdateQuery(Repository uninterceptedRuntime, UpdateQuery<T> query, Serializable... parameters) {
    	return runQuery(uninterceptedRuntime, query, parameters);
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
        return removeObject(identity, runtime);
    }

    /**
     * Restores the object with the given identity.
     * @param identity The object id.
     * @return The restored object.
     */
    public Serializable restore(RepositoryFacade runtime, Serializable... idFields) {
    	if(idFields == null) throw new IllegalArgumentException("Identity cannot be null.");
    	Serializable identity = PersistencyAttributes.getId(idFields);
        Serializable result = this.objectWarehouse.restore(identity, runtime.getTransaction());
        firePostObjectRestoreEvent(result, runtime);
        return result;
    }

    /**
     * Rollbacks the given transaction and all its child transactions.
     * @param runtime The runtime repository
     */
    public void rollback(RepositoryFacade runtime) {
    	if(runtime.getTransaction() == null) throw new IllegalArgumentException("Cannot rollback null transaction.");
        this.objectWarehouse.rollback(runtime.getTransaction());
    }
    
    public void setInterceptor(RepositoryInterceptor interceptor) {
	    this.interceptor = interceptor;
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
            if (!this.objectWarehouse.contains(PersistencyAttributes.getIdForObject(persistentObject), runtime.getTransaction())) {
                return createObject(persistentObject, runtime);
            } else {
                return updateObject(persistentObject, runtime);
            }
    }

    public void takeSnapshot() {
        this.objectWarehouse.takeSnapshot();
	}

    @Override
	public String toString() {
	    return String.format("Repository %s", nameSpace);
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
        return updateObject(persistentObject, runtime);
    }

    private <T extends Serializable> QueryResult<T> runQuery(Repository uninterceptedRuntime, Query<T> query, Serializable...parameters) {
        try {
            Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
            return new QueryResult<T>(query.run(runtime, parameters));
        } catch (Exception e) {
            return new QueryResult<T>(e);
        }
    }

    private <T extends Serializable> T createObject(T persistentObject, RepositoryFacade runtime) throws KeyViolationException {
        T result;
        Collection<Dependency> dependencies = firePreObjectCreateEvent(persistentObject, runtime);
        try {
        	lockDepedencies(dependencies);
            result = this.objectWarehouse.store(persistentObject, runtime.getTransaction(), StoreOperation.Create);
            firePostObjectCreateEvent(result, runtime);
            return result;
        } catch (PersistentNotFoundException ex) {
            throw new GlooException("Unexpected exception caught", ex);
        } finally {
        	unlockDependencies(dependencies);
        }
    }

    private void createSystemObjects() {
    	// no system objects
    }

    private void firePostObjectCreateEvent(Serializable updatedObject, Repository runtime) {
        invokeCallback(PostCreate.class, updatedObject, runtime);
    }

    private void firePostObjectRemoveEvent(Serializable victimObject, Repository runtime) {
        invokeCallback(PostRemove.class, victimObject, runtime);
    }

    private void firePostObjectRestoreEvent(Serializable persistentObject, Repository runtime) {
        if (persistentObject != null) {
            invokeCallback(PostRestore.class, persistentObject, runtime);
        }
    }

    private void firePostObjectStoreEvent(Serializable updatedObject, Repository runtime) {
        invokeCallback(PostUpdate.class, updatedObject, runtime);
    }

    private Collection<Dependency> firePreObjectCreateEvent(Serializable newObject, Repository runtime) {
        return invokeCallback(PreCreate.class, newObject, runtime);
    }

    private Collection<Dependency> firePreObjectRemoveEvent(Serializable victimObject, Repository runtime) {
        return invokeCallback(PreRemove.class, victimObject, runtime);
    }

	private Collection<Dependency> firePreObjectStoreEvent(Serializable newObject, Repository runtime) {
        return invokeCallback(PreUpdate.class, newObject, runtime);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Interceptor<Serializable>> getInterceptorInstances(Serializable persistent, Repository runtime) {
        ArrayList<Interceptor<Serializable>> result = new ArrayList<Interceptor<Serializable>>();
        List<Class<? extends Interceptor<? extends Serializable>>> ids = PersistencyAttributes.getInterceptorIds(persistent);
        if(ids != null) {
            for(Class<? extends Interceptor<? extends Serializable>> id : ids) {
                Interceptor<Serializable> interceptor = (Interceptor<Serializable>) runtime.restore(id);
                result.add(interceptor);
            }
        }
        return result;
    }

    private String getMethodMessage(Method method) {
        return method != null? method.toString(): "unknown (method = null). An interceptor throwning an exception can cause this behaviour.";
    }

	@SuppressWarnings("unchecked")
	private void invokeAfterInterceptors(ArrayList<Interceptor<Serializable>> interceptors, Class<? extends Annotation> clazz,
            Serializable persistent, Repository runtime) throws Exception {
        if(PostRestore.class.equals(clazz)) return;
        for (Interceptor<Serializable> interceptor : interceptors) {
			Interceptor<Serializable> refreshedInterceptor = (Interceptor<Serializable>) runtime.restore(interceptor.getClass());
            refreshedInterceptor.after(clazz, runtime, persistent);
        }
    }
	
	@SuppressWarnings("unchecked")
	private void invokeBeforeInterceptors(ArrayList<Interceptor<Serializable>> interceptors, Class<? extends Annotation> clazz,
            Serializable persistent, Repository runtime) throws Exception {
        if(PostRestore.class.equals(clazz)) return;
        for (Interceptor<Serializable> interceptor : interceptors) {
        	Interceptor<Serializable> refreshedInterceptor = (Interceptor<Serializable>) runtime.restore(interceptor.getClass());
            refreshedInterceptor.before(clazz, runtime, persistent);
        }
    }
	
	private Collection<Dependency> invokeCallback(Class<? extends Annotation> clazz, Serializable persistent, Repository uninterceptedRuntime) {
        Repository runtime = (interceptor != null? interceptor.newInstance().setRepository(uninterceptedRuntime): uninterceptedRuntime);
        Method method = null;
        try {
            ArrayList<Interceptor<Serializable>> interceptors = getInterceptorInstances(persistent, runtime);

            invokeBeforeInterceptors(interceptors, clazz, persistent, runtime);
            method = PersistencyAttributes.getCallback(clazz, persistent);
            
            Object invocationResult = null;
            if (method != null) {
                invocationResult = method.invoke(persistent, runtime);
            }
            invokeAfterInterceptors(interceptors, clazz, persistent, runtime);
            
            return getDependencies(invocationResult, interceptors);
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
	
	private Collection<Dependency> getDependencies(Object callbackInvocationResult, List<Interceptor<Serializable>> interceptors) {
		HashSet<Dependency> dependencies = new HashSet<Dependency>();
		if(callbackInvocationResult != null) {
			@SuppressWarnings("unchecked")
			Collection<Dependency> declaredDependencies = (Collection<Dependency>) callbackInvocationResult;
			dependencies.addAll(declaredDependencies);
		}
		
		// Add all interceptors as write dependencies.
		for(Interceptor<Serializable> interceptor: interceptors) {
			dependencies.add(new Dependency(interceptor.getClass(), Dependency.Type.Write));
		}
		return dependencies;
	}

	private Serializable removeObject(Serializable identity, RepositoryFacade runtime) {
    	Transaction tx = runtime.getTransaction();
        // if the object is not in the repository return without any callbacks.
        if (!this.objectWarehouse.contains(identity, tx)) {
            return null;
        }
        Serializable removedPersistentObject = this.objectWarehouse.restore(identity, tx);
        if (removedPersistentObject != null) {
        	Collection<Dependency> dependencies = firePreObjectRemoveEvent(removedPersistentObject, runtime);
        	try {
        		lockDepedencies(dependencies);
	            this.objectWarehouse.remove(identity, tx);
	            firePostObjectRemoveEvent(removedPersistentObject, runtime);
        	} finally {
        		unlockDependencies(dependencies);
        	}
        }
        return removedPersistentObject;
    }
	
	private <T extends Serializable> T updateObject(T persistentObject, RepositoryFacade runtime) {
        Collection<Dependency> dependencies = firePreObjectStoreEvent(persistentObject, runtime);
        T updatedObject;
        try {
        	lockDepedencies(dependencies);
            updatedObject = this.objectWarehouse.store(persistentObject, runtime.getTransaction(), StoreOperation.Update);
            firePostObjectStoreEvent(updatedObject, runtime);
            return updatedObject;
        } catch (KeyViolationException ex) {
            throw new GlooException("Unexpected exception caught", ex);
        } finally {
        	unlockDependencies(dependencies);
        }
    }

	protected void lockDepedencies(Collection<Dependency> dependencies) {
		// No locking specified (see RepositoryImplementationSyncInterceptor).
	}

	protected void unlockDependencies(Collection<Dependency> dependencies) {
		// No locking specified (see RepositoryImplementationSyncInterceptor).
	}
}
