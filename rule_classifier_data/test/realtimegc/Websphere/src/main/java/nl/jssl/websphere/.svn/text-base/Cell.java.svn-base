package nl.jssl.websphere;

import java.util.ArrayList;
import java.util.List;

import nl.jssl.websphere.server.ApplicationServer;

public class Cell implements ScriptBuilder{
	private String name;
	private DeploymentManager deploymentManager;
	private List<Cluster> clusters;
	private List<Node> nodes;
	private Resources resources;
	private Environment environment;

	public List<ApplicationServer> getApplicationServers() {
		List<ApplicationServer> allApplicationServers = new ArrayList<ApplicationServer>();
		for (Node node : nodes) {
			allApplicationServers.addAll(node.getApplicationServers());
		}
		return allApplicationServers;
	}

	public String toJython() {
		StringBuilder stringBuilder=new StringBuilder();
		for (Cluster cluster: clusters){
			stringBuilder.append(cluster.toJython());
		}
		for (Node node: nodes){
			stringBuilder.append(node.toJython());
		}
		stringBuilder.append(resources.toJython());
		stringBuilder.append(environment.toJython());
		return stringBuilder.toString();
	}
}
