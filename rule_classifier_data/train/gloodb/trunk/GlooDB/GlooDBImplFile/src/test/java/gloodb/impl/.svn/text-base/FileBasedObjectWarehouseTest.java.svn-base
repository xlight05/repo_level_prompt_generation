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
package gloodb.impl;


import gloodb.file.storage.block.BlockStorageConfiguration;
import gloodb.file.storage.block.BlockStorageStrategy;
import gloodb.file.txmgr.DefaultFileTxLogConfiguration;
import gloodb.file.txmgr.FileTxManagerStrategy;
import gloodb.impl.ObjectWarehouseTestBase;
import javax.crypto.NullCipher;

/**
 * A object warehouse test set for file based storage
 * and transaction management strategies.
 */
public class FileBasedObjectWarehouseTest extends ObjectWarehouseTestBase {

    /**
     * Creates a object warehouse test set for file based storage
     * and transaction management strategies.
     */
    public FileBasedObjectWarehouseTest() {
		super(new BlockStorageStrategy(
				new BlockStorageConfiguration(
						"target/UnitTests/Logical", 
						new NullCipher(), 
						new NullCipher()), null),
				new FileTxManagerStrategy(
						new DefaultFileTxLogConfiguration("target/UnitTests/Logical", 
								new NullCipher(), 
								new NullCipher()), null));

    }
}
