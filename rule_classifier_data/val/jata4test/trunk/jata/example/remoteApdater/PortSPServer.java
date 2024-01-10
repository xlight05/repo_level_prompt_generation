package jata.example.remoteApdater;

import java.io.*;
import java.rmi.RemoteException;

import jata.port.*;

public class PortSPServer extends RemoteSAServer implements IRemoteSA{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected PortSPServer() throws RemoteException {
		super();
	}


	public Object Adapter(AdapterPackage pkg) throws Exception {
		
		FileWriter fw = new FileWriter("pwd.txt");
		String s = (String) pkg.stream;
		fw.write(s,0,s.length());
		fw.flush();
		fw.close();
		
		return (Object)"OK";
	}

}
