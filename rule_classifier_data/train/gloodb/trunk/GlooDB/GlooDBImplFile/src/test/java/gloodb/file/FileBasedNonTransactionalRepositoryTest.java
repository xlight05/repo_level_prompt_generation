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

import gloodb.PersistentFactoryCloneable;
import gloodb.PersistentFactory;
import gloodb.Repository;
import gloodb.NonTransactionalRepositoryTestBase;
import gloodb.PersistentFactorySimpleSerializable;
import gloodb.PersistentFactorySimpleVersioned;


/**
 * A repository test set using file based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class FileBasedNonTransactionalRepositoryTest extends NonTransactionalRepositoryTestBase {

    private static final String nameSpace = "target/UnitTests/Repository";
    
    @Parameters
    public static Collection<Object[]> data() {
    	FileBasedRepositoryFactory factory = new FileBasedRepositoryFactory();
        Repository noStorageRepository = factory.buildNoStorageRepository(nameSpace + "/NoStorage");
        Repository blockRepository = factory.buildRepository(nameSpace + "/Block");

        return Arrays.asList(new Object[][] {
                {noStorageRepository, new PersistentFactorySimpleSerializable()},
                {noStorageRepository, new PersistentFactoryCloneable()},
                {noStorageRepository, new PersistentFactorySimpleVersioned()},
                {blockRepository, new PersistentFactorySimpleSerializable()},
                {blockRepository, new PersistentFactoryCloneable()},
                {blockRepository, new PersistentFactorySimpleVersioned()},
         });
    }
    
    public FileBasedNonTransactionalRepositoryTest(Repository repository, PersistentFactory factory) {
        super(repository, factory);
    }
    
}
