package pl.edu.agh.iosr.ftpserverremote.helper;

import pl.edu.agh.iosr.ftpserverremote.data.Admin;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;
import pl.edu.agh.iosr.ftpserverremote.data.Message;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.User;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO;
import pl.edu.agh.iosr.ftpserverremote.manager.DataManager;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class DataSourcePreparator {

  private final DataManager dataManager;

  public DataSourcePreparator() {
    dataManager = new DataManager();
  }

  public void prepareUsers() {
    final GenericDAO<User> userDao = dataManager.getUserDao();
    try {
      final User u = new User();
      u.setName( "user1" );
      u.setPassword( "pass1" );
      u.setRootDirectory( "." );
      u.setServerId( 0L );

      userDao.create( u );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }

    try {
      final User u = new User();
      u.setName( "user2" );
      u.setPassword( "pass2" );
      u.setRootDirectory( "." );
      u.setServerId( 1L );

      userDao.create( u );

    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void prepareIpRestrictions() {
    final GenericDAO<IPRestriction> ipRestrictionDao = dataManager.getIpRestrictionDao();
    try {
      final IPRestriction ip = new IPRestriction();
      ip.setIpPattern( "*" );
      ip.setPermissionBoolean( false );
      ipRestrictionDao.create( ip );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }

    try {
      final IPRestriction ip = new IPRestriction();
      ip.setIpPattern( "127.0.0.1" );
      ip.setPermissionBoolean( true );
      ipRestrictionDao.create( ip );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void prepareMessages() {
    final GenericDAO<Message> messageDao = dataManager.getMessageDao();
    try {
      final Message message = new Message();
      message.setName( "530.ip.restricted" );
      message.setMessage( "Ble" );
      messageDao.create( message );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }

    try {
      final Message message = new Message();
      message.setName( "331.USER" );
      message.setMessage( "Tralala" );
      messageDao.create( message );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void prepareAdmin() {
    final GenericDAO<Admin> adminDao = dataManager.getAdminDao();
    try {
      final Admin a = new Admin();
      a.setLogin( "a" );
      a.setPassword( "aa" );
      adminDao.create( a );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }

    try {
      final Admin a = new Admin();
      a.setLogin( "b" );
      a.setPassword( "bb" );
      adminDao.create( a );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void prepareServer() {
    final GenericDAO<Server> serverDao = dataManager.getServerDao();
    try {
      final Server s = new Server();
      s.setHostname( "example" );
      s.setPort( 123 );
      serverDao.create( s );
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void prepareDataSource() {
    prepareUsers();
    prepareIpRestrictions();
    prepareMessages();
    prepareAdmin();
    prepareServer();
  }

  public static void main( final String[] args ) {
    new DataSourcePreparator().prepareDataSource();
  }
}
