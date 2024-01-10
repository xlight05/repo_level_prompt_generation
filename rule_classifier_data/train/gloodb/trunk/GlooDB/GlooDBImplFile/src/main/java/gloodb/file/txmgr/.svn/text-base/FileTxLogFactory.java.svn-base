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
package gloodb.file.txmgr;

import gloodb.txmgr.TxLog;
import gloodb.txmgr.TxLogConfiguration;
import gloodb.txmgr.TxLogFactory;

/**
 * TxLogFactory implementation, creating file based transaction logs.
 */
class FileTxLogFactory implements TxLogFactory {

	@Override
	public TxLog buildLog(TxLogConfiguration logConfiguration) {
		return new FileTxLog((FileTxLogConfiguration) logConfiguration);
	}

}
