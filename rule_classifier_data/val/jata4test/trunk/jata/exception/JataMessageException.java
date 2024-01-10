package jata.exception;

import jata.message.Message;

public class JataMessageException extends JataException {

	public JataMessageException(Exception lastEx,Message m, String msg) {
		super(lastEx, "<"+m.getTypeName()+">["+ msg+"]");
	}
	public JataMessageException(Exception lastEx,String m, String msg) {
		super(lastEx, "<"+m+">["+ msg+"]");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
