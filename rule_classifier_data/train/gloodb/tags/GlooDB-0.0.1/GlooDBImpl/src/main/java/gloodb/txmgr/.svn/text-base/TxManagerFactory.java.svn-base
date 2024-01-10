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
package gloodb.txmgr;

/**
 * Transaction manager factory. It requires a transaction log factory in order
 * to create the manager
 */
public class TxManagerFactory {
	/**
	 * Transaction manager factory method. Specific TxManagerFactory instances
	 * must implement this method.
	 * 
	 * @param txLogFactory
	 *            The transaction log factory which creates the managed tx log.
	 * @param logConfiguration
	 *            The transaction log configuration.
	 * @param interceptor 
	 *            The transaction manager interceptor if any.           
	 * @return The new transaction manager instance.
	 */
	public TxManager buildTxManager(TxLogFactory txLogFactory, TxLogConfiguration logConfiguration, TxManagerInterceptor interceptor) {
		TxLog txLog = txLogFactory.buildLog(logConfiguration);
		return interceptor != null? interceptor.setTxManager(new TxManagerImplementation(txLog)): new TxManagerImplementation(txLog);
	}
}
