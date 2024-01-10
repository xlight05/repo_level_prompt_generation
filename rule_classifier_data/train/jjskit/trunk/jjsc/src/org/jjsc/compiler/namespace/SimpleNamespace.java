package org.jjsc.compiler.namespace;

import org.jjsc.compiler.Namespace;
/**
 * This implementation of namespace is just an adapter and 
 * does nothing. 
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class SimpleNamespace implements Namespace{
	private Namespace parent;
	
	public SimpleNamespace(Namespace parent){
		this.parent = parent;
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.Namespace#getParent()
	 */
	public final Namespace getParent() {
		return parent;
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.Namespace#resolve(java.lang.String)
	 */
	public String resolve(String qName) {
		if(parent!=null){
			return parent.resolve(qName);
		}
		return qName;
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.Namespace#add(java.lang.String)
	 */
	public String add(String qName) {
		return qName;
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.Namespace#get(java.lang.String)
	 */
	public String get(String qName) {
		if(parent!=null){
			return parent.get(qName);
		}
		return null;
	}
}
