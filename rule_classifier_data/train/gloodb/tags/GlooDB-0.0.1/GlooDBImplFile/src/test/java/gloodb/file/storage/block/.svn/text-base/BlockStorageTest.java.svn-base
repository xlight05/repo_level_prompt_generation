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

import gloodb.file.storage.block.BlockAddress;
import gloodb.file.storage.block.BlockStorageConfiguration;
import gloodb.file.storage.block.BlockStorageFactory;
import gloodb.storage.*;
import java.util.LinkedList;
import javax.crypto.NullCipher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A storage test set, which uses the file / block storage strategy.
 */
public class BlockStorageTest extends StorageTestBase {

    /**
     * Injects the block storage strategy into the storage test.
     */
    @Before
    public void setUp() {
        BlockStorageFactory storageFactory = new BlockStorageFactory();
        this.storage = storageFactory.buildStorage(new BlockStorageConfiguration(nameSpace, new NullCipher(), new NullCipher()), null);
    }

    /**
     * Block addresses are lists of integers points to blocks in the storage
     * file.
     */
    @Test
    public void addressTest() {
        LinkedList<Integer> initialList = new LinkedList<Integer>();
        initialList.add(1);
        initialList.add(2);
        initialList.add(3);
        BlockAddress address = new BlockAddress(initialList);
        assertEquals(3, address.size());
        address.clear(1);
        assertEquals(1, address.size());
    }}
