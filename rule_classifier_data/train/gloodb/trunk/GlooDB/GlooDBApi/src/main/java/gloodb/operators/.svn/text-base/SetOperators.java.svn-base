package gloodb.operators;

import gloodb.associations.Tuple;
import gloodb.operators.Join.JoinCriteria;

import java.io.Serializable;
import java.util.Collection;

public final class SetOperators {
	private SetOperators() {}

	public static <T extends Serializable, C extends Collection<T>> C diff(C result, Collection<T>... subtract) {
		new Diff<T>(result, subtract).evaluate();
		return result;
	}

	public static <T extends Serializable, C extends Collection<T>> C union(C result, Collection<T>... union) {
		new Union<T>(result, union).evaluate();
		return result;
	}

	public static <T extends Serializable, C extends Collection<T>> C intersect(C result, Collection<T>... intersect) {
		new Intersect<T>(result, intersect).evaluate();
		return result;
	}

	public static <C extends Collection<Tuple>> C carthesian(C tupleSet, Collection<?>... joining) {
		new Carthesian(tupleSet, joining).evaluate();
		return tupleSet;
	}

	public static <C extends Collection<Tuple>> C join(JoinCriteria criteria, C tupleSet, Collection<?>... joining) {
		new Join(tupleSet, criteria, joining).evaluate();
		return tupleSet;
	}
}
