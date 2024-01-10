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
package gloodb.utils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gloodb.PersistentFactorySimpleValued;
import gloodb.Repository;
import gloodb.file.FileBasedRepositoryFactory;

@RunWith(Parameterized.class)
public class FileBasedTransactionControllerTest extends TransactionControllerTestBase {
    private static final String nameSpace = "target/UnitTests/Utils";

    @Parameters
    public static Collection<Object[]> data() {
        Repository noStorageRepository = FileBasedRepositoryFactory.buildNoStorageRepository(nameSpace + "/NoStorage", null);
        Repository blockRepository = FileBasedRepositoryFactory.buildRepository(nameSpace + "/Block", null);

        return Arrays.asList(new Object[][] {
                {noStorageRepository, new PersistentFactorySimpleValued()}, 
                {blockRepository, new PersistentFactorySimpleValued()}
         });
    }

    public FileBasedTransactionControllerTest(Repository repository, PersistentFactorySimpleValued factory) {
        super(repository, factory);
    }
}