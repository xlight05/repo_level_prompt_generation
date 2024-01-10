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
package gloodb.file.storage.block;

import gloodb.storage.StorageConfiguration;
import javax.crypto.Cipher;

/**
 * Block storage configuration bean. The configuration specified the following:
 * <ul>
 * <li>block size: the block size used to store objects in the block storage. Default
 * is 256 bytes.
 * <li>repository name: the name for the repository file. Default value is "object".
 * <li>repository extension: the file extension of the repository file. Default value
 * is ".repository".
 * <li>snapshot name format: the string format mask for the snapshot file name. When taking
 * a snapshot the file name used is calculated as String.format(snapshortNameFormat, repositoryVersion).
 * Default format is "%028d".
 * <li>snapshot space: Snapshot namespace. This is the directory where the snaphots are
 * stored. Default value is "Snapshots";
 * <li>input cipher: input cipher used to decode information read from the storage.
 * <li>output cipher: output cipher used to encode information written into the storage.
 * <li>name space: the namespace for the storage. This is the directory where the storage is
 * stored.</ul>
 * 
 */
public class BlockStorageConfiguration implements StorageConfiguration {
    private static final long MAX_BLOCKS = (long)Integer.MAX_VALUE * 8l;
    private final Cipher inputCipher;
    private final Cipher outputCipher;
    private final String nameSpace;

    private int blockSize;
    private String repositoryName;
    private String repositoryExtension;
    private String snapshotName;
    private boolean verificationOn;

    /**
     * Constructs a block storage configuration with default values.
     * @param nameSpace The namespace.
     * @param inputCipher The input chiper.
     * @param outputCipher The output cipher.
     */
    public BlockStorageConfiguration(String nameSpace, Cipher inputCipher, Cipher outputCipher) {
        this.blockSize = 256;
        this.repositoryName = "object";
        this.repositoryExtension = ".repository";
        this.snapshotName = "%028d";
        this.inputCipher = inputCipher;
        this.outputCipher = outputCipher;
        this.nameSpace = nameSpace;
        this.verificationOn = true;
    }

    /**
     * Block size getter. Default value is 256 bytes.
     * @return Returns the block size in bytes.
     */
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Sets the block size in bytes.
     * @param blockSize The block size.
     * @return This configuration object.
     */
    public BlockStorageConfiguration setBlockSize(int blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    /**
     * Input cipher getter. The input cipher is construction time injected.
     * @return The input cipher.
     */
    public Cipher getInputCipher() {
        return inputCipher;
    }

    /**
     * Output cipher getter. The output cipher is construction time injected.
     * @return The injected output cipher.
     */
    public Cipher getOutputCipher() {
        return outputCipher;
    }
    
    /**
     * Returns the repository name. Defaults to "object"
     * @return The respository name.
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Sets the repository name.
     * @param repositoryName The new repository name.
     * @return This configuration object.
     */
    public BlockStorageConfiguration setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        return this;
    }

    /** 
     * Returns the repository file extension. Defaults to ".repository"
     * @return The repository file extension.
     */
    public String getRepositoryExtension() {
        return repositoryExtension;
    }

    /**
     * Sets the repository file extension.
     * @param repositoryExtension
     * @return This configuration object.
     */
    public BlockStorageConfiguration setRepositoryExtension(String repositoryExtension) {
        this.repositoryExtension = repositoryExtension;
        return this;
    }

    /**
     * Snapshot name getter. Defaults to "%028d". The name is generated from a 
     * specified version (i.e. String.format("%028d", repositoryVersion);
     * @return The name format.
     */
    public String getSnapshotName() {
        return snapshotName;
    }

    /**
     * Sets the name format.
     * @param snapshotName The new name format.
     * @return This configuration object.
     */
    public BlockStorageConfiguration setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
        return this;
    }

    /**
     * Name space getter. The name space is construction time injected.
     * @return The name space.
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Returns the maximum number of blocks. This is calculated (Integer.MAX_VALUE * 8)
     * @return The maximum number of blocks.
     */
    public long getMaxBlocks() {
        return MAX_BLOCKS;
    }

    /**
     * Returns the writable block size. This is calculated (blocksize - 1);
     * @return The writable block size.
     */
    public int getWritableBlockSize() {
        return blockSize - 1;
    }

    /**
     * Turns object on verification on / off
     * @param verificationOn The verification switch value.
     */
	public void setVerificationOn(boolean verificationOn) {
		this.verificationOn = verificationOn;
	}

	/**
	 * Gets the verification on/off value.
	 * @return True if verification is on.
	 */
	public boolean isVerificationOn() {
		return verificationOn;
	}

}
