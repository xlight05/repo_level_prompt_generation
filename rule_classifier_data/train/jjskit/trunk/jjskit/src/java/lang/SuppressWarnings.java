package java.lang;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the named compiler warnings should be suppressed in the
 * annotated element (and in all program elements contained in the annotated
 * element).  Note that the set of warnings suppressed in a given element is
 * a superset of the warnings suppressed in all containing elements.  For
 * example, if you annotate a class to suppress one warning and annotate a
 * method to suppress another, both warnings will be suppressed in the method.
 *
 * <p>As a matter of style, programmers should always use this annotation
 * on the most deeply nested element where it is effective.  If you want to
 * suppress a warning in a particular method, you should annotate that
 * method rather than its class.
 *
 * @since 1.5
 * @author Josh Bloch
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressWarnings {
    /**
     * The set of warnings that are to be suppressed by the compiler in the
     * annotated element.  Duplicate names are permitted.  The second and
     * successive occurrences of a name are ignored.  The presence of
     * unrecognized warning names is <i>not</i> an error: Compilers must
     * ignore any warning names they do not recognize.  They are, however,
     * free to emit a warning if an annotation contains an unrecognized
     * warning name.
     *
     * <p>Compiler vendors should document the warning names they support in
     * conjunction with this annotation type. They are encouraged to cooperate
     * to ensure that the same names work across multiple compilers.
     */
    String[] value();
}
