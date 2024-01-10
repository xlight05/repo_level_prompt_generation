import java.lang.reflect.Method;

/**
 * Instances output a single method given from the results of {@link
 * JLoogle}.
 */
interface Output {

  void output(Method m);
}