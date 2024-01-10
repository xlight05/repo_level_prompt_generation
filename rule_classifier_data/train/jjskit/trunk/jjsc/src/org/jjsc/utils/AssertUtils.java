package org.jjsc.utils;

import java.io.File;



/**
 * Provides a lot of useful assertions to test different kinds of conditions.
 * Most valuable part of JJSC validation API.
 *  
 * @author alex.bereznevatiy@gmail.com
 */
public final class AssertUtils {
	/**
	 * @param object to test
	 * @param description of the exception
	 * @throws NullPointerException if target object is <code>null</code>.
	 */
	public static void assertNotNull(Object obj,String description) {
		if(obj==null){
			throw new NullPointerException(description);
		}
	}
	/**
	 * @param collection to test
	 * @param description of the exception
	 * @throws NullPointerException if collection contains <code>null</code>s.
	 */
	public static void assertNotContainsNull(Iterable<?> collection, String description) {
		for(Object o : collection){
			if(o==null){
				throw new NullPointerException(description);
			}
		}
	}
	/**
	 * @param collection to test
	 * @param description of the exception
	 * @throws IllegalArgumentException in case when collection 
	 * is empty or <code>null</code>.
	 */
	public static void assertNotEmpty(Iterable<?> collection,String description) {
		if(collection==null||
			!collection.iterator().hasNext()){
			throw new IllegalArgumentException(description);
		}
	}
	/**
	 * @param location to test
	 * @param description of the exception
	 * @throws IllegalArgumentException in case when location is null or not exists.
	 */
	public static void assertExists(File location, String description) {
		if(location==null||!location.exists()){
			throw new IllegalArgumentException(description);
		}
	}
	/**
	 * @param location to test
	 * @param description of the exception
	 * @throws IllegalArgumentException in case when location is null 
	 * or not exists or not a directory.
	 */
	public static void assertDirectory(File location, String description) {
		assertExists(location,description);
		if(!location.isDirectory()){
			throw new IllegalArgumentException(description);
		}
	}
	/**
	 * @param check
	 * @param description of the exception
	 * @throws IllegalStateException in case if check is <code>false</code>.
	 */
	public static void assertTrue(boolean check, String description) {
		if(!check){
			throw new IllegalStateException(description);
		}
	}
}
