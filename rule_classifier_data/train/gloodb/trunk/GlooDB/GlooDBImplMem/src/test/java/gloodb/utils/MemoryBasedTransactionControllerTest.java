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
package gloodb.utils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gloodb.PersistentFactorySimpleValued;
import gloodb.Repository;
import gloodb.memory.MemoryBasedRepositoryFactory;

/**
 *
 * @author doc
 */
@RunWith(Parameterized.class)
public class MemoryBasedTransactionControllerTest extends TransactionControllerTestBase {
    private static final String nameSpace = "target/UnitTests/Utils";

    @Parameters
    public static Collection<Object[]> data() {
        Repository repository = new MemoryBasedRepositoryFactory().buildRepository(nameSpace);
        
        return Arrays.asList(new Object[][] {
                {repository, new PersistentFactorySimpleValued()}
         });
    }

    public MemoryBasedTransactionControllerTest(Repository repository, PersistentFactorySimpleValued factory) {
        super(repository, factory);
    }
}
