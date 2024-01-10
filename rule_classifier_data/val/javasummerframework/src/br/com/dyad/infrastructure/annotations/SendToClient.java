package br.com.dyad.infrastructure.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface SendToClient {
	
	boolean setNull() default false;

}