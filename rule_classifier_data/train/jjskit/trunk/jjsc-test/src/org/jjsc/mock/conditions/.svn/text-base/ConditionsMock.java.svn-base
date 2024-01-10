package org.jjsc.mock.conditions;



public class ConditionsMock {
	//private static int internal;
	public static void main(String[] args){
		A: {
			System.out.println();
			while (System.out.checkError()) {
				try {
					System.out.println("try");
					break A;
				} catch(RuntimeException ex) {
					ex.printStackTrace();
					continue;
				} finally {
					System.out.println("finally");
				}
			}
			System.out.println();
		}
		System.out.println("ret");
	}
}
