/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gloodb.utils;

import gloodb.GlooException;
import gloodb.ReadOnlyQuery;
import gloodb.UpdateQuery;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.SimpleValued;
import gloodb.utils.TransactionController;
import static gloodb.utils.TransactionController.*;

import java.io.Serializable;
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
			ctrl.run(TxMode.Same, new ReadOnlyQuery() {

				private static final long serialVersionUID = 1l;

				public Serializable run(Repository injectedRepository) {
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
            ctrl.run(TxMode.Top, new ReadOnlyQuery() {

                private static final long serialVersionUID = 1l;

                public Serializable run(Repository injectedRepository) {
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
        QueryResult result = ctrl.run(TxMode.Top, new ReadOnlyQuery() {
            private static final long serialVersionUID = 1l;

            public Serializable run(Repository injectedRepository) {
                assertTrue(injectedRepository.equals(repository));
                return injectedRepository.restore(testId);
            }
        });
        assertThat(repository.restore(testId), is(equalTo(result.getResult())));
    }
	
    @Test
    public void testUpdateQueryWithTopTransaction() {
        QueryResult result = ctrl.run(TxMode.Top, new UpdateQuery() {
            private static final long serialVersionUID = 1l;

            public Serializable run(Repository injectedRepository) {
                SimpleValued obj = (SimpleValued) injectedRepository
                        .restore(testId);
                obj.setValue(obj.getValue() + 1);
                return injectedRepository.update(obj);
            }
        });
        assertThat(repository.restore(testId), is(equalTo(result.getResult())));
    }
	
	@Test
	public void testReadOnlyQueryWithNewTransaction() {
		QueryResult result = ctrl.run(TxMode.New, new ReadOnlyQuery() {
        	private static final long serialVersionUID = 1l;

        	public Serializable run(Repository injectedRepository) {
        		assertFalse(injectedRepository.equals(repository));
        		return injectedRepository.restore(testId);
        	}
        });
		assertThat(repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testUpdateQueryWithNewTransaction() {
		QueryResult result = ctrl.run(TxMode.New, new UpdateQuery() {
        	private static final long serialVersionUID = 1l;

        	public Serializable run(Repository injectedRepository) {
        		SimpleValued obj = (SimpleValued) injectedRepository
        				.restore(testId);
        		obj.setValue(obj.getValue() + 1);
        		return injectedRepository.update(obj);
        	}
        });
		assertThat(repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testUnguardedQueryWithNewTransaction() {
		QueryResult result = ctrl.run(TxMode.New, new UpdateQuery() {
        	private static final long serialVersionUID = 1l;

        	public Serializable run(Repository injectedRepository) {
        		SimpleValued obj = (SimpleValued) injectedRepository
        				.restore(testId);
        		obj.setValue(obj.getValue() + 1);
        		return injectedRepository.update(obj);
        	}
        });
		assertThat(repository.restore(testId), is(equalTo(result.getResult())));
	}

	@Test
	public void testExceptionHandlingWithNewTransaction() {
		QueryResult result = ctrl.run(TxMode.New, new UpdateQuery() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public Serializable run(Repository injectedRepository)
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
		QueryResult result = ctrl.run(TxMode.New, new UpdateQuery() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public Serializable run(Repository injectedRepository)
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
		QueryResult result = ctrl.run(TxMode.New, new UpdateQuery() {
        	private static final long serialVersionUID = -4325041199810239742L;

        	public Serializable run(Repository injectedRepository)
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
