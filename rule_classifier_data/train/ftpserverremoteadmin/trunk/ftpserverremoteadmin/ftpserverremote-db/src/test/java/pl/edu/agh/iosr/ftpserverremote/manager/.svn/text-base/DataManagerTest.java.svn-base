package pl.edu.agh.iosr.ftpserverremote.manager;

import junit.framework.TestCase;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class DataManagerTest extends TestCase {

  public void testReturnedDaos() {
    final DataManager dm = new DataManager();
    assertNotNull( dm.getUserDao() );
    assertNotNull( dm.getIpRestrictionDao() );
    assertNotNull( dm.getMessageDao() );
    assertNotNull( dm.getAdminDao() );
    assertNotNull( dm.getServerDao() );
  }
}
