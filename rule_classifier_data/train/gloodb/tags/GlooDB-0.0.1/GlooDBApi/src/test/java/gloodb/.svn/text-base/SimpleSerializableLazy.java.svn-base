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

public class SimpleSerializableLazy implements Serializable {
    private static final long serialVersionUID = -5981796444710412403L;
    @Identity
    final Serializable id;
    
    final Lazy<SimpleValued> lazyValue;
    
    public SimpleSerializableLazy(Serializable id) { 
        this.id = id;
        this.lazyValue = new Lazy<SimpleValued>((Serializable)null);
    }
    
    public Serializable getId() {
        return id;
    }
    
    public Lazy<SimpleValued> getLazyValue() {
        return lazyValue;
    }
    
    public SimpleSerializableLazy setLazyValue(SimpleValued value) {
        lazyValue.setReference(value.getId());
        return this;
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
        SimpleSerializableLazy other = (SimpleSerializableLazy) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, value: %s", id.toString(), this.lazyValue.toString());
    }
}