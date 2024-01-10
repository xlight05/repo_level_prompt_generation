package jata.util;



import java.rmi.RMISecurityManager;

import javax.naming.*;

public class lookUpRemoteServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			System.setProperty("java.security.policy", "client.policy");
			System.setSecurityManager(new RMISecurityManager());
		
			Context nameingContext = new InitialContext();
			NamingEnumeration<NameClassPair> e =nameingContext.list("rmi://127.0.0.1:1199/");
			while(e.hasMore())
				System.out.println(e.next().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
