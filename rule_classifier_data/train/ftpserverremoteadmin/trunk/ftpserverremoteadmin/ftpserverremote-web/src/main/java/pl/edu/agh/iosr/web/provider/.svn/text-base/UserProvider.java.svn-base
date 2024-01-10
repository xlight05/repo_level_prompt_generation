/*
 * UserProvider.java
 *
 * Created on 2008-01-04, 19:49:13
 *
 */

package pl.edu.agh.iosr.web.provider;

import javax.faces.model.SelectItem;

/**
 * Class providing selectable list of values used in user page
 * 
 * @author Monika Nawrot
 */
public class UserProvider {

    /**
     *  
     * @return values with maximum idle time
     */
    public static SelectItem[] getMaxIdleTimes() {
        return new SelectItem[]{new SelectItem("0"), new SelectItem("60"), new SelectItem("300"), new SelectItem("900"), new SelectItem("1800"), new SelectItem("3600")};
    }
    
    /**
     *  
     * @return values with maximum download time
     */
    public static SelectItem[] getMaxDownload() {
        return new SelectItem[]{new SelectItem("0"), new SelectItem("1200"), new SelectItem("2400"), new SelectItem("4800"), new SelectItem("9600"), new SelectItem("14400"), new SelectItem("28800"), new SelectItem("57600")};
    }
    
    /**
     *  
     * @return values with maximum upload time
     */
    public static SelectItem[] getMaxUpload() {
        return new SelectItem[]{new SelectItem("0"), new SelectItem("1200"), new SelectItem("2400"), new SelectItem("4800"), new SelectItem("9600"), new SelectItem("14400"), new SelectItem("28800"), new SelectItem("57600")};
    }
}