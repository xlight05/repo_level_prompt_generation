/*
 * AdminBean.java
 *
 * Created on 2007-12-01, 16:14:41
 *
 */

package pl.edu.agh.iosr.web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pl.edu.agh.iosr.CommandFactory;
import pl.edu.agh.iosr.CommandFactoryImpl;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.data.Admin;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.command.*;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.web.utils.ErrorPopup;

/**
 * Class responsible for handling data in login page and logging
 * the user in and out
 * 
 * @author Monika Nawrot
 * 
 */
public class AdminBean {

    private Admin admin;
    private ErrorPopup errorPopup;

    /** 
     * Creates a new instance of AdminBean 
     */
    public AdminBean() {
        admin = new Admin();
        admin.setLogin("");
        admin.setPassword("");
        errorPopup = new ErrorPopup("login_error");
    }

    /**
     *  Check if username and password is correct and log user
     * 
     */
    public String login() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(Admin.class.getName());
            List<Entity> adminList = dataService.getAll();
            for (Entity a : adminList) {

                if ((((Admin) a).getLogin().equals(admin.getLogin())) && (((Admin) a).getPassword().equals(admin.getPassword()))) {
                    return "success";
                }
            }
            
            errorPopup.showError("login_error_message");
            
        } catch (DataAccessException ex) {
            Logger.getLogger(AdminBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(AdminBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        }
        
        return "failure";
    }

    /**
     * Logout user. Dispose current context and session
     * 
     */
    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
        for(Server srv : DefaultContext.getInstance().getServers()) {
            try {
                Command cmd = commandFactory.getLogOutCommand(srv);
                cmd.execute();
            } catch(Exception e) {
                 Logger.getLogger(AdminBean.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        DefaultContext.getInstance().dispose();
        
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "success";
    }

    public String getLogin() {
        return admin.getLogin();
    }

    public void setLogin(String login) {
        admin.setLogin(login);
    }

    public String getPassword() {
        return admin.getPassword();
    }

    public void setPassword(String password) {
        admin.setPassword(password);
    }
    
    
    /* error methods */
    
    public void closeLoginErrorPopup() {
        errorPopup.hideError();
    }

    public void setErrorMessage(String errorMessageId) {
        errorPopup.showError(errorMessageId);
    }

    public String getErrorMessage() {
        return errorPopup.getErrorMessage();
    }
    
    public Boolean getShowLoginErrorPopup() {
        return errorPopup.getShowErrorPopup();
    }
    
    public String getErrorHeader() {
        return errorPopup.getErrorHeader();
    }
}