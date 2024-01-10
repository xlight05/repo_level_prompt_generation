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

import org.junit.Test;

public class TimeOperationTestBase {
    private final Repository repository;
    private final PersistentFactory factory;
    
    public TimeOperationTestBase(Repository repository, PersistentFactory factory) {
        super();
        this.repository = repository;
        this.factory = factory;
    }

    @Test
    public void timeOperations() {
        Serializable id = "testId";
        Serializable object = factory.newObject(id);
        long startTime, endTime;
        try {
            startTime = System.nanoTime();
            object = this.repository.create(object);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Create object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));

            startTime = System.nanoTime();
            object = this.repository.update(object);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Update object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));

            startTime = System.nanoTime();
            object = this.repository.store(object);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Store object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));

            startTime = System.nanoTime();
            this.repository.contains(id);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Contains object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));

            startTime = System.nanoTime();
            object = this.repository.restore(id);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Restore cached object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));

            startTime = System.nanoTime();
            object = this.repository.update(object);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Update object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));
        } finally {
            startTime = System.nanoTime();
            object = this.repository.remove(id);
            endTime = System.nanoTime();
            System.out.println(String.format("[%s] Remove object: %d microseconds", getClass().getName(),
                    (endTime - startTime) / 1000));
        }
    }

}
