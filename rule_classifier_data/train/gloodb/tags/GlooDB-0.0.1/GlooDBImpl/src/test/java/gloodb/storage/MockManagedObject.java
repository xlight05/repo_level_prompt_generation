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
package gloodb.storage;

import java.io.Serializable;
import java.math.BigInteger;

class MockManagedObject implements Serializable {

    private static final long serialVersionUID = 1L;
    int version = 0;
    
    private BigInteger bigInteger;

    public MockManagedObject() {
    }

    public MockManagedObject(int version) {
        this.version = version;
    }
    
    public BigInteger allocateBigInteger(long length) {
    	StringBuffer buf = new StringBuffer();
    	for(long i = 0; i < length; i++) {
    		buf.append((int)Math.floor(Math.random() * 10));
    	}
    	bigInteger = new BigInteger(buf.toString());
    	return bigInteger;
    }

	public BigInteger getBigInteger() {
		return bigInteger;
	}
	
	@Override
	public String toString() {
		return String.format("{%s/%d}", bigInteger != null? bigInteger.toString(): null, version);
	}
}
