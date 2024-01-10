package gloodb;

import org.junit.After;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TopTransactionRollbackRepositoryTestBase extends TransactionalRepositoryTestBase {

    private Repository topTransaction;
    
    public TopTransactionRollbackRepositoryTestBase(Repository repository, PersistentFactorySimpleVersioned factory) {
        super(repository.begin(), factory);
        topTransaction = repository;
    }

    @Before
    public void setup() {

    }

    @After
    public void teardown() {
        assertThat(repository.contains(testId), is(topTransaction.contains(testId)));
        assertThat(repository.canComplete(), is(true));
        repository.rollback();
        assertThat(topTransaction.contains(testId), is(false));
        assertThat(repository.isTopLevel(), is(false));
        assertThat(repository.canComplete(), is(false));
        assertThat(topTransaction.isTopLevel(), is(true));
    }
}
