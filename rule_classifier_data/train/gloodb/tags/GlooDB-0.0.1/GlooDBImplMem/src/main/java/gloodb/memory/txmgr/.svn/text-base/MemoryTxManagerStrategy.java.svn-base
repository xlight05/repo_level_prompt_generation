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

import gloodb.txmgr.TxLogConfiguration;
import gloodb.txmgr.TxLogFactory;
import gloodb.txmgr.TxManagerInterceptor;
import gloodb.txmgr.TxManagerStrategy;

/**
 * Memory transaction log strategy. This strategy uses files to implement the
 * transaction manager.
 * 
 */
public class MemoryTxManagerStrategy implements TxManagerStrategy {
    /**
     * The default transaction log configuration.
     */
    private final MemoryTxLogConfiguration configuration;
    
    private final MemoryTxLogFactory factory;
    
    private final TxManagerInterceptor interceptor;

    /**
     * Creates a memory based transaction log strategy for the namespace provided.
     * @param nameSpace The name space.
     */
    public MemoryTxManagerStrategy(String nameSpace, TxManagerInterceptor interceptor) {
        this.configuration = new MemoryTxLogConfiguration(nameSpace);
        this.factory = new MemoryTxLogFactory();
        this.interceptor = interceptor;
    }
    
    /**
     * Memory transaction log configuration getter.
     * @return The configuration.
     */
    public TxLogConfiguration getConfiguration() {
        return this.configuration;
    }

	@Override
	public TxLogFactory getFactory() {
		return this.factory;
	}

    @Override
    public TxManagerInterceptor getInterceptor() {
        return interceptor;
    }
}
