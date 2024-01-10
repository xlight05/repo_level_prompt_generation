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
import gloodb.operators.Expression;
import gloodb.operators.WrapAsLazy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class implementing a one to many persistent association.
 * 
 * @param <O>
 *            The one value type.
 * @param <M>
 *            The many value type.
 */
class OneToManyAssociationImplementation<O extends Serializable, M extends Serializable> implements Cloneable, OneToManyAssociation<O, M> {
    private static final long serialVersionUID = 5742583326562097534L;
    private HashMap<Serializable, HashSet<Serializable>> oneToManyMap;
    private HashMap<Serializable, Serializable> manyToOneMap;
    private final Class<O> oClass;
    private final Class<M> mClass;

    /**
     * Creates the one to many association.
     * 
     * @param oClass
     *            The one class.
     * @param mClass
     *            The many class.
     */
    public OneToManyAssociationImplementation(Class<O> oClass, Class<M> mClass) {
        this.oneToManyMap = new HashMap<Serializable, HashSet<Serializable>>();
        this.manyToOneMap = new HashMap<Serializable, Serializable>();
        this.oClass = oClass;
        this.mClass = mClass;
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#areAssociated(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean areAssociated(Serializable forOne, Serializable... manySet) {
        HashSet<Serializable> associatedOnes = oneToManyMap.get(PersistencyAttributes.getIdFromVariant(oClass, forOne));
        if (associatedOnes == null)
            return false;
        for (Serializable many : manySet) {
            if (!associatedOnes.contains(PersistencyAttributes.getIdFromVariant(mClass, many)))
                return false;
        }
        return true;
    }

    /**
     * Clones the association.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            final OneToManyAssociationImplementation<O, M> copy = (OneToManyAssociationImplementation<O, M>) super.clone();
            copy.oneToManyMap = (HashMap<Serializable, HashSet<Serializable>>) oneToManyMap.clone();
            copy.manyToOneMap = (HashMap<Serializable, Serializable>) manyToOneMap.clone();
        return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#getAssociatesForOne(java.io.Serializable)
     */
    @Override
    public LinkedList<Lazy<M>> getAssociatesForOne(Serializable forOne) {
        HashSet<Serializable> manySet = oneToManyMap.get(PersistencyAttributes.getIdFromVariant(oClass, forOne));
        if(manySet == null) return new LinkedList<Lazy<M>>();
        LinkedList<Lazy<M>> result = new LinkedList<Lazy<M>>();
        Expression.iterate(manySet, new WrapAsLazy<M>(result));
        return result;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#getOnes()
     */
    @Override
    public LinkedList<Lazy<O>> getOnes() {
        LinkedList<Lazy<O>> result = new LinkedList<Lazy<O>>();
        Expression.iterate(oneToManyMap.keySet(), new WrapAsLazy<O>(result));
        return result;
    }
    

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#disassociateMany(java.io.Serializable)
     */
    @Override
    public boolean disassociateMany(Serializable... manySet) {
        boolean dirty = false;
        for(Serializable many: manySet) {
            dirty |= removeManyById(PersistencyAttributes.getIdFromVariant(mClass, many));
        }
        return dirty;
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#dissassociateOne(java.io.Serializable)
     */
    @Override
    public boolean dissassociateOne(Serializable one) {
        return removeOneById(PersistencyAttributes.getIdFromVariant(oClass, one));
    }

    /* (non-Javadoc)
     * @see gloodb.associations.OneToManyAssociation#associate(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean associate(Serializable one, Serializable... manySet) {
        if(manySet == null) return false;
        if(manySet.length == 0) return false;
        boolean dirty = addOneById(PersistencyAttributes.getIdFromVariant(oClass, one));
        for(Serializable many: manySet) {
            dirty |= associateIds(PersistencyAttributes.getIdFromVariant(oClass, one),
                PersistencyAttributes.getIdFromVariant(mClass, many));
        } 
        return dirty;
    }

    private boolean addOneById(Serializable oneId) {
        if(oneId == null) return false;
        if(oneToManyMap.containsKey(oneId)) return false;
        oneToManyMap.put(oneId, new HashSet<Serializable>());
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
            HashSet<Serializable> manySet = oneToManyMap.get(oneId);
            manySet.remove(manyId);
            if(manySet.size() == 0) oneToManyMap.remove(oneId);
            return true;
        }
        return false;
    }

    private boolean removeOneById(Serializable oneId) {
        HashSet<Serializable> manyIds = oneToManyMap.remove(oneId);
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
