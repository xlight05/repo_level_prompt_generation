package nl.jssl.websphere.server;

import org.junit.Test;

import nl.jssl.websphere.Node;


public class ApplicationServerTests {
	@Test
	public void testStartServer() {
		Node node=new Node("node1");
		ApplicationServer applicationServer=new ApplicationServer("server1",node);
		System.out.println(applicationServer.start());
	}
	
	@Test
	public void testToJython() {
		Node node=new Node("node1");
		ApplicationServer applicationServer=new ApplicationServer("server1",node);
		applicationServer.getPorts().addPort("httpNonSecurePort", 9080);
		applicationServer.getPorts().addPort("httpSecurePort", 9043);
		System.out.println(applicationServer.toJython());
	}
}
