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
import gloodb.PreCreate;
import gloodb.Repository;
import gloodb.SortingCriteria;
import gloodb.Version;
import gloodb.tutorials.simple.GlooTutorialException;
import gloodb.utils.Equality;
import gloodb.utils.Sequence;
import static gloodb.utils.Equality.PersistentIdentityEquality;

import java.io.Serializable;

/**
 * Club persistent class. The class is intercepted by the {@link Clubs} interceptor.
 */
@Intercepted({Clubs.class})
public class Club implements Serializable, Cloneable {
    private static final long serialVersionUID = -4080251889953648395L;

    /**
     * Club id holder. The persistent identity is defined as a method ({@link Club#getId()}). 
     * The identity values are automatically generated off a sequence during the PreCreate phase 
     * ({@link Club#preCreate(Repository)}).
     */
    private Serializable id;
    
    /**
     * Persistent object version.
     */
    @Version
    long version;
    
    /**
     * Club name.
     */
    private String name;
    
    /**
     * Creates a club instance. The id will be initialized of the
     * Club.class sequence pre create.
     */
    public Club() {
        super();
        this.version = 0L;
    }
    
    /**
     * Creates a club with a specified id.
     * @param id The club id.
     */
    Club(Serializable id) {
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
    
    /** 
     * Identity method of the Club class. The value is stored by the 
     * {@link Club#id} field.
     * @return The object identity. 
     */
    @Identity
    public Serializable getId() {
        return id;
    }

    /**
     * Returns the club name as a sorting criteria.
     * @return The name sorting criteria.
     */
    @SortingCriteria("Clubs.name")
    public String getName() {
        return name;
    }
    
    /**
     * Sets the club name.
     * @param name The club name.
     * @return This for a fluent api.
     */
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
        return Equality.check(this, obj, new PersistentIdentityEquality<Club>() {
        	@Override
        	public Serializable getId(Club club) {
        		return club.getId();
        	}
        });
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, name: %s", id.toString(), name);
    }
    
    /**
     * Pre create method. Generates an id of the Club.class sequence
     * if the id is null.
     * @param repository The repository.
     */
    @PreCreate
    void preCreate(Repository repository) {
    	this.id = Sequence.getNextId(repository, Club.class);
    }
    
}
