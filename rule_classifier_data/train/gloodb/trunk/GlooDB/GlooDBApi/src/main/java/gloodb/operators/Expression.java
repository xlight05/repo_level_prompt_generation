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

import java.io.Serializable;

/**
 * Abstract base class for persistent objects expressions.
 */
@SuppressWarnings("serial")
public abstract class Expression implements Serializable {
	
    /**
     * Evaluates the expression.
     * 
     * @param parameters The evaluation parameters. 
     */
    public abstract void evaluate(Object... parameters) throws Exception;
}