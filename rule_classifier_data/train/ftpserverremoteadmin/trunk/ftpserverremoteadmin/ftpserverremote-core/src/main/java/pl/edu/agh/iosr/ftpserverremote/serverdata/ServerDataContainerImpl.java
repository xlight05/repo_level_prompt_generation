package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * ServerDataContainer implementation, placed on the web side. It gathers information comming from a FTP Server.
 * 
 * @author Tomasz Jadczyk
 */
public class ServerDataContainerImpl implements ServerDataContainer {
    
    private List<ConnectionData> connectionsDataList;
    private List<FileData> uploadedFile;
    private List<FileData> downloadedFile;
    private List<FileData> removedFile;
    private List<FileData> createdDir;
    private List<FileData> removedDir;
    private List<LogData> logs;
    private Map<Long, List<SpyUserData>> spyData;
    private List<StatisticsData> statsData;

    public ServerDataContainerImpl() {
        connectionsDataList = new LinkedList<ConnectionData>();
        uploadedFile = new LinkedList<FileData>();
        downloadedFile = new LinkedList<FileData>();
        removedFile = new LinkedList<FileData>();
        createdDir = new LinkedList<FileData>();
        removedDir = new LinkedList<FileData>();
        statsData = new ArrayList<StatisticsData>();
        logs = new LinkedList<LogData>();
        spyData = new HashMap<Long, List<SpyUserData>>();
    }
    
    public List<ConnectionData> getConnectionDataList() {
        return connectionsDataList;
    }

    public void setConnectionDataList(List<ConnectionData> list) {
        this.connectionsDataList = list;
    }
    
    public void addConnectionData(ConnectionData data) {
        connectionsDataList.add(data);
    }

    public List<FileData> getFileDataList(FileType fileType) {
        return selectList(fileType);
    }

    public void addFileData(FileData data) {
        selectList(data.getFileType()).add(data);
    }

    private List<FileData> selectList(FileType fileType) {
        List<FileData> returnList = null;
        switch(fileType) {
            case DIRCREATED:
                returnList = createdDir;
                break;
            case DIRREMOVED:
                returnList = removedDir;
                break;
            case DOWNLOADED:
                returnList = downloadedFile;
                break;
            case REMOVED:
                returnList = removedFile;
                break;
            case UPLOADED:
                returnList = uploadedFile;
                break;  
        }
        return returnList;
    }

    //TODO: stats list can be initialized with objects like EmptyStats (initialized with zeros) to prevent web interface from displaying no stats in case there were no stats info from server yet ; instead it would display stats filled with zeros for given server
    public void setServerStat(StatisticsData data) {
	for (int i = 0; i < statsData.size(); i++) {
	    if (statsData.get(i).getStatID() > data.getStatID()) {
		statsData.add(i, data);
		return;
	    } else if (statsData.get(i).getStatID() == data.getStatID()) {
		statsData.set(i, data);
		return;
	    }
	}
	statsData.add(data);
    }

    public List<StatisticsData> getServerStats() {
        return statsData;
    }

    public void addLogData(LogData data) {
        logs.add(data);
    }

    public List<LogData> getLogData() {
        return logs;
    }

    public void clearLogs() {
        logs.clear();
    }

    public void addSpyUserData(SpyUserData data) {
        List<SpyUserData> dataList = spyData.get(data.getConnectionDataID());
        if(dataList == null) {
            dataList = new LinkedList<SpyUserData>();
        }
        dataList.add(data);
        spyData.put(data.getConnectionDataID(), dataList);
    }

    public List<SpyUserData> getSpyUserData(Long connectionDataID) {
        List<SpyUserData> dataList = spyData.get(connectionDataID);
        if(dataList == null) {
            dataList = new LinkedList<SpyUserData>();
            spyData.put(connectionDataID, dataList);
        }
        
        return dataList;
    }

    public void clearSpyUserData(Long connectionDataID) {
        spyData.remove(connectionDataID);
    }

   
}
