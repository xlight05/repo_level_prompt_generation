package nl.sanderspies.cms.repository.nodetype;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.naming.NamingException;

import nl.sanderspies.cms.repository.Manager;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeDefImpl;
import org.apache.jackrabbit.core.nodetype.NodeDefinitionImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.xml.NodeTypeReader;
import org.apache.jackrabbit.core.query.PropertyTypeRegistry;
import org.apache.jackrabbit.name.QName;

public class NodeTypeRAO extends Manager{
	private static NodeTypeRAO instance;
	
	private NodeTypeRAO(){}
	
	public static NodeTypeRAO getInstance(){
		if(instance==null)instance = new NodeTypeRAO();
		return instance;
	}
	
        
        
	/**
	 * Add a NodeType..
	 * 
	 * @param nodeDefinition
	 * @throws NamingException
	 * @throws RepositoryException
	 */
	public void addNodeType(NodeTypeDef nodeDefinition) throws NamingException, RepositoryException{
		Repository repo = getRepository();
		Session session = repo.login();
		Workspace workSpace = session.getWorkspace();
		
		NodeTypeManagerImpl ntmgr;
		try {
			ntmgr = (NodeTypeManagerImpl)workSpace.getNodeTypeManager();
			NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();
			if(!ntreg.isRegistered(nodeDefinition.getName()))
                            ntreg.registerNodeType(nodeDefinition);
			else
				ntreg.reregisterNodeType(nodeDefinition);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvalidNodeTypeDefException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(session!=null){
				session.logout();
			}	
		}
	}
	
	/***
	 * Remove a NodeType
	 * 
	 * @param name
	 * @throws NamingException
	 * @throws RepositoryException
	 */
	public void removeNodeType(QName name) throws NamingException, RepositoryException{
		Repository repo = getRepository();
		Session session = repo.login();
		Workspace workSpace = session.getWorkspace();
		NodeTypeManagerImpl ntmgr;
		try {
			ntmgr = (NodeTypeManagerImpl)workSpace.getNodeTypeManager();
			NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();
			ntreg.unregisterNodeType(name);
                        
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(session!=null){
				session.logout();
			}	
		}
	}
	
	/**
	 * Update a NodeType
	 */
	public void updateNodeType(NodeTypeDef nodeDefinition) throws NamingException, RepositoryException{
		Repository repo = getRepository();
		Session session = repo.login();
		Workspace workSpace = session.getWorkspace();
		NodeTypeManagerImpl ntmgr;
		try {
			ntmgr = (NodeTypeManagerImpl)workSpace.getNodeTypeManager();
			NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();
			ntreg.reregisterNodeType(nodeDefinition);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidNodeTypeDefException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(session!=null){
				session.logout();
			}	
		}
	}
	
	public List getAllNodeTypes() throws NamingException, RepositoryException{
		List nodeTypeList = new ArrayList();
		Repository repo = getRepository();
		Session session = repo.login();
		
		Workspace workSpace = session.getWorkspace();
		
		NodeTypeIterator iter = workSpace.getNodeTypeManager().getAllNodeTypes();
		while(iter.hasNext()){
			NodeType nt = iter.nextNodeType();
			nodeTypeList.add(nt);
			
			System.out.println(nt.getName()+": " + nt);
		}
		
		
		return nodeTypeList;
	}
	
	/*
	 * Get a NodeType
	 */
	public NodeTypeDef getNodeType(QName name) throws NamingException, RepositoryException{
		Repository repo = getRepository();
		Session session = repo.login();
		Workspace workSpace = session.getWorkspace();
		NodeTypeDef nodeTypeDef = null;
		NodeTypeManagerImpl ntmgr;
		try {
			ntmgr = (NodeTypeManagerImpl)workSpace.getNodeTypeManager();
			NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();
			nodeTypeDef = ntreg.getNodeTypeDef(name);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(session!=null){
				session.logout();
			}	
		}
		return nodeTypeDef;
	}
	
}
