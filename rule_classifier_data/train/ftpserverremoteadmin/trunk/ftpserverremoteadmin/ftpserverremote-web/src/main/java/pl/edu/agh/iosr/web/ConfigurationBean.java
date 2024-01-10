/*
 * ConfigurationBean.java
 *
 * Created on 2007-12-05, 12:23:29
 *
 */
package pl.edu.agh.iosr.web;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.apache.myfaces.tobago.model.TreeState;
import pl.edu.agh.iosr.CommandFactory;
import pl.edu.agh.iosr.CommandFactoryImpl;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.command.Command;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.web.provider.ServerProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;

/**
 *
 * Class handlig data in configuration tab
 *
 * @author Monika Nawrot
 */
public class ConfigurationBean {

    private Boolean startDisabled;
    private Boolean stopDisabled;
    private Boolean suspendDisabled;
    private String message;
    private ErrorPopup errorPopup;
    private TreeState treeState;
    private Set<DefaultMutableTreeNode> selection;
    private DefaultMutableTreeNode availableServers;
    private Boolean deletePopup;

    /**
     * Creates a new instance of ConfigurationBean
     */
    public ConfigurationBean() {
        startDisabled = false;
        stopDisabled = true;
        suspendDisabled = true;

        deletePopup = false;
        errorPopup = new ErrorPopup();

        initServerList();
    }

    private void initServerList() {
        treeState = new TreeState();
        availableServers = ServerProvider.getNodeServers();
        treeState.addExpandState(availableServers);
        selection = new HashSet<DefaultMutableTreeNode>();
    }

    /**
     * Start choosen servers
     *
     */
    public void start() {
        CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
        for (Server srv : DefaultContext.getInstance().getServers()) {
            try {
                Command cmd = commandFactory.getServerStartCommand(srv);
                cmd.execute();
            } catch (Exception e) {
                Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, e);
                errorPopup.showError("command_exec_failed_error_message");
            }
        }
    }

    /**
     * Stop choosen servers
     *
     */
    public void stop() {
        CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());

        for (Server srv : DefaultContext.getInstance().getServers()) {
            try {
                Command cmd = commandFactory.getServerStopCommand(srv);
                cmd.execute();
            } catch (Exception e) {
                Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, e);
                errorPopup.showError("command_exec_failed_error_message");
            }
        }
    }

    /**
     * Suspend choosen servers
     *
     */
    public void suspend() {
        CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());

        for (Server srv : DefaultContext.getInstance().getServers()) {
            try {
                Command cmd = commandFactory.getServerSuspendCommand(srv);
                cmd.execute();
            } catch (Exception e) {
                Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, e);
                errorPopup.showError("command_exec_failed_error_message");
            }
        }
    }

    /**
     * Manage choosen servers. Servers are added to the current context
     *
     */
    public void manage() {
        Set<DefaultMutableTreeNode> nodes = treeState.getSelection();
        List<Server> servers = new LinkedList<Server>();
        for (DefaultMutableTreeNode node : nodes) {
            servers.add((Server) node.getUserObject());
        }

        List<Server> workingServers = DefaultContext.getInstance().setAllServers(servers);
        StringBuffer messageBuilder = new StringBuffer();
        if (workingServers.size() != servers.size()) {

            errorPopup.showError("unable_to_connect_error_message");

            for (Server srv : servers) {
                if (!workingServers.contains(srv)) {
                    if (messageBuilder.length() != 0) {
                        messageBuilder.append(", ");
                    }
                    messageBuilder.append(srv.getFullName());
                }
            }
            setMessage(messageBuilder.toString());
            Set<DefaultMutableTreeNode> workingNodes = new HashSet<DefaultMutableTreeNode>();
            for (DefaultMutableTreeNode node : nodes) {
                if (workingServers.contains((Server) node.getUserObject())) {
                    workingNodes.add(node);
                }
            }
            treeState.setSelection(workingNodes);
            this.selection = workingNodes;
        } else {
            treeState.setSelection(nodes);
            this.selection = nodes;
        }
    }

    /**
     * Delete choosen servers. Servers will be permanently 
     * deleted from the data base. If selected servers are in
     * current context, they are removed before deletion.
     * 
     */
    public void delete() {

        closeDeletePopup();

        Set<DefaultMutableTreeNode> nodes = treeState.getSelection();
        for (DefaultMutableTreeNode node : nodes) {
            try {
                Server s = (Server) node.getUserObject();

                if (DefaultContext.getInstance().getServers().contains(s)) {
                    DefaultContext.getInstance().removeServer(s);
                }

                ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
                DataService dataService = serviceFactory.getDataService(Server.class.getName());
                dataService.delete(s);

                initServerList();
                
            } catch (IdNotRecognizedException ex) {
                Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
                errorPopup.showDataError();
            } catch (DataAccessException ex) {
                Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
                errorPopup.showDataError();
            }
        }
    }

    public void showDeletePopup() {
        this.setDeletePopup((Boolean) true);
    }
    
    public void closeDeletePopup() {
        this.setDeletePopup((Boolean) false);
    }

    public Boolean getStartDisabled() {
        return startDisabled;
    }

    public void setStartDisabled(Boolean startDisabled) {
        this.startDisabled = startDisabled;
    }

    public Boolean getStopDisabled() {
        return stopDisabled;
    }

    public void setStopDisabled(Boolean stopDisabled) {
        this.stopDisabled = stopDisabled;
    }

    public Boolean getSuspendDisabled() {
        return suspendDisabled;
    }

    public void setSuspendDisabled(Boolean suspendDisabled) {
        this.suspendDisabled = suspendDisabled;
    }

    public TreeNode getAvailableServers() {
        return availableServers;
    }

    /**
     * 
     * Create message with managed servers shown above
     * the tab panel.
     * 
     */
    public String getPrintableSelectedServers() {
        StringBuffer strBuilder = new StringBuffer();

        for (Server srv : DefaultContext.getInstance().getServers()) {
            strBuilder.append(srv.getFullName());
            strBuilder.append(" ");
        }

        return strBuilder.toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void handleUserAction(ActionEvent ev) {
        FacesContext.getCurrentInstance().renderResponse();
    }

    public TreeState getTreeState() {
        return treeState;
    }

    public void setTreeState(TreeState treeState) {
        this.treeState = treeState;
    }

    /* error methods */
    public void closeErrorPopup() {
        errorPopup.hideError();
    }

    public void setErrorMessage(String errorMessageId) {
        errorPopup.showError(errorMessageId);
    }

    public String getErrorMessage() {
        return errorPopup.getErrorMessage();
    }

    public Boolean getShowErrorPopup() {
        return errorPopup.getShowErrorPopup();
    }

    public String getErrorHeader() {
        return errorPopup.getErrorHeader();
    }

    public Boolean getDeletePopup() {
        return deletePopup;
    }

    public void setDeletePopup(Boolean deletePopup) {
        this.deletePopup = deletePopup;
    }
}