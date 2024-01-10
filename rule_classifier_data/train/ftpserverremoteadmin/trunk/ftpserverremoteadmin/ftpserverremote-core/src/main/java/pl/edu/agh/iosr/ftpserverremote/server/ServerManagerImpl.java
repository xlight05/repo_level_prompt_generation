package pl.edu.agh.iosr.ftpserverremote.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.util.StringUtils;
import org.apache.log4j.Logger;

import pl.edu.agh.iosr.ftpserverremote.connection.JDBCConnection;

/**
 * This is a singleton implementation of a ServerManager that operates on a database.
 * 
 * @author Tomasz Sadura
 *
 */
public class ServerManagerImpl implements ServerManager {

  private static final Logger logger = Logger.getLogger( ServerManagerImpl.class );

  private final static String SELECT_QUERY = "SELECT Id FROM Server WHERE Hostname='{hostname}' AND Port='{port}'";
  private final static String INSERT_QUERY = "INSERT INTO Server (Hostname, Port) VALUES ('{hostname}', '{port}')";
  private final static String ATTR_HOSTNAME = "hostname";
  private final static String ATTR_PORT = "port";

  private static ServerManagerImpl instance;

  private Long serverId;

  private final JDBCConnection connection = new JDBCConnection();
  private static DataSource dataSource;

  /**
   * Returns the singleton instance of the <code>ServerManager</code>.
   * @return the instance of <code>ServerManager</code>
   * @throws FtpException
   */
  public synchronized static ServerManager getServerManager() throws FtpException {
    if ( instance == null ) {
      instance = new ServerManagerImpl();
      instance.getConnection().setDataSource( dataSource );
      instance.save();
    }
    return instance;
  }

  /*
   * (non-Javadoc)
   * @see pl.edu.agh.iosr.ftpserverremote.server.ServerManager#getServerId()
   */
  public Long getServerId() {
    return serverId;
  }

  /**
   * Saves a server information to the database.
   * 
   */
  public void save() throws FtpException {
    Statement stmt = null;
    ResultSet rs = null;
    String hostAddress;
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    }
    catch (final UnknownHostException e) {
      logger.info( e.getMessage() );
      throw new FtpException( e );
    }
    final String portNumber = "21";

    try {
      final Map<String, String> map = new HashMap<String, String>();
      map.put( ATTR_HOSTNAME, hostAddress );
      map.put( ATTR_PORT, portNumber );
      String sql = StringUtils.replaceString( SELECT_QUERY, map );
      logger.info( sql );

      stmt = connection.createStatement();
      rs = stmt.executeQuery( sql );

      if ( rs.next() ) {
        serverId = new Long( rs.getInt( 1 ) );
      }
      else {
        sql = StringUtils.replaceString( INSERT_QUERY, map );
        logger.info( sql );
        stmt = connection.createStatement();
        stmt.executeUpdate( sql );

        rs = stmt.getGeneratedKeys();
        if ( rs.next() ) {
          serverId = new Long( rs.getInt( 1 ) );
        }
      }
      logger.info( "Server id: " + serverId );
    }
    catch (final SQLException ex) {
      logger.error( "ServerManagerImpl.getPermission()", ex );
      throw new FtpException( "ServerManagerImpl.getPermission()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          logger.error( "ServerManagerImpl.getPermission()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          logger.error( "ServerManagerImpl.getPermission()", ex );
        }
      }
    }
  }

  /**
   * Sets the data source fot the manager. 
   * @param dataSource the data source to set
   */
  public static void setDataSource( final DataSource dataSource ) {
    ServerManagerImpl.dataSource = dataSource;
  }

  /**
   * Return the JDBC connection.
   * @return the JDBC connection
   */
  public JDBCConnection getConnection() {
    return connection;
  }

  /**
   * Dispose component - closes the connection to the database.
   */
  public void dispose() {
    connection.closeConnection();
  }
}
