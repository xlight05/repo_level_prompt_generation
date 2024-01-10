/*
 * ServerHelper.java
 *
 * Created on 2007-12-18, 17:05:18
 *
 */

package pl.edu.agh.iosr.ftpserverremote.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 *
 * Converting server information between id and name
 * 
 * @author Monika Nawrot
 */
public class ServerHelper {

    /**
     * Mehtod returning server ID by it's full name (host:port)
     * 
     */
    public static Long getIdByName(String name) {
        if (name.equals("All")) {
            return new Long(0);
        }
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Server.class.getName());
            List<Entity> serverList = dataService.getAll();

            String[] sParam = name.split(":");

            for (Entity e : serverList) {
                if (((Server) e).getHostname().equals(sParam[0])) {
                    if (sParam.length == 1) {
                        return ((Server) e).getId();
                    } else if (Integer.parseInt(sParam[1]) == ((Server) e).getPort().intValue()) {
                        return ((Server) e).getId();
                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    /**
     * Mehtod returning server name by it's ID
     * 
     */
    public static String getNameById(Long id) {
        if (id.compareTo(new Long(0)) == 0) {
            return "All";
        }
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Server.class.getName());
            List<Entity> serverList = dataService.getAll();

            for (Entity e : serverList) {
                if (((Server) e).getServerId().equals(id)) {
                    return ((Server) e).getFullName();
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}