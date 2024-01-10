package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.sql.SQLException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;

/**
 * This is a Hibernate DAO for <code>IPRestriction</code>.
 * 
 * @author Tomasz Sadura
 *
 */
public class IpRestrictionHibernateDAO extends GenericHibernateDAO<IPRestriction> {

  /**
   * Creates a new <code>IpRestrictionHibernateDAO</code>.
   */
  public IpRestrictionHibernateDAO() {
    super( IPRestriction.class );
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericHibernateDAO#getByName(java.lang.String)
   */
  @Override
  public IPRestriction getByName( final String name ) throws DaoException,
      EntityNotFoundException
  {
    if ( name == null ) {
      throw new EntityNotFoundException( "Name is null" );
    }
    IPRestriction result = null;
    try {
      result = (IPRestriction) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "ipPattern", name ) );
          return criteria.uniqueResult();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    if ( result == null ) {
      throw new EntityNotFoundException( "no IPPattern with Name " + name );
    }
    return result;
  }
}
