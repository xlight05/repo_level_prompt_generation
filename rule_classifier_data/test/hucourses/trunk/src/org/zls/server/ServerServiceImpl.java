package org.zls.server;

import org.zls.client.ServerService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerServiceImpl extends RemoteServiceServlet implements ServerService {

	private static final long serialVersionUID = -9038370371349880039L;

	public String randomFunction() {
		return "Works";
	}

}
