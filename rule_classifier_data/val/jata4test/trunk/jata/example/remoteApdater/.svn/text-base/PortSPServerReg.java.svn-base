package jata.example.remoteApdater;

import jata.port.*;

public class PortSPServerReg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IRemoteSA isa = new PortSPServer();
			RemoteSAServerReg reg =new RemoteSAServerReg(isa,"SP");
			reg.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
