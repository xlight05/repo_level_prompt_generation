package pl.edu.agh.iosr.ftpserverremote.command;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.DaoException;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.EntityNotFoundException;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO;

/**
 * Implementation of interface DataService
 * 
 * @author Agnieszka Janowska
 */
public class DataServiceImpl implements DataService {

  private final GenericDAO dao;
  private final Logger logger = LoggerFactory.getLogger( DataServiceImpl.class );

  public DataServiceImpl(final GenericDAO dao) {
    this.dao = dao;
  }

  public void create( final Entity entity ) throws pl.edu.agh.iosr.DataAccessException {
    try {
      dao.create( entity );
    }
    catch (final DaoException ex) {
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }

  }

  public void delete( final Entity entity ) throws pl.edu.agh.iosr.DataAccessException {
    try {
      dao.delete( entity );
    }
    catch (final DaoException ex) {
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }
  }

  public void update( final Entity entity ) throws pl.edu.agh.iosr.DataAccessException {
    try {
      dao.update( entity );
    }
    catch (final DaoException ex) {
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }
  }

  public Entity getById( final long id ) throws EntityNotFoundException,
      pl.edu.agh.iosr.DataAccessException
  {
    try {
      return dao.getById( id );
    }
    catch (final DaoException ex) {
      logger.error( "getEntity", ex );
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }
  }

  public Entity getByName( final String name ) throws pl.edu.agh.iosr.DataAccessException {
    try {
      return null;
    }
    catch (final DaoException ex) {
      logger.error( "getByName", ex );
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }
  }

  public List<Entity> getAll() throws pl.edu.agh.iosr.DataAccessException {
    try {
      return dao.getAll();
    }
    catch (final DaoException ex) {
      throw new pl.edu.agh.iosr.DataAccessException( ex );
    }
  }

  public List<Entity> getByServer( final Server server ) throws EntityNotFoundException,
      DataAccessException
  {
    try {
      return dao.getByServerId( server.getId() );
    }
    catch (final DaoException ex) {
      throw new DataAccessException( ex );
    }
  }
}
