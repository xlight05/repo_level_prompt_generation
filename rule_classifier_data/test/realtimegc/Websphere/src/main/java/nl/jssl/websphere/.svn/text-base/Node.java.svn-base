package nl.jssl.websphere;

import java.util.ArrayList;
import java.util.List;

import nl.jssl.websphere.server.ApplicationServer;
import nl.jssl.websphere.server.WebServer;

public class Node implements ScriptBuilder{
	private final String name;
	private final List<ApplicationServer> applicationServers=new ArrayList<ApplicationServer>();
	private List<WebServer> webServers=new ArrayList<WebServer>();

	
	public Node(String name) {
		super();
		this.name = name;
	}

	public List<ApplicationServer> getApplicationServers() {
		return applicationServers;
	}

	public String getName() {
		return name;
	}

	public List<WebServer> getWebServers() {
		return webServers;
	}

	public String get() {
		return String
				.format("node = AdminConfig.getid(\"/Node:\" + %s + \"/\")\n"
						+ "print \"checking for existence of node \" + %s\n"
						+ "if len(node) == 0:\n"
						+ "   print \"ex1: Error -- node not found for name \" + %s\n"
						+ "   return\n", name,name,name);
	}
	
	public void addApplicationServer(ApplicationServer applicationServer){
		applicationServers.add(applicationServer);
	}

	public String toJython() {
		StringBuilder stringBuilder=new StringBuilder();
		for (ApplicationServer applicationServer:applicationServers){
			stringBuilder.append(applicationServer.toJython());
		}
		for (WebServer webServer:webServers){
			stringBuilder.append(webServer.toJython());
		}
		return stringBuilder.toString();
	}
}
