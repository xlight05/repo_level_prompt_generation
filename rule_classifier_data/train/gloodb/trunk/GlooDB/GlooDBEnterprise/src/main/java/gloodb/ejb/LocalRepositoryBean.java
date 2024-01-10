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
package gloodb.ejb;

import gloodb.Repository;
import gloodb.RepositoryInterceptor;

import java.util.HashMap;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import gloodb.file.FileBasedRepositoryFactory;
import gloodb.impl.RepositoryAdmin;

/**
 * Session Bean implementation class LocalRepositoryBean. The
 * LocalRepositoryBean is a singleton which manages a map of repositories.
 */
@Singleton
@LocalBean
public class LocalRepositoryBean implements LocalRepository {

    @Resource
    TimerService timerService;

    HashMap<String, Repository> repositoryMap;

    /**
     * Default constructor.
     */
    public LocalRepositoryBean() {
        this.repositoryMap = new HashMap<String, Repository>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see gloodb.ejb.LocalRepository#getRepository(java.lang.String)
     */
    @Override
    public Repository getRepository(String nameSpace) {
        Repository repository = getRepositoryInstance(nameSpace);
        setTimer(new RepositoryInfo(nameSpace));
        return repository;
    }
    
    /*
     */
    @Override
    public Repository interceptRepository(String nameSpace, RepositoryInterceptor interceptor) {
        Repository repository = getRepositoryInstance(nameSpace);
        interceptor.setRepository(repository);
        cancelTimer(nameSpace);
        repositoryMap.put(nameSpace, interceptor);
        return interceptor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gloodb.ejb.LocalRepository#setCachePurge(java.lang.String, long,
     * int)
     */
    @Override
    public boolean setCachePurge(String nameSpace, long cachePurgeInterval, int cachePurgePercentage) {
        if (!this.repositoryMap.containsKey(nameSpace))
            return false;
        RepositoryInfo info = new RepositoryInfo(nameSpace).setCachePurgeInterval(cachePurgeInterval).setCachePurgePercentage(
                cachePurgePercentage);

        setTimer(info);
        return true;
    }

    Repository getRepositoryInstance(String nameSpace) {
        Repository repository = repositoryMap.get(nameSpace);
        if (repository == null) {
        	FileBasedRepositoryFactory factory = new FileBasedRepositoryFactory();
            repository = factory.buildRepository(nameSpace);
            repositoryMap.put(nameSpace, repository);
        }
        return repository;
    }

    @Timeout
    void onTimeout(Timer timer) {
        RepositoryInfo repositoryInfo = (RepositoryInfo) timer.getInfo();
        RepositoryAdmin admin = (RepositoryAdmin) getRepository(repositoryInfo.getNameSpace());
        admin.flushCache(repositoryInfo.getCachePurgePercentage());
    }

    @PreDestroy
    void tearDown() {
        for (Timer timer : timerService.getTimers()) {
            timer.cancel();
        }
    }

    private void cancelTimer(String nameSpace) {
        for (Timer timer : timerService.getTimers()) {
            RepositoryInfo timerInfo = (RepositoryInfo) timer.getInfo();
            if (timerInfo.getNameSpace().equals(nameSpace)) {
                timer.cancel();
            }
        }
    }

    private void setTimer(RepositoryInfo repositoryInfo) {
        cancelTimer(repositoryInfo.getNameSpace());
        timerService.createTimer(repositoryInfo.getCachePurgeInterval(), 
                repositoryInfo.getCachePurgeInterval(),
                repositoryInfo);
    }
}
