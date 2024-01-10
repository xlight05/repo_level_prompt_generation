package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import pl.edu.agh.iosr.ftpserverremote.data.User;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class GenericDAOTestHelper extends TestCase {

  private final static Logger logger = Logger.getLogger( GenericDAOTestHelper.class );

  private final GenericDAO<User> userDao;

  public GenericDAOTestHelper(final GenericDAO<User> userDao) {
    this.userDao = userDao;
  }

  public void testUserCrateUpdateDelete() throws EntityNotFoundException {
    final User user = new User();
    user.setName( "a" );

    userDao.create( user );
    final long id = user.getId();
    logger.debug( "Created user. User ID: " + id );

    final User user2 = userDao.getById( id );
    assertEquals( "a", user2.getName() );
    user2.setName( "b" );
    userDao.update( user2 );
    logger.debug( "Updated user. User ID: " + user2.getId() );

    final User user3 = userDao.getById( id );
    assertEquals( "b", user3.getName() );

    userDao.delete( user3 );
    logger.debug( "Deleted user. User ID: " + user3.getId() );

    try {
      userDao.getById( id );
      fail();
    }
    catch (final EntityNotFoundException e) {
      //should be caught
      logger.debug( "User not found. User ID: " + id );
    }
  }

  public void testGetAll() {
    final User user1 = new User();
    final User user2 = new User();
    user1.setName( "a" );
    user1.setPassword( "aa" );
    user1.setRootDirectory( "./res/home" );
    user1.setEnabledBoolean( true );
    user2.setName( "b" );

    final List<User> initialList = userDao.getAll();
    final int initialSize = initialList.size();

    userDao.create( user1 );
    final List<User> oneItem = userDao.getAll();
    assertEquals( initialSize + 1, oneItem.size() );

    userDao.create( user2 );
    final List<User> twoItems = userDao.getAll();
    assertEquals( initialSize + 2, twoItems.size() );

    logger.debug( "Got all (2) items." );

    twoItems.get( 0 ).getName();
    twoItems.get( 1 ).getName();

    userDao.delete( user1 );
    userDao.delete( user2 );
  }

  public void testGetByServerid() throws EntityNotFoundException {
    final User user1 = new User();
    final User user2 = new User();
    user1.setName( "a" );
    user2.setName( "b" );
    user1.setServerId( 1L );
    user2.setServerId( 2L );

    userDao.create( user1 );
    final List<User> emptyList = userDao.getByServerId( 2L );
    assertEquals( 0, emptyList.size() );

    userDao.create( user2 );
    final List<User> oneItem = userDao.getByServerId( 2L );
    assertEquals( 1, oneItem.size() );

    logger.debug( "Got all (1) items with proper server id." );

    userDao.delete( user1 );
    userDao.delete( user2 );
  }
}
