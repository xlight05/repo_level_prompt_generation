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
import gloodb.operators.Expression;
import gloodb.operators.WrapAsLazy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class implementing a one to one persistent association.
 * 
 * @param <L>
 *            The left value type.
 * @param <R>
 *            The right value type.
 */
class OneToOneAssociationImplementation<L extends Serializable, R extends Serializable> implements Cloneable, OneToOneAssociation<L, R> {
    private static final long serialVersionUID = 5742583326562097534L;
    private HashMap<Serializable, Serializable> leftToRightMap;
    private HashMap<Serializable, Serializable> rightToLeftMap;
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
    public OneToOneAssociationImplementation(Class<L> lClass, Class<R> rClass) {
        this.leftToRightMap = new HashMap<Serializable, Serializable>();
        this.rightToLeftMap = new HashMap<Serializable, Serializable>();
        this.lClass = lClass;
        this.rClass = rClass;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#add(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean associate(Serializable leftOne, Serializable rightOne) {
        if(leftOne == null && rightOne == null) return false;
        if(leftOne == null) return dissassociateFromRight(rightOne);
        if(rightOne == null) return dissassociateFromLeft(leftOne);
        return associateById(PersistencyAttributes.getIdFromVariant(lClass, leftOne), PersistencyAttributes.getIdFromVariant(rClass, rightOne));
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#areAssociated(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public boolean areAssociated(Serializable leftOne, Serializable rightOne) {
        return areAssociatedById(PersistencyAttributes.getIdFromVariant(lClass, leftOne), PersistencyAttributes.getIdFromVariant(rClass, rightOne));
    }

    /**
     * Clones the association.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            final OneToOneAssociationImplementation<L, R> copy = (OneToOneAssociationImplementation<L, R>) super.clone();
            copy.leftToRightMap = (HashMap<Serializable, Serializable>) leftToRightMap.clone();
            copy.rightToLeftMap = (HashMap<Serializable, Serializable>) rightToLeftMap.clone();
            return copy;
        } catch(CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getLeftAssociate(java.io.Serializable)
     */
    @Override
    public Lazy<L> getLeftAssociate(Serializable rightOne) {
        return new Lazy<L>(rightToLeftMap.get(PersistencyAttributes.getIdFromVariant(rClass, rightOne)));
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getLeftAssociates()
     */
    @Override
    public LinkedList<Lazy<L>> getLeftAssociates() {
        LinkedList<Lazy<L>> result = new LinkedList<Lazy<L>>();
        Expression.iterate(rightToLeftMap.values(), new WrapAsLazy<L>(result));
        return result;
        
    }
    
    @Override
    public Lazy<R> getRightAssociate(Serializable leftOne) {
        return new Lazy<R>(leftToRightMap.get(PersistencyAttributes.getIdFromVariant(lClass, leftOne)));
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#getRightAssociates()
     */
    @Override
    public LinkedList<Lazy<R>> getRightAssociates() {
        LinkedList<Lazy<R>> result = new LinkedList<Lazy<R>>();
        Expression.iterate(rightToLeftMap.keySet(), new WrapAsLazy<R>(result));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#dissassociateFromLeft(java.io.Serializable)
     */
    @Override
    public boolean dissassociateFromLeft(Serializable leftOne) {
        return removeLeftById(PersistencyAttributes.getIdFromVariant(lClass, leftOne));
    }

    /*
     * (non-Javadoc)
     * @see gloodb.associations.OneToOneAssociation#dissassociateFromRight(java.io.Serializable)
     */
    @Override
    public boolean dissassociateFromRight(Serializable rightOne) {
        return removeRightById(PersistencyAttributes.getIdFromVariant(rClass, rightOne));
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
}
