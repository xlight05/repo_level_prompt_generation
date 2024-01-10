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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import gloodb.Identity;
import gloodb.Lazy;
import gloodb.PostCreate;
import gloodb.PostRemove;
import gloodb.PostUpdate;
import gloodb.PreCreate;
import gloodb.Repository;
import gloodb.associations.AssociationFactory;
import gloodb.associations.NonUniqueIndex;
import gloodb.associations.OrderBy;
import gloodb.associations.OrderByUniqueIndex;
import gloodb.associations.OrderByUniqueIndex.Access;
import gloodb.associations.UniqueIndex;
import gloodb.operators.ExpressionedInterceptor;
import gloodb.operators.ReferenceFetch;
import gloodb.utils.IntegerComparator;
import gloodb.utils.StringComparator;

import static gloodb.operators.Iterators.*;

@Identity
public class Players extends ExpressionedInterceptor implements Serializable, Cloneable {
    private static final long serialVersionUID = 3728635244714982745L;

    public static ArrayList<Lazy<Player>> getPlayers(Repository repository, OrderBy<Player> orderBy, boolean fetch, boolean ascending) {
    	ArrayList<Lazy<Player>> players = orderBy.getOrderedCollection(new ArrayList<Lazy<Player>>(), repository, ascending);
		if(fetch) {
			iterate(players, new ReferenceFetch<Player>(repository));
		}
		return players;
    }
    
	public static ArrayList<Lazy<Player>> getNameSortedPlayers(Repository repository, boolean fetch, boolean ascending) {
		return getPlayers(repository, new OrderByUniqueIndex<Player>(Players.class, "playersNameIndex", Access.SortedSet), fetch, ascending);
	}
    
    private UniqueIndex<Player, String> playersNameIndex;
    private NonUniqueIndex<Player, Integer> playersValueIndex;

    @SuppressWarnings({ "unchecked", "serial" })
	public Players() {
    	super();
        this.playersNameIndex = null;
     
        this.adviceAfter(new InterceptorMethod<Player>() {
			@Override
			protected void evaluate(Player player, Class<? extends Annotation> clazz, Repository repository) {
				addPlayerToIndexes(repository, player);
			}
		}, PostCreate.class);

        this.adviceAfter(new InterceptorMethod<Player>() {
			@Override
			protected void evaluate(Player player, Class<? extends Annotation> clazz, Repository repository) {
				updatePlayerIndexes(repository, player);
			}
		}, PostUpdate.class);
        
        this.adviceAfter(new InterceptorMethod<Player>() {
			@Override
			protected void evaluate(Player player, Class<? extends Annotation> clazz, Repository repository) {
				removePlayerFromIndexes(repository, player);
			}
		}, PostRemove.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        Players copy = (Players) super.clone();
        copy.playersNameIndex = (UniqueIndex<Player, String>) playersNameIndex.clone();
        return copy;
    }

    public ArrayList<Lazy<Player>> getPlayersSortedByName(boolean ascending) {
        return playersNameIndex.getSortedSet(new ArrayList<Lazy<Player>>(), ascending);
    }

    public int size() {
        return playersNameIndex.size();
    }
    
    @SuppressWarnings("unused")
	@PreCreate
    private void preCreate(final Repository repository) {
        AssociationFactory factory = AssociationFactory.getInstance(repository);
        playersNameIndex = factory.newUniqueIndex(Player.class, "Player.name", new StringComparator());
        playersValueIndex = factory.newNonUniqueIndex(Player.class, "Player.value", new IntegerComparator());
    }
    
    @Override
    public String toString() {
        return "Players";
    }
    
    private void addPlayerToIndexes(Repository repository, Player player) {
        playersNameIndex.add(player);
        playersValueIndex.add(player);
    	repository.update(this);
    }
    
    private void updatePlayerIndexes(Repository repository, Player player) {
    	boolean dirty = playersValueIndex.add(player);
    	if(dirty) repository.update(this);
    }
    
    private void removePlayerFromIndexes(Repository repository, Player player) {
    	boolean dirty = playersNameIndex.remove(player);
    	dirty |= playersValueIndex.remove(player);
    	if(dirty) repository.update(this);
    }
}
