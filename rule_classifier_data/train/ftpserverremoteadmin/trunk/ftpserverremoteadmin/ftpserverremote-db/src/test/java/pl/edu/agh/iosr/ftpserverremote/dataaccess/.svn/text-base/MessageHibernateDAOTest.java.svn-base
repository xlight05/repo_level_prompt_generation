package pl.edu.agh.iosr.ftpserverremote.dataaccess;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import pl.edu.agh.iosr.ftpserverremote.data.Message;
import junit.framework.TestCase;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class MessageHibernateDAOTest extends TestCase {

  public void testGetByName() throws DaoException, EntityNotFoundException {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    final MessageHibernateDAO messageDao = (MessageHibernateDAO) factory.getBean( "messageDao" );

    final Message message1 = new Message();
    message1.setName( "test13_message" );
    message1.setMessage( "ble" );
    messageDao.create( message1 );
    final Long id = message1.getId();

    final Message message2 = messageDao.getByName( "test13_message" );
    assertEquals( id, message2.getId() );

    messageDao.delete( message1 );
  }

}
