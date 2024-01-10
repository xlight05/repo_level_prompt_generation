package com.googlecode.wicketbits.tests;

import com.googlecode.wicketbits.reflection.ClassInfo;

import junit.framework.TestCase;

public class ReflectionTest extends TestCase {

	public void testParent() {
		ClassInfo thisClass = ClassInfo.parse(ReflectionTest.class);

		ClassInfo parentClass = thisClass.getParent();

		assertNotNull(parentClass);
	}

	public void testObjectParent() {
		ClassInfo thisClass = ClassInfo.parse(Object.class);

		ClassInfo parentClass = thisClass.getParent();

		assertNull(parentClass);
	}
}
