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
package gloodb.impl;

import gloodb.RepositoryStrategy;
import gloodb.associations.AssociationFactory;
import gloodb.associations.objectdb.POJOAssociationFactory;

public class ObjectDBRepositoryStrategy implements RepositoryStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RepositoryStrategy.Instance> T getStrategyInstance(Class<T> clazz) {
        if(AssociationFactory.class.equals(clazz)) return (T) new POJOAssociationFactory();
        return null;
    }
}
