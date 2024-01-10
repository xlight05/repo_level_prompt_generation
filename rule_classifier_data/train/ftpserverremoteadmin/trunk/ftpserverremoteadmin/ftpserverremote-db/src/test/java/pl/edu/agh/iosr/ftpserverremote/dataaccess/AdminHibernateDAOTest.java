package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import pl.edu.agh.iosr.ftpserverremote.data.Admin;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class AdminHibernateDAOTest extends TestCase {

  private final static Logger logger = Logger.getLogger( AdminHibernateDAOTest.class );

  private AdminHibernateDAO adminDao;

  public void testGetByName() throws DaoException, EntityNotFoundException {
    final Admin admin1 = new Admin();
    admin1.setLogin( "test13_login" );
    adminDao.create( admin1 );
    final Long id = admin1.getId();

    final Admin admin2 = adminDao.getByName( "test13_login" );
    assertEquals( id, admin2.getId() );

    adminDao.delete( admin1 );
  }

  public void testGetAll() throws DaoException, EntityNotFoundException {
    final List<Admin> admins = adminDao.getAll();
    for (final Admin admin : admins) {
      logger.debug( admin.getLogin() );
    }

  }

  @Override
  public void setUp() {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    adminDao = (AdminHibernateDAO) factory.getBean( "adminDao" );
  }

}
