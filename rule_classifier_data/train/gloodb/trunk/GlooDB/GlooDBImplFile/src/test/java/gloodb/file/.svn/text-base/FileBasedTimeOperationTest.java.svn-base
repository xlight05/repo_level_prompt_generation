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
    	FileBasedRepositoryFactory factory = new FileBasedRepositoryFactory();
        Repository noStorageRepository = factory.buildNoStorageRepository(nameSpace + "/NoStorage");
        Repository blockRepository = factory.buildRepository(nameSpace + "/Block");

        return Arrays.asList(new Object[][] {
                {"File based implementation with no storage", noStorageRepository, new PersistentFactorySimpleSerializable()},
                {"File based implementation with no storage", noStorageRepository, new PersistentFactoryCloneable()},
                {"File based implementation with no storage", noStorageRepository, new PersistentFactorySimpleVersioned()},
                {"File based implementation with no storage", noStorageRepository, new PersistentFactorySimpleValuedWithCallbacks()},
                {"File based implementation with block storage", blockRepository, new PersistentFactorySimpleSerializable()},
                {"File based implementation with block storage", blockRepository, new PersistentFactoryCloneable()},
                {"File based implementation with block storage", blockRepository, new PersistentFactorySimpleVersioned()},
                {"File based implementation with block storage", blockRepository, new PersistentFactorySimpleValuedWithCallbacks()}
         });
    }
    
    public FileBasedTimeOperationTest(String implementation, Repository repository, PersistentFactory factory) {
        super(String.format("%s: %s", implementation, factory.getClass().getName()), repository, factory);
        System.out.println(String.format("\n%s benchmark: %s", implementation, factory.getClass().getName()));
    }
    
}
