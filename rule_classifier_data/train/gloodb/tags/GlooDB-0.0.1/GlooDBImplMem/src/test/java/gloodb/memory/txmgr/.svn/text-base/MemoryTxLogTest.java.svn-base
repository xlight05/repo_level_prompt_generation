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
import gloodb.memory.txmgr.MemoryTxLog;
import gloodb.txmgr.TxLogTestBase;
import org.junit.Before;

/**
 * A transaction log test set, using memory based strategy.
 */
public class MemoryTxLogTest extends TxLogTestBase {

    /**
     * Creates a new memory transaction log.
     */
    @Before
    @Override
    public void setUp() {
        this.tLog = new MemoryTxLog();
        super.setUp();
    }

    /**
     * Closes the memory log.
     */
    @Override
    protected void closeLog() {
       // Does nothing
    }

    @Override
    public void loadTransactions() {
        managedObject = tLog.start(null, managedObject);

        // the in-memory repository doesn't keep a transaction log.
        assertEquals(0, managedObject.version);
    }
    
    
}