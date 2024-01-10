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
package gloodb.file;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gloodb.NestedTransactionRollbackRepositoryTestBase;
import gloodb.Repository;
import gloodb.PersistentFactorySimpleVersioned;
import gloodb.TopTransactionCommitRepositoryTestBase;
import gloodb.TopTransactionRollbackRepositoryTestBase;
import gloodb.TransactionalRepositoryTestBase;


/**
 * A repository test set using memory based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class FileBasedNestedTransactionRollbackRepositoryTest extends NestedTransactionRollbackRepositoryTestBase {

    private static final String nameSpace = "target/UnitTests/Repository";

    @Parameters
    public static Collection<Object[]> data() {
        Repository noStorageRepository = FileBasedRepositoryFactory.buildNoStorageRepository(nameSpace + "/NoStorage", null);
        Repository blockRepository = FileBasedRepositoryFactory.buildRepository(nameSpace + "/Block", null);
        return Arrays.asList(new Object[][] {
                { noStorageRepository, new PersistentFactorySimpleVersioned(), TopTransactionCommitRepositoryTestBase.class },
                { noStorageRepository, new PersistentFactorySimpleVersioned(), TopTransactionRollbackRepositoryTestBase.class }, 
                { blockRepository, new PersistentFactorySimpleVersioned(), TopTransactionCommitRepositoryTestBase.class },
                { blockRepository, new PersistentFactorySimpleVersioned(), TopTransactionRollbackRepositoryTestBase.class }
         });
    }

    /**
     * Creates a repository test set using memory based transaction and storage
     * strategies. 
     */
    public FileBasedNestedTransactionRollbackRepositoryTest(Repository repository, PersistentFactorySimpleVersioned factory,
            Class<? extends TransactionalRepositoryTestBase> delegatedTestClass) throws Exception {
        super(repository, factory, delegatedTestClass);
    }
}
