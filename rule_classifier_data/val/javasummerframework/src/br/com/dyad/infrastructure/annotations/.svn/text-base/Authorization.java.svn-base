package br.com.dyad.infrastructure.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
	
	public boolean data() default false;
	public String dataField() default "id";
	public String permissionFieldName() default "";
	public String permissionLabel() default "";

}
