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

/**
 * Wrapper class for embedded objects. Any serializable object (no Identity required)
 * wrapped by the Embedded class is embedded within a persistent object and 
 * persisted together with its container. On restore, embedded objects are fetched
 * lazyily. 
 * 
 * @param <T> The embedded value type.
 */
public class Embedded<T extends Serializable> extends Lazy<T> {
    static class ValueObject<T extends Serializable> implements Serializable, Cloneable {
        private static final long serialVersionUID = 2818217989632815037L;
        
        @Identity final Serializable id;
        final T value;
        
        public ValueObject(Serializable id, T value) {
            super();
            this.id = id;
            this.value = value;
        }
        
        public Object clone() {
            return new ValueObject<T>(id, value);
        }
        
        @Override
        public String toString() {
            return String.format("ValueObject: %s", value.toString());
        }
    }
    
    private static final long serialVersionUID = -5868777539228225297L;
    
    private transient boolean dirty;
 
    /**
     * Creates an embedded reference.
     * @param containerId The container's id.
     * @param id The reference id.
     */
    public Embedded(Serializable containerId, Serializable id) {
        super(PersistencyAttributes.getId(Embedded.class, containerId, id));
        this.dirty = false;
    }
    
    /**
     * Clones the embedded object.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        Embedded<T> copy = (Embedded<T>) super.clone();
        copy.dirty = this.dirty;
        copy.value = Cloner.deepCopy(value);
        return copy;
    }
    
    
    /**
     * Fetches the embedded object from the repository.
     * 
     * @param repository
     *            The repository.
     * @return this for a fluent api.
     */
    @SuppressWarnings("unchecked")
    public Reference<T> fetch(Repository repository) {
        if (!isDirty()) {
            ValueObject<T> valueObject = (ValueObject<T>)repository.restore(getId());
            this.value = valueObject != null? valueObject.value: null;
        }
        return this;
    }
    
    /**
     * Flushes the embedded object to the repository. If the object value was
     * set to null, the embedded object is removed from the repository. If the
     * embedded object was set to a new value, the embedded object is updated
     * (created or stored).
     * 
     * @param repository
     *            The repository.
     * @return this for a fluent api.
     */
    public Reference<T> flush(Repository repository) {
        if (isDirty()) {
            this.dirty = false;
            if (get() == null) {
                repository.remove(getId());
            } else {
                repository.store(new ValueObject<T>(getId(), get()));
                this.value = null;
            }
        }
        return this;
    }
    
    /**
     * Returns if the object has been set and is marked as dirty. The next flush
     * operation will update the referenced value in the repository. If the set
     * value is null, the object is removed.
     * 
     * @return True if the object is dirty.
     */
    public boolean isDirty() {
        return this.dirty;
    }
    
    /**
     * Marks the reference object for removal. The next flush call will remove
     * the object from the repository.
     * 
     * @return this for a fluent api.
     */
    public Reference<T> remove() {
        set(null);
        return this;
    }
    
    /**
     * Resets the object to its initial value.
     * 
     * @return this for a fluent api.
     */
    public Reference<T> reset() {
        if (isDirty()) {
            this.value = null;
            this.dirty = false;
        }
        return this;
    }
    
    /**
     * Sets the lazily loaded value. If the value is set to null, the object is
     * removed from the repository on the next flush. Otherwise the set value is
     * stored in the repository.
     * 
     * @param value
     *            The set value.
     * @return this for a fluent api.
     */
    public Embedded<T> set(T value) {
        this.value = value;
        setDirty();
        return this;
    }

    /**
     * Sets the lazy variable dirty. The next flush operation will update the
     * object in the repository. If the value was set to null, the referenced
     * object is removed from the repository.
     * 
     * @return this for a fluent api.
     */
    public Reference<T> setDirty() {
        this.dirty = true;
        return this;
    }

    /**
     * The setReference method is not supported.
     * @throws UnsupportedOperationException Always.
     */
    @Override
    public Reference<T> setReference(Serializable id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The id of embedded objects is immutable");
    }
}
