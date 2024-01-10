package gloodb.utils;

import java.io.Serializable;
import java.util.Arrays;

import gloodb.KeyViolationException;
import gloodb.PersistentNotFoundException;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;
import gloodb.UpdateQuery;

public class RepositorySysoutInterceptor extends RepositoryInterceptor {
    private boolean logTransactions = true;
    private boolean logQueries = true;
    private boolean logUpdates = true;
    private boolean logRestores = false;
    
    @Override
    public RepositoryInterceptor newInstance() {
        return new RepositorySysoutInterceptor()
            .logQueries(logQueries)
            .logRestores(logRestores)
            .logTransactions(logTransactions)
            .logUpdates(logUpdates);
    }

    @Override
    public Repository begin() {
        if(logTransactions) System.out.println(String.format("%s: begin", toString()));
        return super.begin();
    }

    @Override
    public void commit() {
        if(logTransactions) System.out.println(String.format("%s: commit", toString()));
        super.commit();
    }

    @Override
    public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
        if(logUpdates) System.out.println(String.format("%s: create %s", toString(), persistentObject.toString()));
        return super.create(persistentObject);
    }

    @Override
    public QueryResult query(Query query) {
        if(logQueries) System.out.println(String.format("%s: query %s", toString(), query.toString()));
        return super.query(query);
    }

    @Override
    public QueryResult readOnlyQuery(ReadOnlyQuery query) {
        if(logQueries && logRestores) System.out.println(String.format("%s: readonly query %s", toString(), query.toString()));
        return super.readOnlyQuery(query);
    }

    @Override
    public QueryResult updateQuery(UpdateQuery query) {
        if(logQueries && logUpdates) System.out.println(String.format("%s: update query %s", toString(), query.toString()));
        return super.updateQuery(query);
    }

    @Override
    public Serializable remove(Serializable... idFields) {
        Serializable result = super.remove(idFields);
        if(result != null && logUpdates) System.out.println(String.format("%s: remove %s", toString(), Arrays.toString(idFields)));
        return result;
        
    }

    @Override
    public Serializable restore(Serializable... idFields) {
        if(logRestores) System.out.println(String.format("%s: restore %s", toString(), Arrays.toString(idFields)));
        return super.restore(idFields);
    }

    @Override
    public void rollback() {
        if(logTransactions) System.out.println(String.format("%s: rollback", toString()));
        super.rollback();
    }

    @Override
    public <T extends Serializable> T store(T persistentObject) {
        if(logUpdates) System.out.println(String.format("%s: store %s", toString(), persistentObject.toString()));
        return super.store(persistentObject);
    }

    @Override
    public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
        if(logUpdates) System.out.println(String.format("%s: update %s", toString(), persistentObject.toString()));
        return super.update(persistentObject);
    }

    @Override
    public void markForRollback() {
        if(logTransactions) System.out.println(String.format("%s: mark for rollback", toString()));
        super.markForRollback();
    }

    public RepositorySysoutInterceptor logTransactions(boolean logTransactions) {
        this.logTransactions = logTransactions;
        return this;
    }

    public RepositorySysoutInterceptor logQueries(boolean logQueries) {
        this.logQueries = logQueries;
        return this;
    }

    public RepositorySysoutInterceptor logUpdates(boolean logUpdates) {
        this.logUpdates = logUpdates;
        return this;
    }

    public RepositorySysoutInterceptor logRestores(boolean logRestores) {
        this.logRestores = logRestores;
        return this;
    }
    
    

}
