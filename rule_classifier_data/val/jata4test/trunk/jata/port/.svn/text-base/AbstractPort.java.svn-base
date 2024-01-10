package jata.port;

import jata.alt.IAltBranch;
import jata.message.*;
import jata.exception.JataPortException;

public abstract class AbstractPort extends Port implements IAltBranch{
	public AbstractPort (){
		super();
	}
	public void send(Message m) throws JataPortException{
		try{
			push2Pipe(m);
		}catch (Exception ex){
			throw new JataPortException(ex,this,"send()|F|");
		}
	}
	
	//receive
	public Message waitHandle(Message m) throws JataPortException{
		try {
			synchronized (pool) {
				if (pool.isEmpty())
					return null;
				Message msg = pool.peek();
				if (msg.getClass().isInstance(m)) {
					Message re = pool.poll();
					return re;
				} else {
					return null;
				}
			}
		} catch (Exception ex){
			throw new JataPortException(ex,this,"receive()|F|");
		}
	}
	
	protected void inputListening() {
		
	}

}
