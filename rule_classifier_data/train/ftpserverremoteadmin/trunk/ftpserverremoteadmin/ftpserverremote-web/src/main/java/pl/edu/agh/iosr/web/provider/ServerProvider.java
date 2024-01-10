/*
 * ServerProvider.java
 *
 * Created on 2008-01-04, 17:22:15
 *
 */

package pl.edu.agh.iosr.web.provider;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 *
 * Class providing list of servers in format demanded by the view
 *
 * @author Monika Nawrot
 */
public class ServerProvider {

    /**
     * Method returning list of all servers (defined in the database
     * and found using rmi registry
     *
     * @return list of servers as a tree
     */
    public static DefaultMutableTreeNode getNodeServers() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode();
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Server.class.getName());
            List<Entity> serverList = dataService.getAll();

            for (Entity e : serverList) {
                top.add(new DefaultMutableTreeNode((Server) e));
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ServerProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(ServerProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return top;
    }

    /**
     * Method returning selectable array of servers available
     * in current context
     *
     */
    public static SelectItem[] getCurrentContextServers() {
        List<Server> servers = DefaultContext.getInstance().getServers();
        SelectItem[] serverLabels = new SelectItem[servers.size()];

        int i = 0;
        for (Server srv : servers) {
            serverLabels[i++] = new SelectItem(srv.getFullName());
        }

        return serverLabels;
    }

    /**
     * Method returning selectable array of servers available
     * in current context with "All" option
     *
     */
    public static SelectItem[] getCurrentContextServersWithAll() {
        List<Server> servers = DefaultContext.getInstance().getServers();
        SelectItem[] serverLabels = new SelectItem[servers.size() + 1];

        serverLabels[0] = new SelectItem("All");

        int i = 1;
        for (Server srv : servers) {
            serverLabels[i++] = new SelectItem(srv.getFullName());
        }

        return serverLabels;
    }
}