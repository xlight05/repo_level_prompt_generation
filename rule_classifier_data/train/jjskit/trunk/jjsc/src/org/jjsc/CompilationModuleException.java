package org.jjsc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jjsc.compiler.CompilationError;
import org.jjsc.utils.AssertUtils;
/**
 * Combined exception - the result of multiple errors in the module compilation.
 * The main goal of this class is to provide easy way to print the results of wrong compilation
 * with standard java methods.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class CompilationModuleException extends LoadModuleException {
	private static final long serialVersionUID = 7870516929489916074L;
	
	private List<Throwable> errors;
	/**
	 * Constructs the {@link CompilationModuleException} with list of errors.
	 * @param errors
	 * @throws NullPointerException if first parameter is <code>null</code> 
	 * or some of the exceptions are <code>null</code>s.
	 */
	CompilationModuleException(List<Throwable> errors) {
		super("Compilation error");
		AssertUtils.assertNotNull(errors,"errors cannot be NULL");
		AssertUtils.assertNotContainsNull(errors,"Some of the errors are NULLs");
		this.errors = new ArrayList<Throwable>(errors);
		fillInStackTrace();
	}
	/**
	 * Fills stack traces with custom errors information.
	 * @see CompilationError#toStackTraceElement()
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		if(errors==null)return this;
		List<StackTraceElement> traces = new LinkedList<StackTraceElement>();
		for(Throwable throwable : errors){
			traces.add(createStackTrace(throwable));
		}
		setStackTrace(traces.toArray(new StackTraceElement[traces.size()]));
		return this;
	}

	private StackTraceElement createStackTrace(Throwable throwable) {
		if(throwable instanceof CompilationError){
			CompilationError error = (CompilationError)throwable;
			return error.toStackTraceElement();
		}
		return createCommonStackTrace(throwable);
	}

	public static StackTraceElement createCommonStackTrace(Throwable throwable) {
		StackTraceElement delegate = getFirstJSSTrace(throwable);
		String message = throwable.toString()+' '+delegate.getClassName();
		return new StackTraceElement(message,
				delegate.getMethodName(),
				delegate.getFileName(),
				delegate.getLineNumber());
	}

	private static StackTraceElement getFirstJSSTrace(Throwable error) {
		final String PKG = CompilationModuleException.class.getPackage().getName();
		for(StackTraceElement element : error.getStackTrace()){
			if(element.getClassName().startsWith(PKG)){
				return element;
			}
		}
		return error.getStackTrace()[0];
	}
}
