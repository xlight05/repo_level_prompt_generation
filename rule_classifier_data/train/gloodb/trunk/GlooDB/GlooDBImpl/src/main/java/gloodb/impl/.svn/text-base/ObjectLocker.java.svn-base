package gloodb.impl;

import gloodb.PersistencyAttributes;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class ObjectLocker {
    private final Map<Serializable, SafeReentrantReadWriteLock> objectLockMap;
    private final ReentrantLock masterLock;

    public ObjectLocker() {
        this.objectLockMap = new HashMap<Serializable, SafeReentrantReadWriteLock>();
        this.masterLock = new ReentrantLock();
	}

    
	public void readLock(Serializable... idFields) {
		try {
			masterLock.lock();
			SafeReentrantReadWriteLock lock = getObjectLock(PersistencyAttributes.getId(idFields));
			lock.readLock();
		} finally {
			masterLock.unlock();
		}
	}
	
	public void writeLock(Serializable... idFields) {
		try {
			masterLock.lock();
			SafeReentrantReadWriteLock lock = getObjectLock(PersistencyAttributes.getId(idFields));
			lock.writeLock();
		} finally {
			masterLock.unlock();
		}
	}
	
	public void readUnlock(Serializable... idFields) {
		try {
			masterLock.lock();
			SafeReentrantReadWriteLock lock = getObjectLock(PersistencyAttributes.getId(idFields));
			lock.readUnlock();
		} finally {
			masterLock.unlock();
		}
	}

	public void writeUnlock(Serializable... idFields) {
		try {
			masterLock.lock();
			SafeReentrantReadWriteLock lock = getObjectLock(PersistencyAttributes.getId(idFields));
			lock.writeUnlock();
		} finally {
			masterLock.unlock();
		}
	}
	
    @SuppressWarnings("serial")
	private SafeReentrantReadWriteLock getObjectLock(final Serializable identity) {
    	try {    		
	    	masterLock.lock();
			SafeReentrantReadWriteLock lock = this.objectLockMap.get(identity);
			if(lock == null) {
				lock = new SafeReentrantReadWriteLock() {
	
					@Override
					public void readUnlock() {
						super.readUnlock();
						if(!isInUse()) objectLockMap.remove(identity);
					}
	
					@Override
					public void writeUnlock() {
						super.writeUnlock();
						if(!isInUse()) objectLockMap.remove(identity);
					}
					
				};
				this.objectLockMap.put(identity, lock);
			}
			return lock;
    	} finally {
    		masterLock.unlock();
    	}
	}
}
