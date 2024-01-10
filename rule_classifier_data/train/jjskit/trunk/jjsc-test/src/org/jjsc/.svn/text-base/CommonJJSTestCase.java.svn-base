package org.jjsc;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class CommonJJSTestCase extends TestCase {
	public static void assertEmpty(Iterable<?> collection) {
		assertEmpty("", collection);
	}
	
	public static void assertEmpty(String message, Iterable<?> collection) {
		assertFalse(message, collection.iterator().hasNext());
	}
	
	public static void assertEquals(String expected, Object actual) {
		if(expected == null || actual==null){
			Assert.assertEquals(expected, actual);
			return;
		}
		assertEquals(expected, actual.toString());
	} 
}
