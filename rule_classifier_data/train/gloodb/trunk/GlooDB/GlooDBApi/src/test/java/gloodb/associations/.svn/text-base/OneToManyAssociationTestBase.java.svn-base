package gloodb.associations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.LinkedHashSet;

import gloodb.Lazy;
import gloodb.Repository;
import gloodb.SimpleSerializable;
import gloodb.SimpleValued;

import org.junit.Before;
import org.junit.Test;

public class OneToManyAssociationTestBase {
    private Repository repository;
    private OneToManyAssociation<SimpleSerializable, SimpleValued> association;
    
    public OneToManyAssociationTestBase(Repository repository) {
        this.repository = repository;
    }

    @Before
    public void setup() throws Exception {
        association = AssociationFactory.getInstance(repository).newOneToManyAssociation(SimpleSerializable.class, SimpleValued.class);
    }

    @Test
    public void testAssociate() {
        SimpleSerializable one = new SimpleSerializable("one");
        SimpleValued many1 = new SimpleValued("many1");
        SimpleValued many2 = new SimpleValued("many2");
        assertThat(association.associate(one, many1, many2), is(true));
        assertThat(association.associate(one, many1, many2), is(false));
        assertThat(association.areAssociated(one, many1, many2), is(true));
        assertThat(association.areAssociated(one, many1, many2, "bla"), is(false));
        assertThat(association.areAssociated("bla", many1, many2), is(false));

        SimpleSerializable two = new SimpleSerializable("two");
        assertThat(association.associate(two, many2), is(true));
        assertThat(association.areAssociated(one, many1), is(true));
        assertThat(association.areAssociated(one, many2), is(false));
        assertThat(association.areAssociated(two, many2), is(true));
    }
    
    @Test
    public void testGetters() {
    	LinkedHashSet<Lazy<SimpleSerializable>> oneResultSet = new LinkedHashSet<Lazy<SimpleSerializable>>();
        SimpleSerializable one = new SimpleSerializable("one");
        SimpleValued many1 = new SimpleValued("many1");
        SimpleValued many2 = new SimpleValued("many2");
        assertThat(association.associate(one, many1, many2), is(true));
        assertThat(association.areAssociated(one, many1, many2), is(true));
        assertThat(association.areAssociated(one, many1, many2, "bla"), is(false));

        SimpleSerializable two = new SimpleSerializable("two");
        SimpleValued many3 = new SimpleValued("many3");
        SimpleValued many4 = new SimpleValued("many4");
        assertThat(association.associate(two, many3, many4), is(true));

        assertThat(association.getOnes(oneResultSet).size(), is(2));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("one")), is(true));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("two")), is(true));       
        
    	LinkedHashSet<Lazy<SimpleValued>> manyResultSet = new LinkedHashSet<Lazy<SimpleValued>>();
        assertThat(association.getAssociatesForOne(manyResultSet, one).size(), is(2));
        assertThat(association.getAssociatesForOne(manyResultSet, one).contains(new Lazy<SimpleValued>("many1")), is(true));
        assertThat(association.getAssociatesForOne(manyResultSet, one).contains(new Lazy<SimpleValued>("many2")), is(true));       

        assertThat(association.getAssociatesForOne(manyResultSet, two).size(), is(2));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many3")), is(true));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many4")), is(true));       
        
        SimpleSerializable notThere = new SimpleSerializable("notThere");
        association.getAssociatesForOne(manyResultSet, notThere);
        assertThat(manyResultSet.size(), is(0));
    }
    
    @Test
    public void testDisassociate() {
    	LinkedHashSet<Lazy<SimpleValued>> manyResultSet = new LinkedHashSet<Lazy<SimpleValued>>();

        SimpleSerializable one = new SimpleSerializable("one");
        SimpleValued many1 = new SimpleValued("many1");
        SimpleValued many2 = new SimpleValued("many2");
        assertThat(association.associate(one, many1, many2), is(true));
        assertThat(association.areAssociated(one, many1, many2), is(true));

        SimpleSerializable two = new SimpleSerializable("two");
        SimpleValued many3 = new SimpleValued("many3");
        SimpleValued many4 = new SimpleValued("many4");
        assertThat(association.associate(two, many3, many4), is(true));
        assertThat(association.areAssociated(two, many3, many4), is(true));

        // Test disassociate many
        assertThat(association.getAssociatesForOne(manyResultSet, two).size(), is(2));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many3")), is(true));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many4")), is(true));       

        // Try and assert the operation.
        assertThat(association.disassociateMany(many4), is(true));
        assertThat(association.disassociateMany(many4), is(false));
        assertThat(association.getAssociatesForOne(manyResultSet, two).size(), is(1));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many3")), is(true));
        assertThat(association.getAssociatesForOne(manyResultSet, two).contains(new Lazy<SimpleValued>("many4")), is(false));       
        assertThat(association.areAssociated(two, many3), is(true));
        assertThat(association.areAssociated(two, many4), is(false));
        
        // Test disassociate one
    	LinkedHashSet<Lazy<SimpleSerializable>> oneResultSet = new LinkedHashSet<Lazy<SimpleSerializable>>();

        assertThat(association.getOnes(oneResultSet).size(), is(2));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("one")), is(true));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("two")), is(true));  
        
        // Try and assert the operation.
        assertThat(association.dissassociateOne(one), is(true));
        assertThat(association.dissassociateOne(one), is(false));
        assertThat(association.getOnes(oneResultSet).size(), is(1));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("one")), is(false));
        assertThat(association.getOnes(oneResultSet).contains(new Lazy<SimpleSerializable>("two")), is(true));  
    }

    @Test
    public void testNullBehaviour() {
        SimpleSerializable one = new SimpleSerializable("one");
        SimpleValued many1 = new SimpleValued("many1");
        SimpleValued many2 = new SimpleValued("many2");
        assertThat(association.associate(one, many1, many2), is(true));
        assertThat(association.areAssociated(one, many1, many2), is(true));

        assertThat(association.associate(null, null, null), is(false));
        assertThat(association.associate(one), is(false));
        assertThat(association.associate(one, (Serializable)null), is(false));
        assertThat(association.associate(one, (Serializable [])null), is(false));
        
        assertThat(association.associate(null, many1), is(true));
        assertThat(association.areAssociated(one, many1), is(false));
    }
}
