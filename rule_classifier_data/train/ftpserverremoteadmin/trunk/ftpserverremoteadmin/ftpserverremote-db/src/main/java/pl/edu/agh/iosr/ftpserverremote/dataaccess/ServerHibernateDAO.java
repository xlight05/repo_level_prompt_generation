package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.sql.SQLException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 * This is a Hibernate DAO for <code>Server</code>.
 * 
 * @author Tomasz Sadura
 *
 */
public class ServerHibernateDAO extends GenericHibernateDAO<Server> {

  /**
   * Creates a new <code>ServerHibernateDAO</code>.
   */
  public ServerHibernateDAO() {
    super( Server.class );
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericHibernateDAO#getByName(java.lang.String)
   */
  @Override
  public Server getByName( final String name ) throws DaoException, EntityNotFoundException {
    if ( name == null ) {
      throw new EntityNotFoundException( "Name is null" );
    }
    final int colonIndex = name.indexOf( ':' );
    if ( colonIndex == -1 ) {
      throw new DaoException( "Wrong name specified. SPecify hostname:port_numer" );
    }
    final String hostname = name.substring( 0, colonIndex );
    Integer portNumber = 0;
    try {
      portNumber = Integer.parseInt( name.substring( colonIndex + 1, name.length() ) );
    }
    catch (final NumberFormatException e) {
      throw new DaoException( "Wrong port number", e );
    }
    final Integer port = portNumber;
    Server result = null;
    try {
      result = (Server) getHibernateTemplate().execute( new HibernateCallback() {

        public Object doInHibernate( final Session session ) throws HibernateException,
            SQLException
        {
          final Criteria criteria = session.createCriteria( getEntityClass() );
          criteria.add( Restrictions.eq( "hostname", hostname ) );
          criteria.add( Restrictions.eq( "port", port ) );
          return criteria.uniqueResult();
        }
      } );
    }
    catch (final DataAccessException e) {
      throw new DaoException( e );
    }
    if ( result == null ) {
      throw new EntityNotFoundException( "no Server with Name " + name );
    }
    return result;
  }
}
