package pl.edu.agh.iosr;

import pl.edu.agh.iosr.ftpserverremote.command.DataService;

/**
 * Interface for DataService factory creating DataServices depending on dataServiceId
 * 
 * @author Agnieszka Janowska
 */
public interface ServiceFactory {
    
    /**
     * Returns DataService for given as a parameter class
     * @param dataServiceId id of class for which DataService is to be applied
     * @return DataService for requested class
     * @throws pl.edu.agh.iosr.IdNotRecognizedException
     */
    public DataService getDataService(String dataServiceId) throws IdNotRecognizedException;
    
}
