package gloodb.associations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.LinkedList;

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
    public void testSorting() {
        index.add(new SortedSerializable("3test1", "a"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("1test3", "c"));
        
        // Sort based on the descending value order
        LinkedList<Lazy<SortedSerializable>> ascList = index.getSortedSet(false);
        assertThat((String)ascList.get(0).getId(), is(equalTo("1test3")));
        assertThat((String)ascList.get(1).getId(), is(equalTo("2test2")));
        assertThat((String)ascList.get(2).getId(), is(equalTo("3test1")));

        // Sort based on the ascending value order
        LinkedList<Lazy<SortedSerializable>> descList = index.getSortedSet(true);
        assertThat((String)descList.get(0).getId(), is(equalTo("3test1")));
        assertThat((String)descList.get(1).getId(), is(equalTo("2test2")));
        assertThat((String)descList.get(2).getId(), is(equalTo("1test3")));
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
