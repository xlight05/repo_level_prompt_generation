/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.iosr.web;

/**
 *
 * @author Tomasz Jadczyk
 */
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import org.apache.myfaces.tobago.model.SheetState;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.ServiceFactory;
import pl.edu.agh.iosr.ServiceFactoryImpl;
import pl.edu.agh.iosr.ftpserverremote.command.DataService;
import pl.edu.agh.iosr.IdNotRecognizedException;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.IPRestriction;
import pl.edu.agh.iosr.ftpserverremote.server.ServerHelper;
import pl.edu.agh.iosr.web.provider.ServerProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;

public class IpRestrictorBean {

    private IPRestriction ipRestriction;
    private IPRestriction[] ipRestrictions;
    private List<Entity> ipRestrictionsList;
    private SheetState sheetState;
    private SelectItem[] serversIP;
    private ErrorPopup errorPopup;

    public IpRestrictorBean() {
        errorPopup = new ErrorPopup();
        ipRestriction = new IPRestriction();
        ipRestrictionsList = new LinkedList<Entity>();
        reload();
    }

    /* Actions */
    public void add() {
        ipRestrictionsList.add(createNewIPRestriction());
    }

    public void remove() {
        if (isRowSelected()) {
            ipRestrictionsList.remove(sheetState.getSelectedRows().get(0).intValue());
        }
    }

    public void insert() {
        if (isRowSelected()) {
            ipRestrictionsList.add(sheetState.getSelectedRows().get(0).intValue(), createNewIPRestriction());
        }
    }

    public void move_up() {
        if (isRowSelected()) {
            int selectedRowIndex = sheetState.getSelectedRows().get(0).intValue();
            swap(selectedRowIndex, selectedRowIndex - 1);
        }
    }

    public void move_down() {
        if (isRowSelected()) {
            int selectedRowIndex = sheetState.getSelectedRows().get(0).intValue();
            swap(selectedRowIndex, selectedRowIndex + 1);
        }
    }

    public void reload() {
        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(IPRestriction.class.getName());
            ipRestrictionsList = dataService.getAll();
            if (ipRestrictionsList.size() > 0) {
                ipRestriction = (IPRestriction) ipRestrictionsList.get(0);
            }
            for (Entity e : ipRestrictionsList) {
                setHostname((IPRestriction) e);
            }
            
        } catch (IdNotRecognizedException idNotRecognizedException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, idNotRecognizedException);
            errorPopup.showDataError();
        } catch (DataAccessException dataAccessException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, dataAccessException);
            errorPopup.showDataError();
        }
    }

    public boolean isPatternValid(String pattern) {
        String[] parts = pattern.split("\\.");

        for (String part : parts) {

            if (part.matches("\\*")) {
                continue;
            }
            if (!part.matches("\\d+")) {
                return false;
            }
            int number = Integer.parseInt(part);
            if (number < 0 || number > 255) {
                return false;
            }
        }

        return true;
    }

    public boolean isValid() {
        for (Entity e : ipRestrictionsList) {
            IPRestriction ipr = (IPRestriction) e;
            if (!isPatternValid(ipr.getIpPattern())) {
                return false;
            }
        }

        return true;
    }

    public void save() {

        if (!isValid()) {
            errorPopup.showError("pattern_error_message");
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();
            DataService dataService = serviceFactory.getDataService(IPRestriction.class.getName());

//            for (Entity e : ipRestrictionsList) {
//                setServerID((IPRestriction) e);
//                if (e.getId() != null) {
//                    dataService.update(e);
//                } else {
//                    dataService.create(e);
//                }
//            }
            for(Entity e :ipRestrictionsList) {
                dataService.delete(e);
            }
            for(Entity e : ipRestrictionsList) {
                dataService.create(e);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (IdNotRecognizedException idNotRecognizedException) {
            Logger.getLogger(IpRestrictorBean.class.getName()).log(Level.SEVERE, null, idNotRecognizedException);
            errorPopup.showDataError();
        }
    }

    private IPRestriction createNewIPRestriction() {
        IPRestriction newIPRestriction = new IPRestriction();
        newIPRestriction.setPermissionBoolean(Boolean.TRUE);
        newIPRestriction.setIpPattern("*");
        setHostname(newIPRestriction);

        return newIPRestriction;
    }

    private void swap(int indexA, int indexB) {

        if (indexA >= 0 && indexB >= 0 && indexA < ipRestrictionsList.size() && indexB < ipRestrictionsList.size()) {
            IPRestriction a = (IPRestriction) ipRestrictionsList.get(indexA);
            IPRestriction b = (IPRestriction) ipRestrictionsList.get(indexB);

            String tmpPattern = a.getIpPattern();
            Boolean tmpPermission = a.getPermissionBoolean();

            a.setIpPattern(b.getIpPattern());
            a.setPermissionBoolean(b.getPermissionBoolean());

            b.setIpPattern(tmpPattern);
            b.setPermissionBoolean(tmpPermission);
        }
    }

    /* getters and setters */
    public IPRestriction getIpRestriction() {
        return ipRestriction;
    }

    public void setIpRestriction(IPRestriction ipRestriction) {
        this.ipRestriction = ipRestriction;
    }

    public IPRestriction[] getIpRestrictions() {
        return ipRestrictions;
    }

    public void setIpRestrictions(IPRestriction[] ipRestrictions) {
        this.ipRestrictions = ipRestrictions;
    }

    public List<Entity> getIpRestrictionsList() {
        return ipRestrictionsList;
    }

    public void setIpRestrictionsList(List<Entity> ipRestrictionsList) {
        this.ipRestrictionsList = ipRestrictionsList;
    }

    public SheetState getSheetState() {
        return sheetState;
    }

    public void setSheetState(SheetState sheetState) {
        this.sheetState = sheetState;
    }

    public void setServerIDs() {
    }

    public SelectItem[] getServersIP() {
        return ServerProvider.getCurrentContextServersWithAll();
    }

    public void setServersIP(SelectItem[] serversIP) {
        this.serversIP = serversIP;
    }

    private boolean isRowSelected() {
        return sheetState.getSelectedRows().size() > 0;
    }

    private void setHostname(IPRestriction restriction) {
        restriction.setHostname(ServerHelper.getNameById(restriction.getServerId()));
    }

    private void setServerID(IPRestriction restriction) {
        restriction.setServerId(ServerHelper.getIdByName(restriction.getHostname()));
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
