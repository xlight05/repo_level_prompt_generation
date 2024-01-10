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

import java.io.Serializable;

/**
 * Tagging interface for repository strategies. Repository strategies abstract
 * the algorithms required to implement persistence operations.
 *
 */
public interface RepositoryStrategy {
    
    /**
     * Tagging interface for repository strategies.
     */
    public interface Instance extends Serializable {}

    /**
     * Returns a strategy instance (e.g. gloodb.associations.AssociationFactory).
     * @param <T> The object type
     * @param clazz The strategy class
     * @return The strategy instance.
     */
    <T extends Instance> T getStrategyInstance(Class<T> clazz);

}
