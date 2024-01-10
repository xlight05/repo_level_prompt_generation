package jata.exception;

public class Assert {
	public static boolean C(boolean b,String msg) throws JataAssertException{
		if (!b){
			throw new JataAssertException("["+msg+ "]can't be false");
		}
		return true;
	}
	
	public static boolean CN(Object p,String msg) throws JataAssertException{
		if (p == null){
			throw new JataAssertException("["+msg+"]can't be null");
		}
		return true;
	}
	
	public static boolean CStrBlankN(String s,String msg) throws JataAssertException{
		CN(s,msg);
		if (s.trim()== ""){
			throw new JataAssertException("["+msg+"]can't be blank");
		}
		return true;
	}
	
	public static boolean CStrEqN(String a,String b,String msg) throws JataAssertException{
		CN(a,msg);
		CN(b,msg);
		if (!a.equals(b)){
			throw new JataAssertException("["+msg+"] is ("+a+"):("+b+") Unequal");
		}
		return true;
	}
}
