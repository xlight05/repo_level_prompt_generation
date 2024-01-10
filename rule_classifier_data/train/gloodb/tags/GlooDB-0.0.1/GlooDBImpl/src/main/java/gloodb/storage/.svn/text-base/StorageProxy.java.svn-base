package gloodb.storage;

import java.io.Serializable;

public interface StorageProxy extends Serializable {

    /**
     * Stores the object in the storage.
     * @param persistentObject The persistent object.
     * @throws StorageFullException If the storage is full.
     */
    void store(Serializable persistentObject) throws StorageFullException;

    /**
     * Restores an object. If the object is cached in the proxy, a deep copy of
     * this object is returned.
     * @param <P> The object type.
     * @param storage The storage.
     * @param clazz The object class.
     * @return The restored object.
     */
    <P extends Serializable> P restore(Storage storage, Class<P> clazz);

    /**
     * Removes an object from the storage.
     * @param storage The storage.
     */
    void remove(Storage storage);

    /**
     * Flushes an object onto the storage. If required it purges the cache.
     * @param storage The storage.
     * @param removeFromCache True to flush the cache.
     */
    void flush(Storage storage, boolean removeFromCache);

    /**
     * Returns the class of the proxied object.
     * @return The class of the proxied object.
     */
    Class<? extends Serializable> getObjectClass();

    /**
     * Returns true if the object is cached.
     * @return True if the object is cached.
     */
    boolean isObjectCached();

    /**
     * Returns the id of the proxied object.
     * @return The id of the proxied object.
     */
    Serializable getId();

}