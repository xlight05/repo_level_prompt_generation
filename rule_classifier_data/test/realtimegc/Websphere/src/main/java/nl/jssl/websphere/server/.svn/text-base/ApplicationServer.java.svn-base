package nl.jssl.websphere.server;

import static nl.jssl.websphere.Output.template;
import nl.jssl.websphere.Node;
import nl.jssl.websphere.ScriptBuilder;
import nl.jssl.websphere.ValueSet;

import com.googlecode.aluminumproject.context.Context;

/**
 * @author Sander
 * 
 */
public class ApplicationServer implements ScriptBuilder{
	private String name;

	private ProcessDefinition procesDefinition;
	private WebContainer webContainer;

	private Ports ports=new Ports();
	
	private Node node;

	
	public ApplicationServer(String name, Node node) {
		super();
		this.name = name;
		this.node=node;
		node.addApplicationServer(this);
	}

	public String create() {
		return template("nl/jssl/websphere/server/createApplicationServer.xml").addValues(new ValueSet() {
			public void set(Context context) {
				context.setVariable("nodeName", node.getName());
				context.setVariable("serverName", name);
				context.setVariable("httpNonSecurePort", ports.getPort("httpNonSecurePort").getNumberAsString());
				context.setVariable("httpSecurePort", ports.getPort("httpSecurePort").getNumberAsString());
			}
		}).getText();
	}

	public String start() {
		return template("nl/jssl/websphere/server/startApplicationServer.xml").addValues(new ValueSet() {
			public void set(Context context) {
				context.setVariable("nodeName", node.getName());
				context.setVariable("serverName", name);
			}
		}).getText();
	}
	
	public Ports getPorts() {
		return ports;
	}

	public String toJython() {
		return create();
	}
}
