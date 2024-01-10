package org.jjsc;
/**
 * This exception occurs when module loading fails either because of 
 * access or compilation problems.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class LoadModuleException extends JJSException {
	private static final long serialVersionUID = 1L;

	public LoadModuleException() {
		super("Internal error");
	}

	public LoadModuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoadModuleException(String message) {
		super(message);
	}

	public LoadModuleException(Throwable cause) {
		super(cause);
	}

}
