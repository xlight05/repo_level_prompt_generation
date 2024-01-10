package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

import pl.edu.agh.iosr.ftpserverremote.data.Admin;

/**
 * This class is a Hibernate implementation of Admin DAO.
 * 
 * @author Tomasz Sadura
 *
 */
public class AdminHibernateDAO extends GenericHibernateDAO<Admin> {

  /*
   * Creates the new DAO.
   */
  public AdminHibernateDAO() {
    super( Admin.class );
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericHibernateDAO#getByName(java.lang.String)
   */
  @Override
  public Admin getByName( final String name ) throws DaoException, EntityNotFoundException {
    if ( name == null ) {
      throw new EntityNotFoundException( "Name is null" );
    }
    Admin result = null;
    try {
      result = (Admin) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "login", name ) );
          return criteria.uniqueResult();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    if ( result == null ) {
      throw new EntityNotFoundException( "no Admin with Name " + name );
    }
    return result;
  }
}
