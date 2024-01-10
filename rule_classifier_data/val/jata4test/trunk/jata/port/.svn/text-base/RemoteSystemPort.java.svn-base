package jata.port;

import jata.exception.*;
import jata.message.ICD;
import jata.message.Message;

import java.rmi.*;

import javax.naming.*;

public abstract class RemoteSystemPort extends Port implements Runnable {
	protected IRemoteSA remoteSA = null;
	
	public void init(String rmiUrl) throws JataException {
		try{
			System.setProperty("java.security.policy", "client.policy");
			System.setSecurityManager(new RMISecurityManager());
		
			Context nameingContext = new InitialContext();
			remoteSA = (IRemoteSA) nameingContext.lookup(rmiUrl);
		} catch (Exception e) {
			throw new JataRemoteSystemPortException(e,this,"init()|F|");
		}
	}
	public RemoteSystemPort (){
		super();
	}
	
	protected Thread thread;
	
	@Override
	protected void inputListening() {
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void run(){
		try {
			adapterProc();
		} catch (JataException e) {
			System.out.println(e.toString());
		}
	}

	public void adapterProc() throws JataException {
		try{
			Message msg;
			synchronized(pool){
				Assert.C(!pool.isEmpty(),"!pool.isEmpty()|Fr|");
				msg = pool.poll();
			}
			AdapterPackage pkg = new AdapterPackage();
			pkg.MessageType = msg.getTypeName();
			pkg.portName = this.getName();
			pkg.stream = msg.code();
			pkg.streamType = pkg.stream.getClass().getName();
			
			Object re = remoteSA.Adapter(pkg);
			Message recv = null;
			if (re != null){
				for (ICD m : outputTypes){
					try{
						Message t = m.newOne();
						t.decode(re);
						
						recv = t;
						break;
					} catch (Exception ex){
						DebugOut.println(this + " last msg isn't "+m);
					}
				}
				
				Assert.CN(recv, "recv");
				push2Pipe(recv);
			}
		}catch (Exception ex){
			throw new JataRemoteSystemPortException(ex,this,"AdapterProc()|F|");
		}
	}

}
