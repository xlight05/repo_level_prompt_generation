package lk.apiit.friends.persistence;

import org.springframework.dao.DataAccessException;

/**
 * Base Data Access Object for DAOs of the solution.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 * 
 * @param <T> Type of model object handled by the DAO
 */
public interface BaseDao<T> {
	
	/**
	 * Finds the object for the given Id
	 * 
	 * @param id identifier
	 * 
	 * @return Object
	 */
	public T findById(Long id) throws DataAccessException ;
	
	/**
	 * Persists the given object
	 * 
	 * @param obj object to persist
	 */
	public void persist(T obj) throws DataAccessException ;
	
	/**
	 * Updates the changes to the given object
	 * 
	 * @param obj object to update
	 */
	public void update(T obj) throws DataAccessException ;
	
	/**
	 * Removes the given object from the persistence store.
	 * 
	 * @param obj Object to remove
	 */
	public void remove(T obj) throws DataAccessException ;
}
