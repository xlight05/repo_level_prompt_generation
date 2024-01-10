package gloodb.associations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import gloodb.GlooException;
import gloodb.KeyViolationException;
import gloodb.Lazy;
import gloodb.Repository;
import gloodb.utils.StringComparator;

import org.junit.Before;
import org.junit.Test;

public class UniqueIndexTestBase {
    private Repository repository;
    private UniqueIndex<SortedSerializable, String> index;
    
    public UniqueIndexTestBase(Repository repository) {
        this.repository = repository;
    }
    
    @Before
    public void setup() {
        index = AssociationFactory.getInstance(repository).newUniqueIndex(SortedSerializable.class, "test", new StringComparator(), 0);
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
        index.add(new SortedSerializable("test1", "a"));
        assertThat(index.contains("test1"), is(true));
        
        try {
            index.add(new SortedSerializable("test2", "a"));
            fail();
        } catch (KeyViolationException kve) {
            // expected
        }
    }

    @Test
    public void testFind() {
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
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
        
        assertThat((String)index.findBySortKey("a.0").getId(), is(equalTo("test1")));
        assertThat((String)index.findBySortKey("b.0").getId(), is(equalTo("test2")));
        assertThat((String)index.findBySortKey("c.0").getId(), is(equalTo("test3")));
        assertThat((String)index.findBySortKey("d.0").getId(), is(nullValue()));
    }

    @Test
    public void testSubSet() {
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>();
        assertEquals(index.getSubset(result, true, "a.0", true, "b.0", true).toString(), "[ID: test1 (not fetched), ID: test2 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", false, "b.0", true).toString(), "[ID: test2 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", true, "b.0", false).toString(), "[ID: test1 (not fetched)]");
        assertEquals(index.getSubset(result, true, "a.0", false, "b.0", false).toString(), "[]");
    
        assertEquals(index.getSubset(result, false, "b.0", true, "a.0", true).toString(), "[ID: test2 (not fetched), ID: test1 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", false, "a.0", true).toString(), "[ID: test1 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", true, "a.0", false).toString(), "[ID: test2 (not fetched)]");
        assertEquals(index.getSubset(result, false, "b.0", false, "a.0", false).toString(), "[]");
    }
    

    @Test
    public void testLowerSet() {
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>();
        assertEquals(index.getLowerSet(result, true, "b.0", true).toString(), "[ID: test1 (not fetched), ID: test2 (not fetched)]");
        assertEquals(index.getLowerSet(result, true, "b.0", false).toString(), "[ID: test1 (not fetched)]");
        assertEquals(index.getLowerSet(result, true, "a.0", false).toString(), "[]");
    
        assertEquals(index.getLowerSet(result, false, "b.0", true).toString(), "[ID: test3 (not fetched), ID: test2 (not fetched)]");
        assertEquals(index.getLowerSet(result, false, "b.0", false).toString(), "[ID: test3 (not fetched)]");
        assertEquals(index.getLowerSet(result, false, "c.0", false).toString(), "[]");
    }

    
    @Test
    public void testUpperSet() {
        index.add(new SortedSerializable("test1", "a"));
        index.add(new SortedSerializable("test2", "b"));
        index.add(new SortedSerializable("test3", "c"));
        
        ArrayList<Lazy<SortedSerializable>> result = new ArrayList<Lazy<SortedSerializable>>();
        assertEquals(index.getUpperSet(result, true, "b.0", true).toString(), "[ID: test2 (not fetched), ID: test3 (not fetched)]");
        assertEquals(index.getUpperSet(result, true, "b.0", false).toString(), "[ID: test3 (not fetched)]");
        assertEquals(index.getUpperSet(result, true, "c.0", false).toString(), "[]");
    
        assertEquals(index.getUpperSet(result, false, "b.0", true).toString(), "[ID: test2 (not fetched), ID: test1 (not fetched)]");
        assertEquals(index.getUpperSet(result, false, "b.0", false).toString(), "[ID: test1 (not fetched)]");
        assertEquals(index.getUpperSet(result, false, "a.0", false).toString(), "[]");
    }

    
    @Test
    public void testSorting() {
        index.add(new SortedSerializable("3test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("1test3", "c"));
        
        // Sort based on the descending value order
        ArrayList<Lazy<SortedSerializable>> ascSet = index.getSortedSet(new ArrayList<Lazy<SortedSerializable>>(), false);
        assertThat((String)ascSet.get(0).getId(), is(equalTo("1test3")));
        assertThat((String)ascSet.get(1).getId(), is(equalTo("2test2")));
        assertThat((String)ascSet.get(2).getId(), is(equalTo("3test1")));

        // Sort based on the ascending value order
        ArrayList<Lazy<SortedSerializable>> descSet = index.getSortedSet(new ArrayList<Lazy<SortedSerializable>>(), true);
        assertThat((String)descSet.get(0).getId(), is(equalTo("3test1")));
        assertThat((String)descSet.get(1).getId(), is(equalTo("2test2")));
        assertThat((String)descSet.get(2).getId(), is(equalTo("1test3")));
    }
    
    @Test
    public void testMissingSortingCriteriaAnnotation() {
        try {
            index = AssociationFactory.getInstance(repository).newUniqueIndex(SortedSerializable.class, "missing", new StringComparator(), 0);
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }

    @Test
    public void testMissingIndexParameters() {
        try {
            index = AssociationFactory.getInstance(repository).newUniqueIndex(SortedSerializable.class, "test", new StringComparator());
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }

    @Test
    public void testMissingComparator() {
        try {
            index = AssociationFactory.getInstance(repository).newUniqueIndex(SortedSerializable.class, "test", null, 0);
            index.add(new SortedSerializable("test", ""));
            fail();
        } catch (GlooException ge) {
            // expected
        }
    }
}
