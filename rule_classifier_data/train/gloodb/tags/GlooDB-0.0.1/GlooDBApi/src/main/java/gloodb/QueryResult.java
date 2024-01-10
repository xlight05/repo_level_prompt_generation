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
 */
public class QueryResult implements Serializable {
	private static final long serialVersionUID = -3337045236199522565L;

	private final Serializable result;

	/**
	 * Creates a new query result.
	 * 
	 * @param result
	 *            The result.
	 */
	public QueryResult(Serializable result) {
		this.result = result;
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
	 * @return The result.
	 * @throws GlooException
	 *             if the query encoutered an exception.
	 */
	public Serializable getResult() {
		if (hasGlooException())
			throw (GlooException) this.result;
		if (hasException())
			throw new GlooException((Exception) this.result);
		return result;
	}

	/**
	 * Returns true if the result is a GlooException
	 * 
	 * @return True if the result is a GlooException.
	 */
	public boolean hasGlooException() {
		return (this.result instanceof GlooException);
	}

	/**
	 * Returns true if the result is an Exception other GlooException.
	 * 
	 * @return True if the result is an Exception other than GlooException.
	 */
	public boolean hasException() {
		return (this.result instanceof Exception);
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
		return (Exception) this.result;
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
	public void throwExceptionsIfAny() {
		getResult();
	}

	/**
	 * Throws any exception caught in kind, without any wrapping.
	 * @throws Exception The exception caught.
	 */
	public void throwUnwrappedExceptionIfAny() throws Exception {
		if(hasException()) throw ((Exception)result);
	}

	/**
	 * When an exception is caught, this will throw the exception only if the 
	 * caught exception matches the specified exception class.
	 * @param exceptionClass The exception class.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Exception> QueryResult throwSpecifiedExceptionIfAny(Class<T> exceptionClass) throws T {
		if(hasException() && (result.getClass().equals(exceptionClass))) {
			throw (T) result;
		}
		return this;
	}
}
