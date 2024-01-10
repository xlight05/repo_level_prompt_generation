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
package gloodb.txmgr;

import java.io.Serializable;

/**
 * Mock managed object implementation.
 */
public class MockManagedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Starts at version 0. Increment and get transaction will change this value.
     */
    public int version = 0;
}
