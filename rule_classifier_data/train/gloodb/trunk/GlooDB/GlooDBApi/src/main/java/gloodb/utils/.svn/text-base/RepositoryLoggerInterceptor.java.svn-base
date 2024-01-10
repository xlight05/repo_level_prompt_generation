/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import gloodb.GlooException;
import gloodb.KeyViolationException;
import gloodb.PersistentNotFoundException;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.RepositoryInterceptor;

/**
 * Repository logger interceptor.
 *
 */
public class RepositoryLoggerInterceptor extends RepositoryInterceptor {
    private boolean logTransactions = true;
    private boolean logQueries = true;
    private boolean logUpdates = true;
    private boolean logRestores = false;
    
    private final Logger logger;
    private Level logLevel = Level.FINE;
    private Level runtimeExceptionLevel = Level.SEVERE;
    private Level glooExceptionLevel = Level.FINE;
    
    /**
     * Creates a repository logger interceptor which logs using an anonymous logger.
     */
    public RepositoryLoggerInterceptor() {
    	super();
    	this.logger = Logger.getAnonymousLogger();
    }
    
    /**
     * Creates a repository logger interceptor which logs using the named logger.
     * @param loggerName
     */
    public RepositoryLoggerInterceptor(String loggerName) {
    	super();
    	this.logger = Logger.getLogger(loggerName);
    }
    
    /**
     * Creates a repository logger interceptor which logs using the logger provided.
     * @param logger
     */
    public RepositoryLoggerInterceptor(Logger logger) {
    	super();
    	this.logger = logger;
    }
    
    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#newInstance()
     */
    @Override
    public RepositoryInterceptor newInstance() {
        return new RepositoryLoggerInterceptor(this.logger)
            .logQueries(logQueries)
            .logRestores(logRestores)
            .logTransactions(logTransactions)
            .logUpdates(logUpdates)
            .setLogLevel(logLevel)
            .setExceptionLogLevel(runtimeExceptionLevel)
            .setGlooExceptionLogLevel(glooExceptionLevel);
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#begin()
     */
    @Override
    public Repository begin() {
        if(logTransactions) logger.log(logLevel, String.format("%s: beginning", toString()));
        try {
        	return super.begin();
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught when beginnig transaction.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught when beginnig transaction.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#commit()
     */
    @Override
    public void commit() {
        if(logTransactions) logger.log(logLevel, String.format("%s: committing", toString()));
    	try {
	        super.commit();
	    } catch (GlooException ge) {
	    	logger.log(glooExceptionLevel, "GlooException caught while comitting transaction.", ge);
	    	throw ge;
	    } catch (RuntimeException rt) {
	    	logger.log(runtimeExceptionLevel, "Exception caught while comitting transaction.", rt);
	    	throw rt;
	    }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#create(java.io.Serializable)
     */
    @Override
    public <T extends Serializable> T create(T persistentObject) throws KeyViolationException {
        if(logUpdates) logger.log(logLevel, String.format("%s: creating %s", toString(), persistentObject.toString()));
        try {        
        	return super.create(persistentObject);
	    } catch (GlooException ge) {
	    	logger.log(glooExceptionLevel, "GlooException caught during creating object.", ge);
	    	throw ge;
	    } catch (RuntimeException rt) {
	    	logger.log(runtimeExceptionLevel, "Exception caught during creating object.", rt);
	    	throw rt;
	    }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#query(gloodb.Query, java.io.Serializable[])
     */
    @Override
    public <T extends Serializable> QueryResult<T> query(Query<T> query, Serializable... parameters) {
        if(logQueries) logger.log(logLevel, String.format("%s: running query %s", toString(), query.toString()));
        try {
        	return super.query(query, parameters);
	    } catch (GlooException ge) {
	    	logger.log(glooExceptionLevel, "GlooException caught during query.", ge);
	    	throw ge;
	    } catch (RuntimeException rt) {
	    	logger.log(runtimeExceptionLevel, "Exception caught during query.", rt);
	    	throw rt;
	    }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#remove(java.io.Serializable[])
     */
    @Override
    public Serializable remove(Serializable... idFields) {
        if(logUpdates) logger.log(logLevel, String.format("%s: removing %s", toString(), Arrays.toString(idFields)));
        try {
	        Serializable result = super.remove(idFields);
	        if(result != null && logUpdates) logger.log(logLevel, String.format("%s: removed %s", toString(), result.toString()));
	        return result;
        } catch (GlooException rt) {
        	logger.log(glooExceptionLevel, "GlooException caught when removing object.", rt);
        	throw rt;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught when removing object.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#restore(java.io.Serializable[])
     */
    @Override
    public Serializable restore(Serializable... idFields) {
        if(logRestores) logger.log(logLevel, String.format("%s: restoring %s", toString(), Arrays.toString(idFields)));
        try {
        	return super.restore(idFields);
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught while restoring object.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught while restoring object.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#rollback()
     */
    @Override
    public void rollback() {
        if(logTransactions) logger.log(logLevel, String.format("%s: rolling back", toString()));
        try {
        	super.rollback();
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught while rolling back transaction.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught while rolling back transaction.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#store(java.io.Serializable)
     */
    @Override
    public <T extends Serializable> T store(T persistentObject) {
        if(logUpdates) logger.log(logLevel, String.format("%s: storing %s", toString(), persistentObject.toString()));
        try {
        	return super.store(persistentObject);
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught storing object.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught storing object.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#update(java.io.Serializable)
     */
    @Override
    public <T extends Serializable> T update(T persistentObject) throws PersistentNotFoundException {
        if(logUpdates) logger.log(logLevel, String.format("%s: updating %s", toString(), persistentObject.toString()));
        try {
        	return super.update(persistentObject);
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught updating object.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught updating object.", rt);
        	throw rt;
        }
    }

    /*
     * (non-Javadoc)
     * @see gloodb.RepositoryInterceptor#markForRollback()
     */
    @Override
    public void markForRollback() {
        if(logTransactions) logger.log(logLevel, String.format("%s: marking for rollback", toString()));
        try {
        	super.markForRollback();
        } catch (GlooException ge) {
        	logger.log(glooExceptionLevel, "GlooException caught while marking transaction for rollback.", ge);
        	throw ge;
        } catch (RuntimeException rt) {
        	logger.log(runtimeExceptionLevel, "Exception caught while marking transaction for rollback.", rt);
        	throw rt;
        }

    }

    /**
     * Sets if transaction should be logged or not (default true).
     * @param logTransactions True to log transaction life cycle.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor logTransactions(boolean logTransactions) {
        this.logTransactions = logTransactions;
        return this;
    }

    /**
     * Sets if queries should be logged or not (default true).
     * @param logQueries True to log queries.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor logQueries(boolean logQueries) {
        this.logQueries = logQueries;
        return this;
    }

    /**
     * Sets if updates should be logged or not (default true).
     * @param logUpdates True to log updates.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor logUpdates(boolean logUpdates) {
        this.logUpdates = logUpdates;
        return this;
    }

    /**
     * Sets if restores should be logged or not (default false).
     * @param logRestores True to log restores.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor logRestores(boolean logRestores) {
        this.logRestores = logRestores;
        return this;
    }

    /**
     * Sets the log level (default FINE).
     * @param level The log level
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor setLogLevel(Level level) {
    	this.logLevel = level;
    	return this;
    }

    /**
     * Sets the log level when exceptions are thrown (default SEVERE)
     * @param level The exception logging level.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor setExceptionLogLevel(Level level) {
    	this.runtimeExceptionLevel = level;
    	return this;
    }

    /**
     * Sets the log level for gloo exceptions (default FINE)
     * @param level The gloo exception logging level.
     * @return This repository logger interceptor.
     */
    public RepositoryLoggerInterceptor setGlooExceptionLogLevel(Level level) {
    	this.glooExceptionLevel = level;
    	return this;
    }
}
