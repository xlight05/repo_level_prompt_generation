package org.seamlets.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.seam.annotations.intercept.Interceptors;
import org.seamlets.interceptor.cookie.CookieInterceptor;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Interceptors(CookieInterceptor.class)
@Documented
@Inherited
public @interface CookieSavedState {

}
