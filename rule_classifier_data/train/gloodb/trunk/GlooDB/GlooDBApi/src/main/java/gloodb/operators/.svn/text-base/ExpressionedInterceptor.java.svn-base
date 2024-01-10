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
package gloodb.operators;

import gloodb.GlooException;
import gloodb.Interceptor;
import gloodb.Repository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Interceptor implementation which uses expressions to implement the before / after and around advice.
 *
 */
public class ExpressionedInterceptor implements Cloneable, Interceptor<Serializable> {
	/**
	 * Tagging interface for interceptor methods. The interceptor invokes the interceptor methods around 
	 * the specified point cuts.
	 *
	 * @param <V> The intercepted value type.
	 */
	@SuppressWarnings("serial")
	public static abstract class InterceptorMethod<V extends Serializable> extends Expression implements Cloneable, Serializable {

		/**
		 * Intercepts values of the specified type.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void evaluate(Object... parameters) throws Exception {
			try {
				// Only intercept values of the specified type.
				evaluate((V)parameters[0], (Class<? extends Annotation>)parameters[1], (Repository)parameters[2]);
			} catch(ClassCastException e) {
				return;
			}
		}
		
		@Override
		public Object clone() {
			try {
				return super.clone();
			} catch(CloneNotSupportedException cnse) {
				throw new GlooException(cnse);
			}
		}
		
		/**
		 * Around advice callback.
		 * @param object The intercepted object.
		 * @param clazz The pre / post specification (PreCreate, PreRemove, etc.)
		 * @param repository The repository.
		 * @throws Exception if any exception is caught during processing.
		 */
		protected abstract void evaluate(V object, Class<? extends Annotation> clazz, Repository repository) throws Exception;
	}
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<Class<? extends Annotation>, Expression> beforeMethods;
	private HashMap<Class<? extends Annotation>, Expression> afterMethods;
	
	/**
	 * Creates an expressioned interceptor instance.
	 */
	public ExpressionedInterceptor() {
		super();
		this.beforeMethods = new HashMap<Class<? extends Annotation>, Expression>();
		this.afterMethods = new HashMap<Class<? extends Annotation>, Expression>();
	}
	
	/**
	 * Adds a before advice to the specified  pre / post callbacks.
	 * @param beforeMethod The before interceptor method.
	 * @param classes The point cut classes.
	 * @return This interceptor.
	 */
	public ExpressionedInterceptor adviceBefore(InterceptorMethod<? extends Serializable> beforeMethod, Class<? extends Annotation>... classes) {
		for(Class<? extends Annotation> clazz: classes) {
			this.beforeMethods.put(clazz, beforeMethod);
		}
		return this;
	}
	
	/**
	 * Adds an after advice to the specified  pre / post callbacks.
	 * @param afterMethod The after interceptor method.
	 * @param classes The point cut classes.
	 * @return This interceptor.
	 */
	public ExpressionedInterceptor adviceAfter(InterceptorMethod<? extends Serializable> afterMethod, Class<? extends Annotation>... classes) {
		for(Class<? extends Annotation> clazz: classes) {
			this.afterMethods.put(clazz, afterMethod);
		}
		return this;
	}
	

	/**
	 * Adds an advice around the specified  pre / post callbacks.
	 * @param aroundMethod The around interceptor method.
	 * @param classes The point cut classes.
	 * @return This interceptor.
	 */
	public ExpressionedInterceptor adviceAround(InterceptorMethod<? extends Serializable> aroundMethod, Class<? extends Annotation>... classes) {
		for(Class<? extends Annotation> clazz: classes) {
			this.beforeMethods.put(clazz, aroundMethod);
			this.afterMethods.put(clazz, aroundMethod);
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		try {
			ExpressionedInterceptor copy = (ExpressionedInterceptor) super.clone();
			copy.afterMethods = (HashMap<Class<? extends Annotation>, Expression>) afterMethods.clone();
			copy.beforeMethods = (HashMap<Class<? extends Annotation>, Expression>) beforeMethods.clone();
			return copy;
		} catch(CloneNotSupportedException cnse) {
			throw new GlooException(cnse);
		}
	}
	
	/**
	 * The before interceptor callback. It invokes the configured before methods.
	 */
	@Override
	public void before(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) throws Exception {
		invoke(this.beforeMethods, annotationClass, repository, persistentObject);
	}

	/**
	 * The after interceptor callback. It invokes the configured after methods.
	 */
	@Override
	public void after(Class<? extends Annotation> annotationClass, Repository repository, Serializable persistentObject) throws Exception {
		invoke(this.afterMethods, annotationClass, repository, persistentObject);
	}
	
	private void invoke(HashMap<Class<? extends Annotation>, Expression> methodMap, Class<? extends Annotation> clazz, Repository repository, Serializable persistentObject) throws Exception {
		Expression method = methodMap.get(clazz);
		if(method != null) {
			method.evaluate(persistentObject, clazz, repository);
		}
	}
}