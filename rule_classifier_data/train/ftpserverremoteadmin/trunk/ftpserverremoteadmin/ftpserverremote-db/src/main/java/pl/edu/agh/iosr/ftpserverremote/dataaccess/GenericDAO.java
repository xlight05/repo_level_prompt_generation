package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;

/**
 * This is a generic interface of a DAO object.
 * 
 * @author Tomasz Sadura
 */
public interface GenericDAO<E extends Entity> {

  /**
   * Deletes the specified entity.
   * @param entity entity to delete
   * @throws DaoException thrown if the specified entity could not be deleted
   */
  public void delete( final E entity ) throws DaoException;

  /**
   * Adds a new entity to the DAO.
   * @param entity entity to add to the DAO
   * @throws DaoException thrown if the specified entity could not be added
   */
  public void create( final E entity ) throws DaoException;

  /**
   * Updates the specified entity in the DAO.
   * @param entity entity to update
   * @throws DaoException thrown when the entity could not be updated
   */
  public void update( final E entity ) throws DaoException;

  /**
   * Returns the entity by it's ID.
   * @param id id of the entity
   * @return the entity
   * @throws EntityNotFoundException thrown if the entity was not found in the DAO
   * @throws DaoException thrown if there was another problem with getting the entity from the DAO  
   */
  public E getById( final Long id ) throws DaoException, EntityNotFoundException;

  /**
   * Returns the entity by it's name.
   * @param name name of the entity
   * @return the entity
   * @throws EntityNotFoundException thrown if the entity was not found in the DAO
   * @throws DaoException thrown if there was another problem with getting the entity from the DAO  
   */
  public E getByName( final String name ) throws DaoException, EntityNotFoundException;

  /**
   * Returns all entities.
   * @return all entities
   * @throws DaoException thrown if a problem occurred during extractions of all entites
   */
  public List<E> getAll() throws DaoException;

  /**
   * Return all entities with specified serverId
   * @param id the server id of the entities to return
   * @return the entities with specified serverId
   * @throws EntityNotFoundException thrown if the entity was not found in the DAO
   * @throws DaoException thrown if there was another problem with getting the entity from the DAO  
   */
  public List<E> getByServerId( final Long id ) throws DaoException, EntityNotFoundException;

  /**
   * Return the class object of the entity class.
   * @return the class object of the entity class
   */
  public Class<E> getEntityClass();
}
