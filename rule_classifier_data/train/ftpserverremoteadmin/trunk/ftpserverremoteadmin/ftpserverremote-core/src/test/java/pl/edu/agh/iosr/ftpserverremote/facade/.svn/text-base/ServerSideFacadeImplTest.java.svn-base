/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.iosr.ftpserverremote.facade;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.ftpserver.config.PropertiesConfiguration;
import org.apache.ftpserver.ftplet.Configuration;
import org.apache.ftpserver.listener.Connection;
import org.apache.ftpserver.listener.ConnectionManager;
import org.apache.ftpserver.listener.ConnectionManagerImpl;
import org.apache.ftpserver.listener.mina.MinaConnection;

/**
 *
 * @author me
 */
public class ServerSideFacadeImplTest extends TestCase {

    private Configuration config;

    public ServerSideFacadeImplTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        config = new PropertiesConfiguration(new Properties());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of startServer method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestStartServer() throws RemoteException {
        System.out.println("startServer");
        ServerSideFacadeImpl instance = null;
        try {
            instance = new ServerSideFacadeImpl(config);
        } catch (RemoteException ex) {
            fail("Remote exception");
        } catch (Exception e) {
            fail("Exception");
        }
        assertTrue(instance.isServerStopped());
        assertFalse(instance.isServerSuspended());
        instance.startServer();
        assertFalse(instance.isServerStopped());
        assertFalse(instance.isServerSuspended());
    }

    /**
     * Test of stopServer method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestStopServer() throws RemoteException {
        System.out.println("stopServer");
        try {
            ServerSideFacadeImpl instance = new ServerSideFacadeImpl(config);
            instance.startServer();
            instance.stopServer();
            assertTrue(instance.isServerStopped());
            assertFalse(instance.isServerSuspended());
        } catch (Exception e) {
            fail("Exception");
        }

    }

    /**
     * Test of suspendServer method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestSuspendServer() throws RemoteException {
        System.out.println("suspendServer");
        try {
            ServerSideFacadeImpl instance = new ServerSideFacadeImpl(config);

            instance.startServer();
            instance.suspendServer();
            assertTrue(instance.isServerSuspended());
            assertFalse(instance.isServerStopped());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test of resumeServer method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestResumeServer() throws RemoteException {
        System.out.println("resumeServer");
        try {
            ServerSideFacadeImpl instance = new ServerSideFacadeImpl(config);
            instance.startServer();
            instance.suspendServer();
            instance.resumeServer();
            assertFalse(instance.isServerSuspended());
            assertFalse(instance.isServerStopped());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test of isServerStopped method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestIsServerStopped() throws RemoteException {
        System.out.println("isServerStopped");
        try {
            ServerSideFacadeImpl instance = new ServerSideFacadeImpl(config);
            boolean expResult = true;
            boolean result = instance.isServerStopped();
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test of isServerSuspended method, of class ServerSideFacadeImpl.
     * @throws RemoteException 
     */
    public void notestIsServerSuspended() throws RemoteException {
        System.out.println("isServerSuspended");
        try {
            ServerSideFacadeImpl instance = new ServerSideFacadeImpl(config);
            boolean expResult = false;
            boolean result = instance.isServerSuspended();
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test of closeConnection method, of class ServerSideFacadeImpl.
     */
    public void notestCloseConnection() {
        System.out.println("closeConnection");
    }

    /**
     * Test of closeConnections method, of class ServerSideFacadeImpl.
     */
    public void notestCloseConnections() {
        System.out.println("closeConnections");
    }

    /**
     * Empty test as others are turned off.
     */
    public void testNothing() {

    }
}
