package org.seamlets.exceptions;

/**
 * 
 * @author Daniel Zwicker
 */
public class ViewIdNotManagedBySeamletsException extends RuntimeException {

	public ViewIdNotManagedBySeamletsException(String message) {
		super(message);
	}
	
	public ViewIdNotManagedBySeamletsException(String message, Throwable cause) {
		super(message, cause);
	}

}
