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
package gloodb.associations.objectdb;

import gloodb.GlooException;
import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.associations.OneToManyAssociation;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import static gloodb.operators.Iterators.*;

/**
 * Class implementing a one to many persistent association.
 * 
 * @param <O>
 *            The one value type.
 * @param <M>
 *            The many value type.
 */
class OneToManyAssociationImplementation<O extends Serializable, M extends Serializable> implements OneToManyAssociation<O, M>, Cloneable {
    private static final long serialVersionUID = 5742583326562097534L;

    private LinkedHashMap<Serializable, LinkedHashSet<Serializable>> oneToManyMap;
    private LinkedHashMap<Serializable, Serializable> manyToOneMap;
    private final Class<O> oClass;
    private final Class<M> mClass;
    private SafeReentrantReadWriteLock lock;

    /**
     * Creates the one to many association.
     * 
     * @param oClass
     *            The one class.
     * @param mClass
     *            The many class.
     */
    public OneToManyAssociationImplementation(Class<O> oClass, Class<M> mClass) {
        this.oneToManyMap = new LinkedHashMap<Serializable, LinkedHashSet<Serializable>>();
        this.manyToOneMap = new LinkedHashMap<Serializable, Serializable>();
        this.oClass = oClass;
        this.mClass = mClass;
        this.lock = new SafeReentrantReadWriteLock();
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#areAssociated(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean areAssociated(Serializable forOne, Serializable... manySet) {
    	try {
    		lock.readLock();
    		Set<Serializable> associatedOnes = oneToManyMap.get(PersistencyAttributes.getIdFromVariant(oClass, forOne));
	        if (associatedOnes == null) return false;
	        for (Serializable many : manySet) {
	            if (!associatedOnes.contains(PersistencyAttributes.getIdFromVariant(mClass, many))) return false;
	        }
	        return true;
    	} finally {
    		lock.readUnlock();
    	}
    }

    /**
     * Clones the association.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
        	lock.readLock();
            final OneToManyAssociationImplementation<O, M> copy = (OneToManyAssociationImplementation<O, M>) super.clone();
            copy.oneToManyMap = (LinkedHashMap<Serializable, LinkedHashSet<Serializable>>) oneToManyMap.clone();
            copy.manyToOneMap = (LinkedHashMap<Serializable, Serializable>) manyToOneMap.clone();
            copy.lock = new SafeReentrantReadWriteLock();
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        } finally {
        	lock.readUnlock();
        }
    }

	/*
     * (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#getAssociatesForOne(java.io.Serializable)
     */
	@Override
    public <R extends Collection<Lazy<M>>> R getAssociatesForOne(R result, Serializable forOne) {
    	result.clear();
        
    	try {
    		lock.readLock();
	    	Set<Serializable> manySet = oneToManyMap.get(PersistencyAttributes.getIdFromVariant(oClass, forOne));
	        if(manySet == null) return result;
	
	        iterate(manySet, wrapIds(result));
	        return result;
    	} finally {
    		lock.readUnlock();
    	}
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#getOnes()
     */
    @Override
    public <R extends Collection<Lazy<O>>> R getOnes(R result) {
    	result.clear();
    	try {
    		lock.readLock();
	        iterate(oneToManyMap.keySet(), wrapIds(result));
	        return result;
    	} finally {
    		lock.readUnlock();
    	}
    }
    

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#disassociateMany(java.io.Serializable)
     */
    @Override
    public boolean disassociateMany(Serializable... manySet) {
        boolean dirty = false;
    	try {
    		lock.writeLock();
	        for(Serializable many: manySet) {
	            dirty |= removeManyById(PersistencyAttributes.getIdFromVariant(mClass, many));
	        }
	        return dirty;
    	} finally {
    		lock.writeUnlock();
    	}
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#dissassociateOne(java.io.Serializable)
     */
    @Override
    public boolean dissassociateOne(Serializable one) {
    	try {
    		lock.writeLock();
    		return removeOneById(PersistencyAttributes.getIdFromVariant(oClass, one));
    	} finally {
    		lock.writeUnlock();
    	}
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#associate(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean associate(Serializable one, Serializable... manySet) {
        if(manySet == null) return false;
        if(manySet.length == 0) return false;
    	try {
    		lock.writeLock();
    		boolean dirty = addOneById(PersistencyAttributes.getIdFromVariant(oClass, one));
	        for(Serializable many: manySet) {
	            dirty |= associateIds(PersistencyAttributes.getIdFromVariant(oClass, one),
	                PersistencyAttributes.getIdFromVariant(mClass, many));
	        } 
	        return dirty;
    	} finally {
    		lock.writeUnlock();
    	}
    }

    private boolean addOneById(Serializable oneId) {
        if(oneId == null) return false;
        if(oneToManyMap.containsKey(oneId)) return false;
        oneToManyMap.put(oneId, new LinkedHashSet<Serializable>());
        return true;
    }

	private void addManyById(Serializable manyId, Serializable forOneId) {
        oneToManyMap.get(forOneId).add(manyId);
        manyToOneMap.put(manyId, forOneId);
    }

    private boolean isAssociatedById(Serializable oneId, Serializable manyId) {
        Serializable associatedOne = manyToOneMap.get(manyId);
        return (associatedOne != null) ? associatedOne.equals(oneId) : false;

    }

    private boolean removeManyById(Serializable manyId) {
        Serializable oneId = manyToOneMap.remove(manyId);
        if (oneId != null) {
            Set<Serializable> manySet = oneToManyMap.get(oneId);
            manySet.remove(manyId);
            if(manySet.size() == 0) oneToManyMap.remove(oneId);
            return true;
        }
        return false;
    }

    private boolean removeOneById(Serializable oneId) {
        Set<Serializable> manyIds = oneToManyMap.remove(oneId);
        if (manyIds == null)
            return false;
        for (Iterator<Serializable> iter = manyIds.iterator(); iter.hasNext();) {
            manyToOneMap.remove(iter.next());
        }
        return true;
    }

    private boolean associateIds(Serializable forOneId, Serializable manyId) {
        if(manyId == null) return false;
        if(forOneId == null) return removeManyById(manyId);
        if (isAssociatedById(forOneId, manyId)) return false;
        addOneById(forOneId);
        removeManyById(manyId);
        addManyById(manyId, forOneId);
        return true;
    }
}
