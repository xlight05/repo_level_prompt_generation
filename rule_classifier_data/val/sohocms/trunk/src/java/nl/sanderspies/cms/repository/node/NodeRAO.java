/*
 * NodeRAO.java
 *
 * Property of Sander Spies
 *
 * Created on 29 juli 2006, 16:54
 *
 */

package nl.sanderspies.cms.repository.node;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.jcr.Credentials;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.NamingException;
import nl.sanderspies.cms.repository.Manager;

/**
 *
 * @author Sander Spies
 */
public class NodeRAO extends Manager{
    private static NodeRAO instance;
    private Logger logger =  Logger.getLogger("NodeRAO");
    private static final String USERID = "userid";
    private static final char[] PASSWORD = "".toCharArray();
    
    /** Creates a new instance of NodeRAO */
    private NodeRAO() {
    }
    
    public static NodeRAO getInstance(){
        if(instance==null)instance = new NodeRAO();
            return instance;
    }
    
    public Node addNode(String parentPath, NodeVO node) throws RepositoryException, NamingException{
        //this part checks for duplicate nodes
        Node node2 = null;
        try{
            node2 = getNode(node.getName());
        }catch(Exception e){}
        if(node2 != null)
            throw new RepositoryException("Node is already in repository, choose another name!");
        
        //add node to repository
        Repository repository = super.getRepository();
        Credentials credentials = new SimpleCredentials(USERID,PASSWORD);
        Session session = repository.login(credentials); 
        Node root = session.getRootNode();
        root = root.getNode(parentPath);
        Node nodeP =  root.addNode(node.getName(), node.getNodeTypeName());
        logger.info(nodeP.getPath());
        session.save();
        nodeP.checkin();
        session.logout();
        return nodeP;
    }
    
    /**
     * removes node on basis of NodePath
     */
    public void removeNode(String nodePath) throws NamingException, RepositoryException, Exception{
        Repository repository = super.getRepository();
        Credentials credentials = new SimpleCredentials(USERID,PASSWORD);
        Session session = repository.login(credentials); 
        Node node =  session.getRootNode();
        node = node.getNode(nodePath);
        node.remove();
        session.save();
        session.logout();
    }
    
    public void updateNode(NodeVO nodeVO) throws Exception{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node node =  session.getRootNode();
        node = node.getNode(nodeVO.getPath());
        node.checkout();
        //update the node...
        session.save();
        node.checkin();
        session.logout();
    }
    
    /**
     * get node on basis of NodePath
     */
    public Node getNode(String nodePath) throws ItemNotFoundException, RepositoryException, NamingException{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node node =  session.getRootNode();
        node = node.getNode(nodePath);
        session.logout();
        return node;
    }
    
    public List<Node> getChildNodes(String nodePath) throws NamingException, RepositoryException{
        ArrayList<Node> childNodes = new ArrayList<Node>();
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node node =  session.getRootNode();
        node = node.getNode(nodePath);
        NodeIterator ni = node.getNodes();
        while(ni.hasNext()){
            Node node2 = (Node)ni.next();
            childNodes.add(node2);
        }
        session.logout();
        return childNodes;
    }
    
    /**
     * Returns the RootNode of the Content Repository.
     */
    public Node getRootNode() throws NamingException, RepositoryException{
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node root = session.getRootNode();
        session.logout();
        return root;
    }

    public void addNodeFolder() {
        //TODO: Sander!
        //add folder thingy in nodetypes...
        
        //this part checks for duplicate node folders
        
        /*Node node2 = getNode(node.getName());
        if(node2 != null)throw new RepositoryException("Node is already in repository, choose another name!");
        
        //add node to repository
        Repository repository = super.getRepository();
        Credentials credentials = new SimpleCredentials(USERID,PASSWORD);
        Session session = repository.login(credentials); 
        Node root = session.getRootNode();
        root = root.getNode(parentPath);
        Node nodeP =  root.addNode(node.getName(), node.getNodeTypeName());
        
        session.save();
        nodeP.checkin();
        session.logout();
        return nodeP;*/
    }
    
    private NodeVO createNodeVO(String nodePath) throws RepositoryException, NamingException{
        NodeVO nodeVO = new NodeVO();
        Repository repository = super.getRepository();
        Session session = repository.login();
        Node node =  session.getRootNode();
        node = node.getNode(nodePath);
        
        
        
        session.logout();
        return nodeVO;
    }
   
}
