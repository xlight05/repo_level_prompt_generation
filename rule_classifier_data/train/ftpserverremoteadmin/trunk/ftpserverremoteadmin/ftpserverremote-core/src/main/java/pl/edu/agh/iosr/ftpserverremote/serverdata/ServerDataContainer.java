
package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * Container for all the information gathered while server is running
 * 
 * @author Tomasz Jadczyk
 */
public interface ServerDataContainer {

    public List<ConnectionData> getConnectionDataList();
    
    public void setConnectionDataList(List<ConnectionData> list);
        
    public void addConnectionData(ConnectionData data);
    
    public List<FileData> getFileDataList(FileType fileType);
    
    public void addFileData(FileData data);
    
    public void setServerStat(StatisticsData data);
    
    public List<StatisticsData> getServerStats();
    
    public void addLogData(LogData data);
    
    public List<LogData> getLogData();
    
    public void clearLogs();
    
    public void addSpyUserData(SpyUserData data);
    
    public List<SpyUserData> getSpyUserData(Long connectionDataID);
    
    public void clearSpyUserData(Long connectionDataID);
}
