/*
 * ErrorPopup.java
 *
 * Created on 2008-01-08, 20:41:23
 *
 */

package pl.edu.agh.iosr.web.utils;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 * Class managing popups with error
 * 
 * @author Monika Nawrot
 */
public class ErrorPopup {

    private Boolean showErrorPopup;
    private String errorMessage;
    private String errorHeader;
    
    /**
     * Create instance with default header
     */
    public ErrorPopup() {
        this("error");
    }
    
    /**
     * Create instance 
     * @params errorHeader header of a popup
     */
    public ErrorPopup(String errorHeader) {
        this.showErrorPopup = false;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("locale", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            this.errorHeader = bundle.getString(errorHeader);
        } catch (Exception e) {
            Logger.getLogger(ErrorPopup.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

     /**
     * Show error. Retrive proper message form the bundle file with locales
     * @params errorMessage key in locale file
     */
    public void showError(String errorMessage) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("locale", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            this.errorMessage = bundle.getString(errorMessage);
        } catch (Exception e) {
            Logger.getLogger(ErrorPopup.class.getName()).log(Level.SEVERE, null, e);
        }

        this.showErrorPopup = true;
    }

    /**
     * Show data access error 
     * 
     */
    public void showDataError() { 
        showError("data_access_error_message");
    }
    
    public void hideError() {
        this.showErrorPopup = false;
    }

    public Boolean getShowErrorPopup() {
        return showErrorPopup;
    }

    public String getErrorHeader() {
        return errorHeader;
    }
}