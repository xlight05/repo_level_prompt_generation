package gloodb.utils;

import static org.junit.Assert.*;

import gloodb.Identity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ComparatorTest {
	@SuppressWarnings("serial")
	public static class TestPersistent implements Serializable, Comparable<TestPersistent> {
		@Identity
		private String id;
		private long stuff;
		
		public TestPersistent(String id, long stuff) {
			this.id = id;
			this.stuff = stuff;
		}
		
		public Serializable getId() {
			return id;
		}
		
		public long getStuff() {
			return stuff;
		}

		@Override
		public int compareTo(TestPersistent right) {
			return id.compareTo(right != null? right.id: null);
		}
	}
	
	
	@Parameters
	public static Collection<Object[]> data() {
 		return Arrays.asList(new Object[][] {
 				{new IntegerComparator(), 0, 0, 0}, 	
 				{new IntegerComparator(), 0, 1, -1}, 
 				{new IntegerComparator(), 1, 0, 1},
 				{new IntegerComparator(), null, null, 0}, 
 				{new IntegerComparator(), 0, null, -1}, 
 				{new IntegerComparator(), null, 0, 1}, 
 				{new IntegerComparator().clone(), 0, 0, 0}, 	
 				{new IntegerComparator().clone(), 0, 1, -1}, 
 				{new IntegerComparator().clone(), 1, 0, 1},
 				{new IntegerComparator().clone(), null, null, 0}, 
 				{new IntegerComparator().clone(), 0, null, -1}, 
 				{new IntegerComparator().clone(), null, 0, 1}, 

 				{new DateComparator(), new Date(0), new Date(0), 0}, 	
 				{new DateComparator(), new Date(0), new Date(1), -1}, 
 				{new DateComparator(), new Date(1), new Date(0), 1},
 				{new DateComparator(), null, null, 0}, 
 				{new DateComparator(), new Date(0), null, -1}, 
 				{new DateComparator(), null, new Date(0), 1}, 
 				{new DateComparator().clone(), new Date(0), new Date(0), 0}, 	
 				{new DateComparator().clone(), new Date(0), new Date(1), -1}, 
 				{new DateComparator().clone(), new Date(1), new Date(0), 1},
 				{new DateComparator().clone(), null, null, 0}, 
 				{new DateComparator().clone(), new Date(0), null, -1}, 
 				{new DateComparator().clone(), null, new Date(0), 1},
 				
 				{new StringComparator(), "0", "0", 0}, 	
 				{new StringComparator(), "0", "1", -1}, 
 				{new StringComparator(), "1", "0", 1},
 				{new StringComparator(), null, null, 0}, 
 				{new StringComparator(), "0", null, -1}, 
 				{new StringComparator(), null, "0", 1}, 
 				{new StringComparator().clone(), "0", "0", 0}, 	
 				{new StringComparator().clone(), "0", "1", -1}, 
 				{new StringComparator().clone(), "1", "0", 1},
 				{new StringComparator().clone(), null, null, 0}, 
 				{new StringComparator().clone(), "0", null, -1}, 
 				{new StringComparator().clone(), null, "0", 1}, 

 				{new ComparableComparator<TestPersistent>(), new TestPersistent("0", 0), new TestPersistent("0", 0), 0}, 	
 				{new ComparableComparator<TestPersistent>(), new TestPersistent("0", 0), new TestPersistent("1", 1), -1}, 
 				{new ComparableComparator<TestPersistent>(), new TestPersistent("1", 1), new TestPersistent("0", 0), 1},
 				{new ComparableComparator<TestPersistent>(), null, null, 0}, 
 				{new ComparableComparator<TestPersistent>(), new TestPersistent("0", 0), null, -1}, 
 				{new ComparableComparator<TestPersistent>(), null, new TestPersistent("0", 0), 1}, 
 				{new ComparableComparator<TestPersistent>().clone(), new TestPersistent("0", 0), new TestPersistent("0", 0), 0}, 	
 				{new ComparableComparator<TestPersistent>().clone(), new TestPersistent("0", 0), new TestPersistent("1", 1), -1}, 
 				{new ComparableComparator<TestPersistent>().clone(), new TestPersistent("1", 1), new TestPersistent("0", 0), 1},
 				{new ComparableComparator<TestPersistent>().clone(), null, null, 0}, 
 				{new ComparableComparator<TestPersistent>().clone(), new TestPersistent("0", 0), null, -1}, 
 				{new ComparableComparator<TestPersistent>().clone(), null, new TestPersistent("0", 0), 1}, 
 				
 		});
	}
	
	private Comparator<Serializable> comparator;
	private Serializable left;
	private Serializable right;
	private int expected;
	
	public ComparatorTest(Comparator<Serializable> comparator, Serializable left, Serializable right, int expected) {
		super();
		this.comparator = comparator;
		this.left = left;
		this.right = right;
		this.expected = expected;
	}

	@Test
	public void testCompare() {
		assertEquals(expected, comparator.compare(left, right));
	}

}
