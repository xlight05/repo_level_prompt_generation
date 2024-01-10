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
package gloodb.associations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class which simplifies creating and managing compounded index keys.
 *
 */
public final class CompoundedKeysUtility {
	private CompoundedKeysUtility() {}
	
	/**
	 * Builds a compounded key.
	 * @param subKeys The subkeys.
	 * @return Returns a compounded key.
	 */
	public static Serializable buildCompoundedKey(Serializable... subKeys) {
		return (Serializable) Arrays.asList(subKeys);
	}
	
	
	/**
	 * Breaks down a compounded key into the subkeys.
	 * @param compoundedKey The compounded key.
	 * @return Returns an array of subkeys.
	 */
	@SuppressWarnings("unchecked")
	public static Serializable[] getSubKeys(Serializable compoundedKey) {
		return ((List<Serializable>)compoundedKey).toArray(new Serializable[0]);
	}
}
