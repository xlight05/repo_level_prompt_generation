package org.jjsc.compiler;

import org.jjsc.CompilationModuleException;


public class CompilationError extends RuntimeException {
	private static final long serialVersionUID = -4596695040086096574L;
	
	private String declaringClass;
	private String methodName;
	private String fileName;
	private int lineNumber;

	public CompilationError() {
		this("Internal error");
	}

	public CompilationError(String message, Throwable cause) {
		super(message, cause);
	}

	public CompilationError(String message) {
		super(message);
	}

	public CompilationError(Throwable cause) {
		super(cause);
	}
	
	public void setTarget(String declaringClass, String methodName, String fileName, int lineNumber) {
		this.declaringClass = declaringClass;
		this.methodName = methodName;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}

	public StackTraceElement toStackTraceElement() {
		if(declaringClass==null){
			return CompilationModuleException.createCommonStackTrace(this);
		}
		return new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
	}
}
