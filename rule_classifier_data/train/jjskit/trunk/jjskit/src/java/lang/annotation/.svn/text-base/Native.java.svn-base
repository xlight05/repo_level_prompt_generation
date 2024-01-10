package java.lang.annotation;
/**
 * <p>This annotation is used to notify JJS compiler that element implementation is defined outside of
 * java source. The contract defines following behavior:
 * <ol>
 * <li>If this annotation is linked to type than type compilation will be skipped and 
 * in case if link is <code>true</code> type function will be loaded from location specified.
 * Otherwise it will be just assigned to corresponding class.
 * <li>If this annotation is linked to constructor or method than its compilation will be skipped 
 * (even if method is not marked as native) and in case if link is <code>true</code> reference function 
 * will be loaded from location specified. Otherwise it will be just assigned to corresponding method.
 * <li>If this annotation is linked to field - field value from java source will be ignored and 
 * in case if link is <code>true</code> value will be loaded from location specified. Otherwise it will be 
 * just assigned to corresponding field.
 * </ol>
 * 
 * <p>Meaning of the fields:
 * <ul>
 * <li><code>link</code> parameter determines does compiler should use the location parameter to load and
 * link the target value. In case of <code>false</code> value developer should manually take care about 
 * loading corresponding library before the class (in case of native class - ClassLoader) initialization.
 * <li><code>location</code> parameter defines the location of library to search (within the current module).
 * <li><code>value</code> parameter defines expression to search in target library (in case when <code>link</code>
 * is <code>true</code>) or to assign (in case when <code>link</code> is <code>false</code>)  
 * </ul>
 * 
 * <p>Example of usage is following. Consider you have manually defined class named 'my.own.ClassName' in some resource
 * (e.g. my/own/library.js) with name 'my.js.impl'. Following code will use this definition:
 * <blockquote><pre>
 * package my.own;
 * 
 * @Native(location="my/own/library.js", value="my.js.impl")
 * class ClassName {
 *   //everything here will be ignored by compiler 
 * }
 * </pre></blockquote>
 * In case you specify link to false you will be forced to load the my/own/library.js manually before the ClassLoader initialization.
 * 
 * <p><b>Implementation warnings:</b>
 * <ul>
 * <li>In case when linker not find the value specified compilation error will be thrown.
 * <li>Compiler is not able to check the type of target expression and since javascript is loosely type you may 
 * got very unclear errors while using this annotation so use it only if you 100% sure there is no other way to 
 * do something. 
 * <li>In case when either linking is not used or/and expression is not a function you may face problems 
 * with non-callable methods. 
 * <li>note that javascript arrays are not equal to JJS arrays. To create correct array you need to use J.array method.
 * </ul>
 * 
 * @author alex.bereznevatiy@gmail.com
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE,ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.FIELD})
public @interface Native {
	boolean link() default true;
	String location() default "";
	String value();
}
