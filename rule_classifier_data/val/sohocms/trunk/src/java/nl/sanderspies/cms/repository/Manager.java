package nl.sanderspies.cms.repository; 

import java.util.Hashtable;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.jackrabbit.core.jndi.RegistryHelper;

public abstract class Manager {
    private static Context ctx = null;
    private static final String SOHOCMSURL = "http://www.sanderspies.com/cms";
    private static final String SOHOCMSPREFIX = "sohocms";
    
    /**
     * Get the Repository.
     * 
     * @return Repository.
     * @throws NamingException
     * @throws RepositoryException
     */
    protected static Repository getRepository() throws NamingException, RepositoryException {
        if (ctx == null) {
            try{Hashtable environment = new Hashtable();
            
            
            environment.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.jackrabbit.core.jndi.provider.DummyInitialContextFactory");
            environment.put(Context.PROVIDER_URL, "localhost");
            ctx = new InitialContext(environment);
            RegistryHelper.registerRepository(ctx, "SimpleCMS", "workspace.xml", "SimpleCMSxxx", true);
            
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
        Repository r = (Repository) ctx.lookup("SimpleCMS");
        Session session = r.login();
        Workspace workspace = session.getWorkspace();
        NamespaceRegistry nsRegistry = workspace.getNamespaceRegistry();
        String testPrefix = null;
        try {
            testPrefix = nsRegistry.getPrefix(SOHOCMSURL);
        } catch (NamespaceException nse) {
            //namespace does not exist....continue
        }
        if (testPrefix == null)
            nsRegistry.registerNamespace(SOHOCMSPREFIX, SOHOCMSURL);
        else if (!testPrefix.equals(SOHOCMSPREFIX))
            throw new RepositoryException(SOHOCMSPREFIX + " is not registered with the expected URI");
        
        return r;
    }
	
}
