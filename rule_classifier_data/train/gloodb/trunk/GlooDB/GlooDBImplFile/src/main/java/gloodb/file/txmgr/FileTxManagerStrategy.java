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
package gloodb.file.txmgr;

import gloodb.txmgr.TxLogConfiguration;
import gloodb.txmgr.TxLogFactory;
import gloodb.txmgr.TxManagerInterceptor;
import gloodb.txmgr.TxManagerStrategy;

/**
 * Based on this tx management strategy, transactions are serialized into a file.
 *
 */
public class FileTxManagerStrategy implements TxManagerStrategy {
	private final TxLogConfiguration configuration;
	private final TxLogFactory factory;
	private final TxManagerInterceptor interceptor;

	/**
	 * Creates a new strategy object, with the given configuration.
	 * @param configuration The tx logging configuration
	 * @param interceptor The tx manager interceptor
	 */
	public FileTxManagerStrategy(TxLogConfiguration configuration, TxManagerInterceptor interceptor) {
		super();
		this.configuration = configuration;
		this.factory = new FileTxLogFactory();
		this.interceptor = interceptor;
	}

	/**
	 * Configuration getter.
	 * @return The tx log configuration.
	 */
	@Override
	public TxLogConfiguration getConfiguration() {
		return this.configuration;
	}

	/**
	 * TxLog factory getter.
	 * @return The tx log factory instance.
	 */
	@Override
	public TxLogFactory getFactory() {
		return this.factory;
	}

    @Override
    public TxManagerInterceptor getInterceptor() {
        return interceptor;
    }

}
