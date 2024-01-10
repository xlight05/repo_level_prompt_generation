package org.jjsc.compiler;

import java.util.NoSuchElementException;

/**
 * This interface provides virtual variables stack to model real JVM opcode execution model.
 * Such way of modeling allows to compile most effective javascript because its matches
 * java source code and contains all java compiler optimizations.
 * 
 * Note also that implementors should not make any difference between 
 * long 2 slots and single slot values - they both should be treated as
 * monolithic ones.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
interface VirtualStack {
	/**
	 * Pushes value into the stack. The value is become available
	 * for {@link #pop()} operation.
	 * @param value
	 */
	void push(Object value);
	/**
	 * Gets top value from this stack. Implementors should take care
	 * about getting value for this stack in case when its empty.
	 * Generally it should be either throwing an exception or redirect
	 * call to the parent stack if any.
	 * @return top value from this stack.
	 * @throws NoSuchElementException in case when stack is empty.
	 */
	Object pop();
	/**
	 * @return size of this stack.
	 */
	int size();
	/**
	 * The same as {@link #pop()} but doesn't remove the element from the stack.
	 * @return top value from this stack.
	 */
	Object get();
	/**
	 * @return <code>true</code> if there are no element in this stack.
	 * Otherwise returns <code>false</code>.
	 */
	boolean isEmpty();
	/**
	 * Replaces all occurrences on value in the stack with replacement passed.
	 * @param value
	 * @param replacement
	 * @return <code>true</code> if call to this method has change the stack
	 */
	boolean replace(String value, String replacement);
	/**
	 * @param value
	 * @return number of occurrences of value in this stack.
	 */
	int getOccurences(String value);
}