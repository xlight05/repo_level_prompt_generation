package jata.exception;

import jata.message.Message;
import jata.port.Port;


public class JataNeverReachException extends JataException {

	public JataNeverReachException(Exception lastEx, Message m,String msg) {
		super(lastEx,"<"+m.getTypeName()+">"+ msg);
	}

	public JataNeverReachException(Exception lastEx, Port m,String msg) {
		super(lastEx,"<"+m.getName()+">"+ msg);
	}
	private static final long serialVersionUID = 1L;

}
