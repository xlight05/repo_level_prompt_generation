package br.com.dyad.infrastructure.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import br.com.dyad.infrastructure.customization.CustomType;

@SuppressWarnings("unchecked")
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Customize {  
  public Class clazz() default Class.class;
  public CustomType type() default CustomType.CLASS;
  public String method() default "";
  public boolean callSuper() default true;
}
