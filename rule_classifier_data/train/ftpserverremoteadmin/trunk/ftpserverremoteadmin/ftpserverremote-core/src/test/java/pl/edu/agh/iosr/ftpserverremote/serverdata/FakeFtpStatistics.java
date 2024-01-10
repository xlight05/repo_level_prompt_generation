package pl.edu.agh.iosr.ftpserverremote.serverdata;

import org.apache.ftpserver.FtpStatisticsImpl;
import org.apache.ftpserver.interfaces.FileObserver;
import org.apache.ftpserver.interfaces.StatisticsObserver;

/**
 * Fake extending FtpStatisticsImpl for test purposes
 * 
 * @author Agnieszka Janowska
 */
public class FakeFtpStatistics extends FtpStatisticsImpl {

    @Override
    public void setFileObserver(FileObserver observer) {
	super.setFileObserver(null);
    }

    @Override
    public void setObserver(StatisticsObserver observer) {
	super.setObserver(null);
    }

}
