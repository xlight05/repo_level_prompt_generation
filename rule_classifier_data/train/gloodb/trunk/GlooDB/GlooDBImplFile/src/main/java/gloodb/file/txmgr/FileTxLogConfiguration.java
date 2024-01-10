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

import gloodb.txmgr.TxLogConfiguration;
import javax.crypto.Cipher;

/**
 * Specifies the required configuration for a file based txmgr implementation.
 */
public interface FileTxLogConfiguration extends TxLogConfiguration {

    /**
     * Input cipher getter. The input cipher is injected.
     * @return The input cipher.
     */
    Cipher getInputCipher();

    /**
     * Output cipher getter. The output cipher is injected.
     * @return The injected output cipher.
     */
    Cipher getOutputCipher();

    /**
     * Returns the extension of transaction log files. Default is ".tlog"
     * @return The transaction log file extension.
     */
    String getTlogExtension();

    /**
     * Returns a string format which will be used to generate transaction log
     * names. This will format the BigInteger version of the transaction log (i.e.
     * must be something like %015d" ). Default value is "%028d"
     * @return The format of the transaction log name.
     */
    String getTlogName();

}
