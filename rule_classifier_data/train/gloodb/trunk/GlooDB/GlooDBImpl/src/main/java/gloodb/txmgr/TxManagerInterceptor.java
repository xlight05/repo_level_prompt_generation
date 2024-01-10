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
package gloodb.txmgr;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * TxManager interceptor class.
 *
 */
public abstract class TxManagerInterceptor implements TxManager {
    private TxManager txManager;

    /**
     * Sets the intercepted tx manager.
     * @param txManager The intercepted tx manager.
     * @return The intercepted tx manager.
     */
    public TxManager setTxManager(TxManager txManager) {
        this.txManager = txManager;
        return this;
    }

    @Override
    public Serializable execute(TxContext ctx, TxLogReadTransaction transaction) {
        return txManager.execute(ctx, transaction);
    }

    @Override
    public Serializable execute(TxContext ctx, TxLogWriteTransaction transaction) {
        return txManager.execute(ctx, transaction);
    }

    @Override
    public BigInteger getManagedObjectVersion() {
        return txManager.getManagedObjectVersion();
    }

    @Override
    public BigInteger getManagedObjectStartVersion() {
        return txManager.getManagedObjectStartVersion();
    }

    @Override
    public <T extends Serializable> T start(TxContext ctx, T managedObject) {
        return txManager.start(ctx, managedObject);
    }

    @Override
    public void takeSnapshot() {
        txManager.takeSnapshot();   
    }
}
