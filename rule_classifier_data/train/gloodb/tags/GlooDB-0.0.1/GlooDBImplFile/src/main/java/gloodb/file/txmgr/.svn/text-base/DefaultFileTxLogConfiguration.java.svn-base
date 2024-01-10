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

import javax.crypto.Cipher;

/**
 * Default file transaction log configuration.
 */
public class DefaultFileTxLogConfiguration implements FileTxLogConfiguration {

    private final String tlogName;
    private final String tlogExtension;
    private Cipher inputCipher;
    private Cipher outputCipher;
    private String nameSpace;

    /**
     * Creates a default file transaction configuration.
     * @param nameSpace The namespace associated with this configuration.
     * @param inputCipher The input cipher used to encrypt this log.
     * @param outputCipher The output cipher used to decrypt this log.
     */
    public DefaultFileTxLogConfiguration(String nameSpace, Cipher inputCipher, Cipher outputCipher) {
        this.tlogName = "%028d";
        this.tlogExtension = ".tlog";
        this.nameSpace = nameSpace;
        this.inputCipher = inputCipher;
        this.outputCipher = outputCipher;
    }

    /**
     * Name space getter. This value is injected.
     * @return The name space value.
     */
    public String getNameSpace() {
        return this.nameSpace;
    }

    /**
     * Returns a string format which will be used to generate transaction log
     * names. This will format the BigInteger version of the transaction log (i.e.
     * must be something like %015d" ). Default value is "%028d"
     * @return The format of the transaction log name.
     */
    public String getTlogName() {
        return tlogName;
    }

    /**
     * Returns the extension of transaction log files. Default is ".tlog"
     * @return The transaction log file extension.
     */
    public String getTlogExtension() {
        return tlogExtension;
    }

    /**
     * Input cipher getter. The input cipher is injected.
     * @return The input cipher.
     */
    public Cipher getInputCipher() {
        return inputCipher;
    }

    /**
     * Output cipher getter. The output cipher is injected.
     * @return The injected output cipher.
     */
    public Cipher getOutputCipher() {
        return outputCipher;
    }
}
