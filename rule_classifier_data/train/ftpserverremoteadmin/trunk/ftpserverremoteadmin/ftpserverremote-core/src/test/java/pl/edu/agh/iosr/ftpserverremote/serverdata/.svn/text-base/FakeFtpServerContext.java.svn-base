package pl.edu.agh.iosr.ftpserverremote.serverdata;

import org.apache.ftpserver.ftplet.FileSystemManager;
import org.apache.ftpserver.ftplet.FtpStatistics;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.interfaces.CommandFactory;
import org.apache.ftpserver.interfaces.FtpServerContext;
import org.apache.ftpserver.interfaces.IpRestrictor;
import org.apache.ftpserver.interfaces.MessageResource;
import org.apache.ftpserver.listener.ConnectionManager;
import org.apache.ftpserver.listener.Listener;

/**
 * Fake FtpServerContext for test purposes
 * @author Agnieszka Janowska
 */

public class FakeFtpServerContext implements FtpServerContext {
    
    private FtpStatistics ftpStatistics = new FakeFtpStatistics();

    public ConnectionManager getConnectionManager() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public MessageResource getMessageResource() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public IpRestrictor getIpRestrictor() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public Ftplet getFtpletContainer() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public Listener getListener(String name) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public Listener[] getListeners() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public CommandFactory getCommandFactory() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dispose() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public UserManager getUserManager() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public FileSystemManager getFileSystemManager() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public FtpStatistics getFtpStatistics() {
	return ftpStatistics;
    }

    public Ftplet getFtplet(String name) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

}
