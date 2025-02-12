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

public class PersistentFactorySimpleValued implements PersistentFactory {

    @Override
    public Serializable newObject(Serializable... id) {
        return newSimpleValued(id);
    }
    
    public SimpleValued newSimpleValued(Serializable... id) {
        return new SimpleValued(id[0]);
    }

}
