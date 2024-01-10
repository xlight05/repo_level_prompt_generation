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

import gloodb.Dependency;
import gloodb.GlooException;
import gloodb.KeyViolationException;
import gloodb.PersistencyAttributes;
import gloodb.PersistentNotFoundException;
import gloodb.QueryResult;
import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;
import gloodb.SystemObject;
import gloodb.UpdateQuery;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

class RepositoryImplementationSyncInterceptor extends RepositoryImplementation {
	private final RepositoryImplementation implementation;
    private final SafeReentrantReadWriteLock repositoryLock;
    private final ObjectLocker objectLocker;

	public RepositoryImplementationSyncInterceptor(RepositoryImplementation implementation) {
        this.repositoryLock = new SafeReentrantReadWriteLock();
		this.implementation = implementation;
		this.objectLocker = new ObjectLocker();
	}

	@Override
	public Transaction begin(RepositoryFacade runtime) {
		try {
			repositoryLock.writeLock();
			return implementation.begin(runtime);
		} finally {
			repositoryLock.writeUnlock();
		}
	}

	@Override
	public void commit(RepositoryFacade runtime) {
		try {
			repositoryLock.writeLock();
			implementation.commit(runtime);
		} finally {
			repositoryLock.writeUnlock();
		}
	}

	@Override
	public boolean contains(RepositoryFacade runtime, Serializable... idFields) {
		try {
			repositoryLock.readLock();
			objectLocker.readLock(idFields);
			return implementation.contains(runtime, idFields);
		} finally {
			objectLocker.readUnlock(idFields);
			repositoryLock.readUnlock();
		}
	}

	@Override
	public <T extends Serializable> T create(RepositoryFacade runtime, T persistentObject) throws KeyViolationException {
		Serializable id = PersistencyAttributes.getIdForObject(persistentObject);
		try {
			repositoryLock.readLock();
			objectLocker.writeLock(id);
			return implementation.create(runtime, persistentObject);
		} finally {
			objectLocker.writeUnlock(id);
			repositoryLock.readUnlock();
		}
	}

	@Override
	public void flushCache(int percentage) {
		try {
			repositoryLock.readLock();
			implementation.flushCache(percentage);
		} finally {
			repositoryLock.readUnlock();
		}
	}

	@Override
    protected void flushObject(Serializable id) {
		try {
			repositoryLock.readLock();
			objectLocker.writeLock(id);
			implementation.flushObject(id);
		} finally {
			objectLocker.writeUnlock(id);
			repositoryLock.readUnlock();
		}
    }

	@Override
	public Class<? extends Serializable> getClassForId(Serializable... idFields) {
		try {
			repositoryLock.readLock();
			return implementation.getClassForId(idFields);
		} finally {
			repositoryLock.readUnlock();
		}
	}

	@Override
	public List<Serializable> getIds() {
		try {
			repositoryLock.readLock();
			return implementation.getIds();
		} finally {
			repositoryLock.readUnlock();
		}
	}

	@Override
	public <T extends SystemObject> T getSystemObject(Class<T> systemObjectClass) {
		try {
			repositoryLock.readLock();
			return implementation.getSystemObject(systemObjectClass);
		} finally {
			repositoryLock.readUnlock();
		}
	}

	@Override
    protected <T extends Serializable> QueryResult<T> runReadOnlyQuery(Repository uninterceptedRuntime, ReadOnlyQuery<T> query, Serializable... parameters) {
		try {
			repositoryLock.readLock();
			return implementation.runReadOnlyQuery(uninterceptedRuntime, query, parameters);
		} finally {
			repositoryLock.readUnlock();
		}
    }

    protected <T extends Serializable> QueryResult<T> runUpdateQuery(Repository uninterceptedRuntime, UpdateQuery<T> query, Serializable... parameters) {
    	try {
    		repositoryLock.writeLock();
    		return implementation.runUpdateQuery(uninterceptedRuntime, query, parameters);
    	} finally {
    		repositoryLock.writeUnlock();
    	}
    }
 
	@Override
	public Serializable remove(RepositoryFacade runtime, Serializable... idFields) {
		try {
			repositoryLock.readLock();
			objectLocker.writeLock(idFields);
			return implementation.remove(runtime, idFields);
		} finally {
			objectLocker.writeUnlock(idFields);
			repositoryLock.readUnlock();
		}
	}

	@Override
	public Serializable restore(RepositoryFacade runtime, Serializable... idFields) {
		try {
			repositoryLock.readLock();
			objectLocker.readLock(idFields);
			return implementation.restore(runtime, idFields);
		} finally {
			objectLocker.readUnlock(idFields);
			repositoryLock.readUnlock();
		}
	}

	@Override
	public void rollback(RepositoryFacade runtime) {
		try {
			repositoryLock.writeLock();
			implementation.rollback(runtime);
		} finally {
			repositoryLock.writeUnlock();
		}
	}

	@Override
	public void setInterceptor(RepositoryInterceptor interceptor) {
		implementation.setInterceptor(interceptor);
	}

	@Override
	public <T extends Serializable> T store(RepositoryFacade runtime, T persistentObject) {
		Serializable id = PersistencyAttributes.getIdForObject(persistentObject);
		try {
			repositoryLock.readLock();
			objectLocker.writeLock(id);
			return implementation.store(runtime, persistentObject);
		} finally {
			objectLocker.writeUnlock(id);
			repositoryLock.readUnlock();
		}
	}

	@Override
	public void takeSnapshot() {
		try {
			repositoryLock.writeLock();
			implementation.takeSnapshot();
		} finally {
			repositoryLock.writeUnlock();
		}
	}

	@Override
	public <T extends Serializable> T update(RepositoryFacade runtime, T persistentObject) throws PersistentNotFoundException {
		Serializable id = PersistencyAttributes.getIdForObject(persistentObject);
		try {
			repositoryLock.readLock();
			objectLocker.writeLock(id);
			return implementation.update(runtime, persistentObject);
		} finally {
			objectLocker.writeUnlock(id);
			repositoryLock.readUnlock();
		}
	}
	
	@Override
	protected void lockDepedencies(Collection<Dependency> dependencies) {
		// Return null if no dependencies are declared.
		if(dependencies == null) return;
		
		for(Dependency dependency: dependencies) {
			if(Dependency.Type.Read.equals(dependency.getType())) {
				objectLocker.readLock(dependency.getId());
			} else if(Dependency.Type.Write.equals(dependency.getType())) {
				objectLocker.writeLock(dependency.getId());
			} else {
				throw new GlooException(String.format("Unknown dependency type %s", dependency.getType()));
			}
		}
	}

	@Override
	protected void unlockDependencies(Collection<Dependency> dependencies) {
		// Return if no dependencies are declared
		if(dependencies == null) return;
		
		for(Dependency dependency: dependencies) {
			if(Dependency.Type.Read.equals(dependency.getType())) {
				objectLocker.readUnlock(dependency.getId());
			} else if(Dependency.Type.Write.equals(dependency.getType())) {
				objectLocker.writeUnlock(dependency.getId());
			} else {
				throw new GlooException(String.format("Unknown dependency type %s", dependency.getType()));
			}
		}
	}
	
}
