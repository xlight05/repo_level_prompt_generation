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
 */

public class GenericHibernateDAOTest extends TestCase {

  public void testHibernateDAO() throws EntityNotFoundException {

    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    final GenericDAO<User> userDao = (GenericDAO<User>) factory.getBean( "userDao" );

    final GenericDAOTestHelper testHelper = new GenericDAOTestHelper( userDao );
    testHelper.testUserCrateUpdateDelete();
    testHelper.testGetAll();
    testHelper.testGetByServerid();
  }
}
