/*
 * UserBean.java
 *
 * Created on 2007-12-01, 15:17:29
 *
 */

package pl.edu.agh.iosr.web;

import java.util.ArrayList;
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
import pl.edu.agh.iosr.FilteredDataService;
import pl.edu.agh.iosr.ftpserverremote.data.User;
import pl.edu.agh.iosr.ftpserverremote.server.ServerHelper;
import pl.edu.agh.iosr.web.provider.ServerProvider;
import pl.edu.agh.iosr.web.provider.UserProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;
import pl.edu.agh.iosr.web.utils.PasswordGenerator;

/**
 *
 * Class for handling data in page with user definitions
 *
 * @author Monika Nawrot
 */
public class UserBean {

    private SelectItem[] maxIdleTimes;
    private SelectItem[] maxDownloads;
    private SelectItem[] maxUploads;
    private SelectItem[] userLabels;
    private String userLabel;
    private List<User> users;
    private User user = new User();
    private String retypePassword;
    private Boolean passwordSet;
    private String generatedPassword;
    private ErrorPopup errorPopup;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {

        /* set time options */
        maxIdleTimes = UserProvider.getMaxIdleTimes();

        /* set download options */
        maxDownloads = UserProvider.getMaxDownload();

        /* set upload options */
        maxUploads = UserProvider.getMaxUpload();

        errorPopup = new ErrorPopup();

        reload();
    }

    private void setUserParams(User _user) {
        user.setEnabledBoolean(_user.getEnabledBoolean());
        user.setMaxIdleTime(_user.getMaxIdleTime());
        user.setMaxDownload(_user.getMaxDownload());
        user.setMaxUpload(_user.getMaxUpload());
        user.setName(_user.getName());
        user.setPassword(_user.getPassword());
        user.setRootDirectory(_user.getRootDirectory());
        user.setServerId(_user.getServerId());
        user.setWritePermissionBoolean(_user.getWritePermissionBoolean());
        user.setMaxLoginFromSameIP(_user.getMaxLoginFromSameIP());
        user.setMaxLoginNumber(_user.getMaxLoginNumber());
        user.setServerId(_user.getServerId());

        retypePassword = _user.getPassword();

        if (!_user.getPassword().equals("")) {
            setPasswordSet(new Boolean(true));
        } else {
            setPasswordSet(new Boolean(false));
        }
    }

    private void saveUser(User _user) {
        _user.setEnabledBoolean(user.getEnabledBoolean());
        _user.setMaxIdleTime(user.getMaxIdleTime());
        _user.setMaxDownload(user.getMaxDownload());
        _user.setMaxUpload(user.getMaxUpload());
        _user.setName(user.getName());
        _user.setRootDirectory(user.getRootDirectory());
        _user.setServerId(user.getServerId());
        _user.setWritePermissionBoolean(user.getWritePermissionBoolean());
        _user.setMaxLoginFromSameIP(user.getMaxLoginFromSameIP());
        _user.setMaxLoginNumber(user.getMaxLoginNumber());

        if (getPasswordSet()) {
            _user.setPassword(user.getPassword());
        } else {
            _user.setPassword(new String(""));
        }
    }

    /**
     * Save user parameters in the current list of users
     *
     */
    public void userChanged() {
        for (User u : users) {
            if (userLabel.equals(u.getName())) {
                setUserParams(u);
                setUserLabel(u.getName());
            }
        }
    }

    /**
     * Create labels from current list of users
     */
    public void refreshUserLabels() {
        userLabels = new SelectItem[users.size()];
        int i = 0;
        for (User u : users) {
            userLabels[i++] = new SelectItem(u.getName());
        }
        userLabel = user.getName();
    }

    /**
     * Check if password and retype password are the same
     */
    public boolean isValid() {

        if (getPasswordSet()) {
            if (!(getPassword().equals(retypePassword))) {
                return false;
            }
        } else {
            setPassword("");
            retypePassword = "";
        }

        return true;
    }

    /**
     * Save user in database. Check if parameters are correct.
     * If user does not exist in a database create user, update when
     * exists. Reload page after query.
     *
     */
    public void save() {

        if (!isValid()) {
            errorPopup.showError("user_error_message");
            return;
        }

        boolean changed = false;

        try {

            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(User.class.getName());

            for (User u : users) {
                if (u.getName().equals(getName())) {
                    saveUser(u);
                    dataService.update(u);
                    changed = true;
                    break;
                }
            }

            if (!changed) {
                dataService.create(user);
            }

            reload();
        } catch (DataAccessException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        }
    }

    /**
     * Delete user from the database. Reload page after query.
     *
     */
    public void delete() {
        try {

            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(User.class.getName());

            for (User u : users) {
                if (u.getName().equals(getName())) {
                    dataService.delete(u);
                    break;
                }
            }

            reload();
        } catch (DataAccessException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        }
    }

    public void closeUserErrorPopup() {
        errorPopup.hideError();
    }

    /**
     * Reload page. Gather data filtred by current context and
     * refresh proper labels.
     *
     */
    public void reload() {
        try {

            List<Entity> userList = FilteredDataService.getData(User.class.getName(), DefaultContext.getInstance());

            if (userList.size() == 0) {
                return;
            }
            users = new ArrayList<User>();
            for (Entity e : userList) {
                users.add((User) e);
            }

            chooseUser();
            refreshUserLabels();
        } catch (IdNotRecognizedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    /**
     * Set parameters in the form when user selection changed
     *
     */
    private void chooseUser() {
        boolean userSet = false;
        if (user.getName() != null) {
            for (User u : users) {
                if (u.getName().equals(user.getName())) {
                    setUserParams(user);
                    userSet = true;
                    break;
                }
            }
        }
        if (userSet == false || user.getName() == null) {
            setUserParams(users.get(0));
        }
    }

    /**
     * Generete password
     *
     */
    public void generate() {
        generatedPassword = new PasswordGenerator().generate();
        setPassword(generatedPassword);
    }

    /**
     * Reload user parameters
     *
     */
    public void reloadUser() {
        for (User u : users) {
            if (u.getName().equals(user.getName())) {
                u.setPassword(generatedPassword);
                setPassword(generatedPassword);
                setRetypePassword("");
                setPasswordSet(new Boolean(true));
            }
        }
    }

    /* getters and setters */
    public SelectItem[] getMaxIdleTimes() {
        return maxIdleTimes;
    }

    public void setMaxIdleTimes(SelectItem[] maxIdleTimes) {
        this.maxIdleTimes = maxIdleTimes;
    }

    public String getMaxIdleTime() {
        return user.getMaxIdleTime().toString();
    }

    public void setMaxIdleTime(String maxIdleTime) {
        user.setMaxIdleTime(new Integer(maxIdleTime));
    }

    public SelectItem[] getMaxDownloads() {
        return maxDownloads;
    }

    public void setMaxDownloads(SelectItem[] maxDownloads) {
        this.maxDownloads = maxDownloads;
    }

    public String getMaxDownload() {
        return user.getMaxDownload().toString();
    }

    public void setMaxDownload(String maxDownload) {
        user.setMaxDownload(new Integer(maxDownload));
    }

    public SelectItem[] getMaxUploads() {
        return maxUploads;
    }

    public void setMaxUploads(SelectItem[] maxUpload) {
        this.maxUploads = maxUpload;
    }

    public String getMaxUpload() {
        return user.getMaxUpload().toString();
    }

    public void setMaxUpload(String maxUpload) {
        user.setMaxUpload(new Integer(maxUpload));
    }

    public String getName() {
        return user.getName();
    }

    public void setName(String name) {
        user.setName(name);
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password) {
        user.setPassword(password);
    }

    public String getRootDirectory() {
        return user.getRootDirectory();
    }

    public void setRootDirectory(String rootDirectory) {
        user.setRootDirectory(rootDirectory);
    }

    public boolean getEnabled() {
        return user.getEnabledBoolean();
    }

    public void setEnabled(boolean enabled) {
        user.setEnabledBoolean(enabled);
    }

    public Boolean getWritePermission() {
        return user.getWritePermissionBoolean();
    }

    public void setWritePermission(Boolean writePermission) {
        user.setWritePermissionBoolean(writePermission);
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public Boolean getPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(Boolean passwordSet) {
        this.passwordSet = passwordSet.booleanValue();
    }

    public String getMaxLoginNumber() {
        return this.user.getMaxLoginNumber().toString();
    }

    public void setMaxLoginNumber(String maxLoginNumber) {
        try {
            Integer newValue = Integer.parseInt(maxLoginNumber);
            if (newValue >= 0) {
                this.user.setMaxLoginNumber(newValue);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            errorPopup.showError("user_error_message");
        }
    }

    public String getMaxLoginFromSameIP() {
        return this.user.getMaxLoginFromSameIP().toString();
    }

    public void setMaxLoginFromSameIP(String maxLoginFromSameIP) {
        try {
            Integer newValue = Integer.parseInt(maxLoginFromSameIP);
            if (newValue >= 0) {
                this.user.setMaxLoginFromSameIP(newValue);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            errorPopup.showError("user_error_message");
        }
    }

    public SelectItem[] getUserLabels() {
        return userLabels;
    }

    public void setUserLabels(SelectItem[] userLabels) {
        this.userLabels = userLabels;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public SelectItem[] getServers() {
        return ServerProvider.getCurrentContextServersWithAll();
    }

    public String getServer() {
        return ServerHelper.getNameById(user.getServerId());
    }

    public void setServer(String name) {
        this.user.setServerId(ServerHelper.getIdByName(name));
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