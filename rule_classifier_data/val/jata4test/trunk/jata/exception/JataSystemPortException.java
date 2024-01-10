package jata.exception;

import jata.port.SystemPort;

public class JataSystemPortException extends JataException {

	public JataSystemPortException(Exception lastEx,SystemPort sys, String msg) {
		super(lastEx, "<"+sys.getName()+">[" + msg+"]");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
