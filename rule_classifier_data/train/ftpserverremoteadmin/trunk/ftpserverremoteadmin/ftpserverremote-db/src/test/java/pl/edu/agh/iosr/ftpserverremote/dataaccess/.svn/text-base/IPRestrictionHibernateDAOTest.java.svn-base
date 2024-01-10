package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class IPRestrictionHibernateDAOTest extends TestCase {

  public void testGetByName() throws DaoException, EntityNotFoundException {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    final IpRestrictionHibernateDAO ipRestrictionDao = (IpRestrictionHibernateDAO) factory.getBean( "ipRestrictionDao" );

    final IPRestriction ipRestriction1 = new IPRestriction();
    ipRestriction1.setIpPattern( "test13_ipRestriction" );
    ipRestrictionDao.create( ipRestriction1 );
    final Long id = ipRestriction1.getId();

    final IPRestriction ipRestriction2 = ipRestrictionDao.getByName( "test13_ipRestriction" );
    assertEquals( id, ipRestriction2.getId() );

    ipRestrictionDao.delete( ipRestriction1 );
  }

}
