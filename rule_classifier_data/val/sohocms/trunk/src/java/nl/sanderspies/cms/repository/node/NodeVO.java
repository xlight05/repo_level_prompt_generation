/*
 * NodeVO.java
 *
 * Created on 29 juli 2006, 19:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.sanderspies.cms.repository.node;

import java.util.ArrayList;

/**
 *
 * @author Sander Spies
 */
public class NodeVO {
    private String name;
    private String nodeType;
    private String path;
    private ArrayList properties = new ArrayList();
    
    
    /** Creates a new instance of NodeVO */
    public NodeVO() {
    }

    public void setPath(String path){
        this.path = path;
    }
    
    public String getPath(){
        return path;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setNodeType(String nodeType){
        this.nodeType = nodeType;
    }
    
    public String getNodeTypeName() {
        return nodeType;
    }

    public String getName() {
        return name;
    }
    
}
