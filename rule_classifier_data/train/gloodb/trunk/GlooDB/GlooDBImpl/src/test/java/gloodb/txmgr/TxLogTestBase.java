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

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A transaction log test set, agnostic of actual logging strategy.
 * 
 */
public abstract class TxLogTestBase {

	/**
	 * The transaction log instance.
	 */
	protected TxLog tLog;
	/**
	 * The mock managed object.
	 */
	protected MockManagedObject managedObject;
	/**
	 * Log files are in target/UnitTests/TxLog
	 */
	protected final static String nameSpace = "target/UnitTests/TxLog";

	/**
	 * Initializes the mock managed object.
	 */
	@Before
	public void setUp() {
		this.managedObject = new MockManagedObject();
	}

	
	@Test
	public void runTests() {
		this.startTransactionLog();
		this.restartTransactionLog();
		this.logTransactions();
		this.loadTransactions();
		this.logAndExecuteTransactions();
		this.takeSnapshot();
	}
	
	/**
	 * Start a new transaction log.
	 */
	public void startTransactionLog() {
		managedObject = tLog.start(null, managedObject);
		assertEquals(0, tLog.getVersion().intValue());
	}

	/**
	 * Start an existing transaction log. The managed object is set to its
	 * latest state.
	 */
	public void restartTransactionLog() {
		tLog.start(null, managedObject);
		closeLog();
		managedObject = tLog.start(null, managedObject);
		assertEquals(0, tLog.getVersion().intValue());
	}

	/**
	 * Changes to the managed object are logged as transactions.
	 */
	public void logTransactions() {
		MockTransaction t = new MockTransaction();
		managedObject = tLog.start(null, managedObject);
		int startVersion = tLog.getVersion().intValue();

		// Log some transactions.
		tLog.log(t);
		tLog.log(t);
		tLog.log(t);
		// The version number should have been increased.
		assertEquals(tLog.getVersion().intValue(), 3 + startVersion);
	}

	/**
	 * On start, all logged transactions are applied to the managed object.
	 */
	public void loadTransactions() {
		managedObject = tLog.start(null, managedObject);

		// Previously created transaction should've run on the managed object
		assertEquals(3, managedObject.version);
	}

	/**
	 * Changes are logged and then applied to the manage object.
	 */
	public void logAndExecuteTransactions() {
		MockTransaction t = new MockTransaction();
		managedObject = tLog.start(null, managedObject);
		int startLogVersion = tLog.getVersion().intValue();
		int startManagedObjectVersion = managedObject.version;

		logAndExecuteTransaction(t);
		logAndExecuteTransaction(t);
		logAndExecuteTransaction(t);
		assertEquals(tLog.getVersion().intValue(), 3 + startLogVersion);
		assertEquals(managedObject.version, 3 + startManagedObjectVersion);
	}

	/**
	 * When a transaction log is snapshot, the current log is close and a new
	 * log is created. The initial state of the new log is the latest state of
	 * the managed object.
	 */
	public void takeSnapshot() {
		MockTransaction t = new MockTransaction();
		managedObject = tLog.start(null, managedObject);
		int startManagedObjectVersion = managedObject.version;

		logAndExecuteTransaction(t);
		logAndExecuteTransaction(t);
		logAndExecuteTransaction(t);
		assertEquals(managedObject.version, 3 + startManagedObjectVersion);

		// Take a snapshot an restart the log. The version of the managed object
		// should be preserved.
		int startVersion = tLog.getVersion().intValue();
		tLog.takeSnapshot(managedObject);
		// Check the version has not been incremented.
		assertEquals(startVersion, tLog.getVersion().intValue());

		// Check the state of the managed object.
		managedObject = tLog.start(null, managedObject);
		assertEquals(managedObject.version, 3 + startManagedObjectVersion);
	}

	/**
	 * When taking a snapshot of a log that did not change, no new log files are
	 * created.
	 */
	@Test
	public void takeSnapshotWithNoTransactions() {
		managedObject = tLog.start(null, managedObject);
		int startVersion = tLog.getVersion().intValue();
		tLog.takeSnapshot(managedObject);
		// Check the version has been incremented.
		assertEquals(startVersion, tLog.getVersion().intValue());
	}

	/**
	 * If a log contains transactions that throw exceptions, the exceptions are
	 * ignored and all transactions are applied. The end state of the managed
	 * object is exactly as it was when the log was shutdown.
	 */
	@Test
	public void startLogWithBadTransactions() {
		MockTransaction t = new MockTransaction();
		BadTransaction bt = new BadTransaction();
		managedObject = tLog.start(null, managedObject);
		int startVersion = managedObject.version;
		logAndExecuteTransaction(bt);
		logAndExecuteTransaction(t);
		logAndExecuteTransaction(t);
		assertEquals(startVersion + 2, managedObject.version);
		closeLog();

		// restart the log
		managedObject = tLog.start(null, managedObject);
		assertEquals(startVersion + 2, managedObject.version);

	}

	private void logAndExecuteTransaction(TxLogWriteTransaction t) {
		tLog.log(t);
		try {
			t.execute(null, managedObject, new Date());
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * The semantics of "Closing a log" is dependent on actual implementation.
	 */
	protected abstract void closeLog();
}