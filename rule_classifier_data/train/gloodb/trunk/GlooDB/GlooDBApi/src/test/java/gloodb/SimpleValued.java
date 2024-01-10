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
package gloodb;

import java.io.Serializable;

public class SimpleValued implements Serializable, Cloneable {
    private static final long serialVersionUID = 3546996863751075659L;
    
    @Identity 
    private Serializable id;
    
    private long value;

    public SimpleValued(Serializable id) {
        this.id = id;
    }
    
    public Serializable getId() {
        return id;
    }
    
    public long getValue() {
        return value;
    }
    
    public SimpleValued setValue(long value) {
        this.value = value;
        return this;
    }
    
    @Override
    public Object clone() {
        try {
            SimpleValued copy = (SimpleValued) super.clone();
            copy.value = value;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new GlooException(e);
        }
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        SimpleValued other = (SimpleValued) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, value: %d", id.toString(), value);
    }
}
