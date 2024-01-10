package org.seamlets.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.seamlets.interceptor.cookie.SplitType;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface CookieValue {

	String name() default "";

	String comment() default "";

	String domain() default "";

	int maxAge() default -1;

	String path() default "";

	boolean secure() default false;

	int version() default 0;

	String split() default "";
	
	SplitType splitType() default SplitType.STRING;
}
