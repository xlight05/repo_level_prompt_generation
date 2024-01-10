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
import gloodb.associations.ManyToManyAssociation;
import gloodb.associations.Tuple;
import gloodb.utils.SafeReentrantReadWriteLock;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import static gloodb.operators.Iterators.*;

/**
 * Class implementing a many to many persistent association.
 * 
 * @param <L>
 *            The left value type.
 * @param <R>
 *            The right value type.
 */
class ManyToManyAssociationImplementation<L extends Serializable, R extends Serializable>  implements Cloneable, ManyToManyAssociation<L, R> {
    private static final long serialVersionUID = 5742583326562097534L;
    
    private SafeReentrantReadWriteLock lock;
    private LinkedHashMap<Serializable, LinkedHashSet<Serializable>> leftToRightMap;
    private LinkedHashMap<Serializable, LinkedHashSet<Serializable>> rightToLeftMap;
    private final Class<L> lClass;
    private final Class<R> rClass;

    /**
     * Creates the one to one association.
     * 
     * @param lClass
     *            The left value class.
     * @param rClass
     *            The right value class.
     */
    public ManyToManyAssociationImplementation(Class<L> lClass, Class<R> rClass) {
        this.leftToRightMap = new LinkedHashMap<Serializable, LinkedHashSet<Serializable>>();
        this.rightToLeftMap = new LinkedHashMap<Serializable, LinkedHashSet<Serializable>>();
        this.lClass = lClass;
        this.rClass = rClass;
        this.lock = new SafeReentrantReadWriteLock();
    }
    
    private boolean areAssociatedById(LinkedHashMap<Serializable, LinkedHashSet<Serializable>> map, Serializable leftOneId, Serializable rightOneId) {
    	try {
    		lock.readLock();
	    	LinkedHashSet<Serializable> associatedSet = map.get(leftOneId);
	        return associatedSet != null? associatedSet.contains(rightOneId) : false;
    	} finally { 
    		lock.readUnlock();
    	}
    }

    @Override
	public boolean areLeftToRightAssociated(Serializable leftOne, Serializable... rightSet) {
    	try {
    		lock.readLock();
			Serializable leftOneId = PersistencyAttributes.getIdFromVariant(lClass, leftOne);
			for(Serializable rightOne: rightSet) {
				Serializable rightOneId = PersistencyAttributes.getIdFromVariant(rClass, rightOne);
				if(!areAssociatedById(leftToRightMap, leftOneId, rightOneId)) return false;
			}
			return true;
    	} finally {
    		lock.readUnlock();
    	}
	}

    @Override
	public boolean areRightToLeftAssociated(Serializable rightOne, Serializable... leftSet) {
    	try {
    		lock.readLock();
			Serializable rightOneId = PersistencyAttributes.getIdFromVariant(rClass, rightOne);
			for(Serializable leftOne: leftSet) {
				Serializable leftOneId = PersistencyAttributes.getIdFromVariant(lClass, leftOne);
				if(!areAssociatedById(rightToLeftMap, rightOneId, leftOneId)) return false;
			}
			return true;
    	} finally {
    		lock.readUnlock();
    	}
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

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#add(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean associate(Serializable leftOne, Serializable rightOne) {
    	
        if(leftOne == null && rightOne == null) return false;
        if(leftOne == null) return false;
        if(rightOne == null) return false;
    	try {
    		lock.writeLock();
    		return associateById(PersistencyAttributes.getIdFromVariant(lClass, leftOne), PersistencyAttributes.getIdFromVariant(rClass, rightOne));
    	} finally {
    		lock.writeUnlock();
    	}
    }
    
    private boolean associateById(Serializable leftId, Serializable rightId) {
        if(areAssociatedById(leftToRightMap, leftId, rightId)) return false;
        associateById(leftToRightMap, leftId, rightId);
        associateById(rightToLeftMap, rightId, leftId);
        return true;
    }

    private void associateById(LinkedHashMap<Serializable, LinkedHashSet<Serializable>> map, Serializable oneId, Serializable manyId) {
    	LinkedHashSet<Serializable> set = map.get(oneId);
        if(set == null) {
        	set = new LinkedHashSet<Serializable>();
        	map.put(oneId, set);
        }
        set.add(manyId);
	}

	@Override
	public boolean associateLeftToRight(Serializable leftOne, Serializable... rightSet) {
		boolean dirty = false;
		Serializable leftOneId = PersistencyAttributes.getIdFromVariant(lClass, leftOne);
    	try {
    		lock.writeLock();
    		for(Serializable rightOne: rightSet) {
				Serializable rightOneId = PersistencyAttributes.getIdFromVariant(rClass, rightOne);
				dirty |= this.associateById(leftOneId, rightOneId);
			}
			return dirty;
    	} finally {
    		lock.writeUnlock();
    	}
	}

    @Override
	public boolean associateRightToLeft(Serializable rightOne, Serializable... leftSet) {
		boolean dirty = false;
		Serializable rightOneId = PersistencyAttributes.getIdFromVariant(rClass, rightOne);
    	try {
    		lock.writeLock();
			for(Serializable leftOne: leftSet) {
				Serializable leftOneId = PersistencyAttributes.getIdFromVariant(lClass, leftOne);
				dirty |= this.associateById(leftOneId, rightOneId);
			}
			return dirty;
    	} finally {
    		lock.writeUnlock();
    	}
	}

    private <T extends Collection<Tuple>> T asTupleSet(T result, LinkedHashMap<Serializable, LinkedHashSet<Serializable>> map) {
		result.clear();
		for(Entry<Serializable, LinkedHashSet<Serializable>> entry: map.entrySet()) {
			for(Serializable s: entry.getValue()) {
				result.add(new Tuple().add(entry.getKey()).add(s));
			}
		}
		return result;
	}

	/**
     * Clones the association.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
    	try {
    		lock.readLock();
	        final ManyToManyAssociationImplementation<L, R> copy = (ManyToManyAssociationImplementation<L, R>) super.clone();
	        copy.leftToRightMap = (LinkedHashMap<Serializable, LinkedHashSet<Serializable>>) leftToRightMap.clone();
	        copy.rightToLeftMap = (LinkedHashMap<Serializable, LinkedHashSet<Serializable>>) rightToLeftMap.clone();
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

	/*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getLeftAssociates()
     */
    @Override
    public <T extends Collection<Lazy<L>>> T getLeftAssociates(T result) {
        result.clear();
    	try {
    		lock.readLock();
	        iterate(leftToRightMap.keySet(), wrapIds(result));
	        return result;
    	} finally {
    		lock.readUnlock();
    	}
    }

	@Override
	public <C extends Collection<Lazy<R>>> C getLeftToRightAssociates(C result, Serializable leftOne) {
		result.clear();
    	try {
    		lock.readLock();
			LinkedHashSet<Serializable> rightSet = this.leftToRightMap.get(PersistencyAttributes.getIdFromVariant(lClass, leftOne)); 
			if(rightSet != null) iterate(rightSet, wrapIds(result));
			return result;
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

	@Override
	public <C extends Collection<Lazy<L>>> C getRightToLeftAssociates(C result, Serializable rightOne) {
		result.clear();
        try {
        	lock.readLock();
			LinkedHashSet<Serializable> leftSet = this.rightToLeftMap.get(PersistencyAttributes.getIdFromVariant(rClass, rightOne)); 
			if(leftSet != null) iterate(leftSet, wrapIds(result));
			return result;
        } finally {
        	lock.readUnlock();
        }		
	}

	private boolean removeLeftById(Serializable leftId) {
		LinkedHashSet<Serializable> rightSet = leftToRightMap.remove(leftId);
        if(rightSet == null) return false;
        for(Serializable rightId: rightSet) {
        	rightToLeftMap.remove(rightId);
        }
        return true;
    }

	private boolean removeRightById(Serializable rightId) {
		LinkedHashSet<Serializable> leftSet = rightToLeftMap.remove(rightId);
        if(leftSet == null) return false;
        for(Serializable leftId: leftSet) {
        	leftToRightMap.remove(leftId);
        }
        return true;
    }
}
