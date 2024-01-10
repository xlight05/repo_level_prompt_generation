package gloodb.utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class PairTest {

	@Test
	public void testHashCode() {
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2));
		assertThat(p1.hashCode(), is(equalTo(new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2)).hashCode())));
		assertNotSame(p1, new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2)));
		assertThat(p1.hashCode(), is(not(equalTo(new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(3)).hashCode()))));
		assertTrue(p1.hashCode() != new Pair<Integer, Integer>(Integer.valueOf(2), Integer.valueOf(2)).hashCode());

		
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(Integer.valueOf(1), null);
		assertEquals(p2.hashCode(), new Pair<Integer, Integer>(Integer.valueOf(1), null).hashCode());
		assertTrue(p2.hashCode() != new Pair<Integer, Integer>(Integer.valueOf(2), null).hashCode());
		assertTrue(p1.hashCode() != p2.hashCode());

		Pair<Integer, Integer> p3 = new Pair<Integer, Integer>(null, Integer.valueOf(2));
		assertEquals(p3.hashCode(), new Pair<Integer, Integer>(null, Integer.valueOf(2)).hashCode());
		assertTrue(p3.hashCode() != new Pair<Integer, Integer>(null, Integer.valueOf(3)).hashCode());
		assertTrue(p1.hashCode() != p3.hashCode());
		assertTrue(p2.hashCode() != p3.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Pair<Integer, Integer> p = new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2));
		assertTrue(p.equals(new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2))));
		assertNotSame(p, new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2)));
		assertFalse(p.equals(null));
		assertFalse(p.equals("bla, bla"));
		assertFalse(p.equals(new Pair<Integer, Integer>(Integer.valueOf(1), null)));
		assertFalse(p.equals(new Pair<Integer, Integer>(null, Integer.valueOf(2))));
		assertFalse(p.equals(new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(3))));
		assertFalse(p.equals(new Pair<Integer, Integer>(Integer.valueOf(2), Integer.valueOf(2))));
	}

	@Test
	public void testGet() {
		Pair<Integer, Integer> p = new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2));
		assertEquals(Integer.valueOf(1), p.getLeft());
		assertEquals(Integer.valueOf(2), p.getRight());
	}
	
	@Test
	public void testToString() {
		Pair<Integer, Integer> p = new Pair<Integer, Integer>(Integer.valueOf(1), Integer.valueOf(2));
		assertEquals("Pair [1, 2]", p.toString());
	}
}
