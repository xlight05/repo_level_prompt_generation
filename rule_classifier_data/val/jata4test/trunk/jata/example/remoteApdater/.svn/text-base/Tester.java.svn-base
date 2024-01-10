package jata.example.remoteApdater;

import jata.exception.*;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			MyTestCase mycase = new MyTestCase();
			mycase.start();
			
			System.out.println("----------------------------------");
			System.out.println("MyTestCase result:"+mycase.getVerdict());
			
		} catch (JataException e) {
			System.out.println(e.toString());
		}
	}

}
