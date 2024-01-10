package jata.port;

import jata.message.*;
import jata.exception.*;

public abstract class SystemPort extends Port implements Runnable {
	public static final int AdapterMode = 0;
	public static final int ListenerMode = 1;
	
	protected int PortMode=0;
	public SystemPort (){
		super();
	}
	protected void inputListening() throws JataException{
		if (PortMode == AdapterMode){
			thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		}else if (PortMode == ListenerMode){
			sendPackage();
		}
	}
	protected Thread thread;
	protected AdapterPackage Adapter(AdapterPackage pkg) throws Exception{
		throw new JataNeverReachException(null,this,"Adapter()|F|");
	}
	public void run(){
		try {
			adapterProc();
		} catch (JataPortException e) {
			System.out.println(e.toString());
		}
	}
	
	protected void adapterProc() throws JataPortException{
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
			
			AdapterPackage re = Adapter(pkg);
			Message recv = null;
			if (re != null){
				for (ICD m : outputTypes){
					try{
						Message t = m.newOne();
						t.decode(re.stream);
						
						recv = t;
						break;
					} catch (Exception ex){
						DebugOut.println(this +(String) re.stream+" last msg isn't "+m);
					}
				}
				
				Assert.CN(recv, "recv");
				push2Pipe(recv);
			}
		}catch (Exception ex){
			throw new JataPortException(ex,this,"AdapterProc()|F|");
		}
	}
	
	public void SendProc(AdapterPackage pkg) throws Exception{
		throw new JataNeverReachException(null,this,"SendProc()|F|");
	}
	
	protected void sendPackage() throws JataException{
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
			
			SendProc(pkg);
		}catch (Exception ex){
			throw new JataPortException(ex,this,"sendPackage()|F|");
		}
	}
	
	public void receiveProc(Object obj){
		try {
			receivePackage(new AdapterPackage(obj) );
		} catch (JataException ex){
			System.out.println(ex);
		}
	}
	
	protected void receivePackage(AdapterPackage re) throws JataException {
		try{
			Message recv = null;
			if (re != null){
				for (ICD m : outputTypes){
					try{
						Message t = m.newOne();
						t.decode(re.stream);
						
						recv = t;
						break;
					} catch (Exception ex){
						ex.printStackTrace();
						DebugOut.println(this +" last msg isn't "+m);
					}
				}
				push2Pipe(recv);
			}
		}catch (Exception ex){
			throw new JataPortException(ex,this,"receivePackage()|F|");
		}
	}
}
