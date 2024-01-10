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

import static org.junit.Assert.assertEquals;
import gloodb.txmgr.IncrementAndGetVersionTransaction;
import gloodb.txmgr.TxManagerFactory;
import gloodb.txmgr.TxManagerTestBase;
import org.junit.Before;

/**
 * A transaction manager test set, using memory based strategy.
 *
 */
public class MemoryTxManagerTest extends TxManagerTestBase {

    /**
     * Creates a new memory transaction manager.
     */
    @Before
    @Override
    public void setUp() {
    	TxManagerFactory txManagerFactory = new TxManagerFactory();
    	MemoryTxLogConfiguration logConfiguration = new MemoryTxLogConfiguration(nameSpace); 
    	MemoryTxLogFactory logFactory = new MemoryTxLogFactory();
        this.transactionManager = txManagerFactory.buildTxManager(logFactory, logConfiguration, null);
        super.setUp();
    }

    @Override
    public void readWriteTransaction() {
        // The in memory doesn't keep a transaction log.
        transactionManager.start(null, managedObject);
        assertEquals(1, transactionManager.execute(null, new IncrementAndGetVersionTransaction()));
    }
}