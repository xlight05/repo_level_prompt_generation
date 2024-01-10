package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import junit.framework.TestCase;

import pl.edu.agh.iosr.ftpserverremote.data.User;

/**
 *
 * @author Tomasz Sadura
 */

public class FakeGenericDAOTest extends TestCase {

  public void testHibernateDAO() throws EntityNotFoundException {

    final GenericDAO<User> userDao = new FakeGenericDAO<User>( User.class );

    final GenericDAOTestHelper testHelper = new GenericDAOTestHelper( userDao );
    testHelper.testUserCrateUpdateDelete();
    testHelper.testGetAll();
    testHelper.testGetByServerid();

  }
}
