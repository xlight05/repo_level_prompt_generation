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

import static gloodb.operators.Expression.iterate;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import gloodb.Identity;
import gloodb.Interceptor;
import gloodb.Lazy;
import gloodb.PostCreate;
import gloodb.PostRemove;
import gloodb.PreCreate;
import gloodb.PostRestore;
import gloodb.Repository;
import gloodb.associations.AssociationFactory;
import gloodb.associations.UniqueIndex;
import gloodb.operators.Expression;
import gloodb.operators.Get;
import gloodb.queries.ObjectIdsQuery;
import gloodb.queries.ObjectIdsQuery.Filter;
import gloodb.tutorials.simple.GlooTutorialException;
import gloodb.utils.StringComparator;

@Identity
public class Players implements Serializable, Cloneable, Interceptor<Player> {
    private static final long serialVersionUID = 3728635244714982745L;

    private LinkedList<Serializable> players;
    private UniqueIndex<Player, String> nameIndex;

    private transient Repository repository;

    public Players() {
        this.players = new LinkedList<Serializable>();
        this.nameIndex = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            Players copy = (Players) super.clone();
            copy.players = (LinkedList<Serializable>) players.clone();
            copy.nameIndex = (UniqueIndex<Player, String>) nameIndex.clone();
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooTutorialException(cnse);
        }
    }

    public LinkedList<Player> getPlayers() {
        LinkedList<Player> playersSet = new LinkedList<Player>();
        iterate(players, new Get<Serializable, Player>(repository, playersSet));
        return playersSet;
    }
    
    public LinkedList<Lazy<Player>> getNameSortedPlayers(boolean ascending) {
        return nameIndex.getSortedSet(ascending);
    }

    public int size() {
        return players.size();
    }
    
    @PreCreate
    void preCreate(final Repository repository) {
        AssociationFactory factory = AssociationFactory.getInstance(repository);
        nameIndex = factory.newUniqueIndex(Player.class, "Player.name", new StringComparator());
        
        List<Serializable> playerIds = ObjectIdsQuery.fetch(repository, new Filter(){
            private static final long serialVersionUID = 2511326005373266316L;

            @Override
            public boolean match(Serializable... id) {
                return Player.class.isAssignableFrom(repository.getClassForId(id));
            }});
        Expression.iterate(playerIds, new Expression(){
                                        @Override
                                        public Expression evaluate(Serializable playerId) {
                                            players.add(playerId);
                                            nameIndex.add((Player)repository.restore(playerId));
                                            return null;
                                        }});
    }
    
    @PostRestore
    void postRestore(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void before(Class<? extends Annotation> annotationClass, Repository repository, Player player) {
    }

    @Override
    public void after(Class<? extends Annotation> annotationClass, Repository repository, Player player) {
        boolean dirty = false;
        if (PostCreate.class.equals(annotationClass)) {
            players.add(player.getId());
            nameIndex.add(player);
            dirty = true;
        } else if (PostRemove.class.equals(annotationClass)) {
            dirty = players.remove(player.getId());
            dirty |= nameIndex.remove(player);
        } else {
            return;
        }
        if(dirty) repository.update(this);
    }
    
    @Override
    public String toString() {
        return "Players";
    }
}
