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
import java.lang.reflect.Field;

/**
 * Version managers increment the version numbers in fields annotated with
 * Version annotation.
 * 
 */
interface VersionManager {
	/**
	 * Version manager for number types. Accepted number types are byte, short,
	 * int, long, float, double, Byte, Short, Integer, Long, Float, Double,
	 * BigInteger and BigDecimal. The NumberVersionManager will increment
	 * version by 1.
	 * 
	 * @param copy
	 *            The copy is the version of the object used in a transaction.
	 *            An optimistic locking failure is generated when the original
	 *            and copy do not match.
	 * @param field
	 *            The field to increment.
	 */
	void increment(Serializable original, Serializable copy, Field field);
}
