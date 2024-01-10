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
import gloodb.associations.OneToOneAssociation;
import gloodb.associations.Tuple;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static gloodb.operators.Iterators.*;

/**
 * Class implementing a one to one persistent association.
 * 
 * @param <L>
 *            The left value type.
 * @param <R>
 *            The right value type.
 */
class OneToOneAssociationImplementation<L extends Serializable, R extends Serializable>  implements Cloneable, OneToOneAssociation<L, R> {
    private static final long serialVersionUID = 5742583326562097534L;
    
    private LinkedHashMap<Serializable, Serializable> leftToRightMap;
    private LinkedHashMap<Serializable, Serializable> rightToLeftMap;
    private final Class<L> lClass;
    private final Class<R> rClass;
    private SafeReentrantReadWriteLock lock;

    /**
     * Creates the one to one association.
     * 
     * @param lClass
     *            The left value class.
     * @param rClass
     *            The right value class.
     */
    public OneToOneAssociationImplementation(Class<L> lClass, Class<R> rClass) {
        this.leftToRightMap = new LinkedHashMap<Serializable, Serializable>();
        this.rightToLeftMap = new LinkedHashMap<Serializable, Serializable>();
        this.lClass = lClass;
        this.rClass = rClass;
        this.lock = new SafeReentrantReadWriteLock();
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#add(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean associate(Serializable leftOne, Serializable rightOne) {
        if(leftOne == null && rightOne == null) return false;
    	try {
    		lock.writeLock();
	        if(leftOne == null) return dissassociateFromRight(rightOne);
	        if(rightOne == null) return dissassociateFromLeft(leftOne);
	        return associateById(PersistencyAttributes.getIdFromVariant(lClass, leftOne), PersistencyAttributes.getIdFromVariant(rClass, rightOne));
    	} finally {
    		lock.writeUnlock();
    	}
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#areAssociated(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean areAssociated(Serializable leftOne, Serializable rightOne) {
    	try {
    		lock.readLock();
    		return areAssociatedById(PersistencyAttributes.getIdFromVariant(lClass, leftOne), PersistencyAttributes.getIdFromVariant(rClass, rightOne));
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
            final OneToOneAssociationImplementation<L, R> copy = (OneToOneAssociationImplementation<L, R>) super.clone();
            copy.leftToRightMap = (LinkedHashMap<Serializable, Serializable>) leftToRightMap.clone();
            copy.rightToLeftMap = (LinkedHashMap<Serializable, Serializable>) rightToLeftMap.clone();
            copy.lock = new SafeReentrantReadWriteLock();
            return copy;
        } catch(CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        } finally {
        	lock.readUnlock();
        }
    }

	/*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getLeftAssociate(java.io.Serializable)
     */
    @Override
    public Lazy<L> getLeftAssociate(Serializable rightOne) {
    	try {
    		lock.readLock();
    		return new Lazy<L>(rightToLeftMap.get(PersistencyAttributes.getIdFromVariant(rClass, rightOne)));
    	} finally {
    		lock.readUnlock();
    	}
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getLeftAssociates()
     */
    @Override
    public <T extends Collection<Lazy<L>>> T getLeftAssociates(T result) {
        result.clear();
    	try {
    		lock.readLock();
    		iterate(rightToLeftMap.values(), wrapIds(result));
    		return result;
    	} finally {
    		lock.readUnlock();
    	}
        
    }
    
    @Override
    public Lazy<R> getRightAssociate(Serializable leftOne) {
    	try {
    		lock.readLock();
    		return new Lazy<R>(leftToRightMap.get(PersistencyAttributes.getIdFromVariant(lClass, leftOne)));
    	} finally {
    		lock.readUnlock();
    	}
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getRightAssociates()
     */
    @Override
    public <T extends Collection<Lazy<R>>> T getRightAssociates(T result) {
        result.clear();
    	try {
    		lock.readLock();
	        iterate(rightToLeftMap.keySet(), wrapIds(result));
	        return result;
    	} finally {
    		lock.readUnlock();
    	}
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#dissassociateFromLeft(java.io.Serializable)
     */
    @Override
    public boolean dissassociateFromLeft(Serializable leftOne) {
    	try {
    		lock.writeLock();
    		return removeLeftById(PersistencyAttributes.getIdFromVariant(lClass, leftOne));
    	} finally {
    		lock.writeUnlock();
    	}
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#dissassociateFromRight(java.io.Serializable)
     */
    @Override
    public boolean dissassociateFromRight(Serializable rightOne) {
    	try {
    		lock.writeLock();
    		return removeRightById(PersistencyAttributes.getIdFromVariant(rClass, rightOne));
    	} finally {
    		lock.writeUnlock();
    	}
    }
    
    private boolean associateById(Serializable leftId, Serializable rightId) {
        if(areAssociatedById(leftId, rightId)) return false;
        removeLeftById(leftId);
        removeRightById(rightId);
        leftToRightMap.put(leftId, rightId);
        rightToLeftMap.put(rightId, leftId);
        return true;
    }

    private boolean areAssociatedById(Serializable leftOneId, Serializable rightOneId) {
        Serializable associated = leftToRightMap.get(leftOneId);
        return associated != null? associated.equals(rightOneId) : false;
    }

    private boolean removeLeftById(Serializable leftId) {
        Serializable rightId = leftToRightMap.remove(leftId);
        if(rightId == null) return false;
        rightToLeftMap.remove(rightId);
        return true;
    }

    private boolean removeRightById(Serializable rightId) {
        Serializable leftId = rightToLeftMap.remove(rightId);
        if(leftId == null) return false;
        leftToRightMap.remove(leftId);
        return true;
    }

	@Override
	public <T extends Collection<Tuple>> T asLeftToRightTupleSet(T result) {
    	try {
    		lock.readLock();
    		return asTupleSet(result, this.leftToRightMap);
    	} finally {
    		lock.readUnlock();
    	}
	}

	@Override
	public <T extends Collection<Tuple>> T asRightToLeftTupleSet(T result) {
    	try {
    		lock.readLock();
    		return asTupleSet(result, this.leftToRightMap);
    	} finally {
    		lock.readUnlock();
    	}
	}
	
	
	private <T extends Collection<Tuple>> T asTupleSet(T result, Map<? extends Serializable,? extends Serializable> map) {
		result.clear();
		for(Entry<? extends Serializable, ? extends Serializable> entry: map.entrySet()) {
			result.add(new Tuple().add(entry.getKey()).add(entry.getValue()));
		}
		return result;

	}
}
