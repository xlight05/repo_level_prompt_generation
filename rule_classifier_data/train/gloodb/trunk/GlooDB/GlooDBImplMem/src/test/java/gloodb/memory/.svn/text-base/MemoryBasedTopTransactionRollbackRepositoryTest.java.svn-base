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
package gloodb.memory;


import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gloodb.Repository;
import gloodb.PersistentFactorySimpleVersioned;
import gloodb.TopTransactionRollbackRepositoryTestBase;

/**
 * A repository test set using memory based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class MemoryBasedTopTransactionRollbackRepositoryTest extends TopTransactionRollbackRepositoryTestBase {

    private static final String nameSpace = "target/UnitTests/Repository";

    @Parameters
    public static Collection<Object[]> data() {
        Repository repository = new MemoryBasedRepositoryFactory().buildRepository(nameSpace);
        
        return Arrays.asList(new Object[][] {
                {repository, new PersistentFactorySimpleVersioned()}
        });
    }
   
    /**
     * Creates a repository test set using memory based transaction and storage strategies.
     */
    public MemoryBasedTopTransactionRollbackRepositoryTest(Repository repository, PersistentFactorySimpleVersioned factory ) {
        super(repository, factory);
    }
}
