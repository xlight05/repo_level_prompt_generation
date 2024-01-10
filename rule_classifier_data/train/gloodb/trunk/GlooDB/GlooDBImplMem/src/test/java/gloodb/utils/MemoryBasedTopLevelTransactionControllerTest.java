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

import gloodb.memory.MemoryBasedRepositoryFactory;

public class MemoryBasedTopLevelTransactionControllerTest extends TopLevelTransactionControllerTestBase {
    private static final String nameSpace = "target/UnitTests/Utils";

    public MemoryBasedTopLevelTransactionControllerTest() {
        super(new MemoryBasedRepositoryFactory().buildRepository(nameSpace));
    }
}