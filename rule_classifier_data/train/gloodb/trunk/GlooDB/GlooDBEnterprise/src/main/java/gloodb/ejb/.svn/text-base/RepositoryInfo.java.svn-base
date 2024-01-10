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

import java.io.Serializable;

class RepositoryInfo implements Serializable {
    private static final long serialVersionUID = 6857234219404158590L;

    private long cachePurgeInterval;
    private int cachePurgePercentage;
    private String nameSpace;

    public RepositoryInfo() {
        super();
        this.cachePurgeInterval = 60000;
        this.setCachePurgePercentage(10);
    }

    public RepositoryInfo(String nameSpace) {
        this();
        this.setNameSpace(nameSpace);
    }

    public long getCachePurgeInterval() {
        return cachePurgeInterval;
    }

    public RepositoryInfo setCachePurgeInterval(long cachePurgeFrequency) {
        this.cachePurgeInterval = cachePurgeFrequency;
        return this;
    }

    public int getCachePurgePercentage() {
        return this.cachePurgePercentage;
    }

    public RepositoryInfo setCachePurgePercentage(int cachePurgePercentage) {
        this.cachePurgePercentage = cachePurgePercentage;
        return this;
    }

    public RepositoryInfo setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nameSpace == null) ? 0 : nameSpace.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RepositoryInfo other = (RepositoryInfo) obj;
        if (nameSpace == null) {
            if (other.nameSpace != null)
                return false;
        } else if (!nameSpace.equals(other.nameSpace))
            return false;
        return true;
    }
    
    

}
