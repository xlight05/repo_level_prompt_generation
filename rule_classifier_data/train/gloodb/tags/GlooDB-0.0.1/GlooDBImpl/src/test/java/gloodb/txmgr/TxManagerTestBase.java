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
package gloodb.txmgr;


import java.io.File;

import gloodb.GlooException;
import gloodb.utils.FileUtils;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A transaction manager test set, agnostic of actual logging strategy.
 * 
 */
public abstract class TxManagerTestBase {

    /**
     * The transaction manager instance.
     */
    protected TxManager transactionManager;
    /**
     * The managed object.
     */
    protected MockManagedObject managedObject;
    /**
     * The namespace (directory) used for the transaction log.
     */
    protected final static String nameSpace = "target/UnitTests/TxManager";

    @BeforeClass
    public static void setUpTest() {
    	FileUtils.deleteFileOrDirectory(new File("target/UnitTests/TxManager"));	
    }

    /**
     * Creates a mock managed object.
     */
    @Before
    public void setUp() {
        this.managedObject = new MockManagedObject();
    }

    /**
     * A new transaction log contains only the managed object as the first transaction
     * (the initial state is the managed object itself).
     */
    @Test
    public void startTransactionManager() {
        transactionManager.start(null, managedObject);
        assertEquals(0, transactionManager.getManagedObjectVersion().intValue());
        assertEquals(0, transactionManager.execute(null, new GetVersionTransaction()));
    }

    /**
     * Changes to the managed object are logged as transactions.
     */
    @Test
    public void writeTransaction() {
        transactionManager.start(null, managedObject);
        transactionManager.execute(null, new IncrementVersionTransaction());
        assertEquals(1, transactionManager.execute(null, new GetVersionTransaction()));
    }

    /**
     * Change to the managed objects may return a result.
     */
    @Test
    public void readWriteTransaction() {
        transactionManager.start(null, managedObject);
        assertEquals(2, transactionManager.execute(null, new IncrementAndGetVersionTransaction()));
    }

    /**
     * Bad transactions (bad because they throw an exception)
     * are logged also since they may change the state of the
     * managed object until they throw the exception.
     */
    @Test
    public void badTransaction() {
        transactionManager.start(null, managedObject);
    	int startVersion = (Integer)transactionManager.execute(null, new GetVersionTransaction());
        try {
            transactionManager.execute(null, new BadTransaction());
            fail("An exception should've been thrown.");
        } catch (GlooException ge) {
            assertEquals(startVersion, transactionManager.execute(null, new GetVersionTransaction()));
        }
    }
}