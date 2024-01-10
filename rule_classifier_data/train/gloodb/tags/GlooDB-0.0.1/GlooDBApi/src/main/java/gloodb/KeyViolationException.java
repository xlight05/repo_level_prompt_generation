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

import gloodb.GlooException;

/**
 * Thrown when trying to create a new persistent object, if the repository
 * already contains an object with the same identity as the new object.
 * 
 * For an object update, use the {@link Repository#store } or
 * {@link Repository#update } method calls.
 */
public class KeyViolationException extends GlooException {
    
    private static final long serialVersionUID = -8198241820354602691L;
    
    /**
     * Creates an exception object
     */
	public KeyViolationException() {
        super();
    }

	/**
	 * Creates an exception object with a detailed message
	 * @param message The detailed message
	 */
    public KeyViolationException(String message) {
        super(message);
    }
}
