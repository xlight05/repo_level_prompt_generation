/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.agh.iosr.ftpserverremote.serverdata;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author me
 */
public class ServerdataSuite extends TestCase {
    
    public ServerdataSuite(String testName) {
        super(testName);
    }            

    public static Test suite() {
        TestSuite suite = new TestSuite("ServerdataSuite");
      //  suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerSideFileObserverTest.suite());
        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataContainerImplTest.suite());
//        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerSideLogListenerTest.suite());
  //      suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerSideConnectionManagerTest.suite());
        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerSideStatisticsObserverTest.suite());
        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProviderTest.suite());
        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataListenerImplTest.suite());
        suite.addTest(pl.edu.agh.iosr.ftpserverremote.serverdata.ServerLogsLevelProviderTest.suite());
        return suite;
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
