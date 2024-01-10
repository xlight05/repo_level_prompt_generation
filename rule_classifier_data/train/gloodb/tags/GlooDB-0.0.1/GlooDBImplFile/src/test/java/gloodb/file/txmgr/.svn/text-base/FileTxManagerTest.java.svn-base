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
package gloodb.file.txmgr;

import static org.junit.Assert.assertTrue;
import gloodb.txmgr.TxManagerFactory;
import gloodb.txmgr.TxManagerTestBase;
import java.io.File;
import javax.crypto.NullCipher;
import org.junit.Before;
import org.junit.Test;

/**
 * A transaction log test set, using file based strategy.
 *
 */
public class FileTxManagerTest extends TxManagerTestBase {

    /**
     * Creates a transaction manager using file base strategy.
     */
    @Before
    @Override
    public void setUp() {
    	TxManagerFactory txManagerFactory = new TxManagerFactory();
    	FileTxLogConfiguration logConfiguration = new DefaultFileTxLogConfiguration(nameSpace, new NullCipher(), new NullCipher()); 
    	FileTxLogFactory logFactory = new FileTxLogFactory();
        this.transactionManager = txManagerFactory.buildTxManager(logFactory, logConfiguration, null);
        super.setUp();
    }
    
    @Test
    @Override
    public void startTransactionManager() {
    	super.startTransactionManager();
        assertTrue(new File(String.format("%s/%028d.tlog", nameSpace, 0)).exists());
    }
}