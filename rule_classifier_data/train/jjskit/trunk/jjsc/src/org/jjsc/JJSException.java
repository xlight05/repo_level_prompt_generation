package org.jjsc;
/**
 * Common superclass for all checked exceptions in JJSC.
 * Not intended to be instantiated.
 * @author alex.bereznevatiy@gmail.com
 */
public abstract class JJSException extends Exception {
	private static final long serialVersionUID = -8044450288384594887L;

	public JJSException() {
		super();
	}

	public JJSException(String message, Throwable cause) {
		super(message, cause);
	}

	public JJSException(String message) {
		super(message);
	}

	public JJSException(Throwable cause) {
		super(cause);
	}

}
