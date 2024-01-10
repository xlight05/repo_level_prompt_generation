package jata.port;

import jata.exception.*;
import jata.message.*;

import java.util.concurrent.locks.*;
import java.util.*;

public abstract class Port implements IPipe{
	protected List<ICD> inputTypes;
	protected List<ICD> outputTypes;
	protected IPipe pipe;
	protected ReentrantLock lock;
	protected void addInputType(ICD m) throws JataPortException {
		try{
			Assert.CN(m, "message|V|");
			inputTypes.add(m);
		} catch (Exception ex){
			throw new JataPortException(ex,this,"addInputType()|F|");
		}
	}
	protected void addOutputType(ICD m) throws JataPortException {
		try{
			Assert.CN(m, "message|V|");
			outputTypes.add(m);
		} catch (Exception ex){
			throw new JataPortException(ex,this,"addOutputType()|F|");
		}
	}
	public String getName(){
		return this.getClass().getName();
	}
	
	protected boolean checkInList (ICD m ,List<ICD> list){
		for (ICD l : list){
			if (l.getClass().isInstance(m)){
				return true;
			}
		}
		return false;
	}
	
	protected abstract void inputListening () throws JataException ;
	public synchronized void pipe2Me(Message m) throws JataPortException {
		try{
			lock.lock();
			synchronized(pool){
				
//				DebugOut.println(this + " receive "+m);
				
				Assert.CN(m, "message|V|");
				Assert.C(checkInList(m, inputTypes),
						"checkInList(m,inputTypes)|Fr|");
				pool.offer(m);
				inputListening();
			}
		} catch (Exception ex){
			throw new JataPortException(ex,this,"pipe2Me()|F|");
		} finally {
			lock.unlock();
		}
	}
	
	public void push2Pipe(Message m) throws JataPortException{
		try{
			
			DebugOut.println(this + " send \t"+m);
			
			Assert.CN(m, "message|V|");
			Assert.C(
					checkInList(m,outputTypes), 
					"checkInList(m,outputTypes)|Fr|");
			pipe.pipe2Me(m);
		} catch (Exception ex){
			throw new JataPortException(ex,this,"push2Pipe()|F|");
		}
	}
	
	protected volatile MessageQueue pool;
	
	public Port(){
		inputTypes = new ArrayList<ICD> ();
		outputTypes = new ArrayList<ICD> ();
		pool = new MessageQueue();
		pipe = null;
		lock = new ReentrantLock();
	}
	
	public boolean isMapped(){
		return pipe != null;
	}

	public void setPipe(IPipe pipe) throws JataPortException {
		try{
			Assert.CN(pipe, "pipe|V|");
			this.pipe = pipe;
		} catch (Exception ex){
			throw new JataPortException(ex,this,"setPipe()|F|");
		}
	}
	
	public void UnMap(){
		if (pipe != null) 
			pipe.UnMap();
		pipe = null;
	}
	
	public void lock() {
		lock.lock();
	}
	public void unlock(){
		lock.unlock();
	}
	
	public String ToString (){
		return this.getName();
	}
}
