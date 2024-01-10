/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb;

import gloodb.GlooException;

import java.io.Serializable;

/**
 * Wrapper class for query results. If an exception was thrown during query run,
 * getResult will throw a GlooException.
 * 
 * @param T result type
 */
public class QueryResult<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -3337045236199522565L;

	private final T result;
	private final Exception exception;

	/**
	 * Creates a new query result.
	 * 
	 * @param result
	 *            The result.
	 * @param T result type           
	 */
	public QueryResult(T result) {
		this.result = result;
		this.exception = null;
	}

	/**
	 * Creates a new query result, wrapping an exception.
	 * 
	 * @param e
	 *            The exception.
	 */
	public QueryResult(Exception e) {
		this.result = null;
		this.exception = e;
	}
	
	/**
	 * Returns the result. If the query encountered any exception during its
	 * run, a GlooException is thrown:
	 * <ul>
	 * <li>if hasGlooException() is false: the result is any Exception except
	 * GlooException. This exception is caught, wrapped in a GlooException and
	 * then re-thrown.
	 * <li>if hasGlooException() is true: the result is a GlooException which is
	 * re-thrown in kind.
	 * </ul>
	 * 
	 * @param T result type
	 * @return The result.
	 * @throws GlooException
	 *             if the query encountered an exception.
	 */
	public T getResult() {
		if (hasGlooException())
			throw (GlooException) this.exception;
		if (hasException())
			throw new GlooException((Exception) this.exception);
		return result;
	}

	/**
	 * Returns true if the result is a GlooException
	 * 
	 * @return True if the result is a GlooException.
	 */
	public boolean hasGlooException() {
		return (this.exception != null && (this.exception instanceof GlooException));
	}

	/**
	 * Returns true if the result is an Exception other GlooException.
	 * 
	 * @return True if the result is an Exception other than GlooException.
	 */
	public boolean hasException() {
		return (this.exception != null);
	}

	/**
	 * Returns the exception result. If the result is not an exception a
	 * ClassCastException is thrown.
	 * 
	 * @return The exception result.
	 * @throws ClassCastException
	 *             if the result is not exception.
	 */
	public Exception getExceptionResult() {
		if(exception == null && result != null) throw new ClassCastException(String.format("Cannot cast %s to java.lang.Exception", result.getClass().getName()));
		return exception;
	}

	/**
	 * If the query encountered any exception a GlooException is thrown:
	 * <ul>
	 * <li>if hasGlooException() is false: the result is any Exception except
	 * GlooException. It will be wrapped in a GlooException and then thrown.
	 * <li>if hasGlooException() is true: the result is a GlooException and this
	 * is re-thrown
	 * </ul>
	 */
	public QueryResult<T> throwExceptionsIfAny() {
		getResult();
		return this;
	}

	/**
	 * Throws any exception caught in kind, without any wrapping.
	 * @throws Exception The exception caught.
	 */
	public void throwUnwrappedExceptionIfAny() throws Exception {
		if(hasException()) throw exception;
	}

	/**
	 * When an exception is caught, this will throw the exception only if the 
	 * caught exception matches the specified exception class.
	 * @param exceptionClass The exception class.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Exception> QueryResult<T> throwSpecifiedExceptionIfAny(Class<E> exceptionClass) throws E {
		if(hasException() && (exception.getClass().equals(exceptionClass))) {
			throw (E) exception;
		}
		return this;
	}
}
