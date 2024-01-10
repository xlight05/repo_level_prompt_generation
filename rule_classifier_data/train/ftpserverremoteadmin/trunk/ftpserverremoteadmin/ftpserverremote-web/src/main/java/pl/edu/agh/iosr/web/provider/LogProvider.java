/*
 * LogProvider.java
 * 
 * Created on 2008-01-04, 18:34:54
 * 
 */

package pl.edu.agh.iosr.web.provider;

import javax.faces.model.SelectItem;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerLogsLevelProvider;

/**
 *
 * Class providing selectable values with logging level to
 * the log page
 * 
 * @author Monika Nawrot
 */
public class LogProvider {

    /**
     *  
     * @return array of selectable log levels
     */
    public static SelectItem[] getSelectableLevels() {
        String[] avaiableLevels = ServerLogsLevelProvider.getLogLevels();
        SelectItem[] items = new SelectItem[avaiableLevels.length];
        int i = 0;
        for(String str : avaiableLevels) {
            items[i++] = new SelectItem(str, str);
        }
        return items;
    }
    
}
