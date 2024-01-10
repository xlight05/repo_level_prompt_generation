package lk.apiit.friends.web.support.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Marker Annotation which denotes that a method
 * is to be injected with authenticated user details
 * if available.
 * 
 * @author Yohan Liyanage
 * @version 15-Sep-2008
 * @since 15-Sep-2008
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticationAware {
	// Marker Annotation
}
