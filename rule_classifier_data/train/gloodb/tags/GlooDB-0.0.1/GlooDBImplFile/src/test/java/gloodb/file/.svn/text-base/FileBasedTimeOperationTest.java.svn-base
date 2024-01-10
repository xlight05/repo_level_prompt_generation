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
import gloodb.PersistentFactorySimpleValuedWithCallbacks;
import gloodb.Repository;
import gloodb.PersistentFactorySimpleSerializable;
import gloodb.PersistentFactorySimpleVersioned;
import gloodb.TimeOperationTestBase;

/**
 * A repository test set using file based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class FileBasedTimeOperationTest extends TimeOperationTestBase {

    private static final String nameSpace = "target/UnitTests/Repository";
    
    @Parameters
    public static Collection<Object[]> data() {
        Repository noStorageRepository = FileBasedRepositoryFactory.buildNoStorageRepository(nameSpace + "/NoStorage", null);
        Repository blockRepository = FileBasedRepositoryFactory.buildRepository(nameSpace + "/Block", null);

        return Arrays.asList(new Object[][] {
                {noStorageRepository, new PersistentFactorySimpleSerializable()},
                {noStorageRepository, new PersistentFactoryCloneable()},
                {noStorageRepository, new PersistentFactorySimpleVersioned()},
                {noStorageRepository, new PersistentFactorySimpleValuedWithCallbacks()},
                {blockRepository, new PersistentFactorySimpleSerializable()},
                {blockRepository, new PersistentFactoryCloneable()},
                {blockRepository, new PersistentFactorySimpleVersioned()},
                {blockRepository, new PersistentFactorySimpleValuedWithCallbacks()}
         });
    }
    
    public FileBasedTimeOperationTest(Repository repository, PersistentFactory factory) {
        super(repository, factory);
        System.out.println(String.format("\nOperation benchmark using file based repository / %s", factory.getClass().getName()));
    }
    
}
