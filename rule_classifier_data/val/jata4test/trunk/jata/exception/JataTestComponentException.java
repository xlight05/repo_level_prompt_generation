package jata.exception;

import jata.Component.TestComponent;

public class JataTestComponentException extends JataException {

	public JataTestComponentException(Exception lastEx,TestComponent tc ,String msg) {
		super(lastEx,"<"+tc.getClass().getName()+">"+ msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
