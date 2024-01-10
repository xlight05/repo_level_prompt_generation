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
 * Interface for one to many persistent associations.
 * 
 * @param <O>
 *            The one value type.
 * @param <M>
 *            The many value type.
 */
public interface OneToManyAssociation<O extends Serializable, M extends Serializable> extends Serializable, Cloneable {

    /**
     * Returns true if all of the many objects are associated with the specified
     * one.
     * 
     * @param forOne
     *            The one object: can be an object assignable from O, a Lazy<O>
     *            reference or the object id.
     * @param manySet
     *            The many objects: can be objects assignable from M, Lazy<M>
     *            references or object ids.
     * @return True if all of the many objects are associated with one.
     */
    boolean areAssociated(Serializable forOne, Serializable... manySet);

    /**
     * Creates an association. The many objects in the manySet is associated to the 
     * specified one (and it is remove from its previous associates).
     * @param forOne
     *            The one object: can be an object assignable from O, a Lazy<O>
     *            or the object id.
     * @param manySet
     *            The many object set: objects in the set can be an object assignable from M, a Lazy<M>
     *            or the object id.
     * 
     * @return True if the association has been changed.
     */
    boolean associate(Serializable forOne, Serializable... manySet);

    /**
     * Removes the many object from the association.
     * 
     * @param many
     *            The many object: can be an object assignable from M, a Lazy<M>
     *            reference or an object id.
     * @return True if the association has been changed.
     */
    boolean disassociateMany(Serializable... many);

    /**
     * Removes the specified one object from the association.
     * 
     * @param one
     *            The one object: can be an object assignable from O, a Lazy<O>
     *            reference or the object id.
     * @return True if the association has been changed.
     */
    boolean dissassociateOne(Serializable one);

    /**
     * Returns a the linked list of lazy references to the many associates of
     * one.
     * @param forOne
     *            The one object: can be any object assignable from O, a Lazy<O>
     *            reference or the object id.
     * @return The list of references to many.
     */
    LinkedList<Lazy<M>> getAssociatesForOne(Serializable forOne);

    /**
     * Returns a linked list of one object ids.
     * 
     * @return The linked list of one objects.
     */
    LinkedList<Lazy<O>> getOnes();
    
    /**
     * Clone method.
     * @return A copy of this object.
     */
    public Object clone();
}