/*
 * WebStructureService.java
 *
 * Created on 4 augustus 2006, 18:08
 *
 */

package nl.sanderspies.cms.business.webstructure;

import nl.sanderspies.cms.repository.node.NodeRAO;

/**
 * This part defines the web structure...
 *
 * @author Sander Spies
 */
public class WebStructureService {
    private NodeRAO nodeRAO = null;
    private static final String PREFIX = "/WebStructure";
    
    public WebStructureService(){
        nodeRAO = NodeRAO.getInstance();
    }
    
    public void addFolder(){}
    
    public void addPage(){}
    
}
