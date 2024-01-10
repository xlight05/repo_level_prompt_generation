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
package gloodb;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Wrapper class for aggregate identities.
 *
 */
public class AggregateIdentity implements Serializable, Cloneable {
    private static final long serialVersionUID = 9185196793064115226L;
    
    private Serializable[] ids;

    /**
     * Creates a new aggregate identity.
     * @param idFields The id fields
     */
    public AggregateIdentity(Serializable[] idFields) {
        this.ids = Arrays.copyOf(idFields, idFields.length);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        try {
            AggregateIdentity copy = (AggregateIdentity) super.clone();
            copy.ids = new Serializable[ids.length];
            for(int i = 0; i < ids.length; i++) {
                copy.ids[i] = Cloner.deepCopy(ids[i]);
            }
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(ids);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AggregateIdentity other = (AggregateIdentity) obj;
        if (!Arrays.equals(ids, other.ids))
            return false;
        return true;
    }
    
    /**
     * Returns the identity fields as an array.
     * @return The array of identity fields.
     */
    public Serializable[] getIdentityFields() {
        return ids;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Arrays.toString(ids);
    }
}