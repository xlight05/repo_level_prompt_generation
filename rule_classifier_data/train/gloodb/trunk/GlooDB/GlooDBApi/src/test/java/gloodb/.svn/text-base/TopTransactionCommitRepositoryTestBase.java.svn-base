package gloodb;

import org.junit.After;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TopTransactionCommitRepositoryTestBase extends TransactionalRepositoryTestBase {

    private Repository topTransaction;
    
    public TopTransactionCommitRepositoryTestBase(Repository topTransaction, PersistentFactorySimpleVersioned factory) {
        super(topTransaction.begin(), factory);
        this.topTransaction = topTransaction;
    }

    @Before
    public void setup() {

    }

    @After
    public void teardown() {
        assertThat(repository.contains(testId), is(topTransaction.contains(testId)));
        assertThat(repository.canComplete(), is(true));
        repository.commit();
        assertThat(repository.isTopLevel(), is(false));
        assertThat(repository.canComplete(), is(false));
        topTransaction.remove(testId);
    }
}
