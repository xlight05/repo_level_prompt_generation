package jata.alt;

import jata.exception.*;
import jata.message.*;

public class Timer implements IAltBranch{
	private long now;
	private long last = 0;
	public void start(){
		last = new java.util.Date().getTime();
	}
	public void lock() {
		now = new java.util.Date().getTime();
	}

	public Message waitHandle(Message m) throws JataException {
		if ( last == 0 )
			throw new JataTimerException(null,"not start yet!");
		
		
		if (!SetTimeMessage.class.isInstance(m))
			throw new JataTimerException(null,"not SetTimeMessage");
			
		SetTimeMessage st = (SetTimeMessage) m;
		
		if (st.getTime() < 20)
			throw new JataTimerException(null, "Time too sort");

		if (st.getTime() + last < now)
			return new TimeOutMessage();

		
		return null;
	}

	public void unlock() {
		
	}
}
