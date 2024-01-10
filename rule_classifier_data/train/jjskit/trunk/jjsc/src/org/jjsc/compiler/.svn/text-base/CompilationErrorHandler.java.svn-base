package org.jjsc.compiler;
/**
 * This is handler class for exception and errors occurred due compilation.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public interface CompilationErrorHandler {
	/**
	 * This method is call when compilation error occurs.
	 * It is guaranteed by contract that this method will be call
	 * prefer to {@link #onError(Throwable)} one only for this type 
	 * of {@link Throwable}s. Also contract requires that this method 
	 * will not throw any exceptions.
	 * @param error
	 */
	void onCompilationError(CompilationError error);
	/**
	 * This method is call for all other types of exceptions (except
	 * {@link CompilationError} one). Contract requires that this method 
	 * will not throw any exceptions. 
	 * @param error
	 */
	void onError(Throwable error);
}
