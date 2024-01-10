package pl.edu.agh.iosr.ftpserverremote.usermanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.BaseUser;
import org.apache.ftpserver.usermanager.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.DbUserManager;
import org.apache.ftpserver.usermanager.TransferRatePermission;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.WritePermission;
import org.apache.ftpserver.util.StringUtils;
import org.apache.log4j.Logger;

import pl.edu.agh.iosr.ftpserverremote.connection.JDBCConnection;
import pl.edu.agh.iosr.ftpserverremote.server.ServerManager;
import pl.edu.agh.iosr.ftpserverremote.server.ServerManagerImpl;

/**
 * This class is a implementation of <code>UserManager</code> that stores user access in a database.
 * 
 * @author Tomasz Sadura
 *
 */
public class DbAdvancedUserManager extends DbUserManager {

  private static final Logger logger = Logger.getLogger( DbAdvancedUserManager.class );

  private static final String ATTR_SERVERID = "serverId";

  private final JDBCConnection connection = new JDBCConnection();

  private Long serverId = 0L;

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#configure()
   */
  @Override
  public void configure() {
    super.configure();
    try {
      ServerManagerImpl.setDataSource( getDataSource() );
      final ServerManager serverManager = ServerManagerImpl.getServerManager();
      serverId = serverManager.getServerId();
    }
    catch (final FtpException e) {
      logger.info( e.getMessage() );
      throw new RuntimeException( e );
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#setDataSource(javax.sql.DataSource)
   */
  @Override
  public void setDataSource( final DataSource dataSource ) {
    super.setDataSource( dataSource );
    connection.setDataSource( dataSource );
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#isAdmin(java.lang.String)
   */
  @Override
  public boolean isAdmin( final String login ) throws FtpException {

    // check input
    if ( login == null ) {
      return false;
    }

    Statement stmt = null;
    ResultSet rs = null;
    try {

      // create the sql query
      final Map<String, String> map = new HashMap<String, String>();
      map.put( ATTR_LOGIN, escapeString( login ) );
      map.put( ATTR_SERVERID, serverId.toString() );
      final String sql = StringUtils.replaceString( getSqlUserAdmin(), map );
      logger.info( sql );

      // execute query
      stmt = connection.createStatement();
      rs = stmt.executeQuery( sql );
      return rs.next();
    }
    catch (final SQLException ex) {
      logger.error( "DbAdvancedUserManager.isAdmin()", ex );
      throw new FtpException( "DbAdvancedUserManager.isAdmin()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.isAdmin()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.isAdmin()", ex );
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#getUserByName(java.lang.String)
   */
  @Override
  public synchronized User getUserByName( final String name ) throws FtpException {

    Statement stmt = null;
    ResultSet rs = null;
    try {

      // create sql query
      final Map<String, String> map = new HashMap<String, String>();
      map.put( ATTR_LOGIN, escapeString( name ) );
      map.put( ATTR_SERVERID, serverId.toString() );
      final String sql = StringUtils.replaceString( getSqlUserSelect(), map );
      logger.info( sql );

      // execute query
      stmt = connection.createStatement();
      rs = stmt.executeQuery( sql );

      // populate user object
      BaseUser thisUser = null;
      final String trueStr = Boolean.TRUE.toString();
      if ( rs.next() ) {
        thisUser = new BaseUser();
        thisUser.setName( rs.getString( ATTR_LOGIN ) );
        thisUser.setHomeDirectory( rs.getString( ATTR_HOME ) );
        thisUser.setEnabled( trueStr.equalsIgnoreCase( rs.getString( ATTR_ENABLE ) ) );
        thisUser.setMaxIdleTime( rs.getInt( ATTR_MAX_IDLE_TIME ) );

        final List<Authority> authorities = new ArrayList<Authority>();
        if ( trueStr.equalsIgnoreCase( rs.getString( ATTR_WRITE_PERM ) ) ) {
          authorities.add( new WritePermission() );
        }

        authorities.add( new ConcurrentLoginPermission( rs.getInt( ATTR_MAX_LOGIN_NUMBER ),
                                                        rs.getInt( ATTR_MAX_LOGIN_PER_IP ) ) );
        authorities.add( new TransferRatePermission( rs.getInt( ATTR_MAX_DOWNLOAD_RATE ),
                                                     rs.getInt( ATTR_MAX_UPLOAD_RATE ) ) );

        thisUser.setAuthorities( authorities.toArray( new Authority[0] ) );
      }
      return thisUser;
    }
    catch (final SQLException ex) {
      logger.error( "DbAdvancedUserManager.getUserByName()", ex );
      throw new FtpException( "DbAdvancedUserManager.getUserByName()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.getUserByName()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.getUserByName()", ex );
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#doesExist(java.lang.String)
   */
  @Override
  public synchronized boolean doesExist( final String name ) throws FtpException {
    Statement stmt = null;
    ResultSet rs = null;
    try {

      // create the sql
      final Map<String, String> map = new HashMap<String, String>();
      map.put( ATTR_LOGIN, escapeString( name ) );
      map.put( ATTR_SERVERID, serverId.toString() );
      final String sql = StringUtils.replaceString( getSqlUserSelect(), map );
      logger.info( sql );

      // execute query
      stmt = connection.createStatement();
      rs = stmt.executeQuery( sql );
      return rs.next();
    }
    catch (final SQLException ex) {
      logger.error( "DbAdvancedUserManager.doesExist()", ex );
      throw new FtpException( "DbAdvancedUserManager.doesExist()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.doesExist()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.doesExist()", ex );
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#getAllUserNames()
   */
  @Override
  public synchronized String[] getAllUserNames() throws FtpException {

    Statement stmt = null;
    ResultSet rs = null;
    try {

      // create sql query
      final Map<String, String> map = new HashMap<String, String>();
      map.put( ATTR_SERVERID, serverId.toString() );
      final String sql = StringUtils.replaceString( getSqlUserSelectAll(), map );
      logger.info( sql );

      // execute query
      stmt = connection.createStatement();
      rs = stmt.executeQuery( sql );

      // populate list
      final List<String> names = new ArrayList<String>();
      while ( rs.next() ) {
        names.add( rs.getString( ATTR_LOGIN ) );
      }
      return names.toArray( new String[0] );
    }
    catch (final SQLException ex) {
      logger.error( "DbAdvancedUserManager.getAllUserNames()", ex );
      throw new FtpException( "DbAdvancedUserManager.getAllUserNames()", ex );
    }
    finally {
      if ( rs != null ) {
        try {
          rs.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.getAllUserNames()", ex );
        }
      }
      if ( stmt != null ) {
        try {
          stmt.close();
        }
        catch (final Exception ex) {
          logger.error( "DbAdvancedUserManager.getAllUserNames()", ex );
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.apache.ftpserver.usermanager.DbUserManager#authenticate(org.apache.ftpserver.ftplet.Authentication)
   */
  @Override
  public synchronized User authenticate( final Authentication authentication )
      throws AuthenticationFailedException
  {
    if ( authentication instanceof UsernamePasswordAuthentication ) {
      final UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication) authentication;

      final String user = upauth.getUsername();
      String password = upauth.getPassword();

      if ( user == null ) {
        throw new AuthenticationFailedException( "Authentication failed" );
      }

      if ( password == null ) {
        password = "";
      }

      Statement stmt = null;
      ResultSet rs = null;
      try {

        // create the sql query
        final Map<String, String> map = new HashMap<String, String>();
        map.put( ATTR_LOGIN, escapeString( user ) );
        map.put( ATTR_PASSWORD, escapeString( password ) );
        map.put( ATTR_SERVERID, serverId.toString() );
        final String sql = StringUtils.replaceString( getSqlUserAuthenticate(), map );
        logger.info( sql );

        // execute query
        stmt = connection.createStatement();
        rs = stmt.executeQuery( sql );
        if ( rs.next() ) {
          try {
            return getUserByName( user );
          }
          catch (final FtpException e) {
            throw new AuthenticationFailedException( "Authentication failed", e );
          }
        }
        else {
          throw new AuthenticationFailedException( "Authentication failed" );
        }
      }
      catch (final SQLException ex) {
        logger.error( "DbAdvancedUserManager.authenticate()", ex );
        throw new AuthenticationFailedException( "Authentication failed", ex );
      }
      finally {
        if ( rs != null ) {
          try {
            rs.close();
          }
          catch (final Exception ex) {
            logger.error( "DbAdvancedUserManager.authenticate()", ex );
          }
        }
        if ( stmt != null ) {
          try {
            stmt.close();
          }
          catch (final Exception ex) {
            logger.error( "DbAdvancedUserManager.authenticate()", ex );
          }
        }
      }
    }
    else if ( authentication instanceof AnonymousAuthentication ) {
      try {
        if ( doesExist( "anonymous" ) ) {
          return getUserByName( "anonymous" );
        }
        else {
          throw new AuthenticationFailedException( "Authentication failed" );
        }
      }
      catch (final AuthenticationFailedException e) {
        throw e;
      }
      catch (final FtpException e) {
        throw new AuthenticationFailedException( "Authentication failed", e );
      }
    }
    else {
      throw new IllegalArgumentException( "Authentication not supported by this user manager" );
    }
  }

  private String escapeString( final String input ) {
    if ( input == null ) {
      return input;
    }

    final StringBuffer valBuf = new StringBuffer( input );
    for (int i = 0; i < valBuf.length(); i++) {
      final char ch = valBuf.charAt( i );
      if ( ch == '\'' || ch == '\\' || ch == '$' || ch == '^' || ch == '[' || ch == ']'
           || ch == '{' || ch == '}' ) {

        valBuf.insert( i, '\\' );
        i++;
      }
    }
    return valBuf.toString();
  }

  /**
   * Dispose component - closes the connection to the database.
   */
  @Override
  public void dispose() {
    connection.closeConnection();
  }
}
