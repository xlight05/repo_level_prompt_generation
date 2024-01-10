
package pl.edu.agh.iosr.ftpserverremote.serverdata;

/**
 * Interface to associate data listener with corresponding data container
 * 
 * @author Tomasz Jadczyk
 */
public interface IServerDataListenerConfigurable {

    public void setServerDataContainer(ServerDataContainer dataContainer);
    
}
