/*
 * NodeTest.java
 * JUnit based test
 *
 * Created on 29 juli 2006, 18:52
 */

package nl.sanderspies.cms.test.unit.node;

import java.io.File;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
import junit.framework.*;
import nl.sanderspies.cms.repository.node.NodeRAO;
import nl.sanderspies.cms.repository.node.NodeVO;
import org.apache.jackrabbit.core.NodeImpl;

/**
 *
 * @author Lucka Consultancy BV
 */
public class NodeTest extends TestCase {
    
    public NodeTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        System.setProperty("java.security.auth.login.config", "jaas.config");
        //remove directory first....
        File file = new File("C:\\Documents and Settings\\Lucka Consultancy BV\\CMS\\SimpleCMSxxx");
        file.delete();
    }

    protected void tearDown() throws Exception {
    }
    
    public void testGetRootNode() throws NamingException, RepositoryException{
        NodeRAO nodeRAO = NodeRAO.getInstance();
        Node root = nodeRAO.getRootNode();
        assertNotNull(root);
    }
    
    public void testGetNode(){}
    
    public void testAddNode() throws NamingException, RepositoryException{
        NodeRAO nodeRAO = NodeRAO.getInstance();
        NodeVO node = new NodeVO();
        
        nodeRAO.addNode(node);
    }
    
}
