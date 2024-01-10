package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.LinkedList;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 *
 * @author Agnieszka Janowska
 */
public class ServerDataListenerImplTest extends TestCase {

    public ServerDataListenerImplTest(String testName) {
	super(testName);
    }

    public static Test suite() {
	TestSuite suite = new TestSuite(ServerDataListenerImplTest.class);
	return suite;
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    /**
     * Test of addConnection method, of class ServerDataListenerImpl.
     */
    public void testAddConnection() throws Exception {
	System.out.println("addConnection");
	ConnectionData data = new ConnectionData();
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.addConnection(data);
	assertTrue(container.getConnectionDataList().contains(data));
    }

    /**
     * Test of removeConnection method, of class ServerDataListenerImpl.
     */
    public void testRemoveConnection() throws Exception {
	System.out.println("removeConnection");
	ConnectionData data = new ConnectionData();
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.addConnection(data);
	assertTrue(container.getConnectionDataList().contains(data));
	instance.removeConnection(data);
	assertFalse(container.getConnectionDataList().contains(data));
    }

    /**
     * Test of updateConnection method, of class ServerDataListenerImpl.
     */
    public void testUpdateConnection() throws Exception {
	System.out.println("updateConnection");
	ConnectionData data = new ConnectionData();
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	data.setUserName("a");
	instance.addConnection(data);
	assertEquals(container.getConnectionDataList().get(0).getUserName(), "a");
	data.setUserName("b");
	instance.updateConnection(data);
	assertEquals(container.getConnectionDataList().get(0).getUserName(), "b");
    }

    /**
     * Test of setAllConections method, of class ServerDataListenerImpl.
     */
    public void testSetAllConections() throws Exception {
	System.out.println("setAllConections");
	List<ConnectionData> dataList = new LinkedList<ConnectionData>();
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.setAllConections(dataList);
	assertEquals(dataList, container.getConnectionDataList());
    }

    /**
     * Test of addFileData method, of class ServerDataListenerImpl.
     */
    public void testAddFileData() throws Exception {
	System.out.println("addFileData");
	FileData data = new FileData("", "", "", FileType.UPLOADED);
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.addFileData(data);
	assertTrue(container.getFileDataList(FileType.UPLOADED).contains(data));
    }

    /**
     * Test of setServerStat method, of class ServerDataListenerImpl.
     */
    public void testSetServerStat() throws Exception {
	System.out.println("setServerStat");
	StatisticsData data1 = new StatisticsData();
	StatisticsData data2 = new StatisticsData();
	StatisticsData data3 = new StatisticsData();
	data1.setStatID(2);
	data2.setStatID(1);
	data3.setStatID(2);
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	
	instance.setServerStat(data1);
	List<StatisticsData> result = container.getServerStats();
	assertEquals(1, result.size());
	assertTrue(result.contains(data1));
	
	instance.setServerStat(data2);
	result = container.getServerStats();
	assertEquals(2, result.size());
	assertEquals(data2, result.get(0));
	assertEquals(data1, result.get(1));
	
	instance.setServerStat(data3);
	result = container.getServerStats();
	assertEquals(2, result.size());
	assertEquals(data2, result.get(0));
	assertEquals(data3, result.get(1));
    }

    /**
     * Test of addLogData method, of class ServerDataListenerImpl.
     */
    public void testAddLogData() throws Exception {
	System.out.println("addLogData");
	LogData data = new LogData();
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.addLogData(data);
	assertTrue(container.getLogData().contains(data));
    }

    /**
     * Test of addSpyUserData method, of class ServerDataListenerImpl.
     */
    public void notestAddSpyUserData() throws Exception {
	System.out.println("addSpyUserData");
	SpyUserData data = new SpyUserData(1L);
	Server server = new Server();
	ServerDataContainer container = new ServerDataContainerImpl();
	ServerDataListenerImpl instance = new ServerDataListenerImpl(server);
	instance.setServerDataContainer(container);
	instance.addSpyUserData(data);
	assertTrue(container.getSpyUserData(1L).contains(data));
    }
}
