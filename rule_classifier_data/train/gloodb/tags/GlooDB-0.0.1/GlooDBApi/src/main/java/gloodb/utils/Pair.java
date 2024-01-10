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
package gloodb.utils;

import java.io.Serializable;

/**
 * Class implementing pairs of objects.
 */
public class Pair<L extends Serializable, R extends Serializable> implements Serializable {
	private static final long serialVersionUID = -2139716388253669188L;
	
	private final L left;
    private final R right;

    /**
     * Creates a pair.
     * @param left The left side of the pair.
     * @param right The right side of the pair.
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<L, R> other = (Pair<L, R>) obj;
        if (this.left != other.left && (this.left == null || !this.left.equals(other.left))) {
            return false;
        }
        if (this.right != other.right && (this.right == null || !this.right.equals(other.right))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the pair's left side.
     * @return The left side.
     */
    public L getLeft() {
        return this.left;
    }

    /**
     * Returns the pair's right side.
     * @return The right side.
     */
    public R getRight() {
        return this.right;
    }

    /**
     * @see java.lang.Object#hashCode() 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 67 * hash + (this.right != null ? this.right.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Pair [%s, %s]", left.toString(), right.toString());
    }
}
