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

import gloodb.GlooException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tuple implementation class.
 */
@SuppressWarnings("serial")
public class Tuple implements Serializable, Cloneable {
	private ArrayList<Serializable> tuple;
	
	public Tuple() {
		this.tuple = new ArrayList<Serializable>();
	}
	
	public Tuple(Serializable s) {
		this();
		if(s != null && (s instanceof Tuple)) {
			this.tuple.addAll(((Tuple)s).tuple);
		} else if(s != null) {
			this.tuple.add(s);
		}
	}
	
	public int size() {
		return tuple.size();
	}
	
	public Tuple add(int index, Serializable value) {
		if(value instanceof Tuple) {
			Tuple t = (Tuple)value;
			this.tuple.addAll(index, t.tuple);
		} else {
			this.tuple.add(index, value);
		}
		return this;
	}
	
	public Tuple add(Serializable value) {
		if(value instanceof Tuple) {
			Tuple t = (Tuple)value;
			this.tuple.addAll(t.tuple);
		} else {
			this.tuple.add(value);
		}
		return this;
		
	}
	
	public Serializable [] toArray() {
		return this.tuple.toArray(new Serializable []{});
	}
	
	@Override
	public String toString() {
		return tuple != null? tuple.toString(): "null";
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			Tuple copy = (Tuple) super.clone();
			copy.tuple = (ArrayList<Serializable>) copy.clone();
			return copy;
		} catch(CloneNotSupportedException e) {
			throw new GlooException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tuple == null) ? 0 : tuple.hashCode());
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
		Tuple other = (Tuple) obj;
		if (tuple == null) {
			if (other.tuple != null)
				return false;
		} else if (!tuple.equals(other.tuple))
			return false;
		return true;
	}

	public Serializable get(int i) {
		return this.tuple.get(i);
	}
}
