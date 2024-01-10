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
 * Thrown when trying to commit a rollback only transaction or when trying to
 * complete (commit or rollback) an already completed transaction.
 */
public class CannotCompleteTxException extends GlooException {
	private static final long serialVersionUID = 2510961411928306713L;

	/**
	 * Creates an exception object with a detailed message.
	 * 
	 * @param message
	 *            The detail message.
	 */
	public CannotCompleteTxException(String message) {
		super(message);
	}
}
