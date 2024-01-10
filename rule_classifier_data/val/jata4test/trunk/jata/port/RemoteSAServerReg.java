package jata.port;


import javax.naming.*;

public class RemoteSAServerReg {
	protected IRemoteSA remoteSA;
	protected String regName;
	public RemoteSAServerReg(IRemoteSA remoteSA,String regName){
		this.remoteSA = remoteSA;
		this.regName = regName;
	}
	
	public void start() throws Exception{
		
		Context namingContext = new InitialContext();
		System.out.println("rmi://127.0.0.1:1199/"+regName+"<"+remoteSA.getClass().getName()+">");
		try{
			namingContext.bind("rmi://127.0.0.1:1199/"+regName, remoteSA);
		} catch (NameAlreadyBoundException e){
			namingContext.rebind("rmi://127.0.0.1:1199/"+regName, remoteSA);
		}
		System.out.println("register ["+regName+"] to rmi success!");
	}
}
