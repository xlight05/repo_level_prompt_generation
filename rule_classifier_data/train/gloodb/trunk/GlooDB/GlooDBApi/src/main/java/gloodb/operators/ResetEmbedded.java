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
package gloodb.operators;

import gloodb.Embedded;

import java.io.Serializable;

/**
 * Implementation of the Reset expression. It reset the embedded references.
 * @param <T> the embedded type.
 */
public class ResetEmbedded<T extends Serializable> extends IterativeExpression<Embedded<T>> {
	private static final long serialVersionUID = 1L;

	public void evaluateIteratively(Embedded<T> reference) {
        reference.reset();
    }
}