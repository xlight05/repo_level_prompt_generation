
package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.iosr.Context;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * Class provides static methods to access to data gathered from remote facades.
 * @author Tomasz Jadczyk
 */
public class ServerDataProvider {
    
    private static Context context = DefaultContext.getInstance();
    
    public static void setContext(Context newContext) {
	context = newContext;
    }

    /**
     * Get informations about connections from all servers in current context.
     * @return Connections list
     */
    public static List<ConnectionData> getConnectionDataList() {
        List<ConnectionData> list = new LinkedList<ConnectionData>();
        for(Server server : context.getServers()) {
            list.addAll(context.getServerDataContainer(server).
                    getConnectionDataList());
        }
        return list;
    }
    
    /**
     * Get informations about selected file type from all servers.
     * @param fileType selected file type
     * @return Files list
     */
    public static List<FileData> getFileList(FileType fileType) {
        List<FileData> list = new LinkedList<FileData>();
        for(Server server : context.getServers()) {
            list.addAll(context.getServerDataContainer(server).
                    getFileDataList(fileType));
        }
        return list;
    }

    /**
     * Get statistics for selected server
     * @param server
     * @return Statistics list
     */
    public static List<StatisticsData> getServerStats(Server server) {
        return context.getServerDataContainer(server).getServerStats();
    }

    /**
     * Get logs from selected server
     * @param server
     * @return Logs list
     */
    public static List<LogData> getServerLogs(Server server) {
        return context.getServerDataContainer(server).getLogData();
    }
    
    /**
     * Remove all logs informations for selected server
     * @param server
     */
    public static void clearLogs(Server server) {
        context.getServerDataContainer(server).clearLogs();
    }
    
    /**
     * Get informations about spied connection
     * @param server
     * @param data
     * @return Spy informations list
     */
    public static List<SpyUserData> getSpyUserData(Server server, ConnectionData data) {
        return context.getServerDataContainer(server).getSpyUserData(data.getId());
    }
    
    /**
     * Remove all informations about selected connection
     * @param server
     * @param data
     */
    public static void clearSpyUserData(Server server, ConnectionData data) {
        context.getServerDataContainer(server).clearSpyUserData(data.getId());
    }
    
}
