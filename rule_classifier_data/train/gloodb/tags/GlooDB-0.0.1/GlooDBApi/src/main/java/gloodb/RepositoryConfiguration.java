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
package gloodb;

/**
 * Tagging interface for repository configurations.
 */
public interface RepositoryConfiguration {
    /**
     * Repository strategy getter.
     * @return The repository strategy.
     */
	RepositoryStrategy getRepositoryStrategy();
}
