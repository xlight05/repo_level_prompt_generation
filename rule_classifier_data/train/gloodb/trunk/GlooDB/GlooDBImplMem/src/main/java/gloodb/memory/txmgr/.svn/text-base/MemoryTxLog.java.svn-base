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
package gloodb.memory.txmgr;

import gloodb.txmgr.*;

import java.io.Serializable;

import java.math.BigInteger;


/**
 * In memory transaction log implementation class
 * 
 * 
 */
class MemoryTxLog implements TxLog {

	private BigInteger startVersion;
	private BigInteger version;
	
	public MemoryTxLog() {
		this.startVersion = BigInteger.ZERO;
		this.version = BigInteger.ZERO;
	}

	@Override
	public BigInteger getStartVersion() {
		return startVersion;
	}

	@Override
	public BigInteger getVersion() {
		return version;
	}

	@Override
	public synchronized void log(Serializable value) {
		version = version.add(BigInteger.ONE);
	}

	@Override
	public <T extends Serializable> T start(TxContext ctx, T startState) {
        this.startVersion = BigInteger.ZERO;
        this.version = BigInteger.ZERO;
	    return startState;
	}
    
	@Override
	public void takeSnapshot(Serializable startState) {
	}
}
