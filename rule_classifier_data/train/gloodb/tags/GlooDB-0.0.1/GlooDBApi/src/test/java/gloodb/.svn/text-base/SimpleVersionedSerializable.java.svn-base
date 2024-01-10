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
import java.math.BigInteger;

public class SimpleVersionedSerializable implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Identity
    final Serializable id;
    
    @Version
    BigInteger version;
    
    public SimpleVersionedSerializable(Serializable id) { 
        this.id = id;
        this.version = BigInteger.ZERO;
    }
    
    public Serializable getId() {
        return id;
    }
    
    public BigInteger getVersion() {
        return version;
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
        SimpleVersionedSerializable other = (SimpleVersionedSerializable) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}