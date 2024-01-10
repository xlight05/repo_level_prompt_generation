package gloodb.associations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.ArrayList;

import gloodb.Lazy;
import gloodb.Repository;
import gloodb.SimpleSerializable;
import gloodb.SimpleValued;

import org.junit.Before;
import org.junit.Test;

public class ManyToManyAssociationTestBase {
    private Repository repository;
    private ManyToManyAssociation<SimpleSerializable, SimpleValued> association; 
    
    public ManyToManyAssociationTestBase(Repository repository) {
        this.repository = repository;
    }
    
    @Before
    public void setup() {
        association = AssociationFactory.getInstance(repository).newManyToManyAssociation(SimpleSerializable.class, SimpleValued.class);
    }
    
    @Test
    public void testSingleAssociate() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        assertThat(association.areRightToLeftAssociated("test1", "test2"), is(false));
        
        assertThat(association.areRightToLeftAssociated("test2", "test1"), is(true));
        assertThat(association.areLeftToRightAssociated("test2", "test1"), is(false));
                
        assertThat(association.associate(new Lazy<SimpleSerializable>("test1"), new Lazy<SimpleValued>("test2")), is(false));
        ArrayList<Lazy<SimpleValued>> leftSet = association.getLeftToRightAssociates(new ArrayList<Lazy<SimpleValued>>(), "test1");
        ArrayList<Lazy<SimpleSerializable>> rightSet = association.getRightToLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>(), "test1");
        assertThat((String)leftSet.get(0).getId(), is(equalTo("test2")));
        assertThat(rightSet.size(), is(0));
        
        leftSet = association.getLeftToRightAssociates(new ArrayList<Lazy<SimpleValued>>(), "test2");
        rightSet = association.getRightToLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>(), "test2");
        assertThat((String)rightSet.get(0).getId(), is(equalTo("test1")));
        assertThat(leftSet.size(), is(0));
    }
     
    @Test
    public void testMultipleAssociate() {
        assertThat(association.associate("test1", "test2"), is(true));
        assertThat(association.associate("test1", "test4"), is(true));
        assertThat(association.associate("test3", "test2"), is(true));
        assertThat(association.associate("test3", "test4"), is(true));
        
        ArrayList<Lazy<SimpleSerializable>> rightSet = association.getRightToLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>(), "test2");
        ArrayList<Lazy<SimpleValued>> leftSet = association.getLeftToRightAssociates(new ArrayList<Lazy<SimpleValued>>(), "test1");
        
        assertThat((String)rightSet.get(0).getId(), is(equalTo("test1")));
        assertThat((String)rightSet.get(1).getId(), is(equalTo("test3")));
        assertThat((String)leftSet.get(0).getId(), is(equalTo("test2")));
        assertThat((String)leftSet.get(1).getId(), is(equalTo("test4")));

        rightSet = association.getRightToLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>(), "test4");
        leftSet = association.getLeftToRightAssociates(new ArrayList<Lazy<SimpleValued>>(), "test3");
        assertThat((String)rightSet.get(0).getId(), is(equalTo("test1")));
        assertThat((String)rightSet.get(1).getId(), is(equalTo("test3")));
        assertThat((String)leftSet.get(0).getId(), is(equalTo("test2")));
        assertThat((String)leftSet.get(1).getId(), is(equalTo("test4")));
        
        ArrayList<Lazy<SimpleSerializable>> leftResultSet = association.getLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>());
        
        assertThat(leftResultSet.size(), is(2));
        assertThat(leftResultSet.contains(new Lazy<SimpleSerializable>("test1")), is(true));
        assertThat(leftResultSet.contains(new Lazy<SimpleSerializable>("test3")), is(true));

        ArrayList<Lazy<SimpleValued>> rightResultSet = association.getRightAssociates(new ArrayList<Lazy<SimpleValued>>());
        assertThat(rightResultSet.size(), is(2));
        assertThat(rightResultSet.contains(new Lazy<SimpleValued>("test2")), is(true));
        assertThat(rightResultSet.contains(new Lazy<SimpleValued>("test4")), is(true));
    }
    
    @Test
    public void testDisassociateLeft() {
        ArrayList<Lazy<SimpleSerializable>> leftResultSet = new ArrayList<Lazy<SimpleSerializable>>();

        assertThat(association.associate("test1", "test2"), is(true));
        assertThat(association.associate("test1", "test4"), is(true));
        assertThat(association.associate("test3", "test2"), is(true));
        assertThat(association.associate("test3", "test4"), is(true));

        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        assertThat(association.getLeftAssociates(leftResultSet).contains(new Lazy<SimpleSerializable>("test1")), is(true));
        
        assertThat(association.dissassociateFromLeft("test2"), is(false));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        
        assertThat(association.dissassociateFromLeft("test1"), is(true));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(false));
        assertThat(association.getLeftAssociates(leftResultSet).contains(new Lazy<SimpleSerializable>("test1")), is(false));
    }


    @Test
    public void testDisassociateRight() {
        ArrayList<Lazy<SimpleValued>> rightResultSet = new ArrayList<Lazy<SimpleValued>>();

        assertThat(association.associate("test1", "test2"), is(true));
        assertThat(association.associate("test1", "test4"), is(true));
        assertThat(association.associate("test3", "test2"), is(true));
        assertThat(association.associate("test3", "test4"), is(true));

        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        assertThat(association.getRightAssociates(rightResultSet).contains(new Lazy<SimpleValued>("test2")), is(true));
        
        assertThat(association.dissassociateFromRight("test1"), is(false));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        
        assertThat(association.dissassociateFromRight("test2"), is(true));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(false));
        assertThat(association.getRightAssociates(rightResultSet).contains("test2"), is(false));
    }
    
    @Test
    public void associateToNull() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        assertThat(association.dissassociateFromLeft("test1"), is(true));

        assertThat(association.associate("test1", null), is(false));
        assertThat(association.getLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>()).contains(new Lazy<SimpleSerializable>("test1")), is(false));
        assertThat(association.associate(null, "test2"), is(false));
        assertThat(association.getRightAssociates(new ArrayList<Lazy<SimpleValued>>()).contains(new Lazy<SimpleValued>("test2")), is(false));
    }
    
    @Test
    public void testNullBehaviour() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areLeftToRightAssociated("test1", "test2"), is(true));
        
        assertThat(association.areLeftToRightAssociated(null, (Serializable)null), is(false));
        assertThat(association.areLeftToRightAssociated("test1", (Serializable)null), is(false));
        assertThat(association.areLeftToRightAssociated(null, "test2"), is(false));
        assertThat(association.dissassociateFromLeft(null), is(false));
        assertThat(association.dissassociateFromRight(null), is(false));
        assertThat(association.getLeftToRightAssociates(new ArrayList<Lazy<SimpleValued>>(), null).size(), is(0));
        assertThat(association.getRightToLeftAssociates(new ArrayList<Lazy<SimpleSerializable>>(), null).size(), is(0));
    }
}
