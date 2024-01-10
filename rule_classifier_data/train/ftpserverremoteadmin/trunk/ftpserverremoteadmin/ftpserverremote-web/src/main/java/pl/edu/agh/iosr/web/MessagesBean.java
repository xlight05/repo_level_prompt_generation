/*
 * MessagesBean.java
 *
 * Created on 2007-12-05, 12:39:46
 *
 */

package pl.edu.agh.iosr.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Message;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.server.ServerHelper;
import pl.edu.agh.iosr.web.provider.ServerProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;


/**
 *
 * Class for handling data from page with messages
 *
 * @author Monika Nawrot
 */
public class MessagesBean {

    private String server;
    private String prevName;
    private Message message;
    private List<Message> messages;
    private ErrorPopup errorPopup;

    /**
     * Creates a new instance of MessagesBean
     */
    public MessagesBean() {
        errorPopup = new ErrorPopup();

        getServers();
        server = "All";
        reload();
    }

    /**
     * Copy current message
     *
     */
    private Message copyMessage(Message m) {
        Message result = new Message();
        result.setId(m.getServerId());
        result.setId(m.getId());
        result.setMessage(m.getMessage());
        result.setName(m.getName());

        return result;
    }

    /**
     * Save currently selected message
     *
     */
    public void save() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Message.class.getName());
            message.setServerId(ServerHelper.getIdByName(server));
            message.setMessage(getMessage());
            dataService.update(message);
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    /**
     * Reload page. Get messages defined for the currently selected server.
     *
     */
    public void reload() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Message.class.getName());
            List<Entity> mList = dataService.getAll();

            messages = new ArrayList<Message>();
            for (Entity e : mList) {
                if (((Message) e).getServerId().compareTo(ServerHelper.getIdByName(server)) == 0) {
                    messages.add(copyMessage((Message) e));
                }
            }

            Collections.sort(messages, new Comparator<Message>() {

                public int compare(Message o1, Message o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            if (messages.size() > 0) {
                message = copyMessage(messages.get(0));
            } else {
                message = new Message();
                message.setMessage("");
                message.setName("");
            }
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    /**
     * Reload page when server selection changed
     *
     */
    public void serverSelectionChanged() {
        reload();
    }

    /**
     * Apply changes in previously selected message to the current
     * copy of message list.
     *
     */
    public void messageSelectionChanged() {
        for (Message m : messages) {
            if (m.getName().equals(getName())) {
                message = copyMessage(m);
                break;
            }
        }
    }

    /**
     * Save text of the message when text area changed
     *
     */
    public void textAreaChanged() {
        for (Message m : messages) {
            if (m.getName().equals(prevName)) {
                m.setMessage(getMessage());
                messageSelectionChanged();
                break;
            }
        }
    }

    /**
     * Add currently selected message to the list of
     * messages defined for the each server in the
     * current context.
     *
     */
    public void add() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Message.class.getName());

            List<Server> servers = DefaultContext.getInstance().getServers();

            Message newMessage = new Message();
            newMessage.setMessage(message.getMessage());
            newMessage.setName(message.getName());

            for (Server s : servers) {
                newMessage.setServerId(s.getId());
                dataService.create(newMessage);
            }
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    /**
     * Delete currently selected message
     *
     */
    public void delete() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Message.class.getName());
            message.setServerId(ServerHelper.getIdByName(server));
            dataService.delete(message);
            reload();
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    /* getters and setters */
    public SelectItem[] getServers() {
        return ServerProvider.getCurrentContextServersWithAll();
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMessage() {
        return this.message.getMessage();
    }

    public void setMessage(String message) {
        this.message.setMessage(message);
    }

    public String getName() {
        return this.message.getName();
    }

    public void setName(String name) {
        prevName = this.message.getName();
        this.message.setName(name);
    }

    public SelectItem[] getLabels() {
        SelectItem[] labels = new SelectItem[messages.size()];
        int i = 0;
        for (Message m : messages) {
            labels[i++] = new SelectItem(m.getName());
        }
        return labels;
    }

    /**
     * Add button disabled when managing single server; enabled when
     * managing all servers
     *
     */
    public Boolean getAddDisabled() {
        if (ServerHelper.getIdByName(server).compareTo(new Long(0)) == 0) {
            return new Boolean(false);
        }

        return new Boolean(true);
    }

    /**
     * Delete button disabled when managing all servers; enabled when
     * managing single server
     *
     */
    public Boolean getDeleteDisabled() {
        return !(getAddDisabled());
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
}