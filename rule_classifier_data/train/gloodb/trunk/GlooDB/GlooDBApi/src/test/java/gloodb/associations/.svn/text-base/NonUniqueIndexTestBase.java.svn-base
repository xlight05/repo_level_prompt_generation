package gloodb.associations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import gloodb.GlooException;
import gloodb.Lazy;
import gloodb.Repository;
import gloodb.utils.StringComparator;

import org.junit.Before;
import org.junit.Test;

public class NonUniqueIndexTestBase {
    private Repository repository;
    private NonUniqueIndex<SortedSerializable, String> index;
    
    public NonUniqueIndexTestBase(Repository repository) {
        this.repository = repository;
    }
    
    @Before
    public void setup() {
        index = AssociationFactory.getInstance(repository).newNonUniqueIndex(SortedSerializable.class, "test", new StringComparator(), 0);
    }

    @Test
    public void testAdd() {
        index.add(new SortedSerializable("test1", "a"));
        assertThat(index.contains("test1"), is(true));
        // The index is injected the 0 parameter. See the setup function.
        assertThat(index.containsSortKey("a.0"), is(true));
    }
    
    @Test
    public void testDuplicatedAdd() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        index.add(new SortedSerializable("test1", "a"));
        assertThat(index.findBySortKey(result, "a.0").size(), is(1));
        assertThat(index.contains("test1"), is(true));
        assertThat(index.findBySortKey(result, "a.0").contains(new Lazy<SortedSerializable>("test1")), is(true));
        
        index.add(new SortedSerializable("test2", "a"));
        assertThat(index.contains("test1"), is(true));
        assertThat(index.findBySortKey(result, "a.0").size(), is(2));
        assertThat(index.findBySortKey(result, "a.0").contains(new Lazy<SortedSerializable>("test1")), is(true));
        assertThat(index.findBySortKey(result, "a.0").contains(new Lazy<SortedSerializable>("test2")), is(true));
    }

    @Test
    public void testFind() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));

        index.add(new SortedSerializable("1test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("3test3", "c"));

        assertThat(index.size(), is(3));
        assertThat(index.contains("test1"), is(true));
        assertThat(index.contains("test2"), is(true));
        assertThat(index.contains("test3"), is(true));
        assertThat(index.contains("test4"), is(false));

        // The index is injected the 0 parameter. See the setup function.
        assertThat(index.containsSortKey("a.0"), is(true));
        assertThat(index.containsSortKey("b.0"), is(true));
        assertThat(index.containsSortKey("c.0"), is(true));
        assertThat(index.containsSortKey("d.0"), is(false));
        
        assertThat(index.findBySortKey(result, "d.0").size(), is(0));
        
        assertThat(index.findBySortKey(result, "a.0").size(), is(2));
        assertThat(index.findBySortKey(result, "b.0").size(), is(2));
        assertThat(index.findBySortKey(result, "c.0").size(), is(2));

        assertThat((String)index.findBySortKey(result, "a.0").get(0).getId(), is(equalTo("test1")));
        assertThat((String)index.findBySortKey(result, "b.0").get(0).getId(), is(equalTo("test2")));
        assertThat((String)index.findBySortKey(result, "c.0").get(0).getId(), is(equalTo("test3")));

        assertThat((String)index.findBySortKey(result, "a.0").get(1).getId(), is(equalTo("1test1")));
        assertThat((String)index.findBySortKey(result, "b.0").get(1).getId(), is(equalTo("2test2")));
        assertThat((String)index.findBySortKey(result, "c.0").get(1).getId(), is(equalTo("3test3")));
    }
    
    @Test
    public void testSubSet() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        index.add(new SortedSerializable("1test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("3test3", "c"));

        assertEquals(index.getSubset(result, true, "a.0", true, "b.0", true).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched), ID: test2 (not fetched), ID: 2test2 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", false, "b.0", true).toString(), "[ID: test2 (not fetched), ID: 2test2 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", true, "b.0", false).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", false, "b.0", false).toString(), "[]");
    
        assertEquals(index.getSubset(result, false, "b.0", true, "a.0", true).toString(), "[ID: test2 (not fetched), ID: 2test2 (not fetched), ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", false, "a.0", true).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", true, "a.0", false).toString(), "[ID: test2 (not fetched), ID: 2test2 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", false, "a.0", false).toString(), "[]");
    }
    

    @Test
    public void testLowerSet() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        index.add(new SortedSerializable("1test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("3test3", "c"));

        assertEquals(index.getLowerSet(result, true, "b.0", true).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched), ID: test2 (not fetched), ID: 2test2 (not fetched)]");
        assertEquals(index.getLowerSet(result, true, "b.0", false).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getLowerSet(result, true, "a.0", false).toString(), "[]");
    
        assertEquals(index.getLowerSet(result, false, "b.0", true).toString(), "[ID: test3 (not fetched), ID: 3test3 (not fetched), ID: test2 (not fetched), ID: 2test2 (not fetched)]");
        assertEquals(index.getLowerSet(result, false, "b.0", false).toString(), "[ID: test3 (not fetched), ID: 3test3 (not fetched)]");
        assertEquals(index.getLowerSet(result, false, "c.0", false).toString(), "[]");
    }

    
    @Test
    public void testUpperSet() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        index.add(new SortedSerializable("1test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("3test3", "c"));

        assertEquals(index.getUpperSet(result, true, "b.0", true).toString(), "[ID: test2 (not fetched), ID: 2test2 (not fetched), ID: test3 (not fetched), ID: 3test3 (not fetched)]");
        assertEquals(index.getUpperSet(result, true, "b.0", false).toString(), "[ID: test3 (not fetched), ID: 3test3 (not fetched)]");
        assertEquals(index.getUpperSet(result, true, "c.0", false).toString(), "[]");
    
        assertEquals(index.getUpperSet(result, false, "b.0", true).toString(), "[ID: test2 (not fetched), ID: 2test2 (not fetched), ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getUpperSet(result, false, "b.0", false).toString(), "[ID: test1 (not fetched), ID: 1test1 (not fetched)]");
        assertEquals(index.getUpperSet(result, false, "a.0", false).toString(), "[]");
    }
    
    
    @Test
    public void testSorting() {
    	ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>(); 
        
        index.add(new SortedSerializable("3test1", "a"));
        index.add(new SortedSerializable("3test2", "a"));
        index.add(new SortedSerializable("2test1", "b"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("1test1", "c"));
        index.add(new SortedSerializable("1test2", "c"));
        
        // Sort based on the descending value order
        index.getSortedSet(result, false);
        assertThat((String)result.get(0).getId(), is(equalTo("1test1")));
        assertThat((String)result.get(1).getId(), is(equalTo("1test2")));
        assertThat((String)result.get(2).getId(), is(equalTo("2test1")));
        assertThat((String)result.get(3).getId(), is(equalTo("2test2")));
        assertThat((String)result.get(4).getId(), is(equalTo("3test1")));
        assertThat((String)result.get(5).getId(), is(equalTo("3test2")));

        // Sort based on the ascending value order
        index.getSortedSet(result, true);
        assertThat((String)result.get(0).getId(), is(equalTo("3test1")));
        assertThat((String)result.get(1).getId(), is(equalTo("3test2")));
        assertThat((String)result.get(2).getId(), is(equalTo("2test1")));
        assertThat((String)result.get(3).getId(), is(equalTo("2test2")));
        assertThat((String)result.get(4).getId(), is(equalTo("1test1")));
        assertThat((String)result.get(5).getId(), is(equalTo("1test2")));
    }
    
    @Test
    public void testMissingSortingCriteriaAnnotation() {
        try {
            index = AssociationFactory.getInstance(repository).newNonUniqueIndex(SortedSerializable.class, "missing", new StringComparator(), 0);
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }

    @Test
    public void testMissingIndexParameters() {
        try {
            index = AssociationFactory.getInstance(repository).newNonUniqueIndex(SortedSerializable.class, "test", new StringComparator());
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }

    @Test
    public void testMissingComparator() {
        try {
            index = AssociationFactory.getInstance(repository).newNonUniqueIndex(SortedSerializable.class, "test", null, 0);
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }
}
