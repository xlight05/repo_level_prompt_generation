/*
 * NodeRAO.java
 *
 * Property of Sander Spies
 *
 * Created on 29 juli 2006, 16:54
 *
 */

package nl.sanderspies.cms.repository.node;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.naming.NamingException;
import nl.sanderspies.cms.repository.Manager;

/**
 *
 * @author Sander Spies
 */
public class NodeRAO extends Manager{
    private static NodeRAO instance;
    /** Creates a new instance of NodeRAO */
    private NodeRAO() {
    }
    
    public static NodeRAO getInstance(){
        if(instance==null)instance = new NodeRAO();
            return instance;
    }
    
    public void addNode(NodeVO node) throws RepositoryException, NamingException{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node root = session.getRootNode();
        Node nodeP =  root.addNode(node.getName(),node.getNodeTypeName());
        nodeP.addMixin("mix:versionable");
        session.save();
        nodeP.checkin();
        session.logout();
    }
    
    public void removeNode(){}
    
    public void updateNode(){}
    
    public Node getNode(String UUID) throws ItemNotFoundException, RepositoryException, NamingException{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node node =  session.getNodeByUUID(UUID);
        session.logout();
        return node;
    }
    
    public void getAllNodes(){}
    
    public Node getRootNode() throws NamingException, RepositoryException{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node root = session.getRootNode();
        session.logout();
        return root;
    }
}
