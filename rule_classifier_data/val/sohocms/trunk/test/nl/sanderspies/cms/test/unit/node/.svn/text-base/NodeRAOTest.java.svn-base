/*
 * NodeTest.java
 * JUnit based test
 *
 * Created on 29 juli 2006, 18:52
 */

package nl.sanderspies.cms.test.unit.node;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
import junit.framework.*;
import nl.sanderspies.cms.repository.node.NodeRAO;
import nl.sanderspies.cms.repository.node.NodeVO;
import nl.sanderspies.cms.repository.nodetype.NodeTypeRAO;
import org.apache.jackrabbit.core.NodeImpl;

/**
 *
 * @author Sander Spies
 */
public class NodeRAOTest extends TestCase {
    
    public NodeRAOTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        System.setProperty("java.security.auth.login.config", "jaas.config");
    }

    protected void tearDown() throws Exception {
    }
    
    public void testGetRootNode() throws NamingException, RepositoryException{
        NodeRAO nodeRAO = NodeRAO.getInstance();
        Node root = nodeRAO.getRootNode();
        assertNotNull(root);
    }
    
    public void testAddNode() throws NamingException, RepositoryException{
        NodeTypeRAO ntr = NodeTypeRAO.getInstance();
        NodeRAO nodeRAO = NodeRAO.getInstance();
        NodeVO node = new NodeVO();
        node.setName("TestNodeJantje");
        node.setNodeType("sohocms:test");
        Node nodeA = nodeRAO.addNode("/",node); 
        assertNotNull(nodeA);
    }

    public void testGetNode() throws RepositoryException, NamingException{
        NodeRAO nodeRAO = NodeRAO.getInstance();
        Node nodeX = nodeRAO.getNode("TestNodeJantje");
        assertNotNull(nodeX);
    }
    
    public void testUpdateNode() throws RepositoryException, NamingException, Exception{
        NodeTypeRAO ntr = NodeTypeRAO.getInstance();
        NodeRAO nodeRAO = NodeRAO.getInstance();
        Node node = nodeRAO.getNode("TestNodeJantje");
        
        NodeVO nodeVO = new NodeVO();
        nodeVO.setName(node.getName());
        nodeVO.setPath(node.getPath());
        //nodeVO.setNodeType(node.get)
        nodeRAO.updateNode(nodeVO);
    }
    
    
    public void testGetChildNodes() throws NamingException, RepositoryException{
        NodeRAO nr = NodeRAO.getInstance();
        List<Node> nodes = nr.getChildNodes("/");
        assertTrue(nodes.size()>1);
        for(Node node : nodes){
            Logger.getAnonymousLogger().info("Alstu:"+node.getName());
        }
        
        
    }
    
    public void testAddNodeFolder(){
        //TODO: make it work... |-)
        fail();
    }
    
    public void testRemoveNode() throws RepositoryException, NamingException, Exception{
        NodeTypeRAO ntr = NodeTypeRAO.getInstance();
        NodeRAO nodeRAO = NodeRAO.getInstance();
        nodeRAO.removeNode("TestNodeJantje");
        try{
            Node nodeX = nodeRAO.getNode("TestNodeJantje");
            fail();
        }catch(Exception e){
            assertTrue(true);
        }
        
        
    }
}
