package jata.exception;


public class JataCommonException extends JataException {

	public static JataCommonException Convert2JataEx(Exception ex){
//		StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        ex.printStackTrace(pw);
//		return new JataCommonException(ex,sw.toString());
		
		String trace="";
		if (JataException.class.isInstance(ex.getCause())) {
			
			trace+="\n+\n";
			
			((JataException)ex.getCause()).insBlank();
		
			trace +=ex.getCause()+"";
		}
		
		return new JataCommonException(ex,trace);
	}
	protected JataCommonException(Exception lastEx,String trace) {
		super(null,":["+lastEx.getClass().getName()+"]"+lastEx.getLocalizedMessage()+" "+lastEx.getMessage()+trace);
	}

	private static final long serialVersionUID = 1L;

}
