package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;

/**
 * This is a Hibernate DAO implementation.
 *
 * @author Tomasz Sadura
 */
public class GenericHibernateDAO<E extends Entity> extends HibernateDaoSupport
  implements GenericDAO<E> {

  /**
   * The class of the entity handled by this DAO.
   */
  private final Class<E> entityClass;

  /**
   * Creates a new DAO.
   * @param entityClass class of entities handles by this DAO
   */
  public GenericHibernateDAO(final Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#update(pl.edu.agh.iosr.ftpserverremote.data.Entity)
   */
  public void update( final E entity ) throws DaoException {
    try {
      getHibernateTemplate().update( entity );
      getHibernateTemplate().flush();
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#delete(pl.edu.agh.iosr.ftpserverremote.data.Entity)
   */
  public void delete( final E entity ) throws DaoException {
    try {
      getHibernateTemplate().delete( entity );
      getHibernateTemplate().flush();
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#create(pl.edu.agh.iosr.ftpserverremote.data.Entity)
   */
  public void create( final E entity ) throws DaoException {
    try {
      getHibernateTemplate().save( entity );
      getHibernateTemplate().flush();
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#getEntityClass()
   */
  public Class<E> getEntityClass() {
    return entityClass;
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#getById(java.lang.Long)
   */
  public E getById( final Long id ) throws DaoException, EntityNotFoundException {
    if ( id == null ) {
      throw new EntityNotFoundException( "ID is null" );
    }
    E result = null;
    try {
      result = (E) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "id", id ) );
          return criteria.uniqueResult();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    if ( result == null ) {
      throw new EntityNotFoundException( "no entity with ID " + id );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#getByName(java.lang.String)
   */
  public E getByName( final String name ) throws DaoException, EntityNotFoundException {
    throw new DaoException( "This operation is not supported for " + this.getClass() );
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#getAll()
   */
  public List<E> getAll() throws DaoException {
    List<E> result = null;
    try {
      result = (List<E>) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.addOrder( Order.asc( "id" ) );
          return criteria.list();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO#getByServerId(java.lang.Long)
   */
  public List<E> getByServerId( final Long id ) throws DaoException, EntityNotFoundException {
    if ( id == null ) {
      throw new EntityNotFoundException( "ID is null" );
    }
    List<E> result = null;
    try {
      result = (List<E>) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "serverId", id ) );
          return criteria.list();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    return result;
  }
}
