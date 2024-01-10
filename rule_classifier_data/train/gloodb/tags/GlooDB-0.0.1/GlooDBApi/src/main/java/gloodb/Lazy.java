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
 * Wrapper class for lazy loaded objects. A lazy object does not get fully
 * stored or restored together with its container object. Only the identity of the
 * referred lazy object is persisted. To access the actual object value, the
 * programmer must invoke the fetch() and get() methods. 
 * 
 * @param <T> The lazy value type.
 */
public class Lazy<T extends Serializable> implements Serializable, Cloneable, Reference<T> {

    private static final long serialVersionUID = -6547945092017681520L;
    
    private Serializable id;
    protected transient T value;

    /**
     * Creates a lazy reference associated with the given id.
     * 
     * @param idFields
     *            The id of the referenced object.
     */
    public Lazy(Serializable... idFields) {
        this.id = (idFields == null) ? null : (idFields.length == 1) ? idFields[0]: new AggregateIdentity(idFields);
        this.value = null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            Lazy<T> copy = (Lazy<T>)super.clone();
            copy.value = Cloner.deepCopy(this.value);
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooException(cnse);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lazy<?> other = (Lazy<?>) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see gloodb.utils.Reference#fetch(gloodb.Repository)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Reference<T> fetch(Repository repository) {
        this.value = (T) repository.restore(getId());
        return this;
    }

    /* (non-Javadoc)
     * @see gloodb.utils.Reference#get()
     */
    @Override
    public T get() {
        return this.value;
    }

    /* (non-Javadoc)
     * @see gloodb.utils.Reference#getId()
     */
    @Override
    public Serializable getId() {
        return this.id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Sets the id of the referenced object. The lazy reference state is reset
     * to not dirty and the object must be fetched before any access.
     * 
     * @param id
     *            The new referenced id.
     * @return this for a fluent api.
     */
    public Reference<T> setReference(Serializable id) {
        this.id = id;
        this.value = null;
        return this;
    }

    /**
     * Returns this lazy object as a string. If fetch was called prior to
     * calling toString, value.toString() is returned, otherwise "?".
     * 
     * @return The string format of the lazy reference.
     */
    @Override
    public String toString() {
        return this.value != null ? this.value.toString() : String.format("ID: %s (not fetched)", id.toString());
    }
}
