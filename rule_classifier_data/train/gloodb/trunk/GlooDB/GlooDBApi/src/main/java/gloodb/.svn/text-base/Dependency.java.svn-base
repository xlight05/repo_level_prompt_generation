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

/**
 * Dependency data structure. Pre* callbacks may return a list of
 * dependency instances in order to control synchronization around updates of
 * dependent objects.
 *
 */
public class Dependency {
	public enum Type {
		/**
		 * Read only dependency. During the update, the dependent object is read only locked.
		 */
		Read,
		/**
		 * Write dependency. During the update, the dependent object is write locked. 
		 */
		Write
	}
	
	private final Serializable id;
	private final Type type;
	
	/**
	 * Creates a dependency object.
	 * @param id The id of the dependent object.
	 * @param type The dependency type.
	 */
	public Dependency(Serializable id, Type type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	/**
	 * Creates a dependency object from a variant.
	 * @param <T> T The dependent object type.
	 * @param clazz The dependent object class.
	 * @param variant The variant wrapping the depedent object.
	 * @param type The dependency type.
	 */
	public <T extends Serializable> Dependency(Class<T> clazz, Serializable variant, Type type) {
		this(PersistencyAttributes.getIdFromVariant(clazz, variant), type);
	}

	/**
	 * Returns the dependent object id.
	 * @return The id.
	 */
	public Serializable getId() {
		return id;
	}

	/**
	 * Returns the dependency type.
	 * @return The dependency type.
	 */
	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dependency other = (Dependency) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
