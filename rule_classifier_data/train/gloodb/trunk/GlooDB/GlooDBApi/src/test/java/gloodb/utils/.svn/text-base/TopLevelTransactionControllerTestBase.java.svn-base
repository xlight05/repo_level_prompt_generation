/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gloodb.utils;

import java.io.Serializable;

import gloodb.GlooException;
import gloodb.ReadOnlyQuery;
import gloodb.UpdateQuery;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.SimpleValued;
import gloodb.utils.TransactionController;
import static gloodb.utils.TransactionController.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TopLevelTransactionControllerTestBase {
	private final static String testId = "testId";

	private Repository repository;
	private TransactionController ctrl;

	public TopLevelTransactionControllerTestBase(Repository repository) {
		this.repository = repository;
	}

	@Before
	public void setUp() {
		repository.store(new SimpleValued(testId));
		ctrl = new TransactionController(repository);
	}

	@After
	public void tearDown() {
		repository.remove(testId);
	}

	@Test
	public void testSameWithTopLevelTransaction() {
		try {
			ctrl.run(TxMode.Same, new ReadOnlyQuery<SimpleValued>() {

				private static final long serialVersionUID = 1l;

				public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
					return null;
				}
			});
			fail("Should throw an InvalidTransactionModeException");
		} catch (InvalidTransactionModeException itme) {
			// expected
		}
	}

    @Test
    public void testTopLevelTransactionWithNonTopLevelParent() {
        assertTrue(repository.isTopLevel());
        Repository openTx = repository.begin();
        TransactionController ctrl = new TransactionController(openTx);
        try {
            ctrl.run(TxMode.Top, new ReadOnlyQuery<SimpleValued>() {

                private static final long serialVersionUID = 1l;

                public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                    return null;
                }
            });
            fail("Should throw an InvalidTransactionModeException");
        } catch (InvalidTransactionModeException itme) {
            // expected
        }
    }

    @Test
    public void testReadOnlyQueryWithTopLevelTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new ReadOnlyQuery<SimpleValued>() {
            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                assertTrue(injectedRepository.equals(repository));
                return (SimpleValued)injectedRepository.restore(testId);
            }
        });
        assertThat((SimpleValued)repository.restore(testId), is(equalTo(result.getResult())));
    }
	
    @Test
    public void testUpdateQueryWithTopTransaction() {
        QueryResult<SimpleValued> result = ctrl.run(TxMode.Top, new UpdateQuery<SimpleValued>() {
            private static final long serialVersionUID = 1l;

            public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
                SimpleValued obj = (SimpleValued) injectedRepository
                        .restore(testId);
                obj.setValue(obj.getValue() + 1);
                return (SimpleValued)injectedRepository.update(obj);
            }
        });
        assertThat((SimpleValued)repository.restore(testId), is(equalTo(result.getResult())));
    }
	
	@Test
	public void testReadOnlyQueryWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new ReadOnlyQuery<SimpleValued>() {
        	private static final long serialVersionUID = 1l;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
        		assertFalse(injectedRepository.equals(repository));
        		return (SimpleValued)injectedRepository.restore(testId);
        	}
        });
		assertThat((SimpleValued)repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testUpdateQueryWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
        	private static final long serialVersionUID = 1l;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
        		SimpleValued obj = (SimpleValued) injectedRepository
        				.restore(testId);
        		obj.setValue(obj.getValue() + 1);
        		return (SimpleValued)injectedRepository.update(obj);
        	}
        });
		assertThat((SimpleValued)repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testUnguardedQueryWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
        	private static final long serialVersionUID = 1l;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters) {
        		SimpleValued obj = (SimpleValued) injectedRepository
        				.restore(testId);
        		obj.setValue(obj.getValue() + 1);
        		return injectedRepository.update(obj);
        	}
        });
		assertThat((SimpleValued)repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testExceptionHandlingWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters)
        			throws Exception {
        		throw new Exception("This is a test");
        	}
        });
		assertThat(result.hasException(), is(true));
		assertThat(result.hasGlooException(), is(not(true)));
		assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
	}

	@Test
	public void testGlooExceptionHandlingWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters)
        			throws Exception {
        		throw new GlooException("This is a test");
        	}
        });
		assertThat(result.hasException(), is(true));
		assertThat(result.hasGlooException(), is(true));
		assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
	}

	@Test
	public void testWrappedExceptionHandlingWithNewTransaction() {
		QueryResult<SimpleValued> result = ctrl.run(TxMode.New, new UpdateQuery<SimpleValued>() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public SimpleValued run(Repository injectedRepository, Serializable... parameters)
        			throws Exception {
        		throw new GlooException("GlooException message",
        				new Exception("This is a test"));
        	}
        });
		assertThat(result.hasException(), is(true));
		assertThat(result.hasGlooException(), is(false));
		assertThat(result.getExceptionResult().getMessage(), is(equalTo("This is a test")));
	}
}
