/*
 * NodeTypeRAOTest.java
 * JUnit based test
 *
 * Created on 29 juli 2006, 9:23
 */

package nl.sanderspies.cms.test.unit.nodetype;

import java.io.File;
import java.util.List;
import javax.jcr.RepositoryException;
import javax.naming.NamingException;
import javax.tools.FileObject;
import junit.framework.*;
import nl.sanderspies.cms.repository.nodetype.NodeTypeRAO;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.name.QName;

/**
 *
 * @author Sander Spies
 */
public class NodeTypeRAOTest extends TestCase {
    
    public NodeTypeRAOTest(String testName) {
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
    
    public void testGetInstance(){
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        assertNotNull(nodeTypeRAO);
    }
    
    private static final String SOHOCMSURL = "http://www.sanderspies.com/cms";
    private static final String SOHOCMSPREFIX = "sohocms";
    public void testAddNodeType() throws NamingException, RepositoryException{
        
        

        
        
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        NodeTypeDef nDef = new NodeTypeDef();
        nDef.setSupertypes(new QName[] {QName.NT_BASE, 
    QName.MIX_REFERENCEABLE, QName.MIX_VERSIONABLE});
        nDef.setName(new QName(SOHOCMSURL,"test"));
        //nDef.setName(new QName("nt","test"));
        
            nodeTypeRAO.addNodeType(nDef);  
            NodeTypeDef nd = nodeTypeRAO.getNodeType(new QName(SOHOCMSURL,"test"));
            assertEquals(""+(""+nDef.getName()+nDef.getSupertypes().length+nDef.getPropertyDefs()+nDef.isMixin())+" does not compare to "+
                    (""+nd.getName()+nd.getSupertypes().length+nd.getPropertyDefs()+nd.isMixin()),
                    (""+nDef.getName()+nDef.getSupertypes().length+nDef.getPropertyDefs()+nDef.isMixin()),
                    (""+nd.getName()+nd.getSupertypes().length+nd.getPropertyDefs()+nd.isMixin()));
            nodeTypeRAO.removeNodeType(nDef.getName());
    }
    
    public void testGetAllNodeTypes() throws NamingException, RepositoryException{
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        List nodeTypes = nodeTypeRAO.getAllNodeTypes(); 
        assertNotNull(nodeTypes);
    }
    
    public void testUpdateNodeType() throws NamingException, RepositoryException{
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        NodeTypeDef nDef = new NodeTypeDef();
        nDef.setName(new QName(SOHOCMSURL,"testUp"));
        nDef.setSupertypes(new QName[] {
                QName.NT_BASE, 
                QName.MIX_REFERENCEABLE, 
                QName.MIX_VERSIONABLE});
        nDef.setMixin(false);
        nodeTypeRAO.addNodeType(nDef);  
        NodeTypeDef nDef2 = new NodeTypeDef();
        nDef2.setName(new QName(SOHOCMSURL,"testUp"));
        nDef2.setMixin(true);
        nodeTypeRAO.updateNodeType(nDef2);
        NodeTypeDef nd2 = nodeTypeRAO.getNodeType(new QName(SOHOCMSURL,"testUp"));
        assertTrue(nd2.isMixin());
        nodeTypeRAO.removeNodeType(nDef2.getName());
    } 
    
    public void testGetNodeType() throws NamingException, RepositoryException{
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        NodeTypeDef nDef = new NodeTypeDef();
        nDef.setName(new QName(SOHOCMSURL,"test"));
                nDef.setSupertypes(new QName[] {
                QName.NT_BASE, 
                QName.MIX_REFERENCEABLE, 
                QName.MIX_VERSIONABLE});
        nodeTypeRAO.addNodeType(nDef);  
        NodeTypeDef nd = nodeTypeRAO.getNodeType(new QName(SOHOCMSURL,"test"));
        assertNotNull(nd);
        nodeTypeRAO.removeNodeType(nDef.getName());  
    }
    
    public void testRemoveNodeType() throws NamingException, RepositoryException{
        NodeTypeRAO nodeTypeRAO = NodeTypeRAO.getInstance();
        NodeTypeDef nDef = new NodeTypeDef();
        nDef.setName(new QName(SOHOCMSURL,"test2"));
        nDef.setSupertypes(new QName[] {
                QName.NT_BASE, 
                QName.MIX_REFERENCEABLE, 
                QName.MIX_VERSIONABLE});
        
        nodeTypeRAO.addNodeType(nDef);  
        
        nodeTypeRAO.removeNodeType(nDef.getName());
        NodeTypeDef ntd = nodeTypeRAO.getNodeType(new QName(SOHOCMSURL,"test2"));
        System.out.println("REMOVE TEST:"+ntd.getName());
        assertNull(ntd);
        
        
    }
    
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

}
