package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class FakeGenericDAO<E extends Entity> implements GenericDAO<E> {

  private final Map<Long, E> idToEntity = new HashMap<Long, E>();

  private final Class<E> entityClass;

  private static long staticId = 1;

  public FakeGenericDAO(final Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  public void create( final E entity ) throws DaoException {
    entity.setId( staticId++ );
    idToEntity.put( entity.getId(), entity );
  }

  public void delete( final E entity ) throws DaoException {
    if ( idToEntity.containsKey( entity.getId() == null ) ) {
      throw new RuntimeException( "Entity does not exist" );
    }
    idToEntity.remove( entity.getId() );
  }

  public E getById( final Long id ) throws DaoException, EntityNotFoundException {
    if ( id == null ) {
      throw new EntityNotFoundException( "ID is null" );
    }
    if ( !idToEntity.containsKey( id ) ) {
      throw new EntityNotFoundException( "Entity does not exist" );
    }
    return idToEntity.get( id );
  }

  public Class<E> getEntityClass() {
    return entityClass;
  }

  public void update( final E entity ) throws DaoException {
    if ( idToEntity.containsKey( entity.getId() == null ) ) {
      throw new RuntimeException( "Entity does not exist" );
    }
    idToEntity.put( entity.getId(), entity );
  }

  public List<E> getAll() throws DaoException {
    return new ArrayList<E>( idToEntity.values() );
  }

  public List<E> getByServerId( final Long id ) throws DaoException, EntityNotFoundException {
    if ( id == null ) {
      throw new EntityNotFoundException( "ID is null" );
    }
    final List<E> returnList = new ArrayList<E>();
    for (final E entity : idToEntity.values()) {
      if ( entity.getServerId().equals( id ) ) {
        returnList.add( entity );
      }
    }
    return returnList;
  }

  public E getByName( final String name ) throws DaoException, EntityNotFoundException {
    throw new DaoException( "This operation is not supported" );
  }

}
