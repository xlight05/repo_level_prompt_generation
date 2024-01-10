package org.jjsc.compiler;
/**
 * This is good optimization for the compile-time to reduce the length of the 
 * variables, methods and classes names. Members of the single namespace can refer to
 * each other with short names and this will not lead to any conflicts.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public interface Namespace {
	/**
	 * @return parent namespace to this one or <code>null</code> if there aren't any.
	 */
	Namespace getParent();
	/**
	 * Tries to resolve the full qualified name passed.
	 * If name is not found neither in this namespace nor in parent one
	 * that new short name will be generated for the passed full one.
	 * Note that namespace generated name will be put into is not specified by this 
	 * interface contract and is dependent on implementation.
	 * @param qName - fully qualified name to resolve
	 * @return short unique name for the passed fully qualified name.
	 */
	String resolve(String qName);
	/**
	 * Adds fully qualified name to this namespace (not in parent one). 
	 * @param qName
	 * @return short unique name for the passed fully qualified name. 
	 */
	String add(String qName);
	/**
	 * Works the same as resolve but doesn't add any link and simply returns 
	 * <code>null</code> in case if qName is not found neither in this namespace 
	 * nor in parent one.
	 * @param qName
	 * @return
	 */
	String get(String qName);
}
