package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.net.InetAddress;
import java.rmi.RemoteException;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.interfaces.ServerFtpStatistics;
import org.apache.ftpserver.interfaces.StatisticsObserver;
import org.apache.ftpserver.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * Server statistics listener. This object is informed when statistics event
 * happened. Each method gahter informations about diffrent statistics type and
 * send data to data listener.
 * @author Tomasz Jadczyk
 */
public class ServerSideStatisticsObserver implements StatisticsObserver {

    private ServerDataListener dataListener;
    private FtpServer server;
    private final Logger logger = LoggerFactory.getLogger(ServerSideStatisticsObserver.class);

    public ServerSideStatisticsObserver(FtpServer server, ServerDataListener dataListener) {
        this.dataListener = dataListener;
        this.server = server;
        //info about server start
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        notifyDataListener(ServerStatisticsInfo.I_START_TIME, DateUtils.getISO8601Date(stat.getStartTime().getTime()));
        notifyMkdir();
        notifyRmdir();
        notifyUpload();
        notifyDownload();
        notifyDelete();
        notifyLogin(true);
        notifyLoginFail(null);
        notifyOpenConnection();
        
    }

    public void notifyUpload() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {

            int totalUpload = stat.getTotalUploadNumber();
            notifyDataListener(ServerStatisticsInfo.I_FILE_UPLOAD, String.valueOf(totalUpload));

            long totalUploadSz = stat.getTotalUploadSize();
            notifyDataListener(ServerStatisticsInfo.I_UPLOAD_BYTES, String.valueOf(totalUploadSz));

        }
    }

    public void notifyDownload() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int totalDownload = stat.getTotalDownloadNumber();
            notifyDataListener(ServerStatisticsInfo.I_FILE_DOWNLOAD, String.valueOf(totalDownload));

            long totalDownloadSz = stat.getTotalDownloadSize();
            notifyDataListener(ServerStatisticsInfo.I_DOWNLOAD_BYTES, String.valueOf(totalDownloadSz));
        }
    }

    public void notifyDelete() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int totalDelete = stat.getTotalDeleteNumber();
            notifyDataListener(ServerStatisticsInfo.I_FILE_REMOVED, String.valueOf(totalDelete));
        }
    }

    public void notifyMkdir() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int totalMkdir = stat.getTotalDirectoryCreated();
            notifyDataListener(ServerStatisticsInfo.I_DIR_CREATED, String.valueOf(totalMkdir));
        }
    }

    public void notifyRmdir() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int totalRmdir = stat.getTotalDirectoryRemoved();
            notifyDataListener(ServerStatisticsInfo.I_DIR_REMOVED, String.valueOf(totalRmdir));
        }
    }

    public void notifyLogin(boolean anonymous) {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int loginNbr = stat.getCurrentLoginNumber();
            notifyDataListener(ServerStatisticsInfo.I_CURR_LOGINS, String.valueOf(loginNbr));

            int totalLoginNbr = stat.getTotalLoginNumber();
            notifyDataListener(ServerStatisticsInfo.I_TOTAL_LOGINS, String.valueOf(totalLoginNbr));

            if (anonymous) {
                int anonLoginNbr = stat.getCurrentAnonymousLoginNumber();
                notifyDataListener(ServerStatisticsInfo.I_CURR_ANON_LOGINS, String.valueOf(anonLoginNbr));

                int totalAnonLoginNbr = stat.getTotalAnonymousLoginNumber();
                notifyDataListener(ServerStatisticsInfo.I_TOTAL_ANON_LOGINS, String.valueOf(totalAnonLoginNbr));
            }
        }
    }

    public void notifyLoginFail(InetAddress address) {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int failedNbr = stat.getTotalFailedLoginNumber();
            notifyDataListener(ServerStatisticsInfo.I_TOTAL_LOGIN_FAILS, String.valueOf(failedNbr));
        }
    }

    public void notifyLogout(boolean anonymous) {
        notifyLogin(anonymous);
    }

    public void notifyOpenConnection() {
        ServerFtpStatistics stat = (ServerFtpStatistics) server.getServerContext().getFtpStatistics();
        if (stat != null) {
            int currCon = stat.getCurrentConnectionNumber();
            notifyDataListener(ServerStatisticsInfo.I_CURR_CONS, String.valueOf(currCon));

            int totalCon = stat.getTotalConnectionNumber();
            notifyDataListener(ServerStatisticsInfo.I_TOTAL_CONS, String.valueOf(totalCon));
        }
    }

    public void notifyCloseConnection() {
        notifyOpenConnection();
    }

    /**
     * Create statistics data object and send it to remote data listener.
     * @param statIndex Statistics name index
     * @param value Statistics value
     */
    private void notifyDataListener(int statIndex, String value) {
        try {
            StatisticsData data = new StatisticsData();
            data.setStatID(statIndex);
            data.setStatName(getStatName(statIndex));
            data.setStatValue(value);
            dataListener.setServerStat(data);
        } catch (RemoteException e) {
            logger.error("Statistics change notification.", e);
        }
    }

    private String getStatName(int statIndex) {
        return ServerStatisticsInfo.STAT_NAMES[statIndex];
    }
}
