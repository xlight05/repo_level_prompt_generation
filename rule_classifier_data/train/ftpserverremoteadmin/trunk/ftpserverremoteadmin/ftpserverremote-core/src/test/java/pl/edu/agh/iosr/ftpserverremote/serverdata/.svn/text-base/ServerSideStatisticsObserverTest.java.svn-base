/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.net.InetAddress;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FileObject;
import org.apache.ftpserver.interfaces.ServerFtpStatistics;
import org.apache.ftpserver.listener.Connection;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 *
 * @author Agnieszka Janowska
 */
public class ServerSideStatisticsObserverTest extends TestCase {

    private ServerSideStatisticsObserver instance;
    private ServerDataListener dataListener;
    private ServerDataContainer container;
    private FtpServer ftpServer;
    private Server server;

    public ServerSideStatisticsObserverTest(String testName) {
	super(testName);
    }

    public static Test suite() {
	TestSuite suite = new TestSuite(ServerSideStatisticsObserverTest.class);
	return suite;
    }

    @Override
    protected void setUp() {
	try {
	    super.setUp();
	    ftpServer = new FtpServer(new FakeFtpServerContext());
	    server = new Server();
	    dataListener = new ServerDataListenerImpl(server);
	    container = new ServerDataContainerImpl();
	    ((ServerDataListenerImpl) dataListener).setServerDataContainer(container);
	    instance = new ServerSideStatisticsObserver(ftpServer, dataListener);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    public void testNothing() {

    }

    /**
     * Test of notifyUpload method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyUpload() {
	System.out.println("notifyUpload");
	Connection connection = null;
	FileObject file = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setUpload(connection, file, 2);
	instance.notifyUpload();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_FILE_UPLOAD) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_UPLOAD_BYTES) {
		assertEquals("2", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyDownload method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyDownload() {
	System.out.println("notifyDownload");
	Connection connection = null;
	FileObject file = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setDownload(connection, file, 3);
	instance.notifyDownload();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_FILE_DOWNLOAD) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_DOWNLOAD_BYTES) {
		assertEquals("3", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyDelete method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyDelete() {
	System.out.println("notifyDelete");
	Connection connection = null;
	FileObject file = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setDelete(connection, file);
	instance.notifyDelete();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_FILE_REMOVED) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyMkdir method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyMkdir() {
	System.out.println("notifyMkdir");
	Connection connection = null;
	FileObject file = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setMkdir(connection, file);
	instance.notifyMkdir();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_DIR_CREATED) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyRmdir method, of class ServerSideStatisticsObserver.
     */
    //TODO: change this method to test (note that there is bug in FTPServer - getRmdir returns mkdir value; thats why this test is not included)
    public void notestNotifyRmdir() {
	System.out.println("notifyRmdir");
	Connection connection = null;
	FileObject file = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setRmdir(connection, file);
	instance.notifyRmdir();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_DIR_REMOVED) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyLogin method, of class ServerSideStatisticsObserver.
     */
    //TODO: change this method to test (there are a lot of things happening in setLogin method, so probably a fake FtpStatistics object is needed to perform this test)
    public void notestNotifyLogin() {
	System.out.println("notifyLogin");
	Connection connection = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setLogin(connection);
	instance.notifyLogin(true);
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_CURR_LOGINS) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_TOTAL_LOGINS) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_CURR_ANON_LOGINS) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_TOTAL_ANON_LOGINS) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyLoginFail method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyLoginFail() {
	System.out.println("notifyLoginFail");
	Connection connection = null;
	InetAddress address = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setLoginFail(connection);
	instance.notifyLoginFail(address);
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_TOTAL_LOGIN_FAILS) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }

    /**
     * Test of notifyOpenConnection method, of class ServerSideStatisticsObserver.
     */
    public void testNotifyOpenConnection() {
	System.out.println("notifyOpenConnection");
	Connection connection = null;

	((ServerFtpStatistics) ftpServer.getServerContext().getFtpStatistics()).setOpenConnection(connection);
	instance.notifyOpenConnection();
	List<StatisticsData> result = container.getServerStats();
	for (StatisticsData stat : result) {
	    if (stat.getStatID() == ServerStatisticsInfo.I_CURR_CONS) {
		assertEquals("1", stat.getStatValue());
	    } else if (stat.getStatID() == ServerStatisticsInfo.I_TOTAL_CONS) {
		assertEquals("1", stat.getStatValue());
	    }
	}
    }
}
