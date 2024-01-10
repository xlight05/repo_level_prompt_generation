package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import pl.edu.agh.iosr.ftpserverremote.data.User;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class UserHibernateDAOTest extends TestCase {

  public void testGetByName() throws DaoException, EntityNotFoundException {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    final UserHibernateDAO userDao = (UserHibernateDAO) factory.getBean( "userDao" );

    final User user1 = new User();
    user1.setName( "test13_user" );
    userDao.create( user1 );
    final Long id = user1.getId();

    final User user2 = userDao.getByName( "test13_user" );
    assertEquals( id, user2.getId() );

    userDao.delete( user1 );
  }
}
