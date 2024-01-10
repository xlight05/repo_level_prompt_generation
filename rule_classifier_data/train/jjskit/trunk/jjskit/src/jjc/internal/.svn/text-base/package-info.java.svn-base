/**
 * Internal implementation package that contains utilities for the JJS launching,
 * configuring and some internal purposes. Most developers should not access any of these tools.
 * 
 * Each class of this package will be treated in special way by JJS compiler so it must follow 
 * some contracts:
 * <ol>
 * <li>No more than one constructor should be present.
 * <li>Constructor (if present) should take either no arguments or single argument or var-arg (Object... or String... etc);
 * <li>It is not allowed to use reflection here because reflection calls will be optimized out on the lowest optimization level.
 * </ol>
 */
package jjc.internal;