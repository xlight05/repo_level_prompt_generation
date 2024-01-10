package gloodb.queries;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gloodb.Repository;
import gloodb.queries.ObjectIdsQuery;
import gloodb.queries.ObjectIdsQuery.Filter;


public class ObjectIdQueryTestBase {
	private static final String testId = "testId";
	private Repository repository;

	public ObjectIdQueryTestBase(Repository repository) {
		this.repository = repository;
	}
	
	@Before
	public void setUp() {
	    for(int i = 0; i < 10; i++) {
			repository.store(new SimpleSerializable(testId + i));
		}
	}
	
	@After
	public void tearDown() {
		for(int i = 0; i < 10; i++) {
			repository.remove(testId + i);
		}		
	}
	
	
	@Test
	public void testFetchAllIds() {
		List<Serializable> ids = ObjectIdsQuery.fetch(repository, null);
		assertThat(ids.size(), is(10));
		for(int i = 0; i < 10; i++) {
			assertThat(ids.contains(testId + i), is(true));
		}
	}
	
	@Test
	public void testAddAndRemoveObjects() {
		List<Serializable> ids = ObjectIdsQuery.fetch(repository, null);
		assertThat(ids.size(), is(10));

		repository.store(new SimpleSerializable("newObject"));
		ids = ObjectIdsQuery.fetch(repository, null);
		assertThat(ids.size(), is(11));
		assertThat(ids.contains("newObject"), is(true));
		
		repository.remove("newObject");
		ids = ObjectIdsQuery.fetch(repository, null);
		assertEquals(10, ids.size());
		assertFalse(ids.contains("newObject"));
	}
	
	@Test
	public void testFiltering() {
		List<Serializable> ids = ObjectIdsQuery.fetch(repository, new Filter() {
			private static final long serialVersionUID = 7112548569705880279L;

			@Override
			public boolean match(Serializable... id) {
			    if(!(SimpleSerializable.class.isAssignableFrom(repository.getClassForId(id)))) return false;
			    return (testId + 5).compareTo((String)id[0]) > 0;
			}
		});
		assertThat(ids.size(), is(5));
		assertThat(ids.contains("testId4"), is(true));
	}
}
