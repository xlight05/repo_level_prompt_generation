package org.jjsc;
/**
 * This exception is thrown when module cannot be found in the VM for any reason.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class ModuleNotFoundException extends LoadModuleException {
	private static final long serialVersionUID = -1093253165412200671L;
	
	public ModuleNotFoundException(String message) {
		super(message);
	}	
}
