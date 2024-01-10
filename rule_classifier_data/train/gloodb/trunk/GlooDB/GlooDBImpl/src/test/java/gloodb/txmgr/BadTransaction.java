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
 * Implementation of a bad transaction (will throw a runtime exception).
 */
public class BadTransaction implements TxLogWriteTransaction {

	private static final long serialVersionUID = 1L;

        /**
         * Throws a runtime exception.
         * @param ctx Context
         * @param target Target managed object.
         * @param timestamp Timestamp.
         * @return The transaction result.
         */
	public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        throw new RuntimeException("Shit happens.");
    }
}
