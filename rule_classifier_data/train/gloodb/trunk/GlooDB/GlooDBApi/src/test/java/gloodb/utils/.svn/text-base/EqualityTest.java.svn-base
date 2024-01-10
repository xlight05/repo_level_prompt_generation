package gloodb.utils;

import static org.junit.Assert.*;

import gloodb.Identity;
import static gloodb.utils.Equality.Operator;

import java.io.Serializable;

import org.junit.Test;

public class EqualityTest {

	@SuppressWarnings("serial")
	public static class TestPersistent implements Serializable {
		@Identity
		private Serializable id;
		private long stuff;
		
		public TestPersistent(Serializable id, long stuff) {
			this.id = id;
			this.stuff = stuff;
		}
		
		public Serializable getId() {
			return id;
		}
		
		public long getStuff() {
			return stuff;
		}
	}
	
	@Test
	public void testCheckObjectObjectOperatorOfT() {
		TestPersistent p1 = new TestPersistent("1", 0);
		TestPersistent p2 = new TestPersistent("2", 2);
		TestPersistent p3 = new TestPersistent("1", 2);
		TestPersistent p4 = new TestPersistent(Integer.valueOf(0), 0);
		Operator<TestPersistent> o =  new Operator<TestPersistent>(){
			@Override
			public boolean check(TestPersistent left, TestPersistent right) {
				return ((String)left.id).equals(right.id);
			}};
		assertTrue(Equality.check(p1, p3, o));
		assertFalse(Equality.check(p1, null, o));
		assertFalse(Equality.check(null, p1, o));
		assertFalse(Equality.check(p1, p2, o));
		assertFalse(Equality.check(p1, p4, o));
		assertFalse(Equality.check(p4, p1, o));
	}

	@Test
	public void testCheckSerializableSerializable() {
		TestPersistent p1 = new TestPersistent("1", 0);
		TestPersistent p2 = new TestPersistent("2", 2);
		TestPersistent p3 = new TestPersistent("1", 2);
		TestPersistent p4 = new TestPersistent(Integer.valueOf(0), 0);
		
		assertTrue(Equality.check(p1, p1));
		assertTrue(Equality.check(p1, p3));
		assertFalse(Equality.check(p1, null));
		assertFalse(Equality.check(null, p1));
		assertFalse(Equality.check(p1, p2));
		assertFalse(Equality.check(p1, p4));
		assertFalse(Equality.check(p4, p1));
	}

}
