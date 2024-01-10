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

import java.lang.annotation.Annotation;
import java.util.HashSet;

@Identity
public class SimpleInterceptor implements Interceptor<SimpleIntercepted> {
    private static final long serialVersionUID = -4402321491549316715L;
    HashSet<Class<? extends Annotation>> afterSet;
    HashSet<Class<? extends Annotation>> beforeSet;
 
    public SimpleInterceptor() {
        this.afterSet = new HashSet<Class<? extends Annotation>>();
        this.beforeSet = new HashSet<Class<? extends Annotation>>();
    }
    
    @Override
    public void before(Class<? extends Annotation> annotationClass, Repository repository, SimpleIntercepted object)
            throws Exception {
        beforeSet.add(annotationClass);
        repository.update(this);
    }

    @Override
    public void after(Class<? extends Annotation> annotationClass, Repository repository, SimpleIntercepted object)
            throws Exception {
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

    
}