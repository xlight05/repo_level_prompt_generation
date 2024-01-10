package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class ServerHibernateDAOTest extends TestCase {

  private ServerHibernateDAO serverDao;

  public void testGetByName() throws DaoException, EntityNotFoundException {
    final Server server1 = new Server();
    server1.setHostname( "host13" );
    server1.setPort( 21 );

    serverDao.create( server1 );
    final Long id = server1.getId();

    final Server server2 = serverDao.getByName( "host13:21" );

    assertEquals( id, server2.getId() );

    serverDao.delete( server1 );
  }

  public void testGetAll() {
    serverDao.getAll();
  }

  @Override
  public void setUp() {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    serverDao = (ServerHibernateDAO) factory.getBean( "serverDao" );
  }
}
