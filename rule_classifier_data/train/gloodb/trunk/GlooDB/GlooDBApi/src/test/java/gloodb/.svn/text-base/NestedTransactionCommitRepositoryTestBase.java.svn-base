package gloodb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class NestedTransactionCommitRepositoryTestBase {
    private final Repository topTransaction;
    private final Repository childTransaction;
    private final TransactionalRepositoryTestBase delegatedTest;

    public NestedTransactionCommitRepositoryTestBase(Repository repository, PersistentFactorySimpleVersioned factory,
            Class<? extends TransactionalRepositoryTestBase> delegatedTestClass) throws Exception {        
        topTransaction = repository;
        childTransaction = topTransaction.begin();
        delegatedTest = delegatedTestClass.getConstructor(Repository.class, PersistentFactorySimpleVersioned.class).newInstance(childTransaction, factory);
    }
    
    @Before
    public void setup() {
        delegatedTest.setup();
    }
    
    @After
    public void teardown() {
        assertThat(delegatedTest.repository.contains(TransactionalRepositoryTestBase.testId), is(topTransaction.contains(TransactionalRepositoryTestBase.testId)));
        assertThat(childTransaction.contains(TransactionalRepositoryTestBase.testId), is(false));
        assertThat(childTransaction.canComplete(), is(true));
        childTransaction.commit();
        assertThat(childTransaction.isTopLevel(), is(false));
        assertThat(childTransaction.canComplete(), is(false));
        topTransaction.remove(TransactionalRepositoryTestBase.testId);
    }

    @Test
    public void testCommitNewTransaction() {
        delegatedTest.testCommitNewTransaction();
    }

    @Test
    public void testRollbackNewTransaction() {
        delegatedTest.testRollbackNewTransaction();
    }

    @Test
    public void testLockingWithNewTransaction() {
        delegatedTest.testLockingWithNewTransaction();
    }

    @Test
    public void testCommitRemoveTransaction() {
        delegatedTest.testCommitRemoveTransaction();
    }

    @Test
    public void testRollbackRemoveTransaction() {
        delegatedTest.testRollbackRemoveTransaction();
    }

    @Test
    public void testLockingWithRemoveTransaction() {
        delegatedTest.testLockingWithRemoveTransaction();
    }

    @Test
    public void testCommitUpdateTransaction() {
        delegatedTest.testCommitUpdateTransaction();
    }

    @Test
    public void testRollbackUpdateTransaction() {
        delegatedTest.testRollbackUpdateTransaction();
    }

    @Test
    public void testLockingWithUpdateTransaction() {
        delegatedTest.testLockingWithUpdateTransaction();
    }
 
}
