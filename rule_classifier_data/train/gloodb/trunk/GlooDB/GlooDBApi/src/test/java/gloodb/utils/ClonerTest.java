package gloodb.utils;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import gloodb.Cloner;
import gloodb.GlooException;

import java.io.NotSerializableException;
import java.io.Serializable;

import org.junit.Test;

public class ClonerTest {
	/**
	 * This class is not serializable because it is a non-static inner class of
	 * a non-serializable class.
	 */
	class NonSerializableClass implements Serializable {
		private static final long serialVersionUID = -8947542280675849982L;
		Integer id;
	}

	static class SerializableClass implements Serializable {
		private static final long serialVersionUID = -7314083064679504412L;

		Integer id;
		transient Integer value;
	}
	
	/**
	 * Cloneable classes should implement clone
	 *
	 */
	static class NonClonableClass implements Serializable, Cloneable {
		private static final long serialVersionUID = -7314083064679504412L;

		Integer id;
		transient Integer value;
	}	

	/**
	 * Persistent objects must always implement serialization as they are stored
	 * in serialized form. Cloning is used to copy construct (which is something
	 * that gloodb does internally).
	 * 
	 */
	static class CloneableClass implements Serializable, Cloneable {
		private static final long serialVersionUID = -7314083064679504412L;

		Integer id;
		transient Integer value;

		public Object clone() {
			CloneableClass result = new CloneableClass();
			result.id = this.id;
			// Cloning via serialization would not copy transient values.
			// Normally the following line should be
			// result.value = null;
			// However, in order to distinguish between serialization
			// and cloning copying methods, this test well assign a value to
			// a transient field.
			result.value = Integer.valueOf(this.value + 1);
			return result;
		}
	}

	@Test
	public void testDeepCopyViaSerialization() {
		SerializableClass original = new SerializableClass();
		original.id = 1;
		original.value = 2;

		SerializableClass copy = Cloner.deepCopy(original);
		assertNotSame(original, copy);
		assertThat(original.id, is(equalTo(copy.id)));
		assertThat(Integer.valueOf(2), is(equalTo(original.value)));
		assertThat(copy.value, is(nullValue()));
	}

	@Test
	public void testDeepCopyViaCloning() {
		CloneableClass original = new CloneableClass();
		original.id = 1;
		original.value = 2;

		CloneableClass copy = Cloner.deepCopy(original);
		assertNotSame(original, copy);
		assertThat(original.id, is(equalTo(copy.id)));
		assertThat(Integer.valueOf(2), is(equalTo(original.value)));
		assertThat(Integer.valueOf(original.value + 1), is(equalTo(copy.value)));
	}

	@Test
	public void testNullCloning() {
		assertThat(Cloner.deepCopy(null), is (nullValue()));
	}
	
	@Test
	public void negativeSerializationTesting() {
		NonSerializableClass original = new NonSerializableClass();
		original.id = 1;
		try {
			Cloner.deepCopy((Serializable) original);
			fail();
		} catch (GlooException ge) {
			assertThat(ge.getCause(), is(instanceOf(NotSerializableException.class)));
		}
	}

	@Test
	public void negativeCloningTesting() {
		NonClonableClass original = new NonClonableClass();
		original.id = 1;
		try {
			Cloner.deepCopy((Serializable) original);
			fail();
		} catch (GlooException ge) {
			assertThat(ge.getCause(), is(instanceOf(NoSuchMethodException.class)));
		}
	}
}
