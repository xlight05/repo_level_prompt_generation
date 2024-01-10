package jata.exception;

import jata.port.RemoteSystemPort;

public class JataRemoteSystemPortException extends JataException{

	public JataRemoteSystemPortException(Exception lastEx,RemoteSystemPort r, String msg) {
		super(lastEx,"<"+r.getName()+">"+ msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
