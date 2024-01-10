package gloodb.operators;

import java.io.Serializable;

/**
 * Tagging interface for operations over collections of persistent objects.
 * 
 * <pre>
 *     ArrayList<Lazy<MyClass>> collection = new ArrrayList<<Lazy<MyClass>>();
 *     ArrayList<MyClass> values = new ArrayList<MyClass>();
 *     // ... more code ...
 *     
 *     Expression.iterate(collection, new FetchLazy<MyClass>(repository),
 *                                    new GetLazy<MyClass>(repository, values));
 * </pre>
 */
public abstract class Expression {
    /**
     * Iterates a collection of values and applies the provided persistent
     * operations.
     * 
     * @param collection
     *            The collection the expressions get  applied on.
     * @param expressions
     *            The expressions.
     */
    public static void iterate(Iterable<? extends Serializable> collection, Expression... expressions) {
        for (Serializable value : collection) {
            for (Expression expression : expressions) {
                while (expression != null) {
                    expression = expression.evaluate(value);
                }
            }
        }
    }

    /**
     * Applies the expression to the value. Override this method to implement
     * specific expressions.
     * 
     * @param value
     *            The value.
     * @return The next expression to execute. Return null to continue the expression
     *         iteration.
     */
    public abstract Expression evaluate(Serializable value);
}
