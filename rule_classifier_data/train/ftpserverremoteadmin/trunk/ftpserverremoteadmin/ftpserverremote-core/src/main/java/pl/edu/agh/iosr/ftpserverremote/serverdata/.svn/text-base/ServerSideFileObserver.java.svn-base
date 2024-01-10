
package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.RemoteException;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FileObject;
import org.apache.ftpserver.interfaces.FileObserver;
import org.apache.ftpserver.listener.Connection;
import org.apache.ftpserver.util.DateUtils;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;

/**
 * Class gather information about files on ftp server
 * This informations are send to data listener on web-side.
 * @author Tomasz Jadczyk
 */
public class ServerSideFileObserver implements FileObserver{

    /**
     * Data receiver on web
     */
    private ServerDataListener dataListener;
    private FtpServer server;
    private final Logger logger = LoggerFactory.getLogger(ServerSideFileObserver.class);
    
    public ServerSideFileObserver(FtpServer server, ServerDataListener dataListener) {
        this.dataListener = dataListener;
        this.server = server;                               
    }
 
    /**
     * Information about new uploaded file
     * @param connection
     * @param file
     * @param size
     */
    public void notifyUpload(Connection connection, FileObject file, long size) {
        try {
            dataListener.addFileData(
                    createFileData(connection, file, FileType.UPLOADED));
        } catch (RemoteException ex) {
            logger.error("ServerSideConnectionObserver.notifyUpload()", ex);
        }
    }

    /**
     * Information about downloaded file
     * @param connection
     * @param file
     * @param size
     */
    public void notifyDownload(Connection connection, FileObject file, long size) {
       try {
            dataListener.addFileData(
                    createFileData(connection, file, FileType.DOWNLOADED));
        } catch (RemoteException ex) {
            logger.error("ServerSideConnectionObserver.notifyDownload()", ex);
        }
    }

    /**
     * Information about deleted file
     * @param connection
     * @param file
     */
    public void notifyDelete(Connection connection, FileObject file) {
        try {
            dataListener.addFileData(
                    createFileData(connection, file, FileType.REMOVED));
        } catch (RemoteException ex) {
            logger.error("ServerSideConnectionObserver.notifyDelete()", ex);
        }
    }

    /**
     * Information about newly created directory
     * @param connection
     * @param file
     */
    public void notifyMkdir(Connection connection, FileObject file) {
        try {
            dataListener.addFileData(
                    createFileData(connection, file, FileType.DIRCREATED));
        } catch (RemoteException ex) {
            logger.error("ServerSideConnectionObserver.notifyUpload()", ex);
        }
    }

    /**
     * Information about deleted directory
     * @param connection
     * @param file
     */
    public void notifyRmdir(Connection connection, FileObject file) {
        try {
            dataListener.addFileData(
                    createFileData(connection, file, FileType.DIRREMOVED));
        } catch (RemoteException ex) {
            logger.error("ServerSideConnectionObserver.notifyUpload()", ex);
        }
    }
    
    /**
     * Create printable information about file for web interface
     * @param connection
     * @param file
     * @param fileType
     * @return FileData contains information about file
     */
    private FileData createFileData(Connection connection, FileObject file, FileType fileType) {
        return new FileData(file.getFullName(), connection.getSession().getUser().getName(), 
                 DateUtils.getISO8601Date(System.currentTimeMillis()), fileType);
    }

}
