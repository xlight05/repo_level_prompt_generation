package gloodb.utils;

import java.io.Serializable;
import java.util.Arrays;

import gloodb.KeyViolationException;
import gloodb.PersistentNotFoundException;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;

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
        if(logTransactions) System.out.println(String.format("%s: beginning", toString()));
        try {
        	return super.begin();
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught when beginnig transaction.");
        	rt.printStackTrace();
        	throw rt;
        }
    }

    @Override
    public void commit() {
    	try {
	        if(logTransactions) System.out.println(String.format("%s: committing", toString()));
	        super.commit();
	    } catch (RuntimeException rt) {
	    	System.out.println("Exception caught while comitting transaction.");
	    	rt.printStackTrace();
	    	throw rt;
	    }
    }

    @Override
    public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
        if(logUpdates) System.out.println(String.format("%s: creating %s", toString(), persistentObject.toString()));
        try {        
        	return super.create(persistentObject);
	    } catch (RuntimeException rt) {
	    	System.out.println("Exception caught during creating object.");
	    	rt.printStackTrace();
	    	throw rt;
	    }
}

    @Override
    public <T extends Serializable> QueryResult<T> query(Query<T> query, Serializable... parameters) {
        if(logQueries) System.out.println(String.format("%s: running query %s", toString(), query.toString()));
        try {
        	return super.query(query, parameters);
	    } catch (RuntimeException rt) {
	    	System.out.println("Exception caught during query.");
	    	rt.printStackTrace();
	    	throw rt;
	    }
    }

    @Override
    public Serializable remove(Serializable... idFields) {
        if(logUpdates) System.out.println(String.format("%s: removing %s", toString(), Arrays.toString(idFields)));
        try {
	        Serializable result = super.remove(idFields);
	        if(result != null && logUpdates) System.out.println(String.format("%s: removed %s", toString(), result.toString()));
	        return result;
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught when removing object.");
        	rt.printStackTrace();
        	throw rt;
        }

        
    }

    @Override
    public Serializable restore(Serializable... idFields) {
        if(logRestores) System.out.println(String.format("%s: restoring %s", toString(), Arrays.toString(idFields)));
        try {
        return super.restore(idFields);
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught while restoring object.");
        	rt.printStackTrace();
        	throw rt;
        }
    }

    @Override
    public void rollback() {
        if(logTransactions) System.out.println(String.format("%s: rolling back", toString()));
        try {
        	super.rollback();
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught while rolling back transaction.");
        	rt.printStackTrace();
        	throw rt;
        }
    }

    @Override
    public <T extends Serializable> T store(T persistentObject) {
        if(logUpdates) System.out.println(String.format("%s: storing %s", toString(), persistentObject.toString()));
        try {
        	return super.store(persistentObject);
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught storing object.");
        	rt.printStackTrace();
        	throw rt;
        }
    }

    @Override
    public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
        if(logUpdates) System.out.println(String.format("%s: updating %s", toString(), persistentObject.toString()));
        try {
        	return super.update(persistentObject);
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught updating object.");
        	rt.printStackTrace();
        	throw rt;
        }
    }

    @Override
    public void markForRollback() {
        if(logTransactions) System.out.println(String.format("%s: marking for rollback", toString()));
        try {
        	super.markForRollback();
        } catch (RuntimeException rt) {
        	System.out.println("Exception caught marking transaction for rollback.");
        	rt.printStackTrace();
        	throw rt;
        }

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
