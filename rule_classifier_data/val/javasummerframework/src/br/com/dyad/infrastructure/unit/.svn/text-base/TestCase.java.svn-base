package br.com.dyad.infrastructure.unit;

import br.com.dyad.infrastructure.aspect.UserInfoAspect;

public abstract class TestCase {

	public static void assertTrue(boolean expression){
		if (!expression) {
			throw new AssertionException("Assertion error!");
		}
	}
	
	public static void assertFalse(boolean expression){
		if (expression) {
			throw new AssertionException("Assertion error!");
		}
	}
	
	public static String getDataBase(){
		return UserInfoAspect.getDatabase();
	}
	
}
