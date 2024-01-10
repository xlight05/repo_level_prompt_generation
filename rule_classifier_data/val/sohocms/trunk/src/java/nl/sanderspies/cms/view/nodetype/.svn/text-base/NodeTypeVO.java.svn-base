/*
 * NodeTypeBean.java
 *
 * Created on 23 juli 2006, 10:32
 *
 * Property of Sander Spies
 */

package nl.sanderspies.cms.view.nodetype;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.jcr.PropertyType;

/**
 *
 * @author Sander Spies
 */
public class NodeTypeVO {
    private String name;
    private String author;
    private String tempParName;
    private String tempParType;
    private List properties = new ArrayList();
    
    public String getTempParName(){
        return tempParName;
    }
    
    public String getTempParType(){
        return tempParType;
    }
    
    private static ArrayList types = new ArrayList();
    static{
        SelectItem si = new SelectItem(PropertyType.STRING+"","String");
        types.add(si);
        si = new SelectItem(PropertyType.BINARY+"","Binary");
        types.add(si);
        si = new SelectItem(PropertyType.DATE+"","Date");
        types.add(si);
        si = new SelectItem(PropertyType.LONG+"","Long");
        types.add(si);
        si = new SelectItem(PropertyType.DOUBLE+"","Double");
        types.add(si);
        si = new SelectItem(PropertyType.BOOLEAN+"","Boolean");
        types.add(si);
        
    }
    
    public List getTypes(){
        return types;
    }
    
    public void setTempParName(String tempParName){
        this.tempParName = tempParName;
    }
    
    public void setTempParType(String tempParType){
        this.tempParType = tempParType;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public void addProperty(PropertyVO property){
        properties.add(property);
    }
    
    public void removeProperty(PropertyVO property){
        properties.remove(property);
    }
    
    public void removeProperty(int index){
        properties.remove(index);
    }
    
    public List getProperties(){
        return properties;
    }
    
}
