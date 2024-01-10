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
 * Represents a read only transaction. Read transactions are allowed to run
 * concurrently with any other read transactions, but not concurrently with
 * write transactions. Write transactions must wait until all read transactions
 * conclude. While write transactions execute, no read transaction can run.
 * 
 */
public interface TxLogReadTransaction {

	/**
	 * Runs the transaction on the managed object and returns a result.
	 * 
	 * @param ctx
	 *            The transaction context.
	 * @param managedObject
	 *            The managed object to run the transaction on.
	 * @return The transaction result.
	 */
	Serializable execute(TxContext ctx, Serializable managedObject);
}
