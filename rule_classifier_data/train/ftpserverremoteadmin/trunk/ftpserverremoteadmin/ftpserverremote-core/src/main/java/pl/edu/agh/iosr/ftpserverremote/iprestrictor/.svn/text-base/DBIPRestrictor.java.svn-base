package pl.edu.agh.iosr.ftpserverremote.iprestrictor;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.interfaces.IpRestrictor;
import org.apache.ftpserver.util.RegularExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.iosr.ftpserverremote.connection.JDBCConnection;
import pl.edu.agh.iosr.ftpserverremote.server.ServerManager;
import pl.edu.agh.iosr.ftpserverremote.server.ServerManagerImpl;

/**
 * This class is an implementation of <code>IpRestricttor</code> that gets the restrictions from a database.
 * 
 * @author Tomasz Sadura
 *
 */
public class DBIPRestrictor implements IpRestrictor {

  private static final Logger LOG = LoggerFactory.getLogger( DBIPRestrictor.class );

  private static final String ATTR_IPPATTERN = "IPPattern";
  private static final String ATTR_PERMISSION = "Permission";
  private static final String ATTR_SERVERID = "serverId";

  Object[][] permissions;

  private DataSource dataSource;
  private final JDBCConnection connection = new JDBCConnection();

  private String selectAll;

  /**
   * Returns the SELECT statement used to extract IP restrictions.
   * @return the SELECT statement.
   */
  public String getSqlSelectAll() {
    return selectAll;
  }

  /**
   * Sets the SELECT statement used to extract IP restrictions. 
   * @param selectAll the SELECT statement to set
   */
  public void setSqlSelectAll( final String selectAll ) {
    this.selectAll = selectAll;
  }

  /**
   * Initializes and configures the IP restrictor.
   * @throws FtpException
   */
  public void configure() throws FtpException {

    if ( dataSource == null ) {
      throw new FtpServerConfigurationException( "Required data source not provided" );
    }
    ServerManagerImpl.setDataSource( getDataSource() );

    if ( selectAll == null ) {
      throw new FtpServerConfigurationException( "Required select IPRestriciotn SQL statement not provided" );
    }

    try {
      // test the connection
      connection.createConnection();

      LOG.info( "Database connection opened." );
    }
    catch (final SQLException ex) {
      LOG.error( "DBIPRestrictor.configure()", ex );
      throw new FtpServerConfigurationException( "DBIPRestrictor.configure()", ex );
    }

    permissions = getPermissions();
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.interfaces.IpRestrictor#getPermissions()
   */
  public Object[][] getPermissions() throws FtpException {

    Statement stmt = null;
    ResultSet rs = null;
    try {

      stmt = connection.createStatement();
      final ServerManager serverManager = ServerManagerImpl.getServerManager();
      final Long serverId = serverManager.getServerId();
      final String string = serverId.toString();
      final String selectQuery = selectAll.replaceFirst( "\\{" + ATTR_SERVERID + "\\}", string );
      LOG.info( selectQuery );

      rs = stmt.executeQuery( selectQuery );

      rs.last();
      final int size = rs.getRow();
      rs.beforeFirst();

      final Object[][] result = new Object[size][2];

      int i = 0;
      while ( rs.next() ) {
        final String boolStr = rs.getString( ATTR_PERMISSION );
        final String regexpStr = rs.getString( ATTR_IPPATTERN );
        final Object[] entry = {
            regexpStr, "true".equals( boolStr ) ? Boolean.TRUE : Boolean.FALSE
        };
        result[i] = entry;
        i++;
      }
      return result;
    }
    catch (final SQLException ex) {
      LOG.error( "DBIPRestrictor.getPermission()", ex );
      throw new FtpException( "DBIPRestrictor.getPermission()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          LOG.error( "DBIPRestrictor.getPermission()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          LOG.error( "DBIPRestrictor.getPermission()", ex );
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.interfaces.IpRestrictor#hasPermission(java.net.InetAddress)
   */
  public boolean hasPermission( final InetAddress address ) throws FtpException {
    permissions = getPermissions();
    final String addressStr = address.getHostAddress();
    boolean retVal = true;
    for (int i = 0; i < permissions.length; ++i) {
      final String ipPattern = (String) permissions[i][0];
      final RegularExpr regexp = new RegularExpr( ipPattern );
      if ( regexp.isMatch( addressStr ) ) {
        retVal = ((Boolean) permissions[i][1]).booleanValue();
        break;
      }
    }
    return retVal;
  }

  /**
   * Unsupported.
   */
  public void setPermissions( final Object[][] arg0 ) throws FtpException {
    throw new FtpException( "The setPermissions operation is not supported" );
  }

  /**
   * Return the data source used by this IP restrictor. 
   * @return the data source
   */
  public DataSource getDataSource() {
    return dataSource;
  }

  /**
   * Sets The data source used by this IP restrictor.
   * @param dataSource
   */
  public void setDataSource( final DataSource dataSource ) {
    this.dataSource = dataSource;
    connection.setDataSource( dataSource );
  }

  /**
   * Dispose component - closes the connection to the database.
   */
  public void dispose() {
    connection.closeConnection();
  }
}
