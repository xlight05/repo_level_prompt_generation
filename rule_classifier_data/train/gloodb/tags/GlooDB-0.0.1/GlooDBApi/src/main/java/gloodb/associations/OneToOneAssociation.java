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
package gloodb.associations;

import gloodb.Lazy;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Interface for one to one persistent associations.
 * 
 * @param <L>
 *            The left one value type.
 * @param <R>
 *            The right one value type.
 */
public interface OneToOneAssociation<L extends Serializable, R extends Serializable> extends Serializable, Cloneable {

    /**
     * Returns true if the left and right object are associated.
     * 
     * @param leftOne
     *            The left object: can be any object assignable from L, a Lazy<L>
     *            reference or
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or the object id.
     * @return True if the left and the right are associated.
     */
    boolean areAssociated(Serializable leftOne, Serializable rightOne);

    /**
     * Creates an association between the left and right objects.
     * 
     * @param leftOne
     *            The left object: can be any object assignable from L, a Lazy<L>
     *            reference or the object id.
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or the object id.
     * @return Returns true if the association has been modified.
     */
    boolean associate(Serializable leftOne, Serializable rightOne);

    /**
     * Removes the association based on the left side.
     * 
     * @param leftOne
     *            The object: can be an object assignable from O, a Lazy<O>
     *            reference or the object id.
     * @return True if the association has been changed.
     */
    boolean dissassociateFromLeft(Serializable leftOne);

    /**
     * Removes the association based on the specified right side.
     * 
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or the object id.
     * @return True if the association has been changed.
     */
    boolean dissassociateFromRight(Serializable rightOne);

    /**
     * Returns a reference to left associate of the specified right.
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or the object id.
     * 
     * @return A reference to left associate of the specified right.
     */
    Lazy<L> getLeftAssociate(Serializable rightOne);
    
    /**
     * Returns a linked list of references to the left associates.
     * 
     * @return A linked list of references to the left associates.
     */
    LinkedList<Lazy<L>> getLeftAssociates();

    /**
     * Returns a reference to right associate of the specified left.
     * 
     * @param leftOne
     *            The right object: can be any object assignable from L, a Lazy<L>
     *            reference or the object id.
     * 
     * @return A reference to right associate of the specified left
     */
    Lazy<R> getRightAssociate(Serializable leftOne);

    /**
     * Returns a linked list of references to the right associates.
     * 
     * @return A linked list of references to the right associates.
     */
    LinkedList<Lazy<R>> getRightAssociates();

    /**
     * Clone method.
     * @return A copy of this object.
     */
    public Object clone();
}