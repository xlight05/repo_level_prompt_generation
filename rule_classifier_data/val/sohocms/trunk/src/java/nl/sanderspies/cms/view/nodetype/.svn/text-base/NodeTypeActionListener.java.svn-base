/*
 * NodeTypeActionListener.java
 *
 * Created on 23 juli 2006, 12:58
 *
 * Property of Sander Spies
 */

package nl.sanderspies.cms.view.nodetype;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.jcr.PropertyType;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.PropDef;
import org.apache.jackrabbit.core.nodetype.PropDefImpl;
import org.apache.jackrabbit.name.QName;

/**
 *
 * @author Sander Spies
 */
public class NodeTypeActionListener{
    
    /** Creates a new instance of NodeTypeActionListener */
    public NodeTypeActionListener() {
    }
    
    public String processAddNodeType(){
        FacesContext context = FacesContext.getCurrentInstance();
        NodeTypeVO nvo = (NodeTypeVO)context.getExternalContext().getSessionMap().get("NodeType");
        //ArrayList nvoPropeties = 
        //context.getExternalContext();
        Logger.getAnonymousLogger().info("NodeType name:"+nvo.getName());
        Logger.getAnonymousLogger().info("NodeType author:"+nvo.getAuthor());
        //TODO: transform
        
        NodeTypeDef ntd = new NodeTypeDef();
        ntd.setName(new QName("??",nvo.getName()));
        //ntd.setSupertypes(`); nt:base?!
        PropDef[] properties = new PropDef[nvo.getProperties().size()+1];
        PropDefImpl author = new PropDefImpl();
        author.setRequiredType(PropertyType.STRING);
        author.setMandatory(true);
        author.setName(new QName("??",nvo.getAuthor()));
        properties[0] = author;
        for(int i = 1;i < nvo.getProperties().size()+1;i++){
            PropertyVO pvo = (PropertyVO)nvo.getProperties().get(i);
            PropDefImpl pd = new PropDefImpl();
            pd.setRequiredType(pvo.getType()); 
            pd.setName(new QName("??",pvo.getName()));
            properties[i] = pd;
        }
 
        ntd.setPropertyDefs(properties);
        
 
        //TODO: add ADD to JACKRABBIT functionality :D.
        return "success";
    }
    
    public String processAddProperty(){
        FacesContext context = FacesContext.getCurrentInstance();
        NodeTypeVO nvo = (NodeTypeVO)context.getExternalContext().getSessionMap().get("NodeType");
        if(nvo==null){ 
            nvo = new NodeTypeVO();
        }
        
        PropertyVO pvo = new PropertyVO();
        pvo.setName(nvo.getTempParName()==null?"":nvo.getTempParName());
        pvo.setType(Integer.parseInt(nvo.getTempParType()==null?"":nvo.getTempParType()));
        nvo.addProperty(pvo);
        context.getExternalContext().getSessionMap().put("NodeType", nvo);
        Logger.getAnonymousLogger().info("Try to add property... numbah:"+nvo.getProperties().size());
        return null;
    }
    
}
