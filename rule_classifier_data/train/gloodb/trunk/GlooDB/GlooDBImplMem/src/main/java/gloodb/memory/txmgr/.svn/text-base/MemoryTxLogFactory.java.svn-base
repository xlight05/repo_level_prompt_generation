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

package gloodb.memory.txmgr;

import java.util.HashMap;

import gloodb.txmgr.TxLogConfiguration;
import gloodb.txmgr.TxLogFactory;
import gloodb.txmgr.TxLog;

class MemoryTxLogFactory implements TxLogFactory {
	private static final HashMap<String, MemoryTxLog> instances = new HashMap<String, MemoryTxLog>();
	
    public TxLog buildLog(TxLogConfiguration configuration) {
    	MemoryTxLog txLog = instances.get(configuration.getNameSpace());
    	if(txLog == null) {
    		txLog = new MemoryTxLog();
    		instances.put(configuration.getNameSpace(), txLog);
    	}
    	return txLog;
    }
}
