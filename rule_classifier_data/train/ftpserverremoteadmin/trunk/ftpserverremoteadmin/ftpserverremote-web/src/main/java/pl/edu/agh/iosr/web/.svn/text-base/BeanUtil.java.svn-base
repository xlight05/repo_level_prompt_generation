
package pl.edu.agh.iosr.web;

import java.util.List;


/**
 *
 * @author Tomasz Jadczyk
 */
public class BeanUtil {

   public static String getChangedServerCaption(List<String> smallerTab, List<String> largerTab) {
         String newServerLabel = null;
            for (int i = 0; i < largerTab.size(); i++) {
                String label = largerTab.get(i);
                int j = 0;
                for (j = 0; j < smallerTab.size(); j++) {
                    if (smallerTab.get(i).equalsIgnoreCase(label)) {
                        break;
                    }

                }
                if (j == smallerTab.size()) {
                    //new server label founded
                    newServerLabel = label;
                    break;
                }
            }
         return newServerLabel;
    }
    
}
