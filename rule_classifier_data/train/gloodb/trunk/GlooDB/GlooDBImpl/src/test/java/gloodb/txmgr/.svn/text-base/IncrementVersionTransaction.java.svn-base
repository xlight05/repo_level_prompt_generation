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
 * A transaction which increments the version of the managed object.
 */
public class IncrementVersionTransaction implements TxLogWriteTransaction {

	private static final long serialVersionUID = 1L;

        /**
         * Increments the version of the managed object.
         * @param ctx The transaction context.
         * @param target The managed object.
         * @param timestamp The timestamp
         * @return Always null.
         */
	public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        ((MockManagedObject)target).version += 1;
        return null;
    }
}
