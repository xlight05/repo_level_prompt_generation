package pl.edu.agh.iosr.ftpserverremote.manager;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import pl.edu.agh.iosr.ftpserverremote.data.Admin;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;
import pl.edu.agh.iosr.ftpserverremote.data.Message;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.User;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.GenericDAO;

/**
 * This is a manager responsible for handling DAOs.
 * 
 * @author Tomasz Sadura
 *
 */
public class DataManager {

  private final GenericDAO<User> userDao;
  private final GenericDAO<IPRestriction> ipRestrictionDao;
  private final GenericDAO<Message> messageDao;
  private final GenericDAO<Admin> adminDao;
  private final GenericDAO<Server> serverDao;

  /**
   * Creates a new <code>DataManager</code>.
   */
  public DataManager() {
    final Resource res = new ClassPathResource( "springConfiguration.xml" );
    final BeanFactory factory = new XmlBeanFactory( res );
    userDao = (GenericDAO<User>) factory.getBean( "userDao" );
    ipRestrictionDao = (GenericDAO<IPRestriction>) factory.getBean( "ipRestrictionDao" );
    messageDao = (GenericDAO<Message>) factory.getBean( "messageDao" );
    adminDao = (GenericDAO<Admin>) factory.getBean( "adminDao" );
    serverDao = (GenericDAO<Server>) factory.getBean( "serverDao" );
  }

  /**
   * Returns a <code>UserDAO</code>
   * @return <code>UserDAO</code>
   */
  public GenericDAO<User> getUserDao() {
    return userDao;
  }

  /**
   * Returns a <code>IPRestrictionDAO</code>
   * @return <code>IPRestrictionDAO</code>
   */
  public GenericDAO<IPRestriction> getIpRestrictionDao() {
    return ipRestrictionDao;
  }

  /**
   * Return a <code>ManagerDAO</code>
   * @return <code>ManagerDAO</code>
   */
  public GenericDAO<Message> getMessageDao() {
    return messageDao;
  }

  /**
   * Returns a <code>AdminDAO</code>
   * @return <code>AdminDAO</code>
   */
  public GenericDAO<Admin> getAdminDao() {
    return adminDao;
  }

  /**
   * Returns a <code>ServerDAO</code>
   * @return <code>ServerDAO</code>
   */
  public GenericDAO<Server> getServerDao() {
    return serverDao;
  }
}
