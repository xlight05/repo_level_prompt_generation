package nl.jssl.websphere;

import java.util.Iterator;

import nl.jssl.websphere.resources.JDBC;
import nl.jssl.websphere.resources.JMS;
import nl.jssl.websphere.resources.Mail;
import nl.jssl.websphere.resources.ResourceAdapter;
import nl.jssl.websphere.resources.URL;

public class Resources implements ScriptBuilder{
    private JMS jms;
    private JDBC jdbc;
    private ResourceAdapter resourceAdapter;
    private AsynchronousBean asynchronousBean;
    private Mail mail;
    private URL urlResource;
    
    public JMS getJms() {
        return jms;
    }
    public JDBC getJdbc() {
        return jdbc;
    }
    public ResourceAdapter getResourceAdapter() {
        return resourceAdapter;
    }
    public AsynchronousBean getAsynchronousBean() {
        return asynchronousBean;
    }
    public Mail getMail() {
        return mail;
    }
    public URL getUrlResource() {
        return urlResource;
    }
    
	public String toJython() {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}
