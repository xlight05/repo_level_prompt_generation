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

import static org.junit.Assert.*;
import gloodb.file.txmgr.DefaultFileTxLogConfiguration;
import gloodb.file.txmgr.FileTxLog;
import gloodb.txmgr.TxLogTestBase;
import gloodb.utils.FileUtils;

import java.io.File;
import javax.crypto.NullCipher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A transaction log test set, using file based strategy.
 *
 */
public class FileTxLogTest extends TxLogTestBase {

    @BeforeClass
    public static void setUpTest() {
    	FileUtils.deleteFileOrDirectory(new File("target/UnitTests/TxLog"));	
    }
	
	
    /**
     * Initializes the transaction manager using default configuration and
     * null encryption.
     */
    @Before
    @Override
    public void setUp() {
        this.tLog = new FileTxLog(new DefaultFileTxLogConfiguration(nameSpace,
                new NullCipher(), new NullCipher()));
        super.setUp();
    }

    /**
     * Closes the file log.
     */
    @Override
    protected void closeLog() {
        ((FileTxLog) this.tLog).closeLog();
    }
    
    @Test
    @Override
    public void startTransactionLog() {
    	super.startTransactionLog();
		assertTrue(new File(String.format("%s/%028d.tlog", nameSpace, 0)).exists());
    }
    
    @Test
    @Override
    public void restartTransactionLog() {
    	super.restartTransactionLog();
		assertTrue(new File(String.format("%s/%028d.tlog", nameSpace, 0)).exists());
    }
}
