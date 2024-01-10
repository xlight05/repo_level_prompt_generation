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
import java.util.Date;

/**
 * Mock transaction implementation. Increments the version of the mock object.
 */
public class MockTransaction implements TxLogWriteTransaction {

	private static final long serialVersionUID = 1L;

	public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        MockManagedObject managedObject = (MockManagedObject)target;
        managedObject.version++;
        return null;
    }
}
