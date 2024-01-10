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

import java.io.Serializable;

/**
 * Tagging interface for queries. The repository uses this interface for
 * implementing its querying algorithm:
 * <ul>
 * <li>invoke the run method with the repository as parameter
 * <li>The run method returns the query result.
 * </ul>
 * @param T The result type.
 */
public interface Query<T extends Serializable> extends Serializable {
    /**
     * The repository invokes this method providing a reference to itself.
     * @param repository The repository.
     * @param parameters The query parameters.
     * @param T The result type.
     * @return The query result.
     * @throws The query may throw exceptions during its run.
     */
    public T run(Repository repository, Serializable... parameters) throws Exception;
}
