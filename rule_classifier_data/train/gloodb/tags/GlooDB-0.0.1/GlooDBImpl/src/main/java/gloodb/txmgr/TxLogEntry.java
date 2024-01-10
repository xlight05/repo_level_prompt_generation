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
 * Transaction log entry. A transaction log entry contains a timestamp and a
 * serializable transaction. The first transaction in a transaction log is the
 * managed object itself. When loading a transaction log, this first transaction
 * is used to initialize the managed object.
 */
public class TxLogEntry implements Serializable {
	private static final long serialVersionUID = -8234912494749413010L;

	private long timestamp;
	private Serializable transaction;

	/**
	 * Creates a log entry from the transaction provided.
	 * 
	 * @param transaction
	 *            The transaction to log.
	 */
	public TxLogEntry(Serializable transaction) {
		this.timestamp = System.currentTimeMillis();
		this.transaction = transaction;
	}

	/**
	 * The transaction timestamp.
	 * 
	 * @return The transaction timestamp.
	 */
	public Date getTimeStamp() {
		return new Date(timestamp);
	}

	/**
	 * Returns the transaction object. When this is the first entry in a
	 * transaction log, the inital state of the managed object is returned
	 * instead.
	 * 
	 * @return The transaction object.
	 */
	public Serializable getTransaction() {
		return transaction;
	}
}
