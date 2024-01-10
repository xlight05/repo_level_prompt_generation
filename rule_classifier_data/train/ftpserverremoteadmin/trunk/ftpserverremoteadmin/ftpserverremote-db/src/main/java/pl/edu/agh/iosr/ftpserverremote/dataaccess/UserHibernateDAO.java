package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

import pl.edu.agh.iosr.ftpserverremote.data.User;

/**
 * This is a Hibernate DAO for <code>User</code>.
 * 
 * @author Tomasz Sadura
 *
 */
public class UserHibernateDAO extends GenericHibernateDAO<User> {

  /**
   * Creates a new <code>UserHibernateDAO</code>.
   */
  public UserHibernateDAO() {
    super( User.class );
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericHibernateDAO#getByName(java.lang.String)
   */
  @Override
  public User getByName( final String name ) throws DaoException, EntityNotFoundException {
    if ( name == null ) {
      throw new EntityNotFoundException( "Name is null" );
    }
    User result = null;
    try {
      result = (User) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "name", name ) );
          return criteria.uniqueResult();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    if ( result == null ) {
      throw new EntityNotFoundException( "no User with Name " + name );
    }
    return result;
  }
}
