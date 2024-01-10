package gloodb;


import java.io.Serializable;

/**
 * Tagging class for persistent references
 * @param <T> The referenced type.
 */
public interface Reference<T extends Serializable> extends Serializable {

    /**
     * Returns the lazily loaded value. Fetch must be called before calling this
     * method.
     * 
     * @return The lazily loaded value.
     */
    T get();
    
    /**
     * Fetches the referenced object from the repository.
     * 
     * @param repository
     *            The repository.
     * @return this for a fluent api.
     */
    Reference<T> fetch(Repository repository);

    /**
     * Returns the id of the referenced object.
     * 
     * @return The id
     */
    Serializable getId();
}