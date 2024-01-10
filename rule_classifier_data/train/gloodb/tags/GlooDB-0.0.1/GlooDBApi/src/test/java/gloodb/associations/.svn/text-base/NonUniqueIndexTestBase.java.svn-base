package gloodb.associations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.LinkedList;

import gloodb.GlooException;
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
        index.add(new SortedSerializable("test1", "a"));
        assertThat(index.findIdsBySortKey("a.0").size(), is(1));
        assertThat(index.contains("test1"), is(true));
        assertThat(index.findIdsBySortKey("a.0").contains("test1"), is(true));
        
        index.add(new SortedSerializable("test2", "a"));
        assertThat(index.contains("test1"), is(true));
        assertThat(index.findIdsBySortKey("a.0").size(), is(2));
        assertThat(index.findIdsBySortKey("a.0").contains("test1"), is(true));
        assertThat(index.findIdsBySortKey("a.0").contains("test2"), is(true));
    }

    @Test
    public void testFind() {
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
        
        assertThat(index.findIdsBySortKey("d.0").size(), is(0));
        
        assertThat(index.findIdsBySortKey("a.0").size(), is(2));
        assertThat(index.findIdsBySortKey("b.0").size(), is(2));
        assertThat(index.findIdsBySortKey("c.0").size(), is(2));

        assertThat((String)index.findIdsBySortKey("a.0").get(0), is(equalTo("test1")));
        assertThat((String)index.findIdsBySortKey("b.0").get(0), is(equalTo("test2")));
        assertThat((String)index.findIdsBySortKey("c.0").get(0), is(equalTo("test3")));

        assertThat((String)index.findIdsBySortKey("a.0").get(1), is(equalTo("1test1")));
        assertThat((String)index.findIdsBySortKey("b.0").get(1), is(equalTo("2test2")));
        assertThat((String)index.findIdsBySortKey("c.0").get(1), is(equalTo("3test3")));
    }
    
    @Test
    public void testSorting() {
        index.add(new SortedSerializable("3test1", "a"));
        index.add(new SortedSerializable("3test2", "a"));
        index.add(new SortedSerializable("2test1", "b"));
        index.add(new SortedSerializable("2test2", "b"));
        index.add(new SortedSerializable("1test1", "c"));
        index.add(new SortedSerializable("1test2", "c"));
        
        // Sort based on the descending value order
        LinkedList<Serializable> ascList = index.getSortedIdSet(false);
        assertThat((String)ascList.get(0), is(equalTo("1test1")));
        assertThat((String)ascList.get(1), is(equalTo("1test2")));
        assertThat((String)ascList.get(2), is(equalTo("2test1")));
        assertThat((String)ascList.get(3), is(equalTo("2test2")));
        assertThat((String)ascList.get(4), is(equalTo("3test1")));
        assertThat((String)ascList.get(5), is(equalTo("3test2")));

        // Sort based on the ascending value order
        LinkedList<Serializable> descList = index.getSortedIdSet(true);
        assertThat((String)descList.get(0), is(equalTo("3test1")));
        assertThat((String)descList.get(1), is(equalTo("3test2")));
        assertThat((String)descList.get(2), is(equalTo("2test1")));
        assertThat((String)descList.get(3), is(equalTo("2test2")));
        assertThat((String)descList.get(4), is(equalTo("1test1")));
        assertThat((String)descList.get(5), is(equalTo("1test2")));
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
