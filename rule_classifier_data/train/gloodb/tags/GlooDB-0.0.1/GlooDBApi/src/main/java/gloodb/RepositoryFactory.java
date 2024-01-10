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
package gloodb;

/**
 * Creates a repository for the given name space.
 */
public interface RepositoryFactory {
    /**
     * Factory method which returns an interface to the repository as specified
     * in the configuration.
     * 
     * @param configuration
     *            The configuration.
     * @param interceptor
     *            The repository interceptor (null if not intercepted).
     * @return The repository interface.
     */
    public Repository buildRepository(RepositoryConfiguration configuration, RepositoryInterceptor interceptor);
}
