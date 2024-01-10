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
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 *
 * @author Agnieszka Janowska
 */
public class ServerDataContainerImplTest extends TestCase {
    
    public ServerDataContainerImplTest(String testName) {
        super(testName);
    }

    public static Test suite() {
	TestSuite suite = new TestSuite(ServerDataContainerImplTest.class);
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
     * Test of getConnectionDataList method, of class ServerDataContainerImpl.
     */
    public void testGetConnectionDataList() {
	System.out.println("getConnectionDataList");
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	List<ConnectionData> result = instance.getConnectionDataList();
	assertNotNull(result);
	assertTrue(result.isEmpty());
    }

    /**
     * Test of setConnectionDataList method, of class ServerDataContainerImpl.
     */
    public void testSetConnectionDataList() {
	System.out.println("setConnectionDataList");
	List<ConnectionData> list = new LinkedList<ConnectionData>();
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.setConnectionDataList(list);
	List<ConnectionData> result = instance.getConnectionDataList();
	assertEquals(list, result);
    }

    /**
     * Test of addConnectionData method, of class ServerDataContainerImpl.
     */
    public void testAddConnectionData() {
	System.out.println("addConnectionData");
	ConnectionData data1 = new ConnectionData();
	ConnectionData data2 = new ConnectionData();
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.addConnectionData(data1);
	instance.addConnectionData(data2);
	List<ConnectionData> result = instance.getConnectionDataList();
	assertEquals(result.size(), 2);
	assertTrue(result.contains(data2));
	assertTrue(result.contains(data1));
    }

    /**
     * Test of getFileDataList method, of class ServerDataContainerImpl.
     */
    public void testGetFileDataList() {
	System.out.println("getFileDataList");
	FileType fileType = FileType.UPLOADED;
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	List<FileData> result = instance.getFileDataList(fileType);
	assertNotNull(result);
	assertTrue(result.isEmpty());
    }

    /**
     * Test of addFileData method, of class ServerDataContainerImpl.
     */
    public void testAddFileData() {
	System.out.println("addFileData");
	FileData data = new FileData("", "", "", FileType.UPLOADED);
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.addFileData(data);
	List<FileData> result = instance.getFileDataList(FileType.UPLOADED);
	assertEquals(result.size(), 1);
	assertTrue(result.contains(data));
    }
    
    /**
     * Test of getServerStats method, of class ServerDataContainerImpl.
     */
    public void testGetServerStats() {
	System.out.println("getServerStats");
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	List<StatisticsData> result = instance.getServerStats();
	assertNotNull(result);
	for (int i = 0; i < result.size(); i++) {
	    assertNull(result.get(i));
	}
    }

    /**
     * Test of setServerStat method, of class ServerDataContainerImpl.
     */
    public void testSetServerStat() {
	System.out.println("setServerStat");
	
	StatisticsData data1 = new StatisticsData();
	StatisticsData data2 = new StatisticsData();
	StatisticsData data3 = new StatisticsData();
	data1.setStatID(2);
	data2.setStatID(1);
	data3.setStatID(2);
	ServerDataContainer container = new ServerDataContainerImpl();
	
	container.setServerStat(data1);
	List<StatisticsData> result = container.getServerStats();
	assertEquals(1, result.size());
	assertTrue(result.contains(data1));
	
	container.setServerStat(data2);
	result = container.getServerStats();
	assertEquals(2, result.size());
	assertEquals(data2, result.get(0));
	assertEquals(data1, result.get(1));
	
	container.setServerStat(data3);
	result = container.getServerStats();
	assertEquals(2, result.size());
	assertEquals(data2, result.get(0));
	assertEquals(data3, result.get(1));
    }
    
    /**
     * Test of getLogData method, of class ServerDataContainerImpl.
     */
    public void testGetLogData() {
	System.out.println("getLogData");
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	List<LogData> result = instance.getLogData();
	assertNotNull(result);
	assertTrue(result.isEmpty());
    }

    /**
     * Test of addLogData method, of class ServerDataContainerImpl.
     */
    public void testAddLogData() {
	System.out.println("addLogData");
	LogData data1 = new LogData();
	LogData data2 = new LogData();
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.addLogData(data1);
	instance.addLogData(data2);
	List<LogData> result = instance.getLogData();
	assertEquals(result.size(), 2);
	assertTrue(result.contains(data1));
	assertTrue(result.contains(data2));
    }

    /**
     * Test of clearLogs method, of class ServerDataContainerImpl.
     */
    public void testClearLogs() {
	System.out.println("clearLogs");
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	LogData data = new LogData();
	instance.addLogData(data);
	assertFalse(instance.getLogData().isEmpty());
	instance.clearLogs();
	assertTrue(instance.getLogData().isEmpty());
    }
    
    /**
     * Test of getSpyUserData method, of class ServerDataContainerImpl.
     */
    public void testGetSpyUserData() {
	System.out.println("getSpyUserData");
	Long connectionDataID = 1L;
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	List<SpyUserData> result = instance.getSpyUserData(connectionDataID);
	assertNotNull(result);
	assertTrue(result.isEmpty());
    }

    /**
     * Test of addSpyUserData method, of class ServerDataContainerImpl.
     */
    public void testAddSpyUserData() {
	System.out.println("addSpyUserData");
	SpyUserData data1 = new SpyUserData(1L);
	SpyUserData data2 = new SpyUserData(1L);
	data1.setId(1L);
	data2.setId(2L);
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.addSpyUserData(data1);
	instance.addSpyUserData(data2);
	List<SpyUserData> result = instance.getSpyUserData(1L);
	assertEquals(result.size(), 2);
	assertTrue(result.contains(data1));
	assertTrue(result.contains(data2));
    }

    /**
     * Test of clearSpyUserData method, of class ServerDataContainerImpl.
     */
    public void testClearSpyUserData() {
	System.out.println("clearSpyUserData");
	SpyUserData data = new SpyUserData(1L);
	ServerDataContainerImpl instance = new ServerDataContainerImpl();
	instance.addSpyUserData(data);
	assertFalse(instance.getSpyUserData(1L).isEmpty());
	instance.clearSpyUserData(1L);
	assertTrue(instance.getSpyUserData(1L).isEmpty());
    }

}
