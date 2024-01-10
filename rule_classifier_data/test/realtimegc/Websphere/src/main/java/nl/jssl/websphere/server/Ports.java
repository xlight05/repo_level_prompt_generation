package nl.jssl.websphere.server;

import java.util.HashMap;
import java.util.Map;

public class Ports {
	private Map<String, Port> ports = new HashMap<String, Port>();

	public Port getPort(String name) {
		return ports.get(name);
	}
	
	public void addPort(String name, int number){
		ports.put(name, new Port(name,number));
	}
}
