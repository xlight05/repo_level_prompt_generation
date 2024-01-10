package org.jjsc.compiler.namespace;

import org.jjsc.compiler.Namespace;
/**
 * Provides wrapper for namespace that describes instance (non-static)
 * method or constructor.
 *  
 * @author alex.bereznevatiy@gmail.com
 */
public class InstanceMethodNamespace extends LocalNamespace {
	
	private static final String THIS_KEYWORD = "this";

	public InstanceMethodNamespace(String desc, Namespace parent) {
		super(desc, parent);
	}

	@Override
	public String getLocalVar(int var) {
		if(var==0){
			return THIS_KEYWORD;
		}
		return super.getLocalVar(var-1);
	}
}
