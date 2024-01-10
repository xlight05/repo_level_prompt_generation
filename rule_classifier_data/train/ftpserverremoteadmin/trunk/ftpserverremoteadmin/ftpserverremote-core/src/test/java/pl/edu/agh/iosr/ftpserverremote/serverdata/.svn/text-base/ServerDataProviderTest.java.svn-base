package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pl.edu.agh.iosr.Context;
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
public class ServerDataProviderTest extends TestCase {
    
    private Context context;

    public ServerDataProviderTest(String testName) {
	super(testName);
    }

    public static Test suite() {
	TestSuite suite = new TestSuite(ServerDataProviderTest.class);
	return suite;
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	context = FakeContext.getInstance();
	ServerDataProvider.setContext(context);
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
	context.dispose();
    }

    /**
     * Test of getConnectionDataList method, of class ServerDataProvider.
     */
    public void testGetConnectionDataList() {
	System.out.println("getConnectionDataList");
	Server server1 = new Server();
	Server server2 = new Server();
	context.addServer(server1);
	context.addServer(server2);
	ConnectionData data1 = new ConnectionData();
	ConnectionData data2 = new ConnectionData();
	System.out.println("Init ok");
	System.out.println("" + context);
	System.out.println("" + context.getServerDataContainer(server1));
	context.getServerDataContainer(server1).addConnectionData(data1);
	context.getServerDataContainer(server2).addConnectionData(data2);
	System.out.println("init 2 ok");
	List<ConnectionData> result = ServerDataProvider.getConnectionDataList();
	assertEquals(2, result.size());
	assertTrue(result.contains(data1));
	assertTrue(result.contains(data2));
    }

    /**
     * Test of getFileList method, of class ServerDataProvider.
     */
    public void testGetFileList() {
	System.out.println("getFileList");
	Server server1 = new Server();
	Server server2 = new Server();
	context.addServer(server1);
	context.addServer(server2);
	FileType fileType = FileType.DOWNLOADED;
	FileData data1 = new FileData("", "", "", fileType);
	FileData data2 = new FileData("", "", "", fileType);
	context.getServerDataContainer(server1).addFileData(data1);
	context.getServerDataContainer(server2).addFileData(data2);
	List<FileData> result = ServerDataProvider.getFileList(fileType);
	assertEquals(2, result.size());
	assertTrue(result.contains(data1));
	assertTrue(result.contains(data2));
    }

    /**
     * Test of getServerStats method, of class ServerDataProvider.
     */
    public void testGetServerStats() {
	System.out.println("getServerStats");
	Server server1 = new Server();
	Server server2 = new Server();
	context.addServer(server1);
	context.addServer(server2);
	StatisticsData data1 = new StatisticsData();
	StatisticsData data2 = new StatisticsData();
	data1.setStatID(1);
	data2.setStatID(2);
	context.getServerDataContainer(server1).setServerStat(data1);
	context.getServerDataContainer(server2).setServerStat(data2);
	List<StatisticsData> result1 = ServerDataProvider.getServerStats(server1);
	List<StatisticsData> result2 = ServerDataProvider.getServerStats(server2);
	assertEquals(1, result1.size());
	assertEquals(1, result2.size());
	assertTrue(result1.contains(data1));
	assertTrue(result2.contains(data2));
    }

    /**
     * Test of getServerLogs method, of class ServerDataProvider.
     */
    public void testGetServerLogs() {
	System.out.println("getServerLogs");
	Server server = new Server();
	context.addServer(server);
	LogData data1 = new LogData();
	LogData data2 = new LogData();
	context.getServerDataContainer(server).addLogData(data1);
	context.getServerDataContainer(server).addLogData(data2);
	List<LogData> result = ServerDataProvider.getServerLogs(server);
	assertEquals(2, result.size());
	assertTrue(result.contains(data1));
	assertTrue(result.contains(data2));
    }

    /**
     * Test of clearLogs method, of class ServerDataProvider.
     */
    public void testClearLogs() {
	System.out.println("clearLogs");
	Server server = new Server();
	context.addServer(server);
	LogData data1 = new LogData();
	LogData data2 = new LogData();
	context.getServerDataContainer(server).addLogData(data1);
	context.getServerDataContainer(server).addLogData(data2);
	assertEquals(2, ServerDataProvider.getServerLogs(server).size());
	ServerDataProvider.clearLogs(server);
	assertTrue(ServerDataProvider.getServerLogs(server).isEmpty());
    }

    /**
     * Test of getSpyUserData method, of class ServerDataProvider.
     */
    public void testGetSpyUserData() {
	System.out.println("getSpyUserData");
	Server server = new Server();
	context.addServer(server);
	ConnectionData conn1 = new ConnectionData();
	ConnectionData conn2 = new ConnectionData();
	conn1.setId(2L);
	conn2.setId(3L);
	SpyUserData data1 = new SpyUserData(2L);
	SpyUserData data2 = new SpyUserData(3L);
	context.getServerDataContainer(server).addSpyUserData(data1);
	context.getServerDataContainer(server).addSpyUserData(data2);
	List<SpyUserData> result1 = ServerDataProvider.getSpyUserData(server, conn1);
	List<SpyUserData> result2 = ServerDataProvider.getSpyUserData(server, conn2);
	assertEquals(1, result1.size());
	assertEquals(1, result2.size());
	assertTrue(result1.contains(data1));
	assertTrue(result2.contains(data2));
    }

    /**
     * Test of clearSpyUserData method, of class ServerDataProvider.
     */
    public void testClearSpyUserData() {
	System.out.println("clearSpyUserData");
	Server server = new Server();
	context.addServer(server);
	ConnectionData conn = new ConnectionData();
	conn.setId(2L);
	SpyUserData data1 = new SpyUserData(2L);
	SpyUserData data2 = new SpyUserData(2L);
	context.getServerDataContainer(server).addSpyUserData(data1);
	context.getServerDataContainer(server).addSpyUserData(data2);
	assertEquals(2, ServerDataProvider.getSpyUserData(server, conn).size());
	ServerDataProvider.clearSpyUserData(server, conn);
	assertTrue(ServerDataProvider.getSpyUserData(server, conn).isEmpty());
    }
}
