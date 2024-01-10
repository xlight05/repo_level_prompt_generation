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

import gloodb.InterceptorAndCallbacksTestBase;
import gloodb.Repository;
import gloodb.SimpleDerivedIntercepted;
import gloodb.SimpleIntercepted;

/**
 * An interceptor test set using memory based transaction and storage strategies.
 */
@RunWith(Parameterized.class)
public class MemoryBasedInterceptorAndCallbacksTest extends InterceptorAndCallbacksTestBase {

    private static final String nameSpace = "target/UnitTests/Observer";
    
    @Parameters
    public static Collection<Object[]> data() {
        Repository repository = new MemoryBasedRepositoryFactory().buildRepository(nameSpace);
        
        return Arrays.asList(new Object[][] {
                {repository, new SimpleIntercepted("testId")},
                {repository, new SimpleDerivedIntercepted("testDerivedId")}
         });
    }
 
    
    /**
     * Creates an interceptor test set using memory based transaction and storage strategies.
     */
    public MemoryBasedInterceptorAndCallbacksTest(Repository repository, SimpleIntercepted object) {
        super(repository, object);
    }
}
