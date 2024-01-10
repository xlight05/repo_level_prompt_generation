/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gloodb.utils;

import java.io.Serializable;

import gloodb.GlooException;
import gloodb.PersistentFactorySimpleValued;
import gloodb.UpdateQuery;
import gloodb.ReadOnlyQuery;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.SimpleValued;
import gloodb.utils.TransactionController;
import static gloodb.utils.TransactionController.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TransactionControllerTestBase {
    private final static String testId = "testId";

    private Repository repository;
    private PersistentFactorySimpleValued factory;
    private Repository parentTx;
    private TransactionController ctrl;

    public TransactionControllerTestBase(Repository repository, PersistentFactorySimpleValued factory) {
        this.factory = factory;
        this.repository = repository;
    }

    @Before
    public void setUp() {
        repository.store(factory.newSimpleValued(testId));
        parentTx = repository.begin();
        ctrl = new TransactionController(parentTx);
    }

    @After
    public void tearDown() {
        if (parentTx != null && parentTx.canComplete()) {
            if (parentTx.canCommit()) {
                parentTx.commit();
            } else {
            	parentTx.rollback();
            }
        }
        repository.remove(testId);
    }

	@Test
    public void testReadOnlyQueryWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new ReadOnlyQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                return (SimpleValued)injectedRepository.restore(testId);
            }
        });
        assertThat(parentTx.canComplete(), is(false));
        assertThat((SimpleValued)parentTx.restore( testId), is(equalTo(result.getResult())));
    }
    
	@Test
    public void testReadOnlyQueryWithSameTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new ReadOnlyQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                return (SimpleValued)injectedRepository.restore(testId);
            }
        });
        assertThat(parentTx.canComplete(), is(true));
        assertThat((SimpleValued)parentTx.restore( testId), is(equalTo(result.getResult())));
    }

    @Test
    public void testReadOnlyQueryWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new ReadOnlyQuery<SimpleValued>() {
            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                assertFalse(injectedRepository.equals(parentTx));
                return (SimpleValued)injectedRepository.restore(testId);
            }
        });
        assertThat(parentTx.canComplete(), is(true));
        assertThat((SimpleValued)parentTx.restore( testId), is(equalTo(result.getResult())));
    }

    @Test
    public void testUpdateQueryWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;

        SimpleValued initialObj = (SimpleValued) parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat(parentTx.canComplete(), is(false));
        assertThat(initialObj.getValue() + 1, is(equalTo(((SimpleValued)result.getResult()).getValue())));
    }

    @Test
    public void testUpdateQueryWithSameTransaction() {
        
        SimpleValued initialObj = (SimpleValued) parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new UpdateQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat(parentTx.canComplete(), is(true));
        assertThat(initialObj.getValue() + 1, is(equalTo(((SimpleValued)result.getResult()).getValue())));
    }

    @Test
    public void testUpdateQueryWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat((SimpleValued)parentTx.restore( testId), is(equalTo(result.getResult())));
    }


    @Test
    public void testUnguardedQueryWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;

        SimpleValued initialObj = (SimpleValued)parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat(parentTx.canComplete(), is(false));
        assertThat(initialObj.getValue()+ 1,is(equalTo(((SimpleValued)result.getResult()).getValue())));
    }
    
    @Test
    public void testUnguardedQueryWithSameTransaction() {
        SimpleValued initialObj = (SimpleValued)parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new UpdateQuery<SimpleValued>() {

            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat(parentTx.canComplete(), is(true));
        assertThat(initialObj.getValue()+ 1,is(equalTo(((SimpleValued)result.getResult()).getValue())));
    }

    @Test
    public void testUnguardedQueryWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued)injectedRepository.restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat((SimpleValued)parentTx.restore( testId), is(equalTo(result.getResult())));
    }


    @Test
    public void testExceptionHandlingWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;

        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 7804410813007026078L;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new Exception("This is a test");
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(false));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testExceptionHandlingWithSameTransaction() {
        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = 7804410813007026078L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new Exception("This is a test");
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(true));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testExceptionHandlingWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = -4325041199810239742L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new Exception("This is a test");
            }
        });
        // Parent transaction is unaffected
        assertThat(parentTx.canCommit(), is(true));
        assertThat(parentTx.canComplete(), is(true));

        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }
    
    @Test
    public void testGlooExceptionHandlingWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;

        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 7804410813007026078L;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("This is a test");
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(false));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(true));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testGlooExceptionHandlingWithSameTransaction() {
        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = 7804410813007026078L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("This is a test");
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(true));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(true));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testGlooExceptionHandlingWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = -4325041199810239742L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("This is a test");
            }
        });
        // Parent transaction is unaffected
        assertThat(parentTx.canCommit(), is(true));
        assertThat(parentTx.canComplete(), is(true));

        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(true));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }    
    
    @Test
    public void testWrappedExceptionHandlingWithTopTransaction() {
        TransactionController ctrl = new TransactionController(repository);
        Repository parentTx = repository;

        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 7804410813007026078L;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("GlooException message", new Exception("This is a test"));
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(false));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testWrappedExceptionHandlingWithSameTransaction() {
        parentTx.restore( testId);
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Same, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = 7804410813007026078L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("GlooException message", new Exception("This is a test"));
            }
        });
        assertThat(parentTx.canCommit(), is(false));
        assertThat(parentTx.canComplete(), is(true));
        
        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }

    @Test
    public void testWrappedExceptionHandlingWithNewTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
			private static final long serialVersionUID = -4325041199810239742L;

			public SimpleValued run(Repository injectedRepository, Serializable... parameters) throws Exception {
                throw new GlooException("GlooException message", new Exception("This is a test"));
            }
        });
        // Parent transaction is unaffected
        assertThat(parentTx.canCommit(), is(true));
        assertThat(parentTx.canComplete(), is(true));

        assertThat(result.hasException(), is(true));
        assertThat(result.hasGlooException(), is(false));
        assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
    }
}
