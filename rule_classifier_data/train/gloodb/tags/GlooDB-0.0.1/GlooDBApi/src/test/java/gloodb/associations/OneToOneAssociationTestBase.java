package gloodb.associations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import gloodb.Lazy;
import gloodb.Repository;
import gloodb.SimpleSerializable;
import gloodb.SimpleValued;

import org.junit.Before;
import org.junit.Test;

public class OneToOneAssociationTestBase {
    private Repository repository;
    private OneToOneAssociation<SimpleSerializable, SimpleValued> association; 
    
    public OneToOneAssociationTestBase(Repository repository) {
        this.repository = repository;
    }
    
    @Before
    public void setup() {
        association = AssociationFactory.getInstance(repository).newOneToOneAssociation(SimpleSerializable.class, SimpleValued.class);
    }
    
    @Test
    public void testSingleAssociate() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        
        assertThat(association.areAssociated("test1", "test2"), is(true));
        assertThat(association.areAssociated("test2", "test1"), is(false));
        
        assertThat(association.associate(new Lazy<SimpleSerializable>("test1"), new Lazy<SimpleValued>("test2")), is(false));
        assertThat((String)association.getLeftAssociate("test2").getId(), is(equalTo("test1")));
        assertThat((String)association.getLeftAssociate("test1").getId(), is(nullValue()));
        
        assertThat((String)association.getRightAssociate("test1").getId(), is(equalTo("test2")));
        assertThat((String)association.getRightAssociate("test2").getId(), is(nullValue()));
    }
     
    @Test
    public void testMultipleAssociate() {
        assertThat(association.associate("test1", "test2"), is(true));
        assertThat(association.associate("test3", "test4"), is(true));
        
        assertThat(association.areAssociated("test1", "test2"), is(true));
        assertThat(association.areAssociated("test2", "test1"), is(false));
        assertThat((String)association.getLeftAssociate("test2").getId(), is(equalTo("test1")));
        assertThat((String)association.getRightAssociate("test1").getId(), is(equalTo("test2")));

        assertThat(association.areAssociated("test3", "test4"), is(true));
        assertThat(association.areAssociated("test4", "test3"), is(false));
        assertThat((String)association.getLeftAssociate("test4").getId(), is(equalTo("test3")));
        assertThat((String)association.getRightAssociate("test3").getId(), is(equalTo("test4")));
        
        assertThat(association.getLeftAssociates().size(), is(2));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test1")), is(true));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test3")), is(true));

        assertThat(association.getRightAssociates().size(), is(2));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test2")), is(true));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test4")), is(true));

        assertThat(association.getLeftAssociates().size(), is(2));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test1")), is(true));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test3")), is(true));

        assertThat(association.getRightAssociates().size(), is(2));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test2")), is(true));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test4")), is(true));
    }
    
    @Test
    public void testDisassociateLeft() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(true));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test1")), is(true));
        
        assertThat(association.dissassociateFromLeft("test2"), is(false));
        assertThat(association.areAssociated("test1", "test2"), is(true));
        
        assertThat(association.dissassociateFromLeft("test1"), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(false));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test1")), is(false));
    }


    @Test
    public void testDisassociateRight() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(true));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test2")), is(true));
        
        assertThat(association.dissassociateFromRight("test1"), is(false));
        assertThat(association.areAssociated("test1", "test2"), is(true));
        
        assertThat(association.dissassociateFromRight("test2"), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(false));
        assertThat(association.getRightAssociates().contains("test2"), is(false));
    }
    
    @Test
    public void associateToNull() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(true));

        assertThat(association.associate("test1", null), is(true));
        assertThat(association.getLeftAssociates().contains(new Lazy<SimpleSerializable>("test1")), is(false));

        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(true));

        assertThat(association.associate(null, "test2"), is(true));
        assertThat(association.getRightAssociates().contains(new Lazy<SimpleValued>("test2")), is(false));
    }
    
    @Test
    public void testNullBehaviour() {
        assertThat(association.associate(new SimpleSerializable("test1"), new SimpleValued("test2")), is(true));
        assertThat(association.areAssociated("test1", "test2"), is(true));
        
        assertThat(association.areAssociated(null, null), is(false));
        assertThat(association.areAssociated("test1", null), is(false));
        assertThat(association.areAssociated(null, "test2"), is(false));
        assertThat(association.dissassociateFromLeft(null), is(false));
        assertThat(association.dissassociateFromRight(null), is(false));
        assertThat(association.getLeftAssociate(null).getId(), is(nullValue()));
        assertThat(association.getRightAssociate(null).getId(), is(nullValue()));
    }
}
