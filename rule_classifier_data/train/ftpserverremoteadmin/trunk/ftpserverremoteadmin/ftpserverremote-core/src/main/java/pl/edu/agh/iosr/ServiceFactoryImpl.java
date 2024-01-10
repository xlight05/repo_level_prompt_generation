package pl.edu.agh.iosr;

import pl.edu.agh.iosr.ftpserverremote.command.*;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.iosr.ftpserverremote.data.Admin;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;
import pl.edu.agh.iosr.ftpserverremote.data.Message;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.User;
import pl.edu.agh.iosr.ftpserverremote.manager.DataManager;

/**
 * Singleton implementation of ServiceFactory interface
 * 
 * @author Agnieszka Janowska
 */
public class ServiceFactoryImpl implements ServiceFactory {
    
    private static ServiceFactoryImpl instance;
    
    private Map<String, DataService> dataServices;
    private DataManager dataManager;

    private ServiceFactoryImpl() {
	dataServices = new HashMap<String, DataService>();
	dataManager = new DataManager();
	initialize();
    }
    
    /**
     * Initialization method - fills map with available DataServices
     */
    public void initialize() {
	dataServices.put(User.class.getName(), new DataServiceImpl(dataManager.getUserDao()));
	dataServices.put(IPRestriction.class.getName(), new DataServiceImpl(dataManager.getIpRestrictionDao()));
	dataServices.put(Message.class.getName(), new DataServiceImpl(dataManager.getMessageDao()));
	dataServices.put(Admin.class.getName(), new DataServiceImpl(dataManager.getAdminDao()));
	dataServices.put(Server.class.getName(), new DataServiceImpl(dataManager.getServerDao()));
    }

    /**
     * Returns DataService for given as a parameter class
     * @param dataServiceId name of class for which DataService is to be applied
     * @return DataService for requested class
     * @throws pl.edu.agh.iosr.IdNotRecognizedException
     */
    public DataService getDataService(String dataServiceId) throws IdNotRecognizedException {
	DataService dataService = dataServices.get(dataServiceId);
	if (dataService == null) {
	    throw new IdNotRecognizedException("Could not find DataService for given dataServiceId (" + dataServiceId + ")");
	}
	return dataService;
    }
    
    /**
     * Returns instance of this class
     * @return instance
     */
    public static synchronized ServiceFactoryImpl getInstance() {
	if (instance == null) {
	    instance = new ServiceFactoryImpl();
	} 
	return instance;
    }
}
