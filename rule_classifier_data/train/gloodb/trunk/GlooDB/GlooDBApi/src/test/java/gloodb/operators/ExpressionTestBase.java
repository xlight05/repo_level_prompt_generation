package gloodb.operators;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gloodb.AggregateIdentity;
import gloodb.Embedded;
import gloodb.Identity;
import gloodb.Lazy;
import gloodb.PreRemove;
import gloodb.Repository;
import gloodb.associations.Tuple;
import gloodb.operators.Join.JoinCriteria;
import gloodb.queries.SimpleSerializable;

import static gloodb.operators.Iterators.*;
import static gloodb.operators.SetOperators.*;

import org.junit.After;
import org.junit.Test;

public class ExpressionTestBase {
	private final Repository repository;
	
	public ExpressionTestBase(Repository repository) {
		this.repository = repository;
	}
	
	@After
	public void tearDown() {
		repository.remove("1");
		repository.remove("2");
	}
	
	@Test
	public void testStoreRestoreRemove() throws Exception {
		iterate(Arrays.asList(new SimpleSerializable [] {new SimpleSerializable("1"), new SimpleSerializable("2")}), store(repository));
		
		assertTrue(repository.contains("1"));
		assertTrue(repository.contains("2"));

		ArrayList<SimpleSerializable> list = new ArrayList<SimpleSerializable>();
		iterate(Arrays.asList(new Serializable []{"1", "2"}), restore(repository, list), remove(repository));
		
		assertEquals(2, list.size());
		assertEquals("1", list.get(0).getId());
		assertEquals("2", list.get(1).getId());
		
		assertFalse(repository.contains("1"));
		assertFalse(repository.contains("2"));
		
}
	
	@Test
	public void testWrapFetchGet() throws Exception {
		ArrayList<Lazy<SimpleSerializable>> lazyList = new ArrayList<Lazy<SimpleSerializable>>();
		iterate(Arrays.asList(new SimpleSerializable [] {new SimpleSerializable("1"), new SimpleSerializable("2")}), 
				store(repository), wrapObjects(lazyList));
		
		assertEquals(2, lazyList.size());
		assertEquals("1", lazyList.get(0).getId());
		assertEquals("2", lazyList.get(1).getId());
		assertNull(lazyList.get(0).get());
		assertNull(lazyList.get(1).get());
		
		ArrayList<SimpleSerializable> valueList = new ArrayList<SimpleSerializable>();
		ArrayList<Serializable> idList = new ArrayList<Serializable>();
		iterate(lazyList, fetch(repository), get(valueList), id(idList));
		
		assertNotNull(lazyList.get(0).get());
		assertNotNull(lazyList.get(1).get());
		assertEquals(2, valueList.size());
		assertEquals("1", valueList.get(0).getId());
		assertEquals("2", valueList.get(1).getId());
		assertEquals(2, idList.size());
		assertEquals("1", idList.get(0));
		assertEquals("2", idList.get(1));
	}
	
	@SuppressWarnings("serial")
	static class EmbeddedTest implements Serializable {
		@Identity Serializable id;
		List<Embedded<Long>> embedded = new ArrayList<Embedded<Long>>();
		EmbeddedTest(Serializable id) { this.id = id; }
		@PreRemove void preRemove(Repository repository) throws Exception { iterate(embedded, removeEmbedded(), flush(repository)); }
	}
	
	@Test
	public void testFillFlushResetRemove() throws Exception {
		EmbeddedTest test = new EmbeddedTest("1");
		iterate(new ArrayList<Long>(Arrays.asList(new Long[] {0l, 1l, 2l})), fill("1", "embedded", test.embedded));
		assertEquals(new AggregateIdentity(new Serializable [] {Embedded.class, "1" , "embedded0"}), test.embedded.get(0).getId());
		assertEquals(new AggregateIdentity(new Serializable [] {Embedded.class, "1" , "embedded1"}), test.embedded.get(1).getId());
		assertEquals(new AggregateIdentity(new Serializable [] {Embedded.class, "1" , "embedded2"}), test.embedded.get(2).getId());
		
		assertEquals(Long.valueOf(0l),test.embedded.get(0).get());
		assertEquals(Long.valueOf(1l),test.embedded.get(1).get());
		assertEquals(Long.valueOf(2l),test.embedded.get(2).get());
		
		assertFalse(repository.contains(Embedded.class, "1", "embedded0"));
		assertFalse(repository.contains(Embedded.class, "1", "embedded1"));
		assertFalse(repository.contains(Embedded.class, "1", "embedded2"));
		
		iterate(test.embedded, flush(repository), fetch(repository));
		assertTrue(repository.contains(Embedded.class, "1", "embedded0"));
		assertTrue(repository.contains(Embedded.class, "1", "embedded1"));
		assertTrue(repository.contains(Embedded.class, "1", "embedded2"));
		
		// Check the values stored in the reference
		assertEquals(Long.valueOf(0l), test.embedded.get(0).get());
		assertEquals(Long.valueOf(1l), test.embedded.get(1).get());
		assertEquals(Long.valueOf(2l), test.embedded.get(2).get());
		
		// Setting values w/o flushing will not update the repository
		iterate(test.embedded, set(new ArrayList<Long>(Arrays.asList(new Long[] {3l, 4l, 5l}))));
		assertEquals(Long.valueOf(3l), test.embedded.get(0).get());
		assertEquals(Long.valueOf(4l), test.embedded.get(1).get());
		assertEquals(Long.valueOf(5l), test.embedded.get(2).get());

		// Resetting the values undoes all changes.
		iterate(test.embedded, reset(), fetch(repository));
		// Check the values stored in the reference
		assertEquals(Long.valueOf(0l), test.embedded.get(0).get());
		assertEquals(Long.valueOf(1l), test.embedded.get(1).get());
		assertEquals(Long.valueOf(2l), test.embedded.get(2).get());
		
		iterate(test.embedded, set(new ArrayList<Long>(Arrays.asList(new Long[] {3l, 4l, 5l}))), flush(repository), reset(), fetch(repository));
		assertEquals(Long.valueOf(3l), test.embedded.get(0).get());
		assertEquals(Long.valueOf(4l), test.embedded.get(1).get());
		assertEquals(Long.valueOf(5l), test.embedded.get(2).get());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSetDiff() {
		ArrayList<String> l1 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2", "3", "4"}));
		ArrayList<String> l2 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2"}));
		ArrayList<String> l3 = new ArrayList<String>(Arrays.asList(new String[] {"5"}));
		ArrayList<String> l4 = new ArrayList<String>(Arrays.asList(new String[] {"4"}));
		ArrayList<String> l5 = new ArrayList<String>(Arrays.asList(new String[] {"3"}));
		
		List<String> r = diff(l1, l2, l4);
		assertEquals(1, l1.size());
		assertEquals(r.get(0), "3");
		
		r = diff(l1, l3);
		assertEquals(1, l1.size());
		assertEquals(r.get(0), "3");
		
		r = diff(l1, l5, l2);
		assertEquals(0, l1.size());

		r = diff(l1, l5, l2);
		assertEquals(0, l1.size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testSetUnion() {
		ArrayList<String> l1 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2"}));
		ArrayList<String> l3 = new ArrayList<String>(Arrays.asList(new String[] {"5"}));
		ArrayList<String> l4 = new ArrayList<String>(Arrays.asList(new String[] {"4"}));
		ArrayList<String> l5 = new ArrayList<String>(Arrays.asList(new String[] {"3"}));
		
		List<String> r = union(l1, l2, l3, l4, l5);
		assertEquals(5, l1.size());
		assertEquals(r.get(0), "1");
		assertEquals(r.get(1), "2");
		assertEquals(r.get(2), "5");
		assertEquals(r.get(3), "4");
		assertEquals(r.get(4), "3");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetIntersect() {
		ArrayList<String> l1 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2", "3", "4"}));
		ArrayList<String> l2 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2"}));
		ArrayList<String> l3 = new ArrayList<String>(Arrays.asList(new String[] {"1", "5"}));
		ArrayList<String> l4 = new ArrayList<String>(Arrays.asList(new String[] {"4"}));
		
		List<String> r = intersect(l1, l2, l3);
		assertEquals(1, l1.size());
		assertEquals(r.get(0), "1");
		
		r = intersect(l1, l4);
		assertEquals(0, l1.size());
	}

	@Test
	public void testCarthesian() {
		List<Tuple> result;
		ArrayList<String> l1 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2", "3", "4"}));
		ArrayList<String> l2 = new ArrayList<String>(Arrays.asList(new String[] {"5", "6"}));
		ArrayList<String> l3 = new ArrayList<String>(Arrays.asList(new String[] {"7", "8"}));
		ArrayList<String> l4 = new ArrayList<String>(Arrays.asList(new String[] {}));
		
		result = carthesian(new ArrayList<Tuple>(), l1, l2);
		ArrayList<Tuple> expected = new ArrayList<Tuple>();
		expected.add(new Tuple().add("1").add("5"));
		expected.add(new Tuple().add("1").add("6"));
		expected.add(new Tuple().add("2").add("5"));
		expected.add(new Tuple().add("2").add("6"));
		expected.add(new Tuple().add("3").add("5"));
		expected.add(new Tuple().add("3").add("6"));
		expected.add(new Tuple().add("4").add("5"));
		expected.add(new Tuple().add("4").add("6"));
		assertEquals(expected, result);
		
		result = carthesian(new ArrayList<Tuple>(), l1);
		expected = new ArrayList<Tuple>();
		expected.add(new Tuple().add("1"));
		expected.add(new Tuple().add("2"));
		expected.add(new Tuple().add("3"));
		expected.add(new Tuple().add("4"));
		assertEquals(expected, result);

		result = carthesian(new ArrayList<Tuple>(), result, l2);
		expected = new ArrayList<Tuple>();
		expected.add(new Tuple().add("1").add("5"));
		expected.add(new Tuple().add("1").add("6"));
		expected.add(new Tuple().add("2").add("5"));
		expected.add(new Tuple().add("2").add("6"));
		expected.add(new Tuple().add("3").add("5"));
		expected.add(new Tuple().add("3").add("6"));
		expected.add(new Tuple().add("4").add("5"));
		expected.add(new Tuple().add("4").add("6"));
		assertEquals(expected, result);

		result = carthesian(result, l3);
		expected = new ArrayList<Tuple>();
		expected.add(new Tuple().add("1").add("5").add("7"));
		expected.add(new Tuple().add("1").add("5").add("8"));
		expected.add(new Tuple().add("1").add("6").add("7"));
		expected.add(new Tuple().add("1").add("6").add("8"));
		expected.add(new Tuple().add("2").add("5").add("7"));
		expected.add(new Tuple().add("2").add("5").add("8"));
		expected.add(new Tuple().add("2").add("6").add("7"));
		expected.add(new Tuple().add("2").add("6").add("8"));
		expected.add(new Tuple().add("3").add("5").add("7"));
		expected.add(new Tuple().add("3").add("5").add("8"));
		expected.add(new Tuple().add("3").add("6").add("7"));
		expected.add(new Tuple().add("3").add("6").add("8"));
		expected.add(new Tuple().add("4").add("5").add("7"));
		expected.add(new Tuple().add("4").add("5").add("8"));
		expected.add(new Tuple().add("4").add("6").add("7"));
		expected.add(new Tuple().add("4").add("6").add("8"));
		assertEquals(expected, result);

		
		result = carthesian(result, l4);
		assertEquals(0, result.size());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testJoin() {
		List<Tuple> result;
		ArrayList<String> l1 = new ArrayList<String>(Arrays.asList(new String[] {"1", "2", "3", "4"}));
		ArrayList<String> l2 = new ArrayList<String>(Arrays.asList(new String[] {"2", "5", "6"}));
		ArrayList<String> l3 = new ArrayList<String>(Arrays.asList(new String[] {"7", "8"}));
		ArrayList<String> l4 = new ArrayList<String>(Arrays.asList(new String[] {}));
		
		JoinCriteria criteria = new JoinCriteria() {
			@Override
			public boolean areJoined(Tuple tuple) {
				return tuple.get(0).equals(tuple.get(1));
			}
		}; 
		
		result = join(criteria, new ArrayList<Tuple>(), l1, l2);
		assertEquals(1, result.size());
		assertEquals(new Tuple().add("2").add("2"), result.get(0));
		
		result = join(criteria, new ArrayList<Tuple>(), l1, l3);
		assertEquals(0, result.size());
		
		result = join(criteria, new ArrayList<Tuple>(), l1, l4);
		assertEquals(0, result.size());
	}
}
