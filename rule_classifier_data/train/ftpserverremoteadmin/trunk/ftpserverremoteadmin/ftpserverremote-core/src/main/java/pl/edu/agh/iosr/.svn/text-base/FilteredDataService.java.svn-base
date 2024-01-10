package pl.edu.agh.iosr;

import java.util.LinkedList;
import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 *
 * Class returning list of entities filtered by the given context
 * 
 * @author Monika Nawrot
 */
public class FilteredDataService {

    public static List<Entity> getData(String dataServiceId, Context context) throws IdNotRecognizedException, DataAccessException {
        List<Entity> result = new LinkedList<Entity>();

        ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
        DataService dataService = serviceFactory.getDataService(dataServiceId);
        List<Entity> dataList = dataService.getAll();

        List<Server> contextServers = context.getServers();

        for (Entity entity : dataList) {
            for (Entity server : contextServers) {
                server = (Server) server;
                if (entity.getServerId().compareTo(server.getServerId()) == 0) {
                    result.add(entity);
                }
            }
            
            if (entity.getServerId().compareTo(new Long(0)) == 0)
                result.add(entity);
        }
        return result;
    }
}