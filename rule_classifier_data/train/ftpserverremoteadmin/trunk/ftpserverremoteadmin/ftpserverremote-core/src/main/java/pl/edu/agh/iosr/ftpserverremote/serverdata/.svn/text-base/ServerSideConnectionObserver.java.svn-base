package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.RemoteException;
import org.apache.ftpserver.listener.Connection;
import org.apache.ftpserver.listener.ConnectionObserver;
import org.apache.ftpserver.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;

/**
 * Class implements ConnectionObserver interface from FtpServer.
 * It provides method to send informations about users actions
 * to Web Interface
 * @author Tomasz Jadczyk
 */
public class ServerSideConnectionObserver implements ConnectionObserver {

    private Connection connection;
    private Long connectionDataID;
    private ServerDataListener dataListener;
    private ServerSideConnectionManager manager;
    private final Logger logger = LoggerFactory.getLogger(ServerSideConnectionObserver.class);

    public ServerSideConnectionObserver(Connection connection,
	    ServerDataListener dataListener, ServerSideConnectionManager manager, Long connectionDataID) {
	this.connection = connection;
	this.dataListener = dataListener;
	this.manager = manager;
	this.connectionDataID = connectionDataID;
    }

    /**
     * Sends formated information to Web interface
     * @param msg Information to send
     */
    public void request(String msg) {
	SpyUserData data = new SpyUserData(connectionDataID);
	data.setDate(DateUtils.getISO8601Date(System.currentTimeMillis()));
	data.setMessage(msg);
	data.setId(connectionDataID);
	try {
	    dataListener.addSpyUserData(data);
	} catch (RemoteException ex) {
	    logger.error(ServerSideConnectionObserver.class.getName() +
		    " Cannot send data to DataListener", ex);
	}

    }

    /**
     * Same behavior as request method, only for keeping compatibility with
     * ConnectionObserver interface.
     * @param msg
     */
    public void response(String msg) {
	request(msg);
    }
}
