package nl.jssl.websphere;

import java.util.List;

import nl.jssl.websphere.environment.NameSpaceBindingTests;
import nl.jssl.websphere.environment.VirtualHost;

public class Environment implements ScriptBuilder{
    private List<NameSpaceBindingTests> nameSpaceBindings;
    private List<VirtualHost> virtualHosts;
    
	public String toJython() {
		return null;
	}
    
}
