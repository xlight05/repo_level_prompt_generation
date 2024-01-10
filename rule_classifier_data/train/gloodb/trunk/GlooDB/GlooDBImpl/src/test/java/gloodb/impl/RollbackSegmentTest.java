package gloodb.impl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import gloodb.LockingException;
import gloodb.SimpleVersionedSerializable;
import gloodb.txmgr.TxContext;

import org.junit.Before;
import org.junit.Test;

public class RollbackSegmentTest {
    private HashSet<Transaction> transactionRoll;
    
    @Before
    public void setup() {
        transactionRoll = new HashSet<Transaction>();
    }

    @Test
    public void testRollbackSegment() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        assertThat(rollbackSegment.getPendingTransactions().size(), is(0));
        assertThat(rollbackSegment.existsTx(new Transaction(2, null)), is(false));
    }

    @Test
    public void testStartTx() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        Transaction tx = new Transaction(1, null);
        rollbackSegment.startTx(tx);
        
        assertThat(rollbackSegment.existsTx(tx), is(true));
        assertThat(rollbackSegment.existsTx(new Transaction(2, null)), is(false));
        assertThat(rollbackSegment.getPendingTransactions().size(), is(1));
        assertThat(rollbackSegment.getPendingTransactions().contains(tx), is(true));
    }

    @Test
    public void testFinshTx() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        rollbackSegment.startTx(new Transaction(2, null));
        
        Transaction tx = new Transaction(1, null);
        rollbackSegment.startTx(tx);
        
        rollbackSegment.finshTx(tx);
        assertThat(rollbackSegment.existsTx(tx), is(false));
        assertThat(rollbackSegment.getPendingTransactions().size(), is(1));
        assertThat(rollbackSegment.getPendingTransactions().contains(tx), is(false));
    }

    @Test
    public void testUndoTx() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        final Transaction tx = new Transaction(1, null);
        rollbackSegment.startTx(tx);
        transactionRoll.add(tx);        
        rollbackSegment.addUndoTx(tx, new UndoTransaction(){
            private static final long serialVersionUID = 1776288525012848878L;

            @Override
            public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
                transactionRoll.remove(tx);
                return null;
            }});

        
        final Transaction tx2 = new Transaction(2, null);
        rollbackSegment.startTx(tx2);
        transactionRoll.add(tx2);
        rollbackSegment.addUndoTx(tx2, new UndoTransaction(){
            private static final long serialVersionUID = 1776288525012848878L;

            @Override
            public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
                transactionRoll.remove(tx2);
                return null;
            }});
        
        assertThat(transactionRoll.contains(tx), is(true));
        assertThat(transactionRoll.contains(tx2), is(true));
        
        rollbackSegment.undoTx(null, tx, null);
        assertThat(transactionRoll.contains(tx), is(false));
        assertThat(transactionRoll.contains(tx2), is(true));

        rollbackSegment.undoTx(null, tx2, null);
        assertThat(transactionRoll.contains(tx), is(false));
        assertThat(transactionRoll.contains(tx2), is(false));
}

    @Test
    public void testSetGetLock() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        Transaction tx = new Transaction(1, null);
        rollbackSegment.startTx(tx);
        
        rollbackSegment.setLock("one", tx);
        assertThat(rollbackSegment.getLockingTx("one"), is(equalTo(tx)));
        assertThat(rollbackSegment.getLockingTx("two"), is(nullValue()));
        assertThat(rollbackSegment.getObjectsLockedByTx(tx, false).size(), is(1));
        assertThat(rollbackSegment.getObjectsLockedByTx(tx, false).contains("one"), is(true));
        
        rollbackSegment.finshTx(tx);
        assertThat(rollbackSegment.getLockingTx("one"), is(nullValue()));
    }
    
    @Test
    public void testTryLock() {
        RollbackSegment rollbackSegment = new RollbackSegment();
        Transaction tx = new Transaction(1, null);
        Transaction tx2 = new Transaction(2, null);
        rollbackSegment.startTx(tx);
        rollbackSegment.startTx(tx2);
        
        rollbackSegment.tryLock(tx, "one");
        rollbackSegment.tryLock(tx2, "two");
        
        try {
            rollbackSegment.tryLock(tx2, "one");
            fail();
        } catch(LockingException ge) {
            // expected;
        }
    }

    @Test
    public void testBackupObject() {
        SimpleVersionedSerializable object = new SimpleVersionedSerializable("one");
        Transaction tx = new Transaction(1, null);
        Transaction tx2 = new Transaction(2, null);
        RollbackSegment rollbackSegment = new RollbackSegment();
        
        rollbackSegment.backupObject("one", object);
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx, "one"), is(false));
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx2, "one"), is(false));
        
        rollbackSegment.tryLock(tx, "one");
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx, "one"), is(false));
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx2, "one"), is(true));
        
        assertThat((SimpleVersionedSerializable)rollbackSegment.getObjectBackup("one"), is(equalTo(object)));
        
        rollbackSegment.finshTx(tx);
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx, "one"), is(false));
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx2, "one"), is(false));
    }
    
    @Test
    public void testCommitNestedTransaction() {
        Transaction tx2 = new Transaction(2, null);
        SimpleVersionedSerializable object = new SimpleVersionedSerializable("one");
        
        // Start a parent transaction with a child transaction.
        RollbackSegment rollbackSegment = new RollbackSegment();
        Transaction parentTx = new Transaction(0, null);
        rollbackSegment.startTx(parentTx);
        
        final Transaction childTx = new Transaction(1, parentTx);
        rollbackSegment.startTx(childTx);
        
        // Lock an object in the child tx.
        rollbackSegment.tryLock(childTx, "one");
        rollbackSegment.backupObject("one", object);
        assertThat(rollbackSegment.shouldRestoreFromBackup(childTx, "one"), is(false));
        assertThat(rollbackSegment.shouldRestoreFromBackup(parentTx, "one"), is(true));
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx2, "one"), is(true));
        
        assertThat(rollbackSegment.getLockingTx("one"), is(equalTo(childTx)));
        assertThat(rollbackSegment.getObjectsLockedByTx(childTx, false).size(), is(1));
        assertThat(rollbackSegment.getObjectsLockedByTx(childTx, false).contains("one"), is(true));
        assertThat(rollbackSegment.getObjectsLockedByTx(parentTx, false).size(), is(0));
        assertThat(rollbackSegment.getObjectsLockedByTx(parentTx, false).contains("one"), is(false));
        
        // Add an undo transaction.
        transactionRoll.add(childTx);        
        rollbackSegment.addUndoTx(childTx, new UndoTransaction(){
            private static final long serialVersionUID = 1776288525012848878L;

            @Override
            public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
                transactionRoll.remove(childTx);
                return null;
            }});

        
        // Finish the child transaction. The undo and locks should be transferred to the parent tx.
        rollbackSegment.finshTx(childTx);
        assertThat(transactionRoll.contains(childTx), is(true));

        assertThat(rollbackSegment.shouldRestoreFromBackup(parentTx, "one"), is(false));
        assertThat(rollbackSegment.shouldRestoreFromBackup(tx2, "one"), is(true));

        assertThat(rollbackSegment.getLockingTx("one"), is(equalTo(parentTx)));
        assertThat(rollbackSegment.getObjectsLockedByTx(parentTx, false).size(), is(1));
        assertThat(rollbackSegment.getObjectsLockedByTx(parentTx, false).contains("one"), is(true));
        
        rollbackSegment.undoTx(null, parentTx, null);
        assertThat(transactionRoll.contains(childTx), is(false));

    }
}
