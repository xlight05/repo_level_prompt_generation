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

import gloodb.PersistentFactoryCloneable;
import gloodb.PersistentFactory;
import gloodb.PersistentFactorySimpleIntercepted;
import gloodb.PersistentFactorySimpleValuedWithCallbacks;
import gloodb.Repository;
import gloodb.PersistentFactorySimpleSerializable;
import gloodb.PersistentFactorySimpleVersioned;
import gloodb.TimeOperationTestBase;

/**
 * A repository test set using memory based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class MemoryBasedTimeOperationTest extends TimeOperationTestBase {

    private static final String nameSpace = "target/UnitTests/Repository";

    @Parameters
    public static Collection<Object[]> data() {
        Repository repository = new MemoryBasedRepositoryFactory().buildRepository(nameSpace);
        
        return Arrays.asList(new Object[][] {
                {"Memory implementation", repository, new PersistentFactorySimpleSerializable()},
                {"Memory implementation", repository, new PersistentFactoryCloneable()},
                {"Memory implementation", repository, new PersistentFactorySimpleVersioned()},
                {"Memory implementation", repository, new PersistentFactorySimpleIntercepted()},
                {"Memory implementation", repository, new PersistentFactorySimpleValuedWithCallbacks()}
         });
    }
   
    /**
     * Creates a repository test set using memory based transaction and storage strategies.
     */
    public MemoryBasedTimeOperationTest(String implementation, Repository repository, PersistentFactory factory ) {
        super(String.format("%s: %s", implementation, factory.getClass().getName()), repository, factory);
        System.out.println(String.format("\n%s benchmark: %s", implementation, factory.getClass().getName()));
    }
}
