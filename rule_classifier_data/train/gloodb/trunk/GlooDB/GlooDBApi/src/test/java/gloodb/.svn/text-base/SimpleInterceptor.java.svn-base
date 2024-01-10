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

import gloodb.operators.ExpressionedInterceptor;

import java.lang.annotation.Annotation;
import java.util.HashSet;

@Identity
public class SimpleInterceptor extends ExpressionedInterceptor {
	private static final long serialVersionUID = -4402321491549316715L;
	HashSet<Class<? extends Annotation>> afterSet;
	HashSet<Class<? extends Annotation>> beforeSet;

	@SuppressWarnings({ "unchecked", "serial" })
	public SimpleInterceptor() {
		super();
		this.afterSet = new HashSet<Class<? extends Annotation>>();
		this.beforeSet = new HashSet<Class<? extends Annotation>>();

		this.adviceBefore(new InterceptorMethod<SimpleIntercepted>() {
			@Override
			protected void evaluate( SimpleIntercepted object, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				doBefore(clazz, repository, object);
			}}, PreCreate.class, PreUpdate.class, PreRemove.class, PostCreate.class, PostRemove.class, PostUpdate.class);
		
		this.adviceAfter(new InterceptorMethod<SimpleIntercepted>() {
			@Override
			protected void evaluate( SimpleIntercepted object, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				doAfter(clazz, repository, object);
			}}, PreCreate.class, PreUpdate.class, PreRemove.class, PostCreate.class, PostRemove.class, PostUpdate.class);
	}

	private void doBefore(Class<? extends Annotation> annotationClass, Repository repository, SimpleIntercepted object) throws Exception {
		beforeSet.add(annotationClass);
		repository.update(this);
	}

	private void doAfter(Class<? extends Annotation> annotationClass, Repository repository, SimpleIntercepted object) throws Exception {
		afterSet.add(annotationClass);
		repository.update(this);
	}

	public HashSet<Class<? extends Annotation>> getBefore() {
		return beforeSet;
	}

	public HashSet<Class<? extends Annotation>> getAfter() {
		return afterSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SimpleInterceptor.class.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	@Override
	public Object clone() {
		return super.clone();
	}

}