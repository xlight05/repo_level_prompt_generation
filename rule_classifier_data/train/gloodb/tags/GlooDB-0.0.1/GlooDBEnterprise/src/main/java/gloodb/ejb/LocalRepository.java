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
package gloodb.ejb;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;

import javax.ejb.Local;

/**
 * Tagging interface for LocalRepository. 
 */
@Local
public interface LocalRepository {
    /**
     * Returns the repository for the given name space.
     * @param nameSpace The namespace.
     * @return The repository associated with the namespace.
     */
    Repository getRepository(String nameSpace);
    
    /**
     * Intercepts a repository
     * @param nameSpace The namespace
     * @param interceptor The interceptor
     * @return The intercepted repository.
     */
    Repository interceptRepository(String nameSpace, RepositoryInterceptor interceptor);

    /**
     * Sets the cache purging values for the given namespace.
     * @param nameSpace The namespace.
     * @param cachePurgeInterval The cache purge interval (in milliseconds).
     * @param cachePurgePercentage The cache purge percentage (0 to 100).
     * @return True if the cache is purged.
     */
    boolean setCachePurge(String nameSpace, long cachePurgeInterval, int cachePurgePercentage);
}
