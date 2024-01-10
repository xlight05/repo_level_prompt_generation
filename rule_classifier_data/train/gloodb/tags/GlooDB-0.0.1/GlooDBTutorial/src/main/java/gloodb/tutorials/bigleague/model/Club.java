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
package gloodb.tutorials.bigleague.model;

import gloodb.Identity;
import gloodb.Intercepted;
import gloodb.SortingCriteria;
import gloodb.Version;
import gloodb.tutorials.simple.GlooTutorialException;
import gloodb.utils.Equality;
import static gloodb.utils.Equality.Operator;

import java.io.Serializable;

@Intercepted({Clubs.class})
public class Club implements Serializable, Cloneable {
    private static final long serialVersionUID = -4080251889953648395L;

    private final Serializable id;
    
    @Version
    long version;
    
    private String name;
    
    public Club(Serializable id) {
        super();
        this.id = id;
        this.version = 0L;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new GlooTutorialException(e);
        }
    }
    
    @Identity
    public Serializable getId() {
        return id;
    }

    @SortingCriteria("Clubs.name")
    public String getName() {
        return name;
    }
    
    public Club setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return Equality.check(this, obj, new Operator<Club>() {
            @Override
            public boolean check(Club left, Club right) {
                Serializable leftId = left.id;
                Serializable rightId = right.id;
                if(leftId == rightId) return true;
                if(rightId == null) return false;
                if(leftId == null) return false;
                return leftId.equals(rightId);
            }});
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, name: %s", id.toString(), name);
    }
    
}
