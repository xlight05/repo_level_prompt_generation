/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.utils;

import gloodb.GlooException;
import gloodb.LockingException;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.UpdateQuery;

/**
 * Utility class wrapping the Query interface. The purpose of this utility is to
 * simplify transaction and locking related coding. The programmer can use this class to
 * run multiple repository operation in a single transaction.
 */
public class TransactionController {

    /**
     * Enumeration specifying the transaction modes.
     */
    public enum TxMode {
        /**
         * Runs the queries in non-transactional mode (using the top level transaction).
         */
        Top,
        /**
         * Creates a new transaction using the controller's transaction as a parent
         * (if the controller has one).
         */
        New,
        /**
         * Joins the controller's transaction or uses the top level transaction (null)
         * if the controller doesn't have a transaction.
         */
        Same
    }

    private final Repository repository;

    /**
     * Creates a new transaction controller.
     * @param repository The repository to use.
     */
    public TransactionController(Repository repository) {
        this.repository = repository;
    }

    /**
     * Runs the transaction on the repository using the specified transaction and
     * lock modes. If a LockingException occurs, will retry the transaction up to the
     * number returned by {@link #getMaxRetries()}.
     * @param txMode The tx mode
     * @param lockMode The lock mode
     * @param query The query to run.
     * @return The query result.
     */
    public QueryResult run(TxMode txMode, Query query) {
        Repository runtime = getRuntimeRepository(txMode);
        if(runtime.isTopLevel() && txMode.equals(TxMode.Same)) {
        	// In Same mode with top level transactions cannot be rollbacked.
        	// Therefore this mode is invalid.
        	throw new InvalidTransactionModeException("Cannot use TxMode.Same with top level transaction");
        }
   
        try {
            for(int retry = 0; retry < getMaxRetries() - 1; retry++) {
                try {
                    return runQuery(runtime, query);
                } catch (LockingException le) {
                    continue;
                }
            }
            return runQuery(runtime, query);
        } finally {
            onTransactionDone(runtime, txMode);
        }
    }

    /**
     * Override this method to set the max number of retries.
     * @return Max number of retries (default 3).
     */
    protected int getMaxRetries() {
        return 3;
    }

    /**
     * Depending on the mode this method either:
     * <ul><li>mode is Same: it return the parent transaction
     * <li>mode is New: it creates a new nested transaction.
     * </ul>
     * @param txMode The tx mode.
     * @return The transaction to use for this query.
     */
    protected Repository getRuntimeRepository(TxMode txMode) {
        if (txMode == TxMode.New) {
            return repository.begin();
        } else if (txMode == TxMode.Same) {
            return repository;
        } else if(txMode == TxMode.Top) {
            if(!repository.isTopLevel()) throw new InvalidTransactionModeException(
                    String.format("Unexpected transaction mode %s: a transaction is already open.", txMode.toString()));
            return repository;
        }
        throw new GlooException(String.format("Unexpected transaction mode %s", txMode.toString()));
    }

    /**
     * Depending on the query type, it runs the query:
     * <ul><li>gloodb.Query: completely unguarded. This type of query runs
     * completely unguarded. All other repository changes will run concurrently.
     * <li>gloodb.ReadOnlyQuery: allows concurrent reads. This type of query should be used
     * with read only transactions. It prevents update transaction from changing
     * the repository. <p><b>Important:</b> Running update transactions in this mode
     * will dead lock.
     * <li>gloodb.UpdateQuery: allows concurrent reads, guarded from updates. Use
     * this type of query update transactions.
     * @param runtime The runtime repository
     * @param query The query
     * @return The query result.
     */
    protected QueryResult runQuery(Repository runtime, Query query) {
        QueryResult result;
        if (query instanceof ReadOnlyQuery) {
            result = runtime.readOnlyQuery((ReadOnlyQuery)query);
        } else  if (query instanceof UpdateQuery) {
            result = runtime.updateQuery( (UpdateQuery)query);
        } else {
            result = runtime.query(query);
        }
        if (result.hasException()) {
            result = onException(result, runtime);
        }
        return result;
    }

    /**
     * On exception callback. Default implementation marks the transaction for
     * rollback.
     * @param result The result (i.e the exception).
     * @param runtime The runtime repository
     * @return A new QueryResult if necessary.
     */
    protected QueryResult onException(QueryResult result, Repository runtime) {
        if(!runtime.isTopLevel()) runtime.markForRollback();
    	Exception e = result.getExceptionResult();
    	if(e instanceof GlooException) {
    		return new QueryResult(((GlooException)e).unpack());
    	}
    	return result;
    }

    /**
     * On transaction done callback. If the transaction mode is
     * <ul><li>Same: does nothing since it is the responsibility of the caller
     * to manipulate the parent transaction (the controller only joins this transaction).
     * <li>all other modes: commits on successful transaction, rollback if an
     * exception was caught.
     * </ul>
     * @param runtime The runtime repository.
     * @param txMode The transaction mode.
     * @param repository The repository.
     */
    protected void onTransactionDone(Repository runtime, TxMode txMode) {
        if (runtime.isTopLevel()) {
            return;
        }
        if (txMode == TxMode.New) {
            if (runtime.canCommit()) {
                runtime.commit();
            } else {
                runtime.rollback();
            }
        }
    }
}
