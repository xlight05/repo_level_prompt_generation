/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.utils;

import gloodb.PersistencyAttributes;

import java.io.Serializable;

/**
 * Equality utility class. Use this class to implement equality in persistent classes.
 */
public final class Equality {
    public static interface Operator< T extends Serializable> {
        boolean check(T left, T right);
    }
    
    /**
     * Determines two persistent objects are equal based on their persistent identities.
     *
     */
    public static class PersistentIdentityEquality<T extends Serializable> implements Operator<T> {

    	@Override
		public boolean check(T left, T right) {
			Serializable leftId = getId(left);
	        Serializable rightId = getId(right);
	        if(leftId == rightId) return true;
	        if(leftId != null && rightId == null) return false;
	        if(leftId == null) return rightId == null;
	        return leftId.equals(rightId);
		}
    	
    	public Serializable getId(T obj) {
    		return PersistencyAttributes.getIdForObject(obj);
    	}
    }
    
    private Equality(){}
    
    /**
     * Checks if left and right objects are equal.
     * @param left The left object
     * @param right The right object
     * @param operator The equality operator.
     * @return True if the left and right objects are equal.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> boolean check(Object left, Object right, Operator<T> operator) {
        if(left == right) return true;
        if(left != null && right == null) return false;
        if(left == null && right != null) return false;
        if(left.getClass() != right.getClass()) return false;
        try {
        	return operator.check((T)left, (T)right);
        } catch (ClassCastException e) {
        	return false;
        }
    }


    /**
     * Checks if left and right objects are equal using persistent equality operator.
     * @param left The left object
     * @param right The right object
     * @return True if the left and right objects are equal.
     */
	public static <T extends Serializable> boolean check(Serializable left, Serializable right) {
        if(left == right) return true;
        if(left != null && right == null) return false;
        if(left == null) return false;
        if(left.getClass() != right.getClass()) return false;
        return new PersistentIdentityEquality<Serializable>().check(left, right);
    }
}
