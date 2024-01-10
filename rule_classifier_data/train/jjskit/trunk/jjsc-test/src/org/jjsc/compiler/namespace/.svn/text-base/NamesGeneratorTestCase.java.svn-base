package org.jjsc.compiler.namespace;

import junit.framework.TestCase;

public class NamesGeneratorTestCase extends TestCase {
	public void testInternal(){
		NamesGenerator gen = NamesGenerator.createForTypesNames();
		assertEquals("Object0", gen.get("java.lang.Object"));
		assertEquals("Object1", gen.get("java.lang.Object"));
		assertEquals("Object2", gen.get("java.lang.Object"));
		assertEquals("String0", gen.get("java.lang.String"));
		assertEquals("CustomType", gen.get("java.lang.CustomType"));
	}
	
	public void testLocal(){
		int i = 0;
		NamesGenerator gen = NamesGenerator.createLocal();
		assertEquals("a", gen.get(""));
		assertEquals("b", gen.get(""));
		assertEquals("c", gen.get(""));
		assertEquals("d", gen.get(""));
		String rez;
		do {
			rez = gen.get("");
		}
		while(rez.length()==1);
		assertEquals("aa", rez);
		assertEquals("ab", gen.get(""));
		while(i++<50000){
			rez = gen.get("");
			assertNotNull(rez);
			assertFalse("a".equals(rez));
			assertFalse("ab".equals(rez));
			assertFalse("in".equals(rez));
			assertFalse("new".equals(rez));
		}
	}
}
