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
 * Tagging interface for tx management strategies.
 */
public interface TxManagerStrategy {

	/**
	 * Transaction log factory getter.
	 * @return The factory.
	 */
	TxLogFactory getFactory();

	/**
	 * Transaction log configuration getter.
	 * @return The configuration.
	 */
	TxLogConfiguration getConfiguration();

	/**
	 * Returns any tx manager interceptor associated with the tx manager.
	 * @return The tx manager interceptor.
	 */
    TxManagerInterceptor getInterceptor();

}
