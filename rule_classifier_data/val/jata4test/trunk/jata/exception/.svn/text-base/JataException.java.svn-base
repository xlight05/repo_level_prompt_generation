package jata.exception;

import java.util.*;

public abstract class JataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int blank =0;
	protected List<JataException> exceptionStack;

	public List<JataException> getExceptionStack() {
		return exceptionStack;
	}

	protected void initExcetionStack(Exception lastEx) {
		exceptionStack = new ArrayList<JataException>();
		if (lastEx != null) {
			JataException jEx = null;
			if (lastEx instanceof JataException)
				jEx = (JataException) lastEx;
			else
				jEx = JataCommonException.Convert2JataEx(lastEx);
			exceptionStack.addAll(jEx.getExceptionStack());
			exceptionStack.add(jEx);
		}
	}
	public JataException(Exception lastEx,String msg){
		super(msg);
		initExcetionStack(lastEx);
	}
	public String toStringOne (){
		String re="";
		re += getBlanks()+"("+this.getClass().getName()+")"+this.getMessage()+"\n";
		StackTraceElement [] ss = this.getStackTrace();
		for (StackTraceElement s : ss){
			if (s.isNativeMethod())
				re += getBlanks()+"--- "+s.getClassName()+"(Native Method)\n";
			else
				re += getBlanks()+"--- "+s.getClassName()+"."+s.getMethodName()+"("+s.getFileName()+":"+s.getLineNumber()+")"+"\n";
		}
		return re;
	}
	public String toString(){
		String re="";
		for (JataException ex : exceptionStack ){
			ex.setBlank(blank);
			re +=ex.toStringOne() + "\n";
		}
		re+=this.toStringOne();
		return re;
	}
	protected String getBlanks() {
		String re="";
		for (int i=0;i<blank;i++)
			re +="\t";
		return re;
	}
	public void insBlank(){
		blank++;
	}
	public void delBlank(){
		blank--;
	}
	public void setBlank(int k){
		blank = k;
	}
	public int getBlank(){
		return blank;
	}
}
