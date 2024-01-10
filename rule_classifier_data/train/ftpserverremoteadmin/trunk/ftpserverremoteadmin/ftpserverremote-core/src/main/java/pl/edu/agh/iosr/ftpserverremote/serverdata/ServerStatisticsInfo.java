package pl.edu.agh.iosr.ftpserverremote.serverdata;

/**
 * Class with static final fields containg stat types' id and names.
 * 
 * @author Agnieszka Janowska
 */
public class ServerStatisticsInfo {
    
    public final static int I_START_TIME = 0;
    public final static int I_DIR_CREATED = 1;
    public final static int I_DIR_REMOVED = 2;
    public final static int I_FILE_UPLOAD = 3;
    public final static int I_FILE_DOWNLOAD = 4;
    public final static int I_FILE_REMOVED = 5;
    public final static int I_UPLOAD_BYTES = 6;
    public final static int I_DOWNLOAD_BYTES = 7;
    public final static int I_CURR_LOGINS = 8;
    public final static int I_TOTAL_LOGINS = 9;
    public final static int I_CURR_ANON_LOGINS = 10;
    public final static int I_TOTAL_ANON_LOGINS = 11;
    public final static int I_TOTAL_LOGIN_FAILS = 12;
    public final static int I_CURR_CONS = 13;
    public final static int I_TOTAL_CONS = 14;
    public final static String[] STAT_NAMES = {
        "Server start time",
        "Number of directories created",
        "Number of directories removed",
        "Number of files uploaded",
        "Number of files downloaded",
        "Number of files deleted",
        "Uploaded bytes",
        "Downloaded bytes",
        "Current logins",
        "Total logins",
        "Current anonymous logins",
        "Total anonymous logins",
        "Total failed logins",
        "Current connections",
        "Total connections"
    };

}
