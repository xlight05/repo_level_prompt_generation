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
import java.util.Collection;

/**
 * Interface for many to many persistent associations.
 * 
 * @param <L>
 *            The left one value type.
 * @param <R>
 *            The right one value type.
 */
public interface ManyToManyAssociation<L extends Serializable, R extends Serializable> extends Serializable, Cloneable {

    /**
     * Returns true if the left and right objects are associated.
     * 
     * @param leftOne
     *            The left object: can be any object assignable from L, a Lazy<L>
     *            reference or object id.
     * @param rightSet
     *            The right object set: can be any object set assignable from R, Lazy<R>
     *            reference or object ids.
     * @return True if the left and the right set are associated.
     */
    boolean areLeftToRightAssociated(Serializable leftOne, Serializable... rightSet);

    
    /**
     * Returns true if the right and left objects are associated.
     * 
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or object id.
     * @param leftSet
     *            The left object set: can be any object set assignable from L, Lazy<L>
     *            reference or object ids.
     * @return True if the left set and the right are associated.
     */
    boolean areRightToLeftAssociated(Serializable rightOne, Serializable... leftSet); 
    
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
     * Creates an association between the left object and right object set.
     * 
     * @param leftOne
     *            The left object: can be any object assignable from L, a Lazy<L>
     *            reference or the object id.
     * @param rightSet
     *            The right object set: can be any set of objects assignable from R, Lazy<R>
     *            reference or object ids.
     * @return Returns true if the association has been modified.
     */
    boolean associateLeftToRight(Serializable leftOne, Serializable... rightSet);
    

    /**
     * Creates an association between the left object set and right object.
     * 
     * @param leftSet
     *            The left object set: can be any set of objects assignable from L, a Lazy<L>
     *            reference or the object id.
     * @param rightOne
     *            The right object: can be any object assignable from R, Lazy<R>
     *            reference or object ids.
     * @return Returns true if the association has been modified.
     */
    boolean associateRightToLeft(Serializable rightOne, Serializable... leftSet);
    
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
     * Returns a collection of references to left associate of the specified right.
     * @param result The result collection.
     * @param rightOne
     *            The right object: can be any object assignable from R, a Lazy<R>
     *            reference or the object id.
     * 
     * @return A collection of references to left associates of the specified right.
     */
     <C extends Collection<Lazy<L>>> C getRightToLeftAssociates(C result, Serializable rightOne);

     /**
      * Returns a collection of references to right associate of the specified left.
      * @param result The result collection.
      * @param leftOne
      *            The left object: can be any object assignable from L, a Lazy<L>
      *            reference or the object id.
      * 
      * @return A collection of reference to right associates of the specified left.
      */
      <C extends Collection<Lazy<R>>> C getLeftToRightAssociates(C result, Serializable leftOne);
     
    /**
     * Returns a list of references to the left associates.
     * @param result The result collection.
     * @return A collection of references to the left associates.
     */
     <T extends Collection<Lazy<L>>> T getLeftAssociates(T result);

    /**
     * Returns a list of references to the right associates.
     * @param result The result collection.
     * @return A collection of references to the right associates.
     */
    <T extends Collection<Lazy<R>>> T getRightAssociates(T result);

    /**
     * Clone method.
     * @return A copy of this object.
     */
    public Object clone();

    /**
     * Returns the left to right tuple set represented by this association.
     * @param result The result collection.
     * @return The association as a tuple.
     */
    public <T extends Collection<Tuple>> T asLeftToRightTupleSet(T result);

    /**
     * Returns the right to left tuple set represented by this association.
     * @param result The result collection.
     * @return The association as a tuple.
     */
    public <T extends Collection<Tuple>> T asRightToLeftTupleSet(T result);
}
