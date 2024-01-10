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
package gloodb.memory.storage;

import gloodb.storage.StorageConfiguration;

/**
 * Configuration for an in memory storage.
 */
public class MemoryStorageConfiguration implements StorageConfiguration {

	private final String nameSpace;
	

	/**
	 * Creates a new in memory storage configuration.
	 * @param nameSpace The namespace.
	 */
	public MemoryStorageConfiguration(String nameSpace) {
		super();
		this.nameSpace = nameSpace;
	}

	/**
	 * Returns the namespace.
	 * @return The storage namespace.
	 */
	@Override
	public String getNameSpace() {
		return nameSpace;
	}
}
