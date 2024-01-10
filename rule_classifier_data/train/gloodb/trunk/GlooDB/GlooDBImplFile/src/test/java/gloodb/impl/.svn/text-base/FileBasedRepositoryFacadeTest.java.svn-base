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

import gloodb.file.FileBasedRepositoryFactory;

/**
 * A generic test set for top level transactions, using file based storage and
 * transaction management.
 */
public class FileBasedRepositoryFacadeTest extends RepositoryFacadeTestBase {

	static final String nameSpace = "target/UnitTests/Logical";
	
	/**
	 * Creates a generic test set for repository facade, using file based
	 * storage and transaction management.
	 */
	public FileBasedRepositoryFacadeTest() {
		super(new FileBasedRepositoryFactory().buildRepository(nameSpace));
	}
}
