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

import gloodb.InterceptorAndCallbacksTestBase;
import gloodb.Repository;
import gloodb.SimpleDerivedIntercepted;
import gloodb.SimpleIntercepted;


/**
 * An interceptor test set using file based transaction and storage strategies,
 * unencrypted file writing. The repository is embedded in the test.
 */
@RunWith(Parameterized.class)
public class FileBasedInterceptorAndCallbacksTest extends InterceptorAndCallbacksTestBase {

    private static final String nameSpace = "target/UnitTests/Observer";
    
    @Parameters
    public static Collection<Object[]> data() {
    	FileBasedRepositoryFactory factory = new FileBasedRepositoryFactory();
        Repository noStorageRepository = factory.buildNoStorageRepository(nameSpace + "/NoStorage");
        Repository blockRepository = factory.buildRepository(nameSpace + "/Block");

        return Arrays.asList(new Object[][] {
                {noStorageRepository, new SimpleIntercepted("testId")},
                {noStorageRepository, new SimpleDerivedIntercepted("testDerivedId")},
                {blockRepository, new SimpleIntercepted("testId")},
                {blockRepository, new SimpleDerivedIntercepted("testDerivedId")}
         });
    }
 
    


    /**
     * Creates an interceptor test set using file based transaction and storage strategies,
     * unencrypted file writing. The repository is embedded in the test.
     */
    public FileBasedInterceptorAndCallbacksTest(Repository repository, SimpleIntercepted object) {
        super(repository, object);
    }
}
