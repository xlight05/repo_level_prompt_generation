package jata.exception;

public class DebugOut {
	public static boolean isDebugMode = true;
	
	public static void println(String str){
		if (isDebugMode)
			System.out.println(str);
	}
}
